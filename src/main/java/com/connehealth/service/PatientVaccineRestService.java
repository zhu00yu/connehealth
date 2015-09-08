package com.connehealth.service;

import com.connehealth.dao.PatientDao;
import com.connehealth.dao.PatientVaccineDao;
import com.connehealth.entities.PatientVaccine;
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
@Path("/patient-vaccines")
public class PatientVaccineRestService extends BaseRestService {

    @Autowired
    protected PatientDao patientDao;
    public void setPatientDao(PatientDao patientDao) {
        this.patientDao = patientDao;
    }
    @Autowired
    protected PatientVaccineDao patientVaccineDao;
    public void setPatientVaccineDao(PatientVaccineDao patientVaccineDao) {
        this.patientVaccineDao = patientVaccineDao;
    }

    @Context
    Request request;



    /************************************ CREATE ************************************/
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Transactional
    public Response createPatientVaccine(@Context HttpHeaders headers, PatientVaccine model) {
        model = setAuditInfoForCreator(model, headers);
        try{
            model.setDoctor((String) getDefaultValue(model.getDoctor(), ""));
            model.setDosage((String) getDefaultValue(model.getDosage(), ""));
            model.setPosition((String) getDefaultValue(model.getPosition(), ""));
            model.setPracticeName((String) getDefaultValue(model.getPracticeName(), ""));
            model.setStartDate((Date) getDefaultValue(model.getStartDate(), new Date()));
            model.setTimes((String) getDefaultValue(model.getTimes(), ""));
            model.setVaccineName((String) getDefaultValue(model.getVaccineName(), ""));
            model.setWay((String) getDefaultValue(model.getWay(), ""));
            model.setMemo((String) getDefaultValue(model.getMemo(), ""));
            patientVaccineDao.createPatientVaccine(model);
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
    public Response createPatientVaccines(@Context HttpHeaders headers, List<PatientVaccine> model) {
        for(PatientVaccine item : model){
            model = setAuditInfoForCreator(model, headers);
            patientVaccineDao.createPatientVaccine(item);
        }

        return Response.status(204).build();
    }

    /************************************ READ ************************************/
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<PatientVaccine> getPatientVaccines(@Context HttpHeaders headers) {
        return patientVaccineDao.getPatientVaccines();
    }

    @GET @Path("list/{patientId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getPatientVaccines(@Context HttpHeaders headers, @PathParam("patientId") Long patientId) {
        List<PatientVaccine> data = new ArrayList<PatientVaccine>();

        try{
            data = patientVaccineDao.getPatientVaccinesByPatient(patientId);
        }catch (Exception ex){
            return Response.serverError().entity(ex.getMessage()).build();
        }

        return Response.ok().entity(data).build();
    }

    @GET @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findById(@PathParam("id") Long id) {
        try{
            PatientVaccine data = patientVaccineDao.getPatientVaccineById(id);
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
    public Response updatePatientVaccineById(@Context HttpHeaders headers, @PathParam("id") Long id, PatientVaccine model) {
        if(model.getId() == null) model.setId(id);

        model = setAuditInfoForModifer(model, headers);
        String message;
        int status;
        if(PatientVaccineWasUpdated(model)){
            status = 200; //OK
            message = "User Patient has been updated";
        } else if(PatientVaccineCanBeCreated(model)){
            model = setAuditInfoForCreator(model, headers);
            patientVaccineDao.createPatientVaccine(model);
            status = 201; //Created
            message = "The Patient you provided has been added to the database";
        } else {
            status = 406; //Not acceptable
            message = "Failed to update the Patient data to the database";
        }

        return Response.status(status).entity(message).build();
    }

    private boolean PatientVaccineWasUpdated(PatientVaccine data) {
        return patientVaccineDao.updatePatientVaccine(data) == 1;
    }

    private boolean PatientVaccineCanBeCreated(PatientVaccine data) {
        return true;
    }

    /************************************ DELETE ************************************/
    @DELETE @Path("{id}")
    @Produces({MediaType.TEXT_HTML})
    @Transactional
    public Response deletePatientVaccineById(@PathParam("id") Long id) {
        if(patientVaccineDao.deletePatientVaccineById(id) == 1){
            return Response.status(204).build();
        } else {
            return Response.status(404).entity("Patient with the id " + id + " is not present in the database").build();
        }
    }

    @DELETE
    @Produces({MediaType.TEXT_HTML})
    @Transactional
    public Response deletePatientVaccines() {
        patientVaccineDao.deletePatientVaccines();
        return Response.status(200).entity("All Patients have been successfully removed").build();
    }



}

