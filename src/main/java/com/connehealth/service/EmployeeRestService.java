package com.connehealth.service;

import com.connehealth.dao.EmployeeDao;
import com.connehealth.dao.ProviderDao;
import com.connehealth.dao.UserProfileDao;
import com.connehealth.entities.Employee;
import com.connehealth.entities.Provider;
import com.connehealth.entities.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    protected ProviderDao providerDao;
    public void setProviderDao(ProviderDao providerDao) {
        this.providerDao = providerDao;
    }

    @Autowired
    protected UserProfileDao userProfileDao;
    public void setUserProfileDao(UserProfileDao userProfileDao) {
        this.userProfileDao = userProfileDao;
    }

    @Context
    Request request;



    /************************************ CREATE ************************************/
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Transactional
    public Response createEmployee(@Context HttpHeaders headers, Employee model) {
        Long userId = model.getUserId();
        Long practiceId = model.getPracticeId();
        Provider provider = model.getProvider();
        UserProfile profile = provider != null ? provider.getUserProfile() : null;
        if (provider == null || profile == null){
            return Response.serverError().entity("Data is invalid.").build();
        }
        updateProviderInfo(headers, userId, provider, profile);

        model = setAuditInfoForCreator(model, headers);
        employeeDao.createEmployee(model);

        return Response.status(200).entity(model.getId()).build();
    }

    private boolean updateProviderInfo(HttpHeaders headers, Long userId, Provider provider, UserProfile profile){
        profile = setAuditInfoForModifer(profile, headers);
        userProfileDao.updateUserProfile(profile);

        Provider providerInDb = providerDao.getProviderById(userId);
        provider.setId(userId);
        if (providerInDb != null){
            provider = setAuditInfoForModifer(provider, headers);
            providerDao.updateProvider(provider);
        }else{
            provider = setAuditInfoForCreator(provider, headers);
            providerDao.createProvider(provider);
        }
        return true;
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
    @GET @Path("practice/{practiceId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Employee> getPracticeEmployees(@Context HttpHeaders headers, @PathParam("practiceId") Long practiceId) {
        List<Employee> employees = employeeDao.getPracticeEmployees(practiceId);
        return employees;
    }

    @GET @Path("practice/{id}/users")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getUsers(@Context HttpHeaders headers, @PathParam("id") Long id, @QueryParam("q") String q) {
        List<Map<String, String>> options = new ArrayList<>();
        String term = q;
        try{
            List<UserProfile> users = employeeDao.getFreeUsers(id, term);
            for(UserProfile u : users){
                Map<String, String> m = new HashMap<String, String>();
                m.put("id", u.getId().toString());
                m.put("text", u.getFamilyName() + u.getGivenName());
                options.add(m);
            }
        }catch(Exception ex){
            return Response.serverError().entity(ex.getMessage()).build();
        }

        return Response.ok().entity(options).build();
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

    @GET @Path("{practiceId}/{userId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findByUser(@PathParam("practiceId") Long practiceId, @PathParam("userId") Long userId) {
        try{
            Employee data = employeeDao.getEmployeeByUser(practiceId, userId);
            if(data != null) {
                return Response.status(200).entity(data).build();
            } else {
                data = new Employee();
                data.setPracticeId(practiceId);
                data.setUserId(userId);
                Provider provider = providerDao.getProviderById(userId);
                data.setProvider(provider);
                return Response.status(200).entity(data).build();
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

        Long userId = model.getUserId();
        Provider provider = model.getProvider();
        UserProfile profile = provider != null ? provider.getUserProfile() : null;
        if (provider == null || profile == null){
            return Response.serverError().entity("Data is invalid.").build();
        }
        updateProviderInfo(headers, userId, provider, profile);

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

