package com.connehealth.service;

import com.connehealth.dao.EmployeeDao;
import com.connehealth.entities.Employee;
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
@Path("/employees")
public class EmployeeRestService extends BaseRestService {

    @Autowired
    protected EmployeeDao employeeDao;
    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Context
    Request request;



    /************************************ CREATE ************************************/
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_HTML})
    @Transactional
    public Response createEmployee(@Context HttpHeaders headers, Employee model) {
        model = setAuditInfoForCreator(model, headers);
        employeeDao.createEmployee(model);

        return Response.status(201).entity("A new Employees/resource has been created").build();
    }

    @POST @Path("list")
    @Consumes({MediaType.APPLICATION_JSON})
    @Transactional
    public Response createEmployees(@Context HttpHeaders headers, List<Employee> model) {
        for(Employee item : model){
            model = setAuditInfoForCreator(model, headers);
            employeeDao.createEmployee(item);
        }

        return Response.status(204).build();
    }

    /************************************ READ ************************************/
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Employee> getEmployees(@Context HttpHeaders headers) {
        return employeeDao.getEmployees();
    }

    @GET @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findById(@PathParam("id") Long id) {
        try{
            Employee data = employeeDao.getEmployeeById(id);
            if(data != null) {
                return Response.status(200).entity(data).build();
            } else {
                return Response.status(404).entity("The Employee with the id " + id + " does not exist").build();
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
    public Response updateEmployeeById(@Context HttpHeaders headers, @PathParam("id") Long id, Employee model) {
        if(model.getId() == null) model.setId(id);

        model = setAuditInfoForModifer(model, headers);
        String message;
        int status;
        if(EmployeeWasUpdated(model)){
            status = 200; //OK
            message = "User Employee has been updated";
        } else if(EmployeeCanBeCreated(model)){
            model = setAuditInfoForCreator(model, headers);
            employeeDao.createEmployee(model);
            status = 201; //Created
            message = "The Employee you provided has been added to the database";
        } else {
            status = 406; //Not acceptable
            message = "Failed to update the Employee data to the database";
        }

        return Response.status(status).entity(message).build();
    }

    private boolean EmployeeWasUpdated(Employee data) {
        return employeeDao.updateEmployee(data) == 1;
    }

    private boolean EmployeeCanBeCreated(Employee data) {
        return true;
    }

    /************************************ DELETE ************************************/
    @DELETE @Path("{id}")
    @Produces({MediaType.TEXT_HTML})
    @Transactional
    public Response deleteEmployeeById(@PathParam("id") Long id) {
        if(employeeDao.deleteEmployeeById(id) == 1){
            return Response.status(204).build();
        } else {
            return Response.status(404).entity("Employee with the id " + id + " is not present in the database").build();
        }
    }

    @DELETE
    @Produces({MediaType.TEXT_HTML})
    @Transactional
    public Response deleteEmployees() {
        employeeDao.deleteEmployees();
        return Response.status(200).entity("All Employees have been successfully removed").build();
    }



}

