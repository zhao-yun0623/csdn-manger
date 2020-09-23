package com.tju.csdnmanger.service;


import com.tju.csdnmanger.javaBean.AdminBean;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * JwtService类
 * @author 赵云
 * @date 2020/09/10
 */

@Service
public class JwtService {
    private final static SignatureAlgorithm SIGNATURE_ALGORITHM= SignatureAlgorithm.HS256;
    //token存活时间
    private final static Long ACCESS_TOKEN_EXPIRATION = 3600L*1000;

    //refreshToken存活时间
    private final static Long REFRESH_TOKEN_EXPIRATION = 10*24*3600L*1000;

    //jwt的签发者
    private final static String JWT_ISS = "赵云";

    //jwt的所有人
    private final static String SUBJECT = "赵云";

    private final static String secret="faewghrwaoeifqhyr32855";

    /**
     * 获取token
     * @param user:user信息，map类型
     * @return token
     */
    public String getToken(AdminBean admin){
        Map<String,Object> claims=new HashMap<>();
        claims.put("admin",admin);
        Map<String,Object> header=new HashMap<>();
        header.put("alg","HS256");
        header.put("typ","JWT");
        return Jwts.builder().setIssuer(JWT_ISS)
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis()+ACCESS_TOKEN_EXPIRATION))
                .setHeader(header)
                .setSubject(SUBJECT)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date())
                .signWith(SIGNATURE_ALGORITHM,secret)
                .compact();
    }

    /**
     * 获取refreshToken
     * @param admin:user信息，map类型
     * @return refreshToken
     */
    public String getRefreshToken(AdminBean admin){
        Map<String,Object> claims=new HashMap<>();
        claims.put("admin",admin);
        Map<String,Object> header=new HashMap<>();
        header.put("alg","HS256");
        header.put("typ","JWT");
        return Jwts.builder().setIssuer(JWT_ISS)
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis()+REFRESH_TOKEN_EXPIRATION))
                .setHeader(header)
                .setSubject(SUBJECT)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date())
                .signWith(SIGNATURE_ALGORITHM,secret)
                .compact();
    }

    /**
     * 获取Claims
     * @param token:token
     * @return claims
     */
    public Claims getClaims(String token) throws ExpiredJwtException,Exception{
        Claims claims=null;
        claims= Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return claims;
    }

    /*public UserDetails getUserDetails(String token) throws ExpiredJwtException{
        Claims claims=Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        int id=claims.get("id",Integer.class);
        String name=claims.get("name",String.class);
        String phone=claims.get("phone",String.class);

    }*/

    /**
     * 获取User对象
     * @param token：token字符串
     * @return UserBean
     * @throws ExpiredJwtException:token过期
     */
    public AdminBean getUser(String token) throws ExpiredJwtException,Exception{
        Claims claims=Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        Map<String,Object> userMap= (Map<String, Object>) claims.get("admin");
        int id= (int) userMap.get("id");
        String phone=(String)userMap.get("phone");
        return new AdminBean(id,phone);
    }

    /**
     * 获取User对象，并存在request中
     * @param token：token字符串
     * @param request：HttpServletRequest
     * @return UserBean
     * @throws ExpiredJwtException:token已过期
     */
    public AdminBean getUser(String token, HttpServletRequest request) throws ExpiredJwtException,Exception{
        Claims claims=Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        Map<String,Object> adminMap= (Map<String, Object>) claims.get("admin");
        System.out.println(adminMap);
        int id= (int) adminMap.get("id");
        String phone=(String)adminMap.get("phone");
        AdminBean admin=new AdminBean(id,phone);
        request.setAttribute("admin",admin);
        System.out.println(admin);
        return admin;
    }
}
