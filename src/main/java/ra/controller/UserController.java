package ra.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.constaint.EHttpStatus;

import ra.model.dto.request.AddressRegister;
import ra.model.dto.request.FormLogin;
import ra.model.dto.request.FormRegister;
import ra.model.dto.response.ResponseWrapper;
import ra.model.entity.User;
import ra.repository.RoleRepository;
import ra.security.principals.CustomUserDetailService;
import ra.service.UserService;
import ra.service.impl.AddressService;
import ra.service.impl.ShoppingCartService;

import java.util.List;

@RestController
@RequestMapping("/api.myservice.com/v1")
@CrossOrigin("*")
public class UserController {
    @Autowired
    AddressService addressService;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping("/userList")
    public ResponseEntity<List<User>> getUser() {
        return ResponseEntity.ok(userService.getAllUsers());
    }



    /**
     *  @apiNote 4243 PermitAll    -    POST    -    Đăng kí tài khoản người dùng
     */

    @PostMapping("/auth/sign-up")
    public ResponseEntity<?> register(@Valid @RequestBody FormRegister user) {
        return ResponseEntity.ok(userService.registerOrUpdate(user,false));
    }


    /**
     * @apiNote 4244    PermitAll    -    POST    -    Đăng nhập tài khoản bằng username và password
     * */

    @PostMapping("/auth/sign-in")
    public ResponseEntity<?> login(@Valid @RequestBody FormLogin user) {
        return ResponseEntity.ok(ResponseWrapper.builder()
                .eHttpStatus(EHttpStatus.SUCCESS)
                .statusCode(200)
                .message("Đăng nhập thành công")
                .data(userService.login(user))
                .build());
    }


    @GetMapping("/getUser")
    public ResponseEntity<User> getUser(@RequestParam Integer Id) throws Exception {
        return new ResponseEntity<>(userService.getUserById(Id),HttpStatus.OK);
    }


    /**
     - 4278    ROLE_ADMIN   -    GET     -    Tìm kiếm người dùng theo tên
     -    Bắt buộc    /api.myservice.com/v1/admin/users/search
     */
    @GetMapping("/admin/users/search")
    public ResponseEntity<?> getUserByName(@RequestParam String name) throws Exception
    {
        return ResponseEntity.ok(userService.getUserByName(name));
    }

    /**
     ROLE_ADMIN   -    GET     -    Lấy về danh sách quyền
     -    Bắt buộc    /api.myservice.com/v1/admin/roles
     */
    @GetMapping("/admin/getRoleList")
    public ResponseEntity<?> getRoleList()  {
        return ResponseEntity.ok(roleRepository.findAll());
    }

    /**
     ROLE_ADMIN   -    PUT     -    Khóa / Mở khóa người dùng
     -    Bắt buộc    /api.myservice.com/v1/admin/users/{userId}
     */
    @PutMapping("/admin/blockUsers/{userId}")
    public ResponseEntity<?> blockUser(@PathVariable Integer userId) throws Exception {
       return ResponseEntity.ok(userService.blockUser(userId));
    }

    /**
     - [ ] 4273    ROLE_ADMIN   -    GET     -    Lấy ra danh sách người dùng (phân trang và sắp xếp)
     -    Bắt buộc    /api.myservice.com/v1/admin/users
     */
    @GetMapping("/admin/getListUser")
    public ResponseEntity<?> getListUser(@RequestParam Integer page) {
        return ResponseEntity.ok(userService.getUsers(page).getContent());
    }
    @PostMapping("users/add/role")
    public ResponseEntity<?> addRole(@RequestParam String roleName, @RequestParam Integer userId) {
      return ResponseEntity.ok(  userService.addRoleForUser(roleName,userId));
    }
    @PutMapping("/admin/users/deleteRole")
    public ResponseEntity<?> deleteRole(@RequestParam Integer userId,Integer roleId) throws Exception {
        return ResponseEntity.ok(userService.deleteRole(userId,roleId));
    }

    /**
     ROLE_USER    -    POST    -    Thêm mới địa chỉ
     -    Bắt buộc                  /api.myservice.com/v1/user/account/addresses
     */
    @PostMapping("/user/account/add/addresses")
    public ResponseEntity<?> getAddresses(@RequestBody AddressRegister addressRegister) {
        return ResponseEntity.ok(addressService.add(addressRegister));
    }

    /**
     ROLE_USER    -    GET     -    Lấy địa chỉ người dùng theo addressId
     -    Bắt buộc    /api.myservice.com/v1/user/account/addresses/{addressId}
     */
    @GetMapping("user/account/addresses/getAdById/{addressId}")
    public ResponseEntity<?> getAddresses(@PathVariable Long addressId) throws Exception {
      return ResponseEntity.ok( addressService.findById(addressId));
    }

    /**
     ROLE_USER    -    GET     -    Lấy ra danh sách địa chỉ của người dùng
     -    Bắt buộc       /api.myservice.com/v1/user/account/addresses
     */
    @GetMapping("/user/account/addresses/list" )
    public ResponseEntity<?> getAddressList() throws Exception {
        return ResponseEntity.ok(addressService.findByUser());
    }

    /**
     ROLE_USER    -    DELETE  -    Xóa 1 địa chỉ theo mã địa chỉ
     -    Bắt buộc        /api.myservice.com/v1/user/account/addresses/{addressId}
     */
    @PutMapping("/user/account/addresses/{addressId}")
    public ResponseEntity<?> deleteAddress(@PathVariable Long addressId)  {
        return ResponseEntity.ok(addressService.deleteById(addressId));
    }

    /**
     * ROLE_USER    -    PUT     -    Thay đổi mật khẩu (payload: oldPass, newPass, confirmNewPass)
     * -    Bắt buộc    /api.myservice.com/v1/user/account/change-password
     * @param oldPass
     * @param newPass
     * @param confirmNewPass
     * @return
     * @throws Exception
     */
    @PutMapping("/user/account/change-password")
    public ResponseEntity<?> changePassword(@RequestParam String oldPass,@RequestParam String newPass,@RequestParam String confirmNewPass) throws Exception {
       return ResponseEntity.ok(userService.changePass(oldPass,newPass,confirmNewPass));
    }

    /**
     4260    ROLE_USER    -    PUT     -    Cập nhật thông tin người dùng
     */
    @PutMapping("/user/myAccount/update")
    public ResponseEntity<?> updateUser(@RequestBody FormRegister formRegister) {
       return ResponseEntity.ok(userService.registerOrUpdate(formRegister,true));
    }

    /**
     - [x] 4259    ROLE_USER    -    GET     -    Thông tin tài khoản người dùng
     -    Bắt buộc            /api.myservice.com/v1/user/account
     */
    @GetMapping("user/myAccount")
    public ResponseEntity<?> getMyAccount() {
       return ResponseEntity.ok(userService.myAcc());
    }

    /** *
     *ROLE_USER    -    GET     -    Danh sách sản phẩm trong giỏ hàng
     */
    @GetMapping("/user/cart/list")
    public ResponseEntity<?> getCartList() throws Exception {
        return ResponseEntity.ok(shoppingCartService.getAllShoppingCart());
    }

    /**
     - 4257    ROLE_USER    -    DELETE  -    Xóa toàn bộ sản phẩm trong giỏ hàng
     -    Bắt buộc    /api.myservice.com/v1/user/cart/clear
     */
    @DeleteMapping("/user/cart/clear")
    public ResponseEntity<?> clearCart() throws Exception {
        return ResponseEntity.ok(shoppingCartService.deleteShoppingCart());
    }

    /**
     *
     ROLE_USER    -    DELETE  -    Xóa 1 sản phẩm trong giỏ hàng
     -    Bắt buộc         /api.myservice.com/v1/user/cart/items/{cartItemId}
     */

    @DeleteMapping("/user/cart/items/{cartItemId}")
    public ResponseEntity<?> deleteOneItem(@PathVariable Long cartItemId) throws Exception {
        return ResponseEntity.ok(shoppingCartService.deleteShoppingCartById(cartItemId));
    }

}
