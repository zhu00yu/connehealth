package com.connehealth.service;

import com.connehealth.dao.PracticeDao;
import com.connehealth.dao.PracticeFileDao;
import com.connehealth.entities.Practice;
import com.connehealth.entities.PracticeFile;
import com.connehealth.transfer.PracticeFileTransfer;

import com.connehealth.util.DataUrlUtil;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
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
import java.io.*;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

@Component
@Path("/practice-files")
public class PracticeFileRestService extends BaseRestService {
    @Autowired
    protected PracticeFileDao practiceFileDao;
    public void setPracticeFileDao(PracticeFileDao practiceFileDao) {
        this.practiceFileDao = practiceFileDao;
    }




    /************************************ CREATE ************************************/
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_HTML})
    @Transactional
    public Response createPracticeFile(@Context HttpHeaders headers, PracticeFileTransfer model) {
        PracticeFile file = model.toPracticeFile();
        file = setAuditInfoForCreator(file, headers);
        practiceFileDao.createPracticeFile(file);

        return Response.status(201).entity("A new practice-files/resource has been created").build();
    }

    @POST @Path("list")
    @Consumes({MediaType.APPLICATION_JSON})
    @Transactional
    public Response createPracticesFile(@Context HttpHeaders headers, List<PracticeFileTransfer> model) {
        for(PracticeFileTransfer item : model){
            PracticeFile file = item.toPracticeFile();
            file = setAuditInfoForCreator(file, headers);
            practiceFileDao.createPracticeFile(file);
        }

        return Response.status(204).build();
    }

    /************************************ READ ************************************/
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<PracticeFileTransfer> getPracticeFiles(@Context HttpHeaders headers) {
        List<PracticeFile> files = practiceFileDao.getPracticeFiles();
        List<PracticeFileTransfer> data = new ArrayList<>();
        for(PracticeFile file : files){
            data.add(new PracticeFileTransfer(file));
        }
        return data;
    }

    @GET
    @Path("{id}/data")
    @Produces("image/*")
    public Response findDataById(@PathParam("id") Long id) {
        PracticeFile practiceFile = practiceFileDao.getPracticeFileById(id);
        if (practiceFile == null) {
            throw new WebApplicationException(404);
        }
        String fileName = practiceFile.getFileName();
        String mt = new MimetypesFileTypeMap().getContentType(fileName);

        byte[] fileData = practiceFile.getFileData();
        fileData = DataUrlUtil.convertToImage(fileData);

        return Response.ok().type(mt).entity(fileData).build();
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findById(@PathParam("id") Long id) {
        PracticeFile data = practiceFileDao.getPracticeFileById(id);
        if(data != null) {
            return Response.status(200).entity(new PracticeFileTransfer(data)).build();
        } else {
            return Response.status(404).entity("The practice File with the id " + id + " does not exist").build();
        }
    }


    /************************************ UPDATE ************************************/
    @PUT @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_HTML})
    @Transactional
    public Response updatePracticeFileById(@Context HttpHeaders headers, @PathParam("id") Long id, PracticeFileTransfer model) {
        if(model.getId() == null) model.setId(id);

        PracticeFile file = model.toPracticeFile();
        file = setAuditInfoForModifer(file, headers);
        String message;
        int status;
        if(practiceFileWasUpdated(file)){
            status = 200; //OK
            message = "User practice has been updated";
        } else if(practiceFileCanBeCreated(file)){
            file = setAuditInfoForCreator(file, headers);
            practiceFileDao.createPracticeFile(file);
            status = 201; //Created
            message = "The practice File you provided has been added to the database";
        } else {
            status = 406; //Not acceptable
            message = "Failed to update the practice File data to the database";
        }

        return Response.status(status).entity(message).build();
    }

    private boolean practiceFileWasUpdated(PracticeFile data) {
        return practiceFileDao.updatePracticeFile(data) == 1;
    }

    private boolean practiceFileCanBeCreated(PracticeFile data) {
        return true;
    }

    /************************************ DELETE ************************************/
    @DELETE @Path("{id}")
    @Produces({MediaType.TEXT_HTML})
    @Transactional
    public Response deletePracticeFileById(@PathParam("id") Long id) {
        if(practiceFileDao.deletePracticeFileById(id) == 1){
            return Response.status(204).build();
        } else {
            return Response.status(404).entity("Practice File with the id " + id + " is not present in the database").build();
        }
    }

    @DELETE
    @Produces({MediaType.TEXT_HTML})
    @Transactional
    public Response deletePracticeFiles() {
        practiceFileDao.deletePracticeFiles();
        return Response.status(200).entity("All practice Files have been successfully removed").build();
    }
}
