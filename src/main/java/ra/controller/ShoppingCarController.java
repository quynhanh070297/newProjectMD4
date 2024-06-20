package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ra.service.impl.OrderService;
import ra.service.impl.ShoppingCartService;

@RestController
@RequestMapping("/api.myservice.com/v1")
public class ShoppingCarController {
    @Autowired
    OrderService orderService;
    @Autowired
    ShoppingCartService shoppingCartService;

    /**
     *
     -  4258    ROLE_USER    -    POST    -    Đặt hàng
     -    Bắt buộc    /api.myservice.com/v1/user/cart/checkout
     */
    @PostMapping("/user/cart/checkout")
    public ResponseEntity<?> checkout(@RequestParam Long addressId, @RequestParam String note){
        return ResponseEntity.ok(orderService.checkOut(addressId, note));
    }

    /**
     ROLE_USER    -    POST    -    Thêm mới sản phẩm vào giỏ hàng (payload: productId and quantity) -    Bắt buộc
     */
    @PostMapping("/user/cart/add")
    public ResponseEntity<?> addToCart(@RequestParam Long productId, @RequestParam Integer quantity) throws Exception
    {
        return ResponseEntity.ok(shoppingCartService.addToCart(productId,quantity));
    }

    /**
     * ROLE_USER    -    PUT     -    Thay đổi số lượng đặt hàng của 1 sản phẩm (payload: quantity)
     * -    Bắt buộc    /api.myservice.com/v1/user/cart/items/{cartItemId}
     */
    @PutMapping("/user/cart/items/{productId}/{quantity}")
    public ResponseEntity<?> changeQuantityInCart(@PathVariable Long productId, @PathVariable Integer quantity) throws Exception
    {
        return new ResponseEntity<>(shoppingCartService.addToCart(productId,quantity), HttpStatus.OK);
    }
}
