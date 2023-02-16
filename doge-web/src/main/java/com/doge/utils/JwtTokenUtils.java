package com.doge.utils;

import com.doge.entity.SysUserInfo;
import com.doge.security.handler.MyAuthenticationException;
import com.doge.security.AccessToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT Token工具类
 *
 * @author shixinyu
 * @date 2021-06-10 10:50
 */
public class JwtTokenUtils implements Serializable {

    private static Logger logger = LoggerFactory.getLogger(JwtTokenUtils.class);

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private static final String USERID = "USERID";
    /**
     * 用户名
     */
    private static String USERNAME = Claims.SUBJECT;
    /**
     * 创建时间
     */
    private static final String CREATED = "created";
    /**
     * 密钥
     */
    private static final String SECRET = "sdxk@evaluation";
    /**
     * 有效期2小时
     */
    private static final long EXPIRE_TIME = 2 * 60 * 60 * 1000;

    /**
     * 生成令牌
     *
     * @param authentication 认证信息
     * @return 令牌
     */
    public static AccessToken generateToken(Authentication authentication) {
        Map<String, Object> claims = new HashMap<>(2);
        SysUserInfo userInfo = SecurityUtils.getUserInfo(authentication);
        claims.put(USERNAME, userInfo.getUserName());
        claims.put(CREATED, new Date());
        claims.put("userid", userInfo.getId());
        claims.put("username", userInfo.getUserName());
        Date expirationDate = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        AccessToken accessToken = new AccessToken(generateToken(claims, expirationDate),expirationDate.getTime());
        return accessToken;
    }

    /**
     * 从数据声明生成令牌
     *
     * @param claims         数据声明
     * @param expirationDate 过期时间
     * @return 令牌
     */
    private static String generateToken(Map<String, Object> claims, Date expirationDate) {
        return Jwts.builder().setHeaderParam("type", "JWT").setClaims(claims).setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, SECRET).compact();
    }

    /**
     * 从令牌中获取用户信息
     *
     * @param token 令牌
     * @return 用户名
     */
    public static SysUserInfo getUserInfoFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        if (claims == null) {
            logger.error("从令牌中获取用户信息失败");
            throw new MyAuthenticationException("无效的token");
        }
        String id = claims.get("userid").toString();
        String username = claims.get("username").toString();
        return new SysUserInfo(Integer.parseInt(id), username);
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private static Claims getClaimsFromToken(String token) {
        Claims claims;
        final String bearerModelStr = "Bearer ";
        try {
            if (token == null || !token.startsWith(bearerModelStr)) {
                throw new MyAuthenticationException("无效的token");
            } else {
                claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.substring(7)).getBody();
            }
        } catch (Exception e) {
            logger.error("从令牌中获取数据声明 " + e.toString());
            claims = null;
        }
        return claims;
    }

    /**
     * 刷新令牌
     *
     * @param token
     * @return
     */
    public static String refreshToken(String token) {
        String refreshedToken;
        try {
            Claims claims = getClaimsFromToken(token);
            claims.put(CREATED, new Date());
            Date expirationDate = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            refreshedToken = generateToken(claims,expirationDate);
        } catch (Exception e) {
            logger.error("refreshToken " + e.toString());
            refreshedToken = null;
        }
        return refreshedToken;
    }

    /**
     * 判断令牌是否过期
     *
     * @param token 令牌
     * @return 是否过期
     */
    public static Boolean isTokenExpired(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            logger.error("isTokenExpired " + e.toString());
            return false;
        }
    }
}
