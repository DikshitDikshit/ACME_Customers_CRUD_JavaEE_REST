/*****************************************************************c******************o*******v******id********
 * File: CustomerResource.java
 * Course materials (20F) CST 8277
 *
 * @author (original) Mike Norman
 * 
 * update by : I. Am. A. Student 040946969
 *
 */
package com.algonquincollege.cst8277.rest;

import static com.algonquincollege.cst8277.utils.MyConstants.ADMIN_ROLE;
import static com.algonquincollege.cst8277.utils.MyConstants.RESOURCE_PATH_ID_ELEMENT;
import static com.algonquincollege.cst8277.utils.MyConstants.STORE_RESOURCE_NAME;
import static com.algonquincollege.cst8277.utils.MyConstants.USER_ROLE;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.algonquincollege.cst8277.ejb.CustomerService;
import com.algonquincollege.cst8277.models.StorePojo;

@Path(STORE_RESOURCE_NAME)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StoreResource {
    @Inject
    protected CustomerService customerServiceBean;
    @Inject
    protected ServletContext servletContext;

    @GET
    public Response getStores() {
        servletContext.log("retrieving all stores ...");
        List<StorePojo> stores = customerServiceBean.getAllStores();
        Response response = Response.ok(stores).build();
        return response;
    }

    @GET
    @Path("{id}")
    public Response getStoreById(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
        servletContext.log("try to retrieve specific store " + id);
        StorePojo theStore = customerServiceBean.getStoreById(id);
        Response response = Response.ok(theStore).build();
        return response;
    }

    @PUT
    public Response updateStore(StorePojo storePojo) {
        if (null != storePojo) {
            servletContext.log("try to update specific store ");
            StorePojo theStore = customerServiceBean.updateStore(storePojo);
            Response response = Response.ok(theStore).build();
            return response;
        }
        else {
            return Response.status(BAD_REQUEST).build();
        }
    }

    @POST
    @RolesAllowed({USER_ROLE,ADMIN_ROLE})
    public Response addStore(StorePojo storePojo) {
        if (null != storePojo) {
            servletContext.log("try to add store ");
            StorePojo theStore = customerServiceBean.updateStore(storePojo);
            Response response = Response.ok(theStore).build();
            return response;
        }
        else {
            return Response.status(BAD_REQUEST).build();
        }
    }

    @RolesAllowed({ADMIN_ROLE})
    @DELETE
    @Path("{id}")
    public Response updateStoreName(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
        servletContext.log("try to retrieve specific store " + id);
        StorePojo theStore = customerServiceBean.deletStore(id);
        Response response = Response.ok(theStore).build();
        return response;
    }
}