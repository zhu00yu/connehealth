package com.connehealth.service;

import java.util.*;
import java.util.concurrent.ExecutionException;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import com.connehealth.entities.PersistentLogin;
import com.connehealth.entities.User;
import com.connehealth.security.TokenUtils;
import com.connehealth.transfer.PersistentLoginTransfer;
import com.connehealth.transfer.TokenTransfer;
import com.connehealth.transfer.UserTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@Path("/user")
public class UserRestService extends BaseRestService {
    @Autowired
    private UserDetailsService userService;

    @Autowired
    @Qualifier("authenticationManager")
    private AuthenticationManager authManager;


    /**
     * Retrieves the currently logged in user.
     *
     * @return A transfer containing the username and the roles.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public UserTransfer getUser()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof String && ((String) principal).equals("anonymousUser")) {
            throw new WebApplicationException(401);
        }
        UserDetails userDetails = (UserDetails) principal;

        return new UserTransfer(userDetails.getUsername(), this.createRoleMap(userDetails));
    }


    @Path("authenticate")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public TokenTransfer authenticate(Map<String, String> map)
    {
        String username = map.get("username");
        String password = map.get("password");
        Long practiceId = null;
        try{
            practiceId = Long.parseLong(map.get("practiceId"));
        }catch (Exception ex){
            practiceId = null;
        }


        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = this.authManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

		/*
		 * Reload user as password of authentication principal will be null after authorization and
		 * password is needed for token generation
		 */
        UserDetails userDetails = this.userService.loadUserByUsername(username);
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        List<String> datas = new ArrayList<String>();
        for(GrantedAuthority auth : authorities){
            datas.add(auth.getAuthority());
        }
        String token = TokenUtils.createToken(userDetails, practiceId);

        try {
            PersistentLogin userLogin = new PersistentLogin();
            userLogin.setSeries(String.valueOf(new Date().getTime()));
            userLogin.setLastUsed(new Date());
            userLogin.setUserName(userDetails.getUsername());
            userLogin.setToken(token);

            userDao.createLatestLoginedUser(userLogin);
        }catch(Exception ex){
            String msg = ex.getMessage();
        }

        return new TokenTransfer(token, datas);
    }

    @Path("authenticate/{token}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticate(@PathParam("token") String token) throws Exception {
        TokenTransfer result = null;

        try {
            String userName = TokenUtils.getUserNameFromToken(token);
            if (userName == null) {
                throw new Exception("Token is Wrong!");
            }
            if (TokenUtils.isExpireToken(token)) {
                throw new Exception("Token is Expired!");
            }
            User user = userDao.getUserByUserName(userName);
            if (user == null) {
                throw new Exception(userName + " isn't found!");
            }
            PersistentLogin userLogin = userDao.getLatestLoginedUser(userName);
            if (!token.equals(userLogin.getToken())) {
                throw new Exception("Token is invalid.");
            }
            return Response.status(200).entity(new TokenTransfer(token, user.getAuthorities())).build();
        }catch (Exception ex){
            return Response.status(500).entity(ex.getMessage()).build();
        }
    }


    private Map<String, Boolean> createRoleMap(UserDetails userDetails)
    {
        Map<String, Boolean> roles = new HashMap<String, Boolean>();
        for (GrantedAuthority authority : userDetails.getAuthorities()) {
            roles.put(authority.getAuthority(), Boolean.TRUE);
        }

        return roles;
    }

    @GET
    @Path("login-info")
    @Produces(MediaType.APPLICATION_JSON)
    public PersistentLoginTransfer getUserLoginInfo(@Context HttpHeaders headers)
    {
        User user = getCurrentUser(headers);
        String userName = user.getUserName();
        PersistentLogin login = userDao.getLatestLoginedUser(userName);
        String token = login.getToken();
        Long practiceId = Long.parseLong(TokenUtils.getPracticeIdFromToken(token));
        return new PersistentLoginTransfer(userName, practiceId, token, user.getAuthorities());
    }
}
