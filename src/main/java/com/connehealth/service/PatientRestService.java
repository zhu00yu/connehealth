package com.connehealth.service;

import com.connehealth.dao.PatientDao;
import com.connehealth.entities.Patient;
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
@Path("/patients")
public class PatientRestService extends BaseRestService {

    @Autowired
    protected PatientDao patientDao;
    public void setPatientDao(PatientDao patientDao) {
        this.patientDao = patientDao;
    }

    @Context
    Request request;



    /************************************ CREATE ************************************/
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_HTML})
    @Transactional
    public Response createPatient(@Context HttpHeaders headers, Patient model) {
        model = setAuditInfoForCreator(model, headers);
        patientDao.createPatient(model);

        return Response.status(201).entity("A new Patients/resource has been created").build();
    }

    @POST @Path("list")
    @Consumes({MediaType.APPLICATION_JSON})
    @Transactional
    public Response createPatients(@Context HttpHeaders headers, List<Patient> model) {
        for(Patient item : model){
            model = setAuditInfoForCreator(model, headers);
            patientDao.createPatient(item);
        }

        return Response.status(204).build();
    }

    /************************************ READ ************************************/
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Patient> getPatients(@Context HttpHeaders headers) {
        return patientDao.getPatients();
    }

    @GET @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findById(@PathParam("id") Long id) {
        try{
            Patient data = patientDao.getPatientById(id);
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
    public Response updatePatientById(@Context HttpHeaders headers, @PathParam("id") Long id, Patient model) {
        if(model.getId() == null) model.setId(id);

        model = setAuditInfoForModifer(model, headers);
        String message;
        int status;
        if(PatientWasUpdated(model)){
            status = 200; //OK
            message = "User Patient has been updated";
        } else if(PatientCanBeCreated(model)){
            model = setAuditInfoForCreator(model, headers);
            patientDao.createPatient(model);
            status = 201; //Created
            message = "The Patient you provided has been added to the database";
        } else {
            status = 406; //Not acceptable
            message = "Failed to update the Patient data to the database";
        }

        return Response.status(status).entity(message).build();
    }

    private boolean PatientWasUpdated(Patient data) {
        return patientDao.updatePatient(data) == 1;
    }

    private boolean PatientCanBeCreated(Patient data) {
        return true;
    }

    /************************************ DELETE ************************************/
    @DELETE @Path("{id}")
    @Produces({MediaType.TEXT_HTML})
    @Transactional
    public Response deletePatientById(@PathParam("id") Long id) {
        if(patientDao.deletePatientById(id) == 1){
            return Response.status(204).build();
        } else {
            return Response.status(404).entity("Patient with the id " + id + " is not present in the database").build();
        }
    }

    @DELETE
    @Produces({MediaType.TEXT_HTML})
    @Transactional
    public Response deletePatients() {
        patientDao.deletePatients();
        return Response.status(200).entity("All Patients have been successfully removed").build();
    }



}

