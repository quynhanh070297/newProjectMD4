package ra.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddressRegister {
    @NotBlank(message = "Is not null")
    private String fullAddress;

    @NotBlank(message = "Is not null")
    private String phone;

    @NotBlank(message = "Is not null")
    private String receiveName;
}
