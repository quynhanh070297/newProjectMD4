package ra.model.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.model.entity.User;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TopUserResponse {
    private User user;
    private Double byMoney;
}
