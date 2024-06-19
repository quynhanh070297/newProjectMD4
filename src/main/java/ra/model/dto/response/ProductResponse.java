package ra.model.dto.response;

import lombok.*;
import ra.model.entity.Product;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductResponse {
    private Product product;
    private Long SaleQuantity;
}
