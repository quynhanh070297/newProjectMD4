package ra.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// Định nghĩa lớp JWTAuthenticationEntryPoint implement từ AuthenticationEntryPoint
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {

    // Ghi đè phương thức commence để xử lý khi có lỗi xác thực
    @Override
    // Đặt trạng thái HTTP của response là 401 (Unauthorized)
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(401);
        // Đặt header của response với trạng thái HTTP FORBIDDEN và thông báo "Authentication"
        response.setHeader(HttpStatus.FORBIDDEN.toString(), "Authentication");
        // Tạo một map để chứa thông tin lỗi
        Map<String,String> map = new HashMap<>();
        map.put("Error", "Authentication");
        // Chuyển đổi map thành JSON và ghi vào output stream của response
        new ObjectMapper().writeValue(response.getOutputStream(),map);
    }
}

