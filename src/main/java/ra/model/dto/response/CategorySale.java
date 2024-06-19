package ra.model.dto.response;


import lombok.*;
import ra.model.entity.Category;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CategorySale {
    Category category;
    Double totalMoney;
}
