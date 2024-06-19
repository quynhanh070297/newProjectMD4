package ra.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ra.service.impl.WishListService;

@Controller
public class WishListController {
    @Autowired
    private WishListService wishListService;
    @PostMapping("/user/add/wish-list")
    public ResponseEntity<?> addWishList(@RequestParam Long productId) throws Exception
    {
     return ResponseEntity.ok(wishListService.AddOrDeleteToWishList(productId));
    }
    @GetMapping("user/get/wish-list")
    public ResponseEntity<?> getWishList() throws Exception {
        return ResponseEntity.ok(wishListService.getWishLists());
    }



}
