package vn.huan.shoppingcart.ShoppingCart.security.jwt;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.jaas.SecurityContextLoginModule;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import vn.huan.shoppingcart.ShoppingCart.security.user.ShopUserDetails;
import vn.huan.shoppingcart.ShoppingCart.security.user.ShopUserDetailsService;

import java.io.IOException;



public class AuthTokenFilter extends OncePerRequestFilter {
    private JwtUtils jwtUtils;
    private ShopUserDetailsService UserDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        try {
            String jwt = parseJwt(request);
            if (StringUtils.hasText(jwt) && jwtUtils.validateToken(jwt)){
                String username = jwtUtils.getUsernameFromToken(jwt);
                UserDetails userDetails = UserDetailsService.loadUserByUsername(username);
                var auth = new UsernamePasswordAuthenticationToken(userDetails ,null,userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (JwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(e.getMessage() +" : Invalid or expired token, you may login and try again");
            return;
        } catch (Exception e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(e.getMessage());
            return;
        }
        filterChain.doFilter(request,response);
    }

    private String parseJwt(HttpServletRequest request){
        String headerAuth= request.getHeader("Authorization");
        if(StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")){
            return headerAuth.substring(7);
        }
        return null;
    }
}
