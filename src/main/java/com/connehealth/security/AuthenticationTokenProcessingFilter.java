package com.connehealth.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.connehealth.dao.UserDao;
import com.connehealth.entities.PersistentLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;


public class AuthenticationTokenProcessingFilter extends GenericFilterBean
{

    private final UserDetailsService userService;
    @Autowired
    private UserDao userDao;
    public void setUserDao(UserDao userDao){
        this.userDao = userDao;
    }

    public AuthenticationTokenProcessingFilter(UserDetailsService userService)
    {
        this.userService = userService;
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException
    {
        HttpServletRequest httpRequest = this.getAsHttpRequest(request);

        String authToken = this.extractAuthTokenFromRequest(httpRequest);
        String userName = authToken == null ? null : TokenUtils.getUserNameFromToken(authToken);
        Long practiceId = null;
        try{
            practiceId = authToken == null ? null : Long.parseLong(TokenUtils.getPracticeIdFromToken(authToken));
        }catch (Exception ex){
            practiceId = null;
        }

        if (userName != null && practiceId != null) {

            UserDetails userDetails = this.userService.loadUserByUsername(userName);
            PersistentLogin loginInfo = userDao.getLatestLoginedUser(userName);
            if (loginInfo != null && userDetails != null
                    && authToken.equals(loginInfo.getToken())
                    && TokenUtils.validateToken(authToken, userDetails)) {

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(request, response);
    }


    private HttpServletRequest getAsHttpRequest(ServletRequest request)
    {
        if (!(request instanceof HttpServletRequest)) {
            throw new RuntimeException("Expecting an HTTP request");
        }

        return (HttpServletRequest) request;
    }


    private String extractAuthTokenFromRequest(HttpServletRequest httpRequest)
    {
		/* Get token from header */
        String authToken = httpRequest.getHeader("X-Auth-Token");

		/* If token not found get it from request parameter */
        if (authToken == null) {
            authToken = httpRequest.getParameter("token");
        }

        return authToken;
    }
}
