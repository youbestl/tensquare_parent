package util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Date;

/**
 * @author LiangDong.
 */

@ConfigurationProperties("jwt.config")
public class JwtUtil {

    @Setter
    @Getter
    private String key;

    @Setter
    @Getter
    private long ttl;//一个小时

    /**
     * 生成JWT
     * @param id
     * @param subject
     * @param roles
     * @return
     */
    public String createJWT(String id, String subject, String roles) {
        long now = System.currentTimeMillis();
        JwtBuilder builder = Jwts.builder().setId(id)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, key)
                .claim("roles", roles);
        if (ttl>0) {
            builder.setExpiration(new Date(now + ttl));
        }
        return builder.compact();
    }

    /**
     * 解析token
     * @param jwtStr
     * @return
     */
    public Claims parseJWT(String jwtStr) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwtStr)
                .getBody();
    }

}
