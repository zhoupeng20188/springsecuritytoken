package com.zp.springsecuritytoken.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自定义验证过滤器
 */
public class TokenCheckFilter extends BasicAuthenticationFilter {

    public TokenCheckFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    /**
     * 重写验证逻辑
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");
        if(request.getRequestURI().equals("/login")){
            chain.doFilter(request, response);
        } else{
            if(header == null || !header.startsWith("Bearer")) {
                // header为空或者不以Bearer开头，直接验证失败
                // 返回错误消息
                response.setContentType("application/json;charset=utf-8");
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                PrintWriter writer = response.getWriter();
                Map map = new HashMap();
                map.put("code", HttpServletResponse.SC_FORBIDDEN);
                map.put("msg", "请登录");
                writer.write(new ObjectMapper().writeValueAsString(map));
                writer.flush();
                writer.close();
            } else {
                // header里携带了token的场合

                // 验证token是否正确
                // 这里直接写死，实际情况是调用认证中心的接口来验证token的正确性

                if (header.equals("Bearer test123456")) {
                    // 验证成功

                    // 根据token得到用户信息，这里写死用户名,密码和角色
                    String username = "t1";
                    String password = "p1";
                    String authority = "ROLE_USER";

                    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
                    authorities.add(simpleGrantedAuthority);

                    UsernamePasswordAuthenticationToken authResult =
                            new UsernamePasswordAuthenticationToken(username, password, authorities);

                    SecurityContextHolder.getContext().setAuthentication(authResult);

                    chain.doFilter(request, response);
                }
        }

        }
    }
}
