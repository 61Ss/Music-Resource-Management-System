package com.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class JwtUtil {


    public static final long DEFAULT_EXPIRE = 1000L * 60 * 120;

    public static final String APP_SECRET = "ukc8BDbRigUDaY6pZFfWus2jZWLPHO";

    public static String getJwtToken(Object data) {
        return getJwtToken(data, DEFAULT_EXPIRE);
    }

    public static String getJwtToken(Object data, Long expire) {
        String JwtToken = Jwts.builder()
                
                .setHeaderParam("typ", "JWT")
             
                .setHeaderParam("alg", "HS256")
            
                .setSubject("manager-user")
       
                .setIssuedAt(new Date())
        
                .setExpiration(new Date(System.currentTimeMillis() + expire))
               
                .claim("data", data)
           
                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
             
                .compact();
        return JwtToken;
    }


    public static boolean checkToken(String jwtToken) {
        if (StringUtils.isEmpty(jwtToken)) {
            return false;
        }
        try {

            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);

            return claimsJws.getBody().getExpiration().after(new Date());
        } catch (Exception e) {
            return false;

        }
    }


    public static boolean checkToken(HttpServletRequest request) {
        return checkToken(request.getHeader("token"));
    }

    public static Claims getTokenBody(HttpServletRequest request) {
        return getTokenBody(request.getHeader("token"));
    }


    public static Claims getTokenBody(String jwtToken) {
        if (StringUtils.isEmpty(jwtToken)) {
            return null;
        }
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        return claimsJws.getBody();
    }
}
