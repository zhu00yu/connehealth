package com.connehealth.service;

import com.connehealth.dao.*;
import com.connehealth.entities.PatientAdverseReaction;
import com.connehealth.entities.PatientAllergy;
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
@Path("/patient-allergies")
public class PatientAllergyRestService extends BaseRestService {

    @Autowired
    protected PatientDao patientDao;
    public void setPatientDao(PatientDao patientDao) {
        this.patientDao = patientDao;
    }
    @Autowired
    protected PatientAllergyDao patientAllergyDao;
    public void setPatientAllergyDao(PatientAllergyDao patientAllergyDao) {
        this.patientAllergyDao = patientAllergyDao;
    }
    @Autowired
    protected PatientAdverseReactionDao patientAdverseReactionDao;
    public void setPatientAdverseReactionDao(PatientAdverseReactionDao patientAdverseReactionDao) {
        this.patientAdverseReactionDao = patientAdverseReactionDao;
    }
    @Autowired
    protected AllergenDao allergenDao;
    public void setAllergenDao(AllergenDao allergenDao) {
        this.allergenDao = allergenDao;
    }
    @Autowired
    protected AdverseReactionDao adverseReactionDao;
    public void setAdverseReactionDao(AdverseReactionDao adverseReactionDao) {
        this.adverseReactionDao = adverseReactionDao;
    }

    @Context
    Request request;



    /************************************ CREATE ************************************/
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Transactional
    public Response createPatientAllergy(@Context HttpHeaders headers, PatientAllergy model) {
        model = setAuditInfoForCreator(model, headers);
        try{
            model.setSeverity((String) getDefaultValue(model.getSeverity(), ""));
            model.setAllergenType((String) getDefaultValue(model.getAllergenType(), ""));
            model.setMemo((String) getDefaultValue(model.getMemo(), ""));
            model.setAllergenName((String) getDefaultValue(model.getAllergenName(), ""));
            model.setStage((String) getDefaultValue(model.getStage(), ""));
            model.setCurrentStatus((String) getDefaultValue(model.getCurrentStatus(), ""));
            model.setStartDate((Date) getDefaultValue(model.getStartDate(), new Date()));
            patientAllergyDao.createPatientAllergy(model);

            Long allergyId = model.getId();
            Long patientId = model.getPatientId();
            List<PatientAdverseReaction> reactions = model.getReactions();
            for(PatientAdverseReaction item : reactions){
                item = setAuditInfoForCreator(item, headers);
                item.setAllergyId(allergyId);
                item.setPatientId(patientId);
                item.setReaction((String)getDefaultValue(item.getReaction(), ""));
                patientAdverseReactionDao.createPatientAdverseReaction(item);
            }

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
    public Response createPatientAllergies(@Context HttpHeaders headers, List<PatientAllergy> model) {
        for(PatientAllergy item : model){
            model = setAuditInfoForCreator(model, headers);
            patientAllergyDao.createPatientAllergy(item);
        }

        return Response.status(204).build();
    }

    /************************************ READ ************************************/
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<PatientAllergy> getPatientAllergies(@Context HttpHeaders headers) {
        return patientAllergyDao.getPatientAllergies();
    }

    @GET @Path("list/{patientId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getPatientAllergies(@Context HttpHeaders headers, @PathParam("patientId") Long patientId) {
        List<PatientAllergy> data = new ArrayList<PatientAllergy>();

        try{
            data = patientAllergyDao.getPatientAllergiesByPatient(patientId);
        }catch (Exception ex){
            return Response.serverError().entity(ex.getMessage()).build();
        }

        return Response.ok().entity(data).build();
    }

    @GET @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findById(@PathParam("id") Long id) {
        try{
            PatientAllergy data = patientAllergyDao.getPatientAllergyById(id);
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
    public Response updatePatientAllergyById(@Context HttpHeaders headers, @PathParam("id") Long id, PatientAllergy model) {
        if(model.getId() == null) model.setId(id);

        model = setAuditInfoForModifer(model, headers);
        String message;
        int status;
        if(patientAllergyWasUpdated(model)){
            status = 200; //OK
            message = "User Patient has been updated";
        } else if(patientAllergyCanBeCreated(model)){
            model = setAuditInfoForCreator(model, headers);
            patientAllergyDao.createPatientAllergy(model);
            status = 201; //Created
            message = "The Patient you provided has been added to the database";
        } else {
            status = 406; //Not acceptable
            message = "Failed to update the Patient data to the database";
        }
        if (status != 406) {
            Long allergyId = model.getId();
            Long patientId = model.getPatientId();
            List<PatientAdverseReaction> reactions = model.getReactions();
            patientAdverseReactionDao.deletePatientAdverseReactionByAllergy(allergyId);
            for (PatientAdverseReaction item : reactions) {
                item = setAuditInfoForCreator(item, headers);
                item.setAllergyId(allergyId);
                item.setPatientId(patientId);
                item.setReaction((String) getDefaultValue(item.getReaction(), ""));
                patientAdverseReactionDao.createPatientAdverseReaction(item);
            }
        }

        return Response.status(status).entity(message).build();
    }

    private boolean patientAllergyWasUpdated(PatientAllergy data) {
        boolean bResult =  patientAllergyDao.updatePatientAllergy(data) == 1;
        return bResult;
    }

    private boolean patientAllergyCanBeCreated(PatientAllergy data) {
        return true;
    }

    /************************************ DELETE ************************************/
    @DELETE @Path("{id}")
    @Produces({MediaType.TEXT_HTML})
    @Transactional
    public Response deletePatientAllergyById(@PathParam("id") Long id) {
        if(patientAllergyDao.deletePatientAllergyById(id) == 1){
            return Response.status(204).build();
        } else {
            return Response.status(404).entity("Patient with the id " + id + " is not present in the database").build();
        }
    }

    @DELETE
    @Produces({MediaType.TEXT_HTML})
    @Transactional
    public Response deletePatientAllergies() {
        patientAllergyDao.deletePatientAllergies();
        return Response.status(200).entity("All Patients have been successfully removed").build();
    }



}

