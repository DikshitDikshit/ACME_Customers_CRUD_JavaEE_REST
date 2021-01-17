package com.algonquincollege.cst8277.rest;

import static com.algonquincollege.cst8277.utils.MyConstants.ADMIN_ROLE;
import static com.algonquincollege.cst8277.utils.MyConstants.CUSTOMER_ORDER_ID_RESOURCE_PATH;
import static com.algonquincollege.cst8277.utils.MyConstants.CUSTOMER_ORDER_RESOURCE_PATH;
import static com.algonquincollege.cst8277.utils.MyConstants.ORDER_RESOURCE_NAME;
import static com.algonquincollege.cst8277.utils.MyConstants.RESOURCE_PATH_ID_ELEMENT;
import static com.algonquincollege.cst8277.utils.MyConstants.USER_ROLE;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
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
import com.algonquincollege.cst8277.models.OrderPojo;


@Path(ORDER_RESOURCE_NAME)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OrderResources {
    
    @Inject
    protected CustomerService customerServiceBean;
    @Inject
    protected ServletContext servletContext;
    @Inject
    protected SecurityContext sc;
    
    
    @GET
    //@Path(CUSTOMER_ORDER_RESOURCE_PATH)
    public Response getOrder() {
        servletContext.log("retrieving all orders ...");
        List<OrderPojo> or = customerServiceBean.getAllOrders();
        Response response = Response.ok(or).build();
        return response;
    }



    @GET
    @RolesAllowed({ADMIN_ROLE,USER_ROLE})
    @Path(CUSTOMER_ORDER_ID_RESOURCE_PATH)
    public Response getOrderById(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
        servletContext.log("try to retrieve specific order " + id);
        OrderPojo or = customerServiceBean.getOrderById(id);
        Response response = Response.ok(or).build();
        return response;

    }


    @DELETE
    @RolesAllowed({ADMIN_ROLE})
    @Path(CUSTOMER_ORDER_RESOURCE_PATH)
    public Response deleteOrder(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
        OrderPojo result = customerServiceBean.deleteOrder(id);

        if(result != null) {
            return Response.ok().status(Response.Status.NO_CONTENT).build();
        }else {
            return Response.notModified().build();
        }
    }

    @PUT
    @RolesAllowed(ADMIN_ROLE)
    @Path(CUSTOMER_ORDER_RESOURCE_PATH)
    public Response updateOrder(OrderPojo or) {
        try {
            OrderPojo updated = customerServiceBean.updateOrder(or);
            return Response.ok().entity(updated).build();
        }
        catch (Exception ex) {
            return Response.status(409).build();
        }
    }

    @POST
    @RolesAllowed(ADMIN_ROLE)
    @Path(CUSTOMER_ORDER_RESOURCE_PATH)
    public Response createOrder(OrderPojo or) {
        customerServiceBean.createOrder(or);
        return Response.ok().entity(or).build();
    }
}
