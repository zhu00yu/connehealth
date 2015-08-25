package com.connehealth.service;

import com.connehealth.dao.PodcastDao;
import com.connehealth.dao.UserDao;
import com.connehealth.dao.UserProfileDao;
import com.connehealth.entities.Podcast;
import com.connehealth.entities.User;
import com.connehealth.entities.UserProfile;
import com.connehealth.security.TokenUtils;
import org.glassfish.hk2.api.PerLookup;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

/**
 *
 * Service class that handles REST requests
 * @author amacoder
 *
 */
@Component
@Path("/user-profiles")
public class UserProfileRestService extends BaseRestService {

    @Autowired
    protected UserProfileDao userProfileDao;
    public void setUserProfileDao(UserProfileDao userProfileDao) {
        this.userProfileDao = userProfileDao;
    }

    @Context
    Request request;



    /************************************ CREATE ************************************/
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Transactional
    public Response createUserProfile(UserProfile model) {
        try{
            UserProfile profile = model;
            profile.setGivenName((String)getDefaultValue(profile.getGivenName(), ""));
            profile.setFamilyName((String)getDefaultValue(profile.getFamilyName(), ""));
            profile.setSex((String)getDefaultValue(profile.getSex(), ""));
            profile.setMobile((String)getDefaultValue(profile.getMobile(), ""));
            profile.setEmail((String)getDefaultValue(profile.getEmail(), ""));
            profile.setAddress((String)getDefaultValue(profile.getAddress(), ""));
            profile.setZip((String)getDefaultValue(profile.getZip(), ""));
            profile.setProvinceId((Long)getDefaultValue(profile.getProvinceId(), 0));
            profile.setDistrictId((Long)getDefaultValue(profile.getDistrictId(), 0));
            profile.setCityId((Long)getDefaultValue(profile.getCityId(), 0));

            userProfileDao.createUserProfile(model);
        }catch(Exception ex){
            return Response.serverError().entity(ex.getMessage()).build();
        }
        return Response.status(200).entity(model.getId()).build();
    }
    private Object getDefaultValue(Object value, Object defaultValue){
        if (value == null){
            return defaultValue;
        }
        return value;
    }

    @POST @Path("list")
    @Consumes({MediaType.APPLICATION_JSON})
    @Transactional
    public Response createUserProfiles(List<UserProfile> model) {
        for(UserProfile profile : model){
            userProfileDao.createUserProfile(profile);
        }

        return Response.status(204).build();
    }

    /************************************ READ ************************************/
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<UserProfile> getUserProfiles(@Context HttpHeaders headers) {
        return userProfileDao.getUserProfiles();
    }

    @GET @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findById(@PathParam("id") Long id) {
        UserProfile profile = userProfileDao.getUserProfileById(id);
        if(profile != null) {
            return Response.status(200).entity(profile).build();
        } else {
            return Response.status(404).entity("The user profile with the id " + id + " does not exist").build();
        }
    }


    /************************************ UPDATE ************************************/
    @PUT @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_HTML})
    @Transactional
    public Response updateUserProfileById(@PathParam("id") Long id, UserProfile model) {
        if(model.getId() == null) model.setId(id);
        String message;
        int status;
        if(userProfileWasUpdated(model)){
            status = 200; //OK
            message = "User profile has been updated";
        } else if(userProfileCanBeCreated(model)){
            userProfileDao.createUserProfile(model);
            status = 201; //Created
            message = "The user profile you provided has been added to the database";
        } else {
            status = 406; //Not acceptable
            message = "Failed to update the user profile data to the database";
        }

        return Response.status(status).entity(message).build();
    }

    private boolean userProfileWasUpdated(UserProfile profile) {
        return userProfileDao.updateUserProfile(profile) == 1;
    }

    private boolean userProfileCanBeCreated(UserProfile profile) {
        return true;
    }

    /************************************ DELETE ************************************/
    @DELETE @Path("{id}")
    @Produces({MediaType.TEXT_HTML})
    @Transactional
    public Response deleteUserProfileById(@PathParam("id") Long id) {
        if(userProfileDao.deleteUserProfileById(id) == 1){
            return Response.status(204).build();
        } else {
            return Response.status(404).entity("User profile with the id " + id + " is not present in the database").build();
        }
    }

    @DELETE
    @Produces({MediaType.TEXT_HTML})
    @Transactional
    public Response deleteUserProfiles() {
        userProfileDao.deleteUserProfiles();
        return Response.status(200).entity("All user profiles have been successfully removed").build();
    }



}

