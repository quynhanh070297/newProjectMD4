package ra.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ra.validate.UsernameEx;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FormRegister
{
    @UsernameEx(message = "Tài khoản đã tồn tại")
    @NotBlank(message = "Không Được để trống")
    @Size(min = 6, max = 100, message = "Sai dịnh dạng")
    private String username;
    @NotBlank(message = "Không được để trống email")
    @Email
    @Size(max = 255)
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Email không đúng định dạng")
    private String email;
    @NotBlank(message = "Không được để trống tên")
    @Size(max = 100)
    private String fullName;
    @NotBlank(message = "Không được để trống mật khẩu")
    @Size(max = 255)
    private String password;
    private String avatar;
    @Pattern(regexp = "^[0-9]{10,15}$", message = "Sai định dạng sdt")
    private String phone;
    private String address;
    private List<String> roles;

}
