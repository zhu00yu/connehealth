package com.connehealth.service;

import com.connehealth.dao.PatientDao;
import com.connehealth.dao.PatientSocialHistoryDao;
import com.connehealth.entities.PatientSocialHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * Service class that handles REST requests
 * @author amacoder
 *
 */
@Component
@Path("/patient-social-histories")
public class PatientSocialHistoryRestService extends BaseRestService {

    @Autowired
    protected PatientDao patientDao;
    public void setPatientDao(PatientDao patientDao) {
        this.patientDao = patientDao;
    }
    @Autowired
    protected PatientSocialHistoryDao patientSocialHistoryDao;
    public void setPatientSocialHistoryDao(PatientSocialHistoryDao patientSocialHistoryDao) {
        this.patientSocialHistoryDao = patientSocialHistoryDao;
    }

    @Context
    Request request;



    /************************************ CREATE ************************************/
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Transactional
    public Response createPatientSocialHistory(@Context HttpHeaders headers, PatientSocialHistory model) {
        model = setAuditInfoForCreator(model, headers);
        try{
            model.setSportStatus((String) getDefaultValue(model.getSportStatus(), ""));
            model.setSmokingStatus((String) getDefaultValue(model.getSmokingStatus(), ""));
            model.setReligion((String) getDefaultValue(model.getReligion(), ""));
            model.setOccupation((String) getDefaultValue(model.getOccupation(), ""));
            model.setMaritalStatus((String) getDefaultValue(model.getMaritalStatus(), ""));
            model.setHobby((String) getDefaultValue(model.getHobby(), ""));
            model.setHivStatus((String) getDefaultValue(model.getHivStatus(), ""));
            model.setDrugStatus((String) getDefaultValue(model.getDrugStatus(), ""));
            model.setDrinkStatus((String) getDefaultValue(model.getDrinkStatus(), ""));
            model.setCoffeeStatus((String) getDefaultValue(model.getCoffeeStatus(), ""));
            model.setMemo((String) getDefaultValue(model.getMemo(), ""));
            patientSocialHistoryDao.createPatientSocialHistory(model);
        }catch (Exception ex){
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
    public Response createPatientSocialHistorys(@Context HttpHeaders headers, List<PatientSocialHistory> model) {
        for(PatientSocialHistory item : model){
            model = setAuditInfoForCreator(model, headers);
            patientSocialHistoryDao.createPatientSocialHistory(item);
        }

        return Response.status(204).build();
    }

    /************************************ READ ************************************/
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<PatientSocialHistory> getPatientSocialHistories(@Context HttpHeaders headers) {
        return patientSocialHistoryDao.getPatientSocialHistories();
    }

    @GET @Path("list/{patientId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getPatientSocialHistories(@Context HttpHeaders headers, @PathParam("patientId") Long patientId) {
        List<PatientSocialHistory> data = new ArrayList<PatientSocialHistory>();

        try{
            data = patientSocialHistoryDao.getPatientSocialHistoriesByPatient(patientId);
        }catch (Exception ex){
            return Response.serverError().entity(ex.getMessage()).build();
        }

        return Response.ok().entity(data).build();
    }

    @GET @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findById(@PathParam("id") Long id) {
        try{
            PatientSocialHistory data = patientSocialHistoryDao.getPatientSocialHistoryById(id);
            if(data != null) {
                return Response.status(200).entity(data).build();
            } else {
                return Response.status(404).entity("The Patient with the id " + id + " does not exist").build();
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
    public Response updatePatientSocialHistoryById(@Context HttpHeaders headers, @PathParam("id") Long id, PatientSocialHistory model) {
        if(model.getId() == null) model.setId(id);

        model = setAuditInfoForModifer(model, headers);
        String message;
        int status;
        if(patientSocialHistoryWasUpdated(model)){
            status = 200; //OK
            message = "User Patient has been updated";
        } else if(patientSocialHistoryCanBeCreated(model)){
            model = setAuditInfoForCreator(model, headers);
            patientSocialHistoryDao.createPatientSocialHistory(model);
            status = 201; //Created
            message = "The Patient you provided has been added to the database";
        } else {
            status = 406; //Not acceptable
            message = "Failed to update the Patient data to the database";
        }

        return Response.status(status).entity(message).build();
    }

    private boolean patientSocialHistoryWasUpdated(PatientSocialHistory data) {
        return patientSocialHistoryDao.updatePatientSocialHistory(data) == 1;
    }

    private boolean patientSocialHistoryCanBeCreated(PatientSocialHistory data) {
        return true;
    }

    /************************************ DELETE ************************************/
    @DELETE @Path("{id}")
    @Produces({MediaType.TEXT_HTML})
    @Transactional
    public Response deletePatientSocialHistoryById(@PathParam("id") Long id) {
        if(patientSocialHistoryDao.deletePatientSocialHistoryById(id) == 1){
            return Response.status(204).build();
        } else {
            return Response.status(404).entity("Patient with the id " + id + " is not present in the database").build();
        }
    }

    @DELETE
    @Produces({MediaType.TEXT_HTML})
    @Transactional
    public Response deletePatientSocialHistories() {
        patientSocialHistoryDao.deletePatientSocialHistories();
        return Response.status(200).entity("All Patients have been successfully removed").build();
    }



}

