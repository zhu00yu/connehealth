package com.connehealth.service;

import com.connehealth.dao.ProviderDao;
import com.connehealth.entities.Provider;
import com.connehealth.entities.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
@Path("/providers")
public class ProviderRestService extends BaseRestService {

    @Autowired
    protected ProviderDao providerDao;
    public void setProviderDao(ProviderDao providerDao) {
        this.providerDao = providerDao;
    }

    @Context
    Request request;



    /************************************ CREATE ************************************/
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Transactional
    public Response createProvider(@Context HttpHeaders headers, Provider model) {
        model = setAuditInfoForCreator(model, headers);
        try{
            UserProfile profile = model.getUserProfile();
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

            model.setSpecialties((String)getDefaultValue(model.getSpecialties(), ""));
            model.setSkills((String) getDefaultValue(model.getSkills(), ""));
            model.setProfessionalRank((String) getDefaultValue(model.getProfessionalRank(), ""));
            model.setPrimaryPracticeName((String) getDefaultValue(model.getPrimaryPracticeName(), ""));
            model.setPracticeNo((String) getDefaultValue(model.getPracticeNo(), ""));
            model.setPracticeLocation((String) getDefaultValue(model.getPracticeLocation(), ""));
            model.setCertificateNo((String) getDefaultValue(model.getCertificateNo(), ""));

            providerDao.createProvider(model);
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
    public Response createProviders(@Context HttpHeaders headers, List<Provider> model) {
        for(Provider item : model){
            model = setAuditInfoForCreator(model, headers);
            providerDao.createProvider(item);
        }

        return Response.status(204).build();
    }

    /************************************ READ ************************************/
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Provider> getProviders(@Context HttpHeaders headers) {
        return providerDao.getProviders();
    }

    @GET @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findById(@PathParam("id") Long id) {
        try{
            Provider data = providerDao.getProviderById(id);
            if(data != null) {
                return Response.status(200).entity(data).build();
            } else {
                return Response.status(404).entity("The Provider with the id " + id + " does not exist").build();
            }
        }catch(Exception ex){
            String msg = ex.getMessage();
            return Response.status(500).entity(msg).build();
        }
    }


    /************************************ UPDATE ************************************/
    @PUT @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_HTML})
    @Transactional
    public Response updateProviderById(@Context HttpHeaders headers, @PathParam("id") Long id, Provider model) {
        if(model.getId() == null) model.setId(id);

        model = setAuditInfoForModifer(model, headers);
        String message;
        int status;
        if(providerWasUpdated(model)){
            status = 200; //OK
            message = "User Provider has been updated";
        } else if(providerCanBeCreated(model)){
            model = setAuditInfoForCreator(model, headers);
            providerDao.createProvider(model);
            status = 201; //Created
            message = "The Provider you provided has been added to the database";
        } else {
            status = 406; //Not acceptable
            message = "Failed to update the Provider data to the database";
        }

        return Response.status(status).entity(message).build();
    }

    private boolean providerWasUpdated(Provider data) {
        return providerDao.updateProvider(data) == 1;
    }

    private boolean providerCanBeCreated(Provider data) {
        return true;
    }

    /************************************ DELETE ************************************/
    @DELETE @Path("{id}")
    @Produces({MediaType.TEXT_HTML})
    @Transactional
    public Response deleteProviderById(@PathParam("id") Long id) {
        if(providerDao.deleteProviderById(id) == 1){
            return Response.status(204).build();
        } else {
            return Response.status(404).entity("Provider with the id " + id + " is not present in the database").build();
        }
    }

    @DELETE
    @Produces({MediaType.TEXT_HTML})
    @Transactional
    public Response deleteProviders() {
        providerDao.deleteProviders();
        return Response.status(200).entity("All Providers have been successfully removed").build();
    }



}

