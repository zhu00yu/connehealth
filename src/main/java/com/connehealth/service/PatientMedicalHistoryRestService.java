package com.connehealth.service;

import com.connehealth.dao.PatientDao;
import com.connehealth.dao.PatientMedicalHistoryDao;
import com.connehealth.entities.PatientMedicalHistory;
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
@Path("/patient-medical-histories")
public class PatientMedicalHistoryRestService extends BaseRestService {

    @Autowired
    protected PatientDao patientDao;
    public void setPatientDao(PatientDao patientDao) {
        this.patientDao = patientDao;
    }
    @Autowired
    protected PatientMedicalHistoryDao patientMedicalHistoryDao;
    public void setPatientMedicalHistoryDao(PatientMedicalHistoryDao patientMedicalHistoryDao) {
        this.patientMedicalHistoryDao = patientMedicalHistoryDao;
    }

    @Context
    Request request;



    /************************************ CREATE ************************************/
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Transactional
    public Response createPatientMedicalHistory(@Context HttpHeaders headers, PatientMedicalHistory model) {
        model = setAuditInfoForCreator(model, headers);
        try{
            model.setProblem((String) getDefaultValue(model.getProblem(), ""));
            model.setTherapyCode((String) getDefaultValue(model.getTherapyCode(), ""));
            model.setTherapy((String) getDefaultValue(model.getTherapy(), ""));
            model.setGoals((String) getDefaultValue(model.getGoals(), ""));
            model.setOutcome((String) getDefaultValue(model.getOutcome(), ""));
            model.setPracticeName((String) getDefaultValue(model.getPracticeName(), ""));
            model.setAttendingDoctor((String) getDefaultValue(model.getAttendingDoctor(), ""));
            model.setAnesthetist((String) getDefaultValue(model.getAnesthetist(), ""));
            model.setRecordDate((Date) getDefaultValue(model.getRecordDate(), new Date()));
            model.setMemo((String) getDefaultValue(model.getMemo(), ""));
            patientMedicalHistoryDao.createPatientMedicalHistory(model);
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
    public Response createPatientMedicalHistorys(@Context HttpHeaders headers, List<PatientMedicalHistory> model) {
        for(PatientMedicalHistory item : model){
            model = setAuditInfoForCreator(model, headers);
            patientMedicalHistoryDao.createPatientMedicalHistory(item);
        }

        return Response.status(204).build();
    }

    /************************************ READ ************************************/
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<PatientMedicalHistory> getPatientMedicalHistories(@Context HttpHeaders headers) {
        return patientMedicalHistoryDao.getPatientMedicalHistories();
    }

    @GET @Path("list/{patientId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getPatientMedicalHistories(@Context HttpHeaders headers, @PathParam("patientId") Long patientId) {
        List<PatientMedicalHistory> data = new ArrayList<PatientMedicalHistory>();

        try{
            data = patientMedicalHistoryDao.getPatientMedicalHistoriesByPatient(patientId);
        }catch (Exception ex){
            return Response.serverError().entity(ex.getMessage()).build();
        }

        return Response.ok().entity(data).build();
    }

    @GET @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findById(@PathParam("id") Long id) {
        try{
            PatientMedicalHistory data = patientMedicalHistoryDao.getPatientMedicalHistoryById(id);
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
    public Response updatePatientMedicalHistoryById(@Context HttpHeaders headers, @PathParam("id") Long id, PatientMedicalHistory model) {
        if(model.getId() == null) model.setId(id);

        model = setAuditInfoForModifer(model, headers);
        String message;
        int status;
        if(patientMedicalHistoryWasUpdated(model)){
            status = 200; //OK
            message = "User Patient has been updated";
        } else if(patientMedicalHistoryCanBeCreated(model)){
            model = setAuditInfoForCreator(model, headers);
            patientMedicalHistoryDao.createPatientMedicalHistory(model);
            status = 201; //Created
            message = "The Patient you provided has been added to the database";
        } else {
            status = 406; //Not acceptable
            message = "Failed to update the Patient data to the database";
        }

        return Response.status(status).entity(message).build();
    }

    private boolean patientMedicalHistoryWasUpdated(PatientMedicalHistory data) {
        return patientMedicalHistoryDao.updatePatientMedicalHistory(data) == 1;
    }

    private boolean patientMedicalHistoryCanBeCreated(PatientMedicalHistory data) {
        return true;
    }

    /************************************ DELETE ************************************/
    @DELETE @Path("{id}")
    @Produces({MediaType.TEXT_HTML})
    @Transactional
    public Response deletePatientMedicalHistoryById(@PathParam("id") Long id) {
        if(patientMedicalHistoryDao.deletePatientMedicalHistoryById(id) == 1){
            return Response.status(204).build();
        } else {
            return Response.status(404).entity("Patient with the id " + id + " is not present in the database").build();
        }
    }

    @DELETE
    @Produces({MediaType.TEXT_HTML})
    @Transactional
    public Response deletePatientMedicalHistories() {
        patientMedicalHistoryDao.deletePatientMedicalHistories();
        return Response.status(200).entity("All Patients have been successfully removed").build();
    }



}

