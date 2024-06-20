package ra.service;

import org.springframework.data.domain.Page;

import ra.model.dto.request.FormLogin;
import ra.model.dto.request.FormRegister;
import ra.model.dto.response.JWTResponse;
import ra.model.entity.User;
import ra.security.principals.CustomUserDetail;

import java.util.List;

public interface UserService {
    boolean registerOrUpdate(FormRegister formRegister, Boolean isTheUpdate);
    JWTResponse login(FormLogin formLogin);
    List<User> getAllUsers();
    User getUserById(Integer id) throws Exception;
    List<User> getUserByName(String name) throws Exception;
    User blockUser(Integer id) throws Exception;
     Page<User> getUsers(int page);
     User changePass(String olePass,String newPass,String confirmPass);
     User getUserByUserName(String Username);
     Boolean addRoleForUser(String role,Integer id);
     Boolean deleteRole(Integer userId, Integer roleId) throws Exception;
    CustomUserDetail myAcc();
}
