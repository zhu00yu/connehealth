package com.connehealth.service;

import com.connehealth.dao.PatientDao;
import com.connehealth.dao.PatientMedicationDao;
import com.connehealth.dao.PatientProblemDao;
import com.connehealth.entities.PatientMedication;
import com.connehealth.entities.PatientProblem;
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
@Path("/patient-medications")
public class PatientMedicationRestService extends BaseRestService {

    @Autowired
    protected PatientDao patientDao;
    public void setPatientDao(PatientDao patientDao) {
        this.patientDao = patientDao;
    }
    @Autowired
    protected PatientMedicationDao patientMedicationDao;
    public void setPatientMedicationDao(PatientMedicationDao patientMedicationDao) {
        this.patientMedicationDao = patientMedicationDao;
    }

    @Context
    Request request;



    /************************************ CREATE ************************************/
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Transactional
    public Response createPatientMedication(@Context HttpHeaders headers, PatientMedication model) {
        model = setAuditInfoForCreator(model, headers);
        try{
            model.setDrugName((String) getDefaultValue(model.getDrugName(), ""));
            model.setForm((String) getDefaultValue(model.getForm(), ""));
            model.setStrength((String) getDefaultValue(model.getStrength(), ""));
            model.setStrengthUnit((String) getDefaultValue(model.getStrengthUnit(), ""));
            model.setFrequency((String) getDefaultValue(model.getFrequency(), ""));
            model.setCurrentStatus((String) getDefaultValue(model.getCurrentStatus(), ""));
            model.setStartDate((Date) getDefaultValue(model.getStartDate(), new Date()));
            model.setMemo((String) getDefaultValue(model.getMemo(), ""));
            patientMedicationDao.createPatientMedication(model);
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
    public Response createPatientMedications(@Context HttpHeaders headers, List<PatientMedication> model) {
        for(PatientMedication item : model){
            model = setAuditInfoForCreator(model, headers);
            patientMedicationDao.createPatientMedication(item);
        }

        return Response.status(204).build();
    }

    /************************************ READ ************************************/
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<PatientMedication> getPatientMedications(@Context HttpHeaders headers) {
        return patientMedicationDao.getPatientMedications();
    }

    @GET @Path("list/{patientId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getPatientMedications(@Context HttpHeaders headers, @PathParam("patientId") Long patientId) {
        List<PatientMedication> data = new ArrayList<PatientMedication>();

        try{
            data = patientMedicationDao.getPatientMedicationsByPatient(patientId);
        }catch (Exception ex){
            return Response.serverError().entity(ex.getMessage()).build();
        }

        return Response.ok().entity(data).build();
    }

    @GET @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findById(@PathParam("id") Long id) {
        try{
            PatientMedication data = patientMedicationDao.getPatientMedicationById(id);
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
    public Response updatePatientMedicationById(@Context HttpHeaders headers, @PathParam("id") Long id, PatientMedication model) {
        if(model.getId() == null) model.setId(id);

        model = setAuditInfoForModifer(model, headers);
        String message;
        int status;
        if(PatientMedicationWasUpdated(model)){
            status = 200; //OK
            message = "User Patient has been updated";
        } else if(PatientMedicationCanBeCreated(model)){
            model = setAuditInfoForCreator(model, headers);
            patientMedicationDao.createPatientMedication(model);
            status = 201; //Created
            message = "The Patient you provided has been added to the database";
        } else {
            status = 406; //Not acceptable
            message = "Failed to update the Patient data to the database";
        }

        return Response.status(status).entity(message).build();
    }

    private boolean PatientMedicationWasUpdated(PatientMedication data) {
        return patientMedicationDao.updatePatientMedication(data) == 1;
    }

    private boolean PatientMedicationCanBeCreated(PatientMedication data) {
        return true;
    }

    /************************************ DELETE ************************************/
    @DELETE @Path("{id}")
    @Produces({MediaType.TEXT_HTML})
    @Transactional
    public Response deletePatientMedicationById(@PathParam("id") Long id) {
        if(patientMedicationDao.deletePatientMedicationById(id) == 1){
            return Response.status(204).build();
        } else {
            return Response.status(404).entity("Patient with the id " + id + " is not present in the database").build();
        }
    }

    @DELETE
    @Produces({MediaType.TEXT_HTML})
    @Transactional
    public Response deletePatientMedications() {
        patientMedicationDao.deletePatientMedications();
        return Response.status(200).entity("All Patients have been successfully removed").build();
    }



}

