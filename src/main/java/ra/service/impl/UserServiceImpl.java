package ra.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ra.model.dto.request.FormLogin;
import ra.model.dto.request.FormRegister;
import ra.model.dto.response.JWTResponse;
import ra.model.entity.Role;
import ra.model.entity.RoleName;
import ra.model.entity.User;
import ra.repository.RoleRepository;
import ra.repository.UserRepository;
import ra.security.JWT.JWTProvider;
import ra.security.principals.CustomUserDetail;
import ra.service.UserService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
public class UserServiceImpl implements UserService
{

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTProvider jwtProvider;

    @Override
    public boolean registerOrUpdate(FormRegister formRegister, Long id) {
        // Chuyển đổi FormRegister thành User để lưu vào cơ sở dữ liệu
        User user = User.builder()
                .username(formRegister.getUsername())
                .password(passwordEncoder.encode(formRegister.getPassword()))
                .fullName(formRegister.getFullName())
                .address(formRegister.getAddress())
                .email(formRegister.getEmail())
                .phone(formRegister.getPhone())
                .createdAt(new Date())
                .isDeleted(false) // Giả định rằng người dùng mới không bị xóa
                .updatedAt(new Date())
                .status(true)
                .build();

        if (id != null) {
            user.setId(id);
        }

        // Thêm vai trò cho người dùng

        List<Role> roles = new ArrayList<>();
        if (formRegister.getRoles() != null && !formRegister.getRoles().isEmpty()) {
            for (String role : formRegister.getRoles()) {
                roles.add(roleRepository.findRoleByRoleName(role)
                        .orElseThrow(() -> new NoSuchElementException("Không tồn tại vai trò: " + role)));
            }
        } else {
            roles.add(roleRepository.findRoleByRoleName("ROLE_USER")
                    .orElseThrow(() -> new NoSuchElementException("Không tồn tại vai trò ROLE_USER")));
        }
        user.setRoles(roles);
        userRepository.save(user);
        return true;
    }

    @Override
    public JWTResponse login(FormLogin formLogin) {
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(formLogin.getUsername(), formLogin.getPassword()));
        } catch (AuthenticationException e) {
            log.error("Sai tên đăng nhập hoặc mật khẩu", e);
            throw new RuntimeException("Sai tên đăng nhập hoặc mật khẩu");
        }

        CustomUserDetail userDetail = (CustomUserDetail) authentication.getPrincipal();
        // Tạo token từ userDetail
        String token = jwtProvider.createToken(userDetail);

        return JWTResponse.builder()
                .fullName(userDetail.getFullName())
                .address(userDetail.getAddress())
                .email(userDetail.getEmail())
                .phone(userDetail.getPhone())
                .status(userDetail.getStatus())
                .authorities(userDetail.getAuthorities())
                .token(token)
                .build();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Integer id) throws Exception {
        return userRepository.findById(id)
                .orElseThrow(() -> new Exception("Không tìm thấy người dùng với ID: " + id));
    }

    @Override
    public List<User> getUserByName(String name) throws Exception {
        List<User> users = userRepository.findUserByFullNameContains(name);
        if (users.isEmpty()) {
            throw new Exception("Không tồn tại người dùng với tên: " + name);
        }
        return users;
    }

    @Override
    public User blockUser(Integer id) throws Exception {
        User user = getUserById(id);
        user.setStatus(!user.getStatus());
        return userRepository.save(user);
    }

    @Override
    public Page<User> getUsers(int page) {
        int size = 3;
        Sort sort = Sort.by(Sort.Direction.ASC, "createdAt");
        Pageable pageable = PageRequest.of(page, size, sort);
        return userRepository.findAll(pageable);
    }

    @Override
    public User changePass(String oldPass, String newPass, String confirmPass) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!passwordEncoder.matches(oldPass, user.getPassword())) {
            throw new RuntimeException("Mật khẩu hiện tại không chính xác");
        }
        if (!newPass.equals(confirmPass)) {
            throw new RuntimeException("Mật khẩu mới và mật khẩu xác nhận không khớp");
        }
        user.setPassword(passwordEncoder.encode(newPass));
        return userRepository.save(user);
    }

    @Override
    public User getUserByUserName(String username) {
        return userRepository.findUserByUsername(username).orElse(null);
    }

    @Override
    public Boolean addRoleForUser(String role, Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));
        Role roleEntity = roleRepository.findRoleByRoleName(role)
                .orElseThrow(() -> new NoSuchElementException("Không tồn tại vai trò: " + role));
        user.getRoles().add(roleEntity);
        userRepository.save(user);
        return true;
    }

    @Override
    public Boolean deleteRole(Integer userId, Integer roleId) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("Người dùng không tồn tại"));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new Exception("Role không tồn tại"));
        if (user.getRoles().stream().anyMatch(r -> r.getRoleName().equals(RoleName.ROLE_ADMIN.name()))) {
            throw new RuntimeException("Bạn không có quyền xóa vai trò ADMIN");
        }
        boolean removed = user.getRoles().remove(role);
        userRepository.save(user);
        return removed;
    }

    @Override
    public CustomUserDetail myAcc() {
        return (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
