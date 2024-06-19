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
public class FormLogin {
    @NotBlank(message = "Không được để trống UserName ")
    private String username;
    @NotBlank(message = "Không được để trống Password")
    private String password;
}
