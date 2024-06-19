package ra.model.dto.request;


import lombok.*;
import ra.model.entity.Order;
import ra.model.entity.OrderDetail;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderRequest {
    Order order;
    List<OrderDetail> orderDetails;
}
