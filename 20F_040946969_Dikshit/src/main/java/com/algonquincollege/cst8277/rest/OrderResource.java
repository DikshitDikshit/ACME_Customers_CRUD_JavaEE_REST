///*****************************************************************c******************o*******v******id********
// * File: ProductResource.java
// * Course materials (20F) CST 8277
// *
// * @author (original) Mike Norman
// *
// * update by : I. Am. A. Student 040nnnnnnn
// *
// */
//package com.algonquincollege.cst8277.rest;
//
//import static com.algonquincollege.cst8277.utils.MyConstants.ADMIN_ROLE;
//import static com.algonquincollege.cst8277.utils.MyConstants.ORDER_RESOURCE_NAME;
//import static com.algonquincollege.cst8277.utils.MyConstants.RESOURCE_PATH_ID_ELEMENT;
//import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
//
//import java.util.List;
//
//import javax.annotation.security.RolesAllowed;
//import javax.inject.Inject;
//import javax.servlet.ServletContext;
//import javax.ws.rs.Consumes;
//import javax.ws.rs.DELETE;
//import javax.ws.rs.GET;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//
//import com.algonquincollege.cst8277.ejb.CustomerService;
//import com.algonquincollege.cst8277.models.OrderPojo;
//
//@Path(ORDER_RESOURCE_NAME)
//@Consumes(MediaType.APPLICATION_JSON)
//@Produces(MediaType.APPLICATION_JSON)
//public class OrderResource {
//    @Inject
//    protected CustomerService customerServiceBean;
//    @Inject
//    protected ServletContext servletContext;
//
//    @GET
//    public Response getAllOrders() {
//        servletContext.log("retrieving all orders ...");
//        List<OrderPojo> orders = customerServiceBean.getAllOrders();
//        Response response = Response.ok(orders).build();
//        return response;
//    }
//
//    @GET
//    @Path("{id}")
//    public Response getOrderById(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
//        servletContext.log("try to retrieve specific order " + id);
//        OrderPojo orderPojo = customerServiceBean.getOrderbyId(id);
//        Response response = Response.ok(orderPojo).build();
//        return response;
//    }
//
//    @POST
//    public Response addOrder(OrderPojo pojo) {
//        if (null != pojo) {
//            servletContext.log("Add Order");
//            OrderPojo orderPojo = customerServiceBean.persistOrder(pojo);
//            Response response = Response.ok(orderPojo).build();
//            return response;
//        }
//        else {
//            return Response.status(BAD_REQUEST).build();
//        }
//    }
//
//    @POST
//    public Response updateOrder(OrderPojo pojo) {
//        if (null != pojo) {
//            servletContext.log("Update Order");
//            OrderPojo orderPojo = customerServiceBean.persistOrder(pojo);
//            Response response = Response.ok(orderPojo).build();
//            return response;
//        }
//        else {
//            return Response.status(BAD_REQUEST).build();
//        }
//    }
//
//    @RolesAllowed({ADMIN_ROLE})
//    @DELETE
//    @Path("{id}")
//    public Response deleteorder(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
//        servletContext.log("Deleteing order");
//        OrderPojo orderPojo = customerServiceBean.deleteOrder(id);
//        Response response = Response.ok(orderPojo).build();
//        return response;
//    }
//}