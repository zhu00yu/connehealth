package com.connehealth.service;

import com.connehealth.dao.MasterDataDao;
import com.connehealth.dao.UserProfileDao;
import com.connehealth.entities.MasterData;
import com.connehealth.entities.User;
import com.connehealth.entities.UserProfile;
import com.connehealth.util.JsonUtil;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.glassfish.jersey.server.JSONP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;

/**
 *
 * Service class that handles REST requests
 * @author amacoder
 *
 */
@Component
@Path("/master")
public class MasterDataRestService extends BaseRestService {

    @Autowired
    protected MasterDataDao masterDataDao;
    public void setMasterDataDao(MasterDataDao masterDataDao) {
        this.masterDataDao = masterDataDao;
    }

    @Context
    Request request;

    /************************************ READ ************************************/
    @GET @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findById(@PathParam("id") Long id) {
        MasterData data = masterDataDao.getMasterDataById(id);
        if(data != null) {
            return Response.status(200).entity(data).build();
        } else {
            return Response.status(404).entity("The master with the id " + id + " does not exist").build();
        }
    }

    @GET @Path("value/{type}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findByType(@PathParam("type") String type) {
        List<Object> values = new ArrayList<Object>();
        List<MasterData> data;
        try{
            data = masterDataDao.getMasterData(type);
            if(data != null) {
                for(MasterData d : data){
                    values.add(getDataValue(d));
                }
                return Response.status(200).entity(values).build();
            } else {
                return Response.status(404).entity("The master with the type " + type + " does not exist").build();
            }
        } catch (Exception ex){
            return Response.status(404).entity(ex.getMessage()).build();
        }
    }

    @GET @Path("value/{type}/{key}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findByKey(@PathParam("type") String type, @PathParam("key") String key) {
        try {
            MasterData data = masterDataDao.getMasterDataByKey(type, key);
            if (data != null) {
                return Response.status(200).entity(getDataValue(data)).build();
            } else {
                return Response.status(404).entity("The master with the type " + type + " does not exist").build();
            }
        }catch (Exception ex) {
            return Response.status(404).entity(ex.getMessage()).build();
        }
    }

    private Object getDataValue(MasterData data){
        String dataValue = data.getDataValue();
        String dataKey = data.getDataKey();

        Object ret = null;

        switch (dataKey){
            case "array":
                ret = dataValue.split("\\|");
                break;
            case "json":
                ret = JsonUtil.jsonToMap(dataValue);
                break;
            default:
                ret = dataValue;
                break;
        }

        return ret;
    }

}

