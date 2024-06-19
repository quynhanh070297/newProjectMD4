package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ra.service.impl.OrderService;
import ra.service.impl.ShoppingCartService;

@RestController
@RequestMapping("/api.myservice.com/v1")
public class ShoppingCarController {
    @Autowired
    OrderService orderService;
    @Autowired
    ShoppingCartService shoppingCartService;
    @PostMapping("/user/cart/checkout")
    public ResponseEntity<?> checkout(@RequestParam Long addressId, @RequestParam String note){
        return ResponseEntity.ok(orderService.checkOut(addressId, note));
    }
    @PostMapping("/user/cart/add")
    public ResponseEntity<?> addToCart(@RequestParam Long productId, @RequestParam Integer quantity) throws Exception
    {
        return ResponseEntity.ok(shoppingCartService.addToCart(productId,quantity));
    }
}
