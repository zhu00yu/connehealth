package com.connehealth.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import com.connehealth.dao.ProviderDao;
import com.connehealth.dao.UserDao;
import com.connehealth.dao.UserProfileDao;
import com.connehealth.entities.PersistentLogin;
import com.connehealth.entities.Provider;
import com.connehealth.entities.User;
import com.connehealth.entities.UserProfile;
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
    private ProviderDao providerDao;

    @Autowired
    private UserProfileDao userProfileDao;

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
    public TokenTransfer authenticate(Map<String, String> map) throws Exception {
        String username = map.get("username");
        String password = map.get("password");
        Long practiceId = null;
        try{
            practiceId = Long.parseLong(map.get("practiceId"));
        }catch (Exception ex){
            practiceId = null;
        }

        User user = userDao.getUserByUserName(username);
        if (user == null){
            throw new Exception("User name is not found.");
        }

        password = getPassword(password, user.getSalt());

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
    private String getPassword(String key, String salt){
        String password = key+salt;

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte byteData[] = md.digest();

            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            password = sb.toString();
        } catch (Exception e) {
            //e.printStackTrace();
            password = key;
        }

        return password;
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

    @Path("authenticate/register")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(Map<String, String> map) throws Exception {
        String username = map.get("userName");
        String password = map.get("password");
        String familyName = map.get("familyName");
        String givenName = map.get("givenName");
        String email = map.get("email");
        String mobile = map.get("mobile");
        String sex = map.get("sex");
        String idNo = map.get("idNo");
        String certificateNo = map.get("certificateNo");
        String practiceNo = map.get("practiceNo");
        String practiceLocation = map.get("practiceLocation");
        String primaryPracticeName = map.get("primaryPracticeName");
        String professionalRank = map.get("professionalRank");
        String specialties = map.get("specialties");
        String skills = map.get("skills");

        Long practiceId = null;
        Date dob = null;
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            practiceId = Long.parseLong(map.get("practiceId"));
            dob = sdf.parse(map.get("dob"));
        }catch (Exception ex){
            practiceId = null;
            dob = null;
        }

        User user = userDao.getUserByUserName(username);
        if (user != null){
            throw new Exception("User Name has been used.");
        }
        String salt = UUID.randomUUID().toString();
        password = getPassword(password, salt);
        Long userId = null;
        String uId = "";
        TokenTransfer result = null;
        try{
            user = new User();
            user.setUserName(username);
            user.setEnabled(true);
            user.setPassword(password);
            user.setSalt(salt);
            userDao.createUser(user);
            userDao.createAuthority(username, "ROLE_USER");

            userId = user.getUserId();

            UserProfile profile = new UserProfile();
            Provider provider = new Provider();
            profile.setId(userId);
            profile.setStatus(4);
            profile.setCreateOn(new Date());
            profile.setSex(sex);
            profile.setProvinceId(0L);
            profile.setGivenName(givenName);
            profile.setFamilyName(familyName);
            profile.setEmail(email);
            profile.setZip("");
            profile.setDob(dob);
            profile.setMobile(mobile);
            profile.setDistrictId(0L);
            profile.setCityId(0L);
            profile.setAddress("");

            provider.setId(userId);
            provider.setStatus(4);
            provider.setCreateBy(userId);
            provider.setCreateOn(new Date());
            provider.setApplyOn(new Date());
            provider.setSpecialties(specialties == null ? "" : specialties);
            provider.setSkills(skills == null ? "" : skills);
            provider.setProfessionalRank(professionalRank == null ? "" : professionalRank);
            provider.setPrimaryPracticeName(primaryPracticeName == null ? "" : primaryPracticeName);
            provider.setPracticeNo(practiceNo == null ? "" : practiceNo);
            provider.setPracticeLocation(practiceLocation == null ? "" : practiceLocation);
            provider.setCertificateNo(certificateNo == null ? "" : certificateNo);

            userProfileDao.createUserProfile(profile);
            providerDao.createProvider(provider);

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(username, password);
            Authentication authentication = this.authManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = this.userService.loadUserByUsername(username);
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
            List<String> datas = new ArrayList<String>();
            for(GrantedAuthority auth : authorities){
                datas.add(auth.getAuthority());
            }
            String token = TokenUtils.createToken(userDetails, practiceId);

            PersistentLogin userLogin = new PersistentLogin();
            userLogin.setSeries(String.valueOf(new Date().getTime()));
            userLogin.setLastUsed(new Date());
            userLogin.setUserName(userDetails.getUsername());
            userLogin.setToken(token);
            userDao.createLatestLoginedUser(userLogin);

            result = new TokenTransfer(token, datas);

        }catch(Exception ex){
            return Response.serverError().entity(ex.getMessage()).build();
        }




        return Response.ok().entity(result).build();
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
