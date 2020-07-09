package com.zp.springsecuritytoken.filter;

import com.zp.springsecuritytoken.token.MyToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * token filter bean.
 */
@Component
public class LindTokenAuthenticationFilter extends OncePerRequestFilter {

  //  @Autowired
//  RedisTemplate<String, String> redisTemplate;
  String tokenHead = "Bearer ";
  String tokenHeader = "Authorization";
  @Autowired
  private UserDetailsService userDetailsService;

  /**
   * token filter.
   *
   * @param request     .
   * @param response    .
   * @param filterChain .
   */
  @Override
  protected void doFilterInternal(
          HttpServletRequest request,
          HttpServletResponse response,
          FilterChain filterChain) throws ServletException, IOException {

    String authHeader = request.getHeader(this.tokenHeader);
    if (authHeader != null && authHeader.startsWith(tokenHead)) {
      final String authToken = authHeader.substring(tokenHead.length()); // The part after "Bearer "
//      if (authToken != null && redisTemplate.hasKey(authToken)) {
        if (authToken != null && MyToken.tokenMap.containsKey(authToken)) {
//        String username = redisTemplate.opsForValue().get(authToken);
        String username = (String) MyToken.tokenMap.get(authToken);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
          UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
          //可以校验token和username是否有效，目前由于token对应username存在redis，都以默认都是有效的
          UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                  userDetails, null, userDetails.getAuthorities());
          authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(
                  request));
          logger.info("authenticated user " + username + ", setting security context");
          SecurityContextHolder.getContext().setAuthentication(authentication);
        }
      }
    }

    filterChain.doFilter(request, response);

  }
}