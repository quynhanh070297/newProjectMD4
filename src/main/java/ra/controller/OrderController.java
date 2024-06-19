package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ra.service.impl.OrderService;
import ra.service.impl.ShoppingCartService;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ShoppingCartService shoppingCartService;

    @PutMapping("/history/cancel")
    public ResponseEntity<?> cancelOrder(@RequestParam Long id) throws Exception
    {
      return ResponseEntity.ok(orderService.CancelOrder(id));
    }
    @GetMapping("/user/history/order/status")
    public ResponseEntity<?> getOrder(String status) throws Exception {
        return ResponseEntity.ok(orderService.OrdersByStatusOder(status));
    }
    @GetMapping("/user/history/order")
    public ResponseEntity<?> getOrders() throws Exception {
        return ResponseEntity.ok(orderService.orders());

    }
    @GetMapping("/user/history/serialNumber")
    public ResponseEntity<?> getSerialNumber(String serialNumber) throws Exception {
        return ResponseEntity.ok(orderService.orderRequestForUser(serialNumber));
    }
    @PutMapping("/user/cart/items/quantity/change")
    public ResponseEntity<?> addItem(@RequestParam Long productId, @RequestParam Integer quantity) throws Exception {
        return ResponseEntity.ok(shoppingCartService.addToCart(productId, quantity));
    }

    @PutMapping("/admin/orders/status/change")
    public ResponseEntity<?> changeStatus(@RequestParam String status,@RequestParam Long oderID) throws Exception {
        return ResponseEntity.ok(orderService.changeStatusOder(oderID,status));
    }

    @GetMapping("/admin/ordersByStatus")
    public ResponseEntity<?> getOrdersByStatus(@RequestParam String status) throws Exception {
        return ResponseEntity.ok(orderService.OrdersByStatusOderAdmin(status));
    }
    @GetMapping("/admin/orders/detail")
    public ResponseEntity<?> getOrderDetail(@RequestParam Long orderID) throws Exception {
        return ResponseEntity.ok(orderService.orderRequest(orderID));
    }


}
