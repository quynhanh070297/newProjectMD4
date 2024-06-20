package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ra.service.impl.OrderService;
import ra.service.impl.ShoppingCartService;

@RequestMapping("/api.myservice.com/v1")
@RestController
public class OrderController
{
    @Autowired
    private OrderService orderService;
    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     ROLE_USER    -    PUT     -    Hủy đơn hàng đang trong trạng thái chờ xác nhận
     -    Bắt buộc    /api.myservice.com/v1/user/history/{orderId}/cancel
     */
    @PutMapping("/history/cancel/{orderId}")
    public ResponseEntity<?> cancelOrder(@PathVariable Long orderId) throws Exception
    {
        return ResponseEntity.ok(orderService.CancelOrder(orderId));
    }

    /**
     * 4268    ROLE_USER    -    GET     -    Lấy ra danh sách lịch sử đơn hàng theo trạng thái đơn hàng Bắt buộc
     * /api.myservice.com/v1/user/history/{orderStatus}
     */
    @GetMapping("/user/history/order/{orderStatus}")
    public ResponseEntity<?> getOrder( @PathVariable String orderStatus) throws Exception
    {
        return ResponseEntity.ok(orderService.OrdersByStatusOder(orderStatus));
    }

    /**
     * - [x] 4266    ROLE_USER    -    GET     -    Lấy ra danh sách lịch sử mua hàng
     * -    Bắt buộc    /api.myservice.com/v1/user/history
     */
    @GetMapping("/user/history/order")
    public ResponseEntity<?> getOrders()
    {
        return ResponseEntity.ok(orderService.orders());

    }

    /**
     * - [ ] 4267    ROLE_USER    -    GET     -    Lấy ra chi tiết đơn hàng theo số serial
     * -    Bắt buộc      /api.myservice.com/v1/user/history/{serialNumber}
     */
    @GetMapping("/user/history/serialNumber/{serialNumber}")
    public ResponseEntity<?> getSerialNumber(@PathVariable String serialNumber)
    {
        return ResponseEntity.ok(orderService.orderRequestForUser(serialNumber));
    }

    @PutMapping("/user/cart/items/quantity/change")
    public ResponseEntity<?> addItem(@RequestParam Long productId, @RequestParam Integer quantity) throws Exception
    {
        return ResponseEntity.ok(shoppingCartService.addToCart(productId, quantity));
    }

    /**
     - [x] 4292    ROLE_ADMIN   -    PUT     -    Cập nhật trạng thái đơn hàng (payload: orderStatus)
     -    Bắt buộc    /api.myservice.com/v1/admin/orders/{orderId}/status
     */

    // Task ROLE_ADMIN - PUT - Cập nhật trạng thái đơn hàng
    @PutMapping("/admin/orders/status/{orderId}")
    public ResponseEntity<?> changeStatus(@RequestParam String status, @PathVariable Long orderId) throws Exception
    {
        return ResponseEntity.ok(orderService.changeStatusOder(orderId, status));
    }

    /**
     - [x] 4290    ROLE_ADMIN   -    GET     -    Danh sách đơn hàng theo trạng thái
     -    Bắt buộc    /api.myservice.com/v1/admin/orders/{orderStatus}
     */
    @GetMapping("/admin/ordersByStatus")
    public ResponseEntity<?> getOrdersByStatus(@RequestParam String status) throws Exception
    {
        return ResponseEntity.ok(orderService.OrdersByStatusOderAdmin(status));
    }

    /**
     - [x] 4291    ROLE_ADMIN   -    GET     -    Chi tiết đơn hàng
     -    Bắt buộc    /api.myservice.com/v1/admin/orders/{orderId}
     */
    @GetMapping("/admin/orders/detail")
    public ResponseEntity<?> getOrderDetail(@RequestParam Long orderID) throws Exception
    {
        return ResponseEntity.ok(orderService.orderRequest(orderID));
    }

    /**
     * - [x] 4289    ROLE_ADMIN   -    GET     -    Danh sách tất cả đơn hàng
     * -    Bắt buộc    /api.myservice.com/v1/admin/orders
     */
    @GetMapping("/admin/ListOrders")
    public ResponseEntity<?> getListOrder(){
      return ResponseEntity.ok(orderService.fillAllOrder());
    }


}
