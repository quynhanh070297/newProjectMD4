package ra.security.JWT;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JWTProvider {
    @Value("${jwt_expiration}")
    private int expiration;
    @Value("${jwt_secret}")
    private String secret;

    public String createToken(UserDetails userDetails){
        Date today = new Date();
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(today)
                .setExpiration(new Date(today.getTime()+expiration))
                .signWith(SignatureAlgorithm.HS384,secret)
                .compact();
    }

    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        }catch (ExpiredJwtException e){
              log.error("Hết hạn");
        }catch (UnsupportedJwtException e){
            log.error("Server Không hỗ trợ bảo mật với JWT");
        }catch (MalformedJwtException e){
            log.error("Token Không đúng định dạng");
        }catch (SignatureException e){
            log.error("Chữ ký Token không hợp lệ ");
        }catch (IllegalArgumentException e){
            log.error("Token Rỗng hoặc không hợp lệ");
        }
        return false;
    }

    public String getUsernameFromToken(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }
}
