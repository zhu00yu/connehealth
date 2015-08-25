package com.connehealth.service;

import com.connehealth.dao.PatientDao;
import com.connehealth.entities.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
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
    @Transactional
    public Response createPatient(@Context HttpHeaders headers, Patient model) {
        model = setAuditInfoForCreator(model, headers);
        UUID uuid = UUID.randomUUID();
        model.setMrn(uuid.hashCode() + "");
        try{
            model.setIdNo((String)getDefaultValue(model.getIdNo(), ""));
            model.setSsn((String)getDefaultValue(model.getSsn(), ""));
            model.setPatientName((String)getDefaultValue(model.getPatientName(), ""));
            model.setGivenName((String)getDefaultValue(model.getGivenName(), ""));
            model.setFamilyName((String)getDefaultValue(model.getFamilyName(), ""));
            model.setSex((String)getDefaultValue(model.getSex(), ""));
            model.setBloodType((String)getDefaultValue(model.getBloodType(), ""));
            model.setEmail((String)getDefaultValue(model.getEmail(), ""));
            model.setMobile((String)getDefaultValue(model.getMobile(), ""));
            model.setEthnicity((String)getDefaultValue(model.getEthnicity(), ""));
            model.setNationality((String)getDefaultValue(model.getNationality(), ""));
            model.setNativePlace((String)getDefaultValue(model.getNativePlace(), ""));
            model.setPreferredLanguage((String)getDefaultValue(model.getPreferredLanguage(), ""));
            model.setRace((String)getDefaultValue(model.getRace(), ""));
            model.setMaritalStatus((String)getDefaultValue(model.getMaritalStatus(), ""));

            model.setCompany((String)getDefaultValue(model.getCompany(), ""));
            model.setIndustry((String)getDefaultValue(model.getIndustry(), ""));
            model.setContactPerson((String)getDefaultValue(model.getContactPerson(), ""));
            model.setContactPhone((String)getDefaultValue(model.getContactPhone(), ""));
            model.setContactRelationship((String)getDefaultValue(model.getContactRelationship(), ""));
            model.setHomeAddress((String)getDefaultValue(model.getHomeAddress(), ""));
            model.setHomeZip((String)getDefaultValue(model.getHomeZip(), ""));
            model.setRegisteredAddress((String)getDefaultValue(model.getRegisteredAddress(), ""));
            model.setRegisteredZip((String)getDefaultValue(model.getRegisteredZip(), ""));
            model.setWorkAddress((String)getDefaultValue(model.getWorkAddress(), ""));
            model.setWorkPhone((String)getDefaultValue(model.getWorkPhone(), ""));
            model.setWorkZip((String)getDefaultValue(model.getWorkZip(), ""));
            patientDao.createPatient(model);
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

    @GET @Path("list/{providerId}/{scheduleDate}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Patient> getPatients(@Context HttpHeaders headers, @PathParam("providerId") Long providerId) {
        return patientDao.getPatientsByProvider(providerId);
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

