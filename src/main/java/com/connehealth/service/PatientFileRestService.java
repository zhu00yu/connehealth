package com.connehealth.service;

import com.connehealth.dao.PatientFileDao;
import com.connehealth.entities.PatientFile;
import com.connehealth.transfer.PatientFileTransfer;
import com.connehealth.util.DataUrlUtil;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.activation.MimetypesFileTypeMap;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Component
@Path("/patient-files")
public class PatientFileRestService extends BaseRestService {
    @Autowired
    protected PatientFileDao patientFileDao;
    public void setPatientFileDao(PatientFileDao patientFileDao) {
        this.patientFileDao = patientFileDao;
    }




    /************************************ CREATE ************************************/
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_HTML})
    @Transactional
    public Response createPatientFile(@Context HttpHeaders headers, PatientFileTransfer model) {
        PatientFile file = model.toPatientFile();
        file = setAuditInfoForCreator(file, headers);
        patientFileDao.createPatientFile(file);

        return Response.status(201).entity("A new Patient-files/resource has been created").build();
    }

    @POST @Path("list")
    @Consumes({MediaType.APPLICATION_JSON})
    @Transactional
    public Response createPatientsFile(@Context HttpHeaders headers, List<PatientFileTransfer> model) {
        for(PatientFileTransfer item : model){
            PatientFile file = item.toPatientFile();
            file = setAuditInfoForCreator(file, headers);
            patientFileDao.createPatientFile(file);
        }

        return Response.status(204).build();
    }

    /************************************ READ ************************************/
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<PatientFileTransfer> getPatientFiles(@Context HttpHeaders headers) {
        List<PatientFile> files = patientFileDao.getPatientFiles();
        List<PatientFileTransfer> data = new ArrayList<>();
        for(PatientFile file : files){
            data.add(new PatientFileTransfer(file));
        }
        return data;
    }

    @GET
    @Path("{id}/data")
    @Produces("image/*")
    public Response findDataById(@PathParam("id") Long id) {
        PatientFile patientFile = patientFileDao.getPatientFileById(id);
        if (patientFile == null) {
            throw new WebApplicationException(404);
        }
        String fileName = patientFile.getFileName();
        String mt = new MimetypesFileTypeMap().getContentType(fileName);

        byte[] fileData = patientFile.getFileData();
        fileData = DataUrlUtil.convertToImage(fileData);

        return Response.ok().type(mt).entity(fileData).build();
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findById(@PathParam("id") Long id) {
        PatientFile data = patientFileDao.getPatientFileById(id);
        if(data != null) {
            return Response.status(200).entity(new PatientFileTransfer(data)).build();
        } else {
            return Response.status(404).entity("The Patient File with the id " + id + " does not exist").build();
        }
    }


    /************************************ UPDATE ************************************/
    @PUT @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_HTML})
    @Transactional
    public Response updatePatientFileById(@Context HttpHeaders headers, @PathParam("id") Long id, PatientFileTransfer model) {
        if(model.getId() == null) model.setId(id);

        PatientFile file = model.toPatientFile();
        file = setAuditInfoForModifer(file, headers);
        String message;
        int status;
        if(PatientFileWasUpdated(file)){
            status = 200; //OK
            message = "User Patient has been updated";
        } else if(PatientFileCanBeCreated(file)){
            file = setAuditInfoForCreator(file, headers);
            patientFileDao.createPatientFile(file);
            status = 201; //Created
            message = "The Patient File you provided has been added to the database";
        } else {
            status = 406; //Not acceptable
            message = "Failed to update the Patient File data to the database";
        }

        return Response.status(status).entity(message).build();
    }

    private boolean PatientFileWasUpdated(PatientFile data) {
        return patientFileDao.updatePatientFile(data) == 1;
    }

    private boolean PatientFileCanBeCreated(PatientFile data) {
        return true;
    }

    /************************************ DELETE ************************************/
    @DELETE @Path("{id}")
    @Produces({MediaType.TEXT_HTML})
    @Transactional
    public Response deletePatientFileById(@PathParam("id") Long id) {
        if(patientFileDao.deletePatientFileById(id) == 1){
            return Response.status(204).build();
        } else {
            return Response.status(404).entity("Patient File with the id " + id + " is not present in the database").build();
        }
    }

    @DELETE
    @Produces({MediaType.TEXT_HTML})
    @Transactional
    public Response deletePatientFiles() {
        patientFileDao.deletePatientFiles();
        return Response.status(200).entity("All Patient Files have been successfully removed").build();
    }
}
