package ra.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ra.service.impl.WishListService;
@RequestMapping("/api.myservice.com/v1")
@Controller
public class WishListController {
    @Autowired
    /**
     *  ROLE_USER    -    POST    -    Thêm mới 1 sản phẩm vào danh sách yêu thích (payload: productId) -    Bắt buộc
     *  /api.myservice.com/v1/user/wish-list
     */
    private WishListService wishListService;
    @PostMapping("/user/add/wish-list")
    public ResponseEntity<?> addWishList(@RequestParam Long productId) throws Exception
    {
     return ResponseEntity.ok(wishListService.AddOrDeleteToWishList(productId));
    }

    /**
     - 4271    ROLE_USER    -    GET     -    Lấy ra danh sách yêu thích
     -    Bắt buộc    /api.myservice.com/v1/user/wish-list
     */
    @GetMapping("user/get/wish-list")
    public ResponseEntity<?> getWishList()  {
        return ResponseEntity.ok(wishListService.getWishLists());
    }



}
