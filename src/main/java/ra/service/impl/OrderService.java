package ra.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ra.model.dto.request.OrderRequest;
import ra.model.entity.Order;
import ra.model.entity.OrderDetail;
import ra.model.entity.ShoppingCart;
import ra.model.entity.User;
import ra.repository.AddressRepository;
import ra.repository.OrderDetailRepository;
import ra.repository.OrderRepository;
import ra.repository.ShoppingCartRepository;
import ra.security.principals.CustomUserDetail;
import ra.service.UserService;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;

    public Boolean changeStatusOder(Long orderId, String statusString) throws Exception
    {
        try {
            Order order = orderRepository.findById(orderId).orElseThrow(() -> new Exception("Order not found"));
            Order.OrderStatus status = Order.OrderStatus.valueOf(statusString);
            order.setStatus(status);
            return true;
        } catch (Exception e) {
            throw new Exception("Status is not exits");
        }
    }

    public OrderRequest orderRequest(Long id) throws Exception {
        Order order = orderRepository.findById(id).orElseThrow(() -> new Exception("Order is not exits"));
        return OrderRequest.builder()
                .order(order)
                .orderDetails(orderDetailRepository.findAllByOrder(order))
                .build();
    }

    public List<Order> OrdersByStatusOder(String statusString) throws Exception {
        try {
            Order.OrderStatus status = Order.OrderStatus.valueOf(statusString);
            CustomUserDetail userDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userService.getUserByUserName(userDetails.getUsername());
            return orderRepository.findAllByStatusAndUser(status,user);
        } catch (Exception e) {
            throw new Exception("Order Status is not exits");
        }
    }
    public List<Order> OrdersByStatusOderAdmin(String statusString) throws Exception {
        try {
            return orderRepository.findAllByStatus(Order.OrderStatus.valueOf(statusString));
        } catch (Exception e) {
            throw new Exception("Status is not exits");
        }
    }

    public List<Order> orders() {
        CustomUserDetail userDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getUserByUserName(userDetails.getUsername());
        return orderRepository.findAllByUser(user);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean checkOut(Long addressId, String note) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 2);
        Date twoDaysFromNow = calendar.getTime();
        CustomUserDetail userDetail = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getUserByUserName(userDetail.getUsername());
        List<ShoppingCart> shoppingCarts = shoppingCartRepository.findAllByUser(user);

        Order order = Order.builder()
                .createdAt(new Date())
                .note(note)
                .user(user)
                .receiveAddress(addressRepository.findById(addressId).orElseThrow(() -> new RuntimeException("Address is not exits")).getFullAddress())
                .receiveName(addressRepository.findById(addressId).orElseThrow(() -> new RuntimeException("Name is not exits")).getReceiveName())
                .receivePhone(addressRepository.findById(addressId).orElseThrow(() -> new RuntimeException("NumberPhone is not exits")).getPhone())
                .serialNumber(UUID.randomUUID().toString())
                .receivedAt(twoDaysFromNow)
                .status(Order.OrderStatus.WAITING)
                .build();
        order.setTotalPrice(shoppingCarts.stream().mapToDouble(o -> o.getProduct().getUnitPrice() * o.getOrderQuantity()).sum());
        Order newOrder = orderRepository.save(order);

        List<OrderDetail> orderDetails = shoppingCarts.stream().map(s -> OrderDetail.builder()
                .name(s.getProduct().getProductName())
                .orderQuantity(s.getOrderQuantity())
                .product(s.getProduct())
                .unitPrice(s.getProduct().getUnitPrice())
                .order(newOrder)
                .build()).toList();
        shoppingCarts.forEach(o -> {
            try {
                productService.productQuantityChange(o.getProduct().getProductId(), o.getOrderQuantity());
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }

        });

        orderDetailRepository.saveAll(orderDetails);
        shoppingCartRepository.deleteAll(shoppingCarts);
        return true;
    }
    public Boolean CancelOrder(Long orderId) throws Exception {
        Order order = orderRepository.findById(orderId).orElseThrow(()->new Exception("Oder is not exits"));
        if (order.getStatus().equals(Order.OrderStatus.WAITING)) {
            order.setStatus(Order.OrderStatus.CANCEL);
            orderRepository.save(order);
            return true;
        }else {
            throw new RuntimeException("Can't cancel order");
        }
    }
    public List<Order> UserOfOderByStatus(String statusString) throws Exception {
        CustomUserDetail userDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getUserByUserName(userDetails.getUsername());
        return OrdersByStatusOder(statusString).stream().filter(order -> order.getUser().equals(user)).toList();
    }
    public OrderRequest orderRequestForUser(String serialNumber)  {
        Order order = orderRepository.findBySerialNumber(serialNumber);
        return OrderRequest.builder()
                .order(order)
                .orderDetails(orderDetailRepository.findAllByOrder(order))
                .build();
    }


}

