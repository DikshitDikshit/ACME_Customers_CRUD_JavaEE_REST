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
import static com.algonquincollege.cst8277.utils.MyConstants.CUSTOMER_RESOURCE_NAME;
import static com.algonquincollege.cst8277.utils.MyConstants.RESOURCE_PATH_ID_ELEMENT;
import static com.algonquincollege.cst8277.utils.MyConstants.USER_ROLE;
import static com.algonquincollege.cst8277.utils.MyConstants.CUSTOMER_ORDER_RESOURCE_PATH;
import static com.algonquincollege.cst8277.utils.MyConstants.RESOURCE_PATH_ID_PATH;
import static com.algonquincollege.cst8277.utils.MyConstants.CUSTOMER_ORDER_ID_RESOURCE_PATH;
import static com.algonquincollege.cst8277.utils.MyConstants.ORDER_RESOURCE_NAME;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.OK;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import javax.servlet.ServletContext;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import org.glassfish.soteria.WrappingCallerPrincipal;

import com.algonquincollege.cst8277.ejb.CustomerService;
import com.algonquincollege.cst8277.models.AddressPojo;
import com.algonquincollege.cst8277.models.CustomerPojo;
import com.algonquincollege.cst8277.models.OrderLinePojo;
import com.algonquincollege.cst8277.models.OrderPojo;
import com.algonquincollege.cst8277.models.SecurityUser;

@Path(CUSTOMER_RESOURCE_NAME)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CustomerResource {
    @Inject
    protected CustomerService customerServiceBean;
    @Inject
    protected ServletContext servletContext;
    @Inject
    protected SecurityContext sc;

    @RolesAllowed({ADMIN_ROLE})
    @GET
    public Response getCustomers() {
        servletContext.log("retrieving all customers ...");
        List<CustomerPojo> custs = customerServiceBean.getAllCustomers();
        Response response = Response.ok(custs).build();
        return response;
    }

    @GET
    @Path(RESOURCE_PATH_ID_PATH)
    @RolesAllowed({USER_ROLE,ADMIN_ROLE})
    public Response getCustomerById(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
        servletContext.log("try to retrieve specific customer " + id);
        Response response = null;
        CustomerPojo cust = null;
        if (sc.isCallerInRole(ADMIN_ROLE)) {
            cust = customerServiceBean.getCustomerById(id);
            response = Response.status(cust == null ? NOT_FOUND : OK).entity(cust).build();
        }
        else if (sc.isCallerInRole(USER_ROLE)) {
            WrappingCallerPrincipal wCallerPrincipal = (WrappingCallerPrincipal)sc.getCallerPrincipal();
            SecurityUser sUser = (SecurityUser)wCallerPrincipal.getWrapped();
            cust = sUser.getCustomer();
            if (cust != null && cust.getId() == id) {
                response = Response.status(OK).entity(cust).build();
            }
            else {
                throw new ForbiddenException();
            }
        }
        else {
            response = Response.status(BAD_REQUEST).build();
        }
        return response;
    }

    @Transactional
    @RolesAllowed({USER_ROLE,ADMIN_ROLE})
    @POST
    public Response addCustomer(CustomerPojo newCustomer) {
        Response response = null;
        if (null != newCustomer) {
            CustomerPojo newCustomerWithIdTimestamps = customerServiceBean.persistCustomer(newCustomer);
            // build a SecurityUser linked to the new customer
//            customerServiceBean.buildUserForNewCustomer(newCustomerWithIdTimestamps);
            response = Response.ok(newCustomerWithIdTimestamps).build();
        }
        else {
            Response.status(BAD_REQUEST).build();
        }
        return response;
    }

   // @Transactional
    @PUT
    @RolesAllowed({ADMIN_ROLE})
    @Path(RESOURCE_PATH_ID_PATH)
    public Response updateAddressForCustomer(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id,
        AddressPojo newAddress) {
        Response response = null;
        if (null != newAddress) {
            CustomerPojo updatedCustomer = customerServiceBean.setAddressFor(id, newAddress);
            response = Response.ok(updatedCustomer).build();
        }
        else {
            Response.status(BAD_REQUEST).build();
        }
        return response;
    }
    

    //@Transactional
    @RolesAllowed({ADMIN_ROLE})
    @DELETE
    @Path(RESOURCE_PATH_ID_PATH)
    public Response delete(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
       // Response response = null;
        CustomerPojo deletedCustomer = customerServiceBean.deletCustomer(id);
        if(deletedCustomer != null) {
            return Response.ok(deletedCustomer).status(Response.Status.NO_CONTENT).build();
        }else {
            return Response.notModified().build();
        }
    }


//    @GET
//    public Response getOrderLine() {
//        servletContext.log("retrieving all orderline ...");
//        List<OrderLinePojo> or = customerServiceBean.getAllOrderLine();
//        Response response = Response.ok(or).build();
//        return response;
//    }
//
//
//
//    @GET
//    @RolesAllowed({ADMIN_ROLE,USER_ROLE})
//    @Path("{id}")
//    public Response getOrderLineById(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
//        servletContext.log("try to retrieve specific orderline " + id);
//        OrderLinePojo or = customerServiceBean.getOrderLineById(id);
//        Response response = Response.ok(or).build();
//        return response;
//
//    }
//
//    @DELETE
//    @RolesAllowed({ADMIN_ROLE})
//    @Path("{id}")
//    public Response deleteOrderLine(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
//        boolean result = customerServiceBean.deleteOrderLine(id);
//
//        if(result) {
//            return Response.ok().status(Response.Status.NO_CONTENT).build();
//        }else {
//            return Response.notModified().build();
//        }
//    }
//
//
//    @PUT
//    @RolesAllowed(ADMIN_ROLE)
//    public Response updateOrderLines(OrderLinePojo or) {
//        try {
//            OrderLinePojo updated = customerServiceBean.updateOrderLine(or);
//            return Response.ok().entity(updated).build();
//        }
//        catch (Exception ex) {
//            return Response.status(409).build();
//        }
//    }
//
//
//    @POST
//    @RolesAllowed(ADMIN_ROLE)
//    public Response createOrderLine(OrderLinePojo or) {
//        customerServiceBean.createOrderLine(or);
//        return Response.ok().entity(or).build();
//    }
}