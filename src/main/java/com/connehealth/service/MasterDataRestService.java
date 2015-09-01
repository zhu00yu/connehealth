package com.connehealth.service;

import com.connehealth.dao.*;
import com.connehealth.entities.*;
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

    @Autowired
    protected Icd10Dao icd10Dao;
    public void setIcd10Dao(Icd10Dao icd10Dao) {
        this.icd10Dao = icd10Dao;
    }

    @Autowired
    protected DrugDao drugDao;
    public void setDrugDao(DrugDao drugDao) {
        this.drugDao = drugDao;
    }

    @Autowired
    protected AllergenDao allergenDao;
    public void setAllergenDao(AllergenDao allergenDao) {
        this.allergenDao = allergenDao;
    }

    @Autowired
    protected AdverseReactionDao adverseReactionDao;
    public void setAdverseReactionDao(AdverseReactionDao adverseReactionDao) {
        this.adverseReactionDao = adverseReactionDao;
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

    // For ICD10
    @GET @Path("icd/list/{term}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findIcd(@PathParam("term") String term) {

        List<Icd10> icds = new ArrayList<Icd10>();
        try{
            icds = icd10Dao.getIcds(term.toUpperCase());
        } catch(Exception ex){
            return Response.serverError().entity(ex.getMessage()).build();
        }

        if(icds != null && icds.size() > 0) {
            return Response.status(200).entity(icds).build();
        } else {
            return Response.status(404).entity("The icd with the " + term + " does not exist").build();
        }
    }

    @GET @Path("icd/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findIcdById(@PathParam("id") Long id) {

        Icd10 icd = null;
        try{
            icd = icd10Dao.getIcdById(id);
        } catch(Exception ex){
            return Response.serverError().entity(ex.getMessage()).build();
        }

        if(icd != null) {
            return Response.status(200).entity(icd).build();
        } else {
            return Response.status(404).entity("The icd with the id " + id + " does not exist").build();
        }
    }

    @GET @Path("icd/query/options")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getIcdOptions(@QueryParam("q") String q) {
        String term = q;
        List<Map<String, String>> options = new ArrayList<Map<String, String>>();
        if (term != null){
            List<Icd10> icds = icd10Dao.getIcds(term.toUpperCase());
            if(icds != null) {
                for(Icd10 p : icds){
                    Map<String, String> m = new HashMap<String, String>();
                    m.put("id", p.getId().toString());
                    m.put("text", p.getName());
                    options.add(m);
                }
                return Response.status(200).entity(options).build();
            }
        }

        return Response.status(200).build();
    }

    // For DRUG
    @GET @Path("drug/list/{term}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findDrug(@PathParam("term") String term) {

        List<Drug> drugs = new ArrayList<Drug>();
        try{
            drugs = drugDao.getDrugs(term.toUpperCase());
        } catch(Exception ex){
            return Response.serverError().entity(ex.getMessage()).build();
        }

        if(drugs != null && drugs.size() > 0) {
            return Response.status(200).entity(drugs).build();
        } else {
            return Response.status(404).entity("The Drug with the " + term + " does not exist").build();
        }
    }

    @GET @Path("drug/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findDrugById(@PathParam("id") Long id) {

        Drug drug = null;
        try{
            drug = drugDao.getDrugById(id);
        } catch(Exception ex){
            return Response.serverError().entity(ex.getMessage()).build();
        }

        if(drug != null) {
            return Response.status(200).entity(drug).build();
        } else {
            return Response.status(404).entity("The Drug with the id " + id + " does not exist").build();
        }
    }

    @GET @Path("drug/query/options")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getDrugOptions(@QueryParam("q") String q) {
        String term = q;
        List<Map<String, String>> options = new ArrayList<Map<String, String>>();
        if (term != null){
            try{
                List<Drug> drugs = drugDao.getDrugs(term.toUpperCase());
                if(drugs != null) {
                    for(Drug p : drugs){
                        Map<String, String> m = new HashMap<String, String>();
                        m.put("id", p.getId().toString());
                        m.put("text", p.getBrandName());
                        options.add(m);
                    }
                    return Response.status(200).entity(options).build();
                }

            }catch(Exception ex){
                return Response.serverError().entity(ex.getMessage()).build();
            }
        }

        return Response.status(200).build();
    }

    // For ALLERGEN
    @GET @Path("allergen/list/{term}/{type}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findAllergen(@PathParam("term") String term, @PathParam("type") String type) {

        List<Allergen> allergens = new ArrayList<Allergen>();
        try{
            allergens = allergenDao.getAllergens(type, term.toUpperCase());
        } catch(Exception ex){
            return Response.serverError().entity(ex.getMessage()).build();
        }

        if(allergens != null && allergens.size() > 0) {
            return Response.status(200).entity(allergens).build();
        } else {
            return Response.status(404).entity("The Drug with the " + term + " does not exist").build();
        }
    }

    @GET @Path("allergen/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findAllergenById(@PathParam("id") Long id) {

        Allergen allergen = null;
        try{
            allergen = allergenDao.getAllergenById(id);
        } catch(Exception ex){
            return Response.serverError().entity(ex.getMessage()).build();
        }

        if(allergen != null) {
            return Response.status(200).entity(allergen).build();
        } else {
            return Response.status(404).entity("The Drug with the id " + id + " does not exist").build();
        }
    }

    @GET @Path("allergen/query/options")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAllergenOptions(@QueryParam("q") String q) {
        String term = q;
        List<Map<String, String>> options = new ArrayList<Map<String, String>>();
        if (term != null){
            try{
                List<Allergen> allergens = allergenDao.getAllergens(null, term.toUpperCase());
                if(allergens != null) {
                    for(Allergen p : allergens){
                        Map<String, String> m = new HashMap<String, String>();
                        m.put("id", p.getId().toString());
                        m.put("text", p.getName());
                        options.add(m);
                    }
                    return Response.status(200).entity(options).build();
                }

            }catch(Exception ex){
                return Response.serverError().entity(ex.getMessage()).build();
            }
        }

        return Response.status(200).build();
    }

    // For ADVERSE REACTION
    @GET @Path("adverse-reaction/list/{term}/{type}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findAdverseReaction(@PathParam("term") String term, @PathParam("type") String type) {

        List<AdverseReaction> adverseReactions = new ArrayList<AdverseReaction>();
        try{
            adverseReactions = adverseReactionDao.getAdverseReactions(type, term.toUpperCase());
        } catch(Exception ex){
            return Response.serverError().entity(ex.getMessage()).build();
        }

        if(adverseReactions != null && adverseReactions.size() > 0) {
            return Response.status(200).entity(adverseReactions).build();
        } else {
            return Response.status(404).entity("The Drug with the " + term + " does not exist").build();
        }
    }

    @GET @Path("adverse-reaction/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findAdverseReactionById(@PathParam("id") Long id) {

        AdverseReaction adverseReaction = null;
        try{
            adverseReaction = adverseReactionDao.getAdverseReactionById(id);
        } catch(Exception ex){
            return Response.serverError().entity(ex.getMessage()).build();
        }

        if(adverseReaction != null) {
            return Response.status(200).entity(adverseReaction).build();
        } else {
            return Response.status(404).entity("The Drug with the id " + id + " does not exist").build();
        }
    }

    @GET @Path("adverse-reaction/query/options")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAdverseReactionOptions(@QueryParam("q") String q) {
        String term = q;
        List<Map<String, String>> options = new ArrayList<Map<String, String>>();
        if (term != null){
            try{
                List<AdverseReaction> adverseReactions = adverseReactionDao.getAdverseReactions(null, term.toUpperCase());
                if(adverseReactions != null) {
                    for(AdverseReaction p : adverseReactions){
                        Map<String, String> m = new HashMap<String, String>();
                        m.put("id", p.getId().toString());
                        m.put("text", p.getReaction());
                        options.add(m);
                    }
                    return Response.status(200).entity(options).build();
                }

            }catch(Exception ex){
                return Response.serverError().entity(ex.getMessage()).build();
            }
        }

        return Response.status(200).build();
    }

}

