package ra.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ra.model.entity.Order;
import ra.model.entity.OrderDetail;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long> {
    List<OrderDetail> findAllByOrder(Order order);


}
