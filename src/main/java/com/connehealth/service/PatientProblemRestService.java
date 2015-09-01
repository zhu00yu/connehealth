package com.connehealth.service;

import com.connehealth.dao.PatientDao;
import com.connehealth.dao.PatientProblemDao;
import com.connehealth.entities.PatientProblem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 *
 * Service class that handles REST requests
 * @author amacoder
 *
 */
@Component
@Path("/patient-problems")
public class PatientProblemRestService extends BaseRestService {

    @Autowired
    protected PatientDao patientDao;
    public void setPatientDao(PatientDao patientDao) {
        this.patientDao = patientDao;
    }
    @Autowired
    protected PatientProblemDao patientProblemDao;
    public void setPatientProblemDao(PatientProblemDao patientProblemDao) {
        this.patientProblemDao = patientProblemDao;
    }

    @Context
    Request request;



    /************************************ CREATE ************************************/
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Transactional
    public Response createPatientProblem(@Context HttpHeaders headers, PatientProblem model) {
        model = setAuditInfoForCreator(model, headers);
        try{
            model.setSeverity((String) getDefaultValue(model.getSeverity(), ""));
            model.setProblemName((String) getDefaultValue(model.getProblemName(), ""));
            model.setMemo((String) getDefaultValue(model.getMemo(), ""));
            model.setIcdNo((String) getDefaultValue(model.getIcdNo(), ""));
            model.setCurrentStatus((String) getDefaultValue(model.getCurrentStatus(), ""));
            model.setStartDate((Date) getDefaultValue(model.getStartDate(), new Date()));
            patientProblemDao.createPatientProblem(model);
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
    public Response createPatientProblems(@Context HttpHeaders headers, List<PatientProblem> model) {
        for(PatientProblem item : model){
            model = setAuditInfoForCreator(model, headers);
            patientProblemDao.createPatientProblem(item);
        }

        return Response.status(204).build();
    }

    /************************************ READ ************************************/
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<PatientProblem> getPatientProblems(@Context HttpHeaders headers) {
        return patientProblemDao.getPatientProblems();
    }

    @GET @Path("list/{patientId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getPatientProblems(@Context HttpHeaders headers, @PathParam("patientId") Long patientId) {
        List<PatientProblem> data = new ArrayList<PatientProblem>();

        try{
            data = patientProblemDao.getPatientProblemsByPatient(patientId);
        }catch (Exception ex){
            return Response.serverError().entity(ex.getMessage()).build();
        }

        return Response.ok().entity(data).build();
    }

    @GET @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findById(@PathParam("id") Long id) {
        try{
            PatientProblem data = patientProblemDao.getPatientProblemById(id);
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
    public Response updatePatientProblemById(@Context HttpHeaders headers, @PathParam("id") Long id, PatientProblem model) {
        if(model.getId() == null) model.setId(id);

        model = setAuditInfoForModifer(model, headers);
        String message;
        int status;
        if(PatientProblemWasUpdated(model)){
            status = 200; //OK
            message = "User Patient has been updated";
        } else if(PatientProblemCanBeCreated(model)){
            model = setAuditInfoForCreator(model, headers);
            patientProblemDao.createPatientProblem(model);
            status = 201; //Created
            message = "The Patient you provided has been added to the database";
        } else {
            status = 406; //Not acceptable
            message = "Failed to update the Patient data to the database";
        }

        return Response.status(status).entity(message).build();
    }

    private boolean PatientProblemWasUpdated(PatientProblem data) {
        return patientProblemDao.updatePatientProblem(data) == 1;
    }

    private boolean PatientProblemCanBeCreated(PatientProblem data) {
        return true;
    }

    /************************************ DELETE ************************************/
    @DELETE @Path("{id}")
    @Produces({MediaType.TEXT_HTML})
    @Transactional
    public Response deletePatientProblemById(@PathParam("id") Long id) {
        if(patientProblemDao.deletePatientProblemById(id) == 1){
            return Response.status(204).build();
        } else {
            return Response.status(404).entity("Patient with the id " + id + " is not present in the database").build();
        }
    }

    @DELETE
    @Produces({MediaType.TEXT_HTML})
    @Transactional
    public Response deletePatientProblems() {
        patientProblemDao.deletePatientProblems();
        return Response.status(200).entity("All Patients have been successfully removed").build();
    }



}

