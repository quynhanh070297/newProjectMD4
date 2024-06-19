package ra.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ra.model.entity.Order;
import ra.model.entity.User;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByStatusAndUser(Order.OrderStatus status, User user);

    Order findBySerialNumber(String serialNumber);

    List<Order> findAllByUser(User user);

    List<Order> findAllByStatus(Order.OrderStatus status);

}
