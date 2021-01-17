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
import static com.algonquincollege.cst8277.utils.MyConstants.PRODUCT_RESOURCE_NAME;
import static com.algonquincollege.cst8277.utils.MyConstants.RESOURCE_PATH_ID_ELEMENT;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.transaction.Transactional;
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
import com.algonquincollege.cst8277.models.ProductPojo;

@Path(PRODUCT_RESOURCE_NAME)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {
    @Inject
    protected CustomerService customerServiceBean;
    @Inject
    protected ServletContext servletContext;

    @GET
    public Response getProducts() {
        servletContext.log("retrieving all products ...");
        List<ProductPojo> custs = customerServiceBean.getAllProducts();
        Response response = Response.ok(custs).build();
        return response;
    }

    @GET
    @Path("{id}")
    public Response getProductById(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
        servletContext.log("try to retrieve specific product " + id);
        ProductPojo theProduct = customerServiceBean.getProductById(id);
        Response response = Response.ok(theProduct).build();
        return response;
    }

    @Transactional
    @POST
    public Response addProduct(ProductPojo pojo) {
        if (null != pojo) {
            servletContext.log("Add product");
            ProductPojo newProduct = customerServiceBean.persistProduct(pojo);
            Response response = Response.ok(newProduct).build();
            return response;
        }
        else {
            return Response.status(BAD_REQUEST).build();
        }
    }

    @Transactional
    @PUT
    public Response updateProduct(ProductPojo pojo) {
        if (null != pojo) {
            servletContext.log("update product");
            ProductPojo newProduct = customerServiceBean.persistProduct(pojo);
            Response response = Response.ok(newProduct).build();
            return response;
        }
        else {
            return Response.status(BAD_REQUEST).build();
        }
    }

    @Transactional
    @RolesAllowed({ADMIN_ROLE})
    @DELETE
    @Path("{id}")
    public Response deleteProduct(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
        servletContext.log("Deleteing product");
        ProductPojo productPojo = customerServiceBean.deleteProduct(id);
        Response response = Response.ok(productPojo).build();
        return response;
    }
}