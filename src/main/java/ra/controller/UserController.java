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
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody FormRegister user) {
        return ResponseEntity.ok(userService.registerOrUpdate(user,null));
    }
    @GetMapping("/getUser")
    public ResponseEntity<User> getUser(@RequestParam Integer Id) throws Exception {
        return new ResponseEntity<>(userService.getUserById(Id),HttpStatus.OK);
    }
    @PostMapping("/Login")
    public ResponseEntity<?> login(@Valid @RequestBody FormLogin user) {
        return ResponseEntity.ok(ResponseWrapper.builder()
                        .eHttpStatus(EHttpStatus.SUCCESS)
                        .statusCode(200)
                        .message("đăng nhập thành công")
                        .data(userService.login(user))
                .build());
    }
    @GetMapping("/admin/user/search")
    public ResponseEntity<?> getUserByName(@RequestParam String name) throws Exception
    {
        return ResponseEntity.ok(userService.getUserByName(name));
    }
    @GetMapping("/admin/getRoleList")
    public ResponseEntity<?> getRoleList()  {
        return ResponseEntity.ok(roleRepository.findAll());
    }
    @PutMapping("/admin/blockUser")
    public ResponseEntity<?> blockUser(@RequestParam Integer id) throws Exception {
       return ResponseEntity.ok(userService.blockUser(id));
    }
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
    @PostMapping("/user/account/add/addresses")
    public ResponseEntity<?> getAddresses(@RequestBody AddressRegister addressRegister) {
        return ResponseEntity.ok(addressService.add(addressRegister));
    }

    @GetMapping("user/account/get/addresses")
    public ResponseEntity<?> getAddresses(@RequestParam Long address) throws Exception {
      return ResponseEntity.ok( addressService.findById(address));
    }
    @GetMapping("/user/account/list/addresses" )
    public ResponseEntity<?> getAddressList() throws Exception {
        return ResponseEntity.ok(addressService.findByUser());
    }
    @PutMapping("/user/account/delete/addresses")
    public ResponseEntity<?> deleteAddress(@RequestParam Long addressId) throws Exception {
        return ResponseEntity.ok(addressService.deleteById(addressId));
    }
    @PutMapping("/user/account/change-password")
    public ResponseEntity<?> changePassword(@RequestParam String oldPassword,@RequestParam String newPassword,@RequestParam String confirmPass) throws Exception {
       return ResponseEntity.ok(userService.changePass(oldPassword,newPassword,confirmPass));
    }
    @PutMapping("/user/account/update")
    public ResponseEntity<?> updateUser(@RequestParam Long id,@RequestBody FormRegister formRegister) {
       return ResponseEntity.ok(userService.registerOrUpdate(formRegister,id));
    }
    @GetMapping("user/myAccount")
    public ResponseEntity<?> getMyAccount() {
       return ResponseEntity.ok(userService.myAcc());
    }
    @GetMapping("/user/cart/list")
    public ResponseEntity<?> getCartList() throws Exception {
        return ResponseEntity.ok(shoppingCartService.getAllShoppingCart());
    }
    @DeleteMapping("/user/cart/clear")
    public ResponseEntity<?> clearCart() throws Exception {
        return ResponseEntity.ok(shoppingCartService.deleteShoppingCart());
    }
    @DeleteMapping("/user/cart/items/deleteOne")
    public ResponseEntity<?> deleteOneItem(@RequestParam Long id) throws Exception {
        return ResponseEntity.ok(shoppingCartService.deleteShoppingCartById(id));
    }

}
