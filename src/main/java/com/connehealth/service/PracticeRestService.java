package com.connehealth.service;

import com.connehealth.dao.PracticeDao;
import com.connehealth.dao.UserProfileDao;
import com.connehealth.entities.Practice;
import com.connehealth.entities.User;
import com.connehealth.entities.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Date;
import java.util.List;

/**
 *
 * Service class that handles REST requests
 * @author amacoder
 *
 */
@Component
@Path("/practices")
public class PracticeRestService extends BaseRestService {

    @Autowired
    protected PracticeDao practiceDao;
    public void setPracticeDao(PracticeDao practiceDao) {
        this.practiceDao = practiceDao;
    }

    @Context
    Request request;



    /************************************ CREATE ************************************/
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_HTML})
    @Transactional
    public Response createPractice(@Context HttpHeaders headers, Practice model) {
        model = setAuditInfoForCreator(model, headers);
        practiceDao.createPractice(model);

        return Response.status(201).entity("A new practices/resource has been created").build();
    }

    @POST @Path("list")
    @Consumes({MediaType.APPLICATION_JSON})
    @Transactional
    public Response createPractices(@Context HttpHeaders headers, List<Practice> model) {
        for(Practice item : model){
            model = setAuditInfoForCreator(model, headers);
            practiceDao.createPractice(item);
        }

        return Response.status(204).build();
    }

    /************************************ READ ************************************/
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Practice> getPractices(@Context HttpHeaders headers) {
        return practiceDao.getPractices();
    }

    @GET @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findById(@PathParam("id") Long id) {
        Practice data = practiceDao.getPracticeById(id);
        if(data != null) {
            return Response.status(200).entity(data).build();
        } else {
            return Response.status(404).entity("The practice with the id " + id + " does not exist").build();
        }
    }


    /************************************ UPDATE ************************************/
    @PUT @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_HTML})
    @Transactional
    public Response updatePracticeById(@Context HttpHeaders headers, @PathParam("id") Long id, Practice model) {
        if(model.getId() == null) model.setId(id);

        model = setAuditInfoForModifer(model, headers);
        String message;
        int status;
        if(practiceWasUpdated(model)){
            status = 200; //OK
            message = "User practice has been updated";
        } else if(practiceCanBeCreated(model)){
            model = setAuditInfoForCreator(model, headers);
            practiceDao.createPractice(model);
            status = 201; //Created
            message = "The practice you provided has been added to the database";
        } else {
            status = 406; //Not acceptable
            message = "Failed to update the practice data to the database";
        }

        return Response.status(status).entity(message).build();
    }

    private boolean practiceWasUpdated(Practice data) {
        return practiceDao.updatePractice(data) == 1;
    }

    private boolean practiceCanBeCreated(Practice data) {
        return true;
    }

    /************************************ DELETE ************************************/
    @DELETE @Path("{id}")
    @Produces({MediaType.TEXT_HTML})
    @Transactional
    public Response deletePracticeById(@PathParam("id") Long id) {
        if(practiceDao.deletePracticeById(id) == 1){
            return Response.status(204).build();
        } else {
            return Response.status(404).entity("Practice with the id " + id + " is not present in the database").build();
        }
    }

    @DELETE
    @Produces({MediaType.TEXT_HTML})
    @Transactional
    public Response deletePractices() {
        practiceDao.deletePractices();
        return Response.status(200).entity("All practices have been successfully removed").build();
    }



}

