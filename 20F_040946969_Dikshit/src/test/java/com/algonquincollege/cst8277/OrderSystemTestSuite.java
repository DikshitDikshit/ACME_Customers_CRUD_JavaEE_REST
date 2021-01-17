/*****************************************************************c******************o*******v******id********
 * File: OrderSystemTestSuite.java
 * Course materials (20F) CST 8277
 * (Original Author) Mike Norman
 *
 * @date 2020 10
 *
 * (Modified) @author Student Name Dikshit Dikshit
 */
package com.algonquincollege.cst8277;

import static com.algonquincollege.cst8277.utils.MyConstants.APPLICATION_API_VERSION;
import static com.algonquincollege.cst8277.utils.MyConstants.CUSTOMER_RESOURCE_NAME;
import static com.algonquincollege.cst8277.utils.MyConstants.DEFAULT_ADMIN_USER;
import static com.algonquincollege.cst8277.utils.MyConstants.DEFAULT_ADMIN_USER_PASSWORD;
import static com.algonquincollege.cst8277.utils.MyConstants.DEFAULT_USER_PASSWORD;
import static com.algonquincollege.cst8277.utils.MyConstants.DEFAULT_USER_PREFIX;
import static com.algonquincollege.cst8277.utils.MyConstants.ORDER_RESOURCE_NAME;
import static com.algonquincollege.cst8277.utils.MyConstants.PRODUCT_RESOURCE_NAME;
import static com.algonquincollege.cst8277.utils.MyConstants.STORE_RESOURCE_NAME;
//import static com.algonquincollege.cst8277.utils.MyConstants.RESOURCE_PATH_ID_PATH;
import static javax.ws.rs.core.Response.Status.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.logging.LoggingFeature;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import static org.hamcrest.CoreMatchers.not;

import com.algonquincollege.cst8277.models.BillingAddressPojo;
import com.algonquincollege.cst8277.models.CustomerPojo;
import com.algonquincollege.cst8277.models.OrderLinePk;
import com.algonquincollege.cst8277.models.OrderLinePojo;
import com.algonquincollege.cst8277.models.OrderPojo;
import com.algonquincollege.cst8277.models.ProductPojo;
import com.algonquincollege.cst8277.models.ShippingAddressPojo;
import com.algonquincollege.cst8277.models.StorePojo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
public class OrderSystemTestSuite {
    private static final Class<?> _thisClaz = MethodHandles.lookup().lookupClass();
    private static final Logger logger = LoggerFactory.getLogger(_thisClaz);
    static final String APPLICATION_CONTEXT_ROOT = "rest-orderSystem";
    static final String HTTP_SCHEMA = "http";
    static final String HOST = "localhost";
    static final int PORT = 8080;
    // test fixture(s)
    static URI uri;
    static HttpAuthenticationFeature adminAuth;
    static HttpAuthenticationFeature userAuth;

    @BeforeAll
    public static void oneTimeSetUp() throws Exception {
        logger.debug("oneTimeSetUp");
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        uri = UriBuilder.fromUri(APPLICATION_CONTEXT_ROOT + APPLICATION_API_VERSION).scheme(HTTP_SCHEMA)
            .host(HOST).port(PORT).build();
        adminAuth = HttpAuthenticationFeature.basic(DEFAULT_ADMIN_USER, DEFAULT_ADMIN_USER_PASSWORD);
        userAuth = HttpAuthenticationFeature.basic(DEFAULT_USER_PREFIX, DEFAULT_USER_PASSWORD);
    }

    protected WebTarget webTarget;

    @BeforeEach
    public void setUp() {
        Client client = ClientBuilder.newClient(
            new ClientConfig().register(MyObjectMapperProvider.class).register(new LoggingFeature()));
        webTarget = client.target(uri);
    }

    @Test
    public void test01_all_customers_with_adminrole() throws JsonMappingException, JsonProcessingException {
        Builder request = webTarget.register(adminAuth).path(CUSTOMER_RESOURCE_NAME)
            .request(MediaType.APPLICATION_JSON);
        Response response = request.get();
        assertThat(response.getStatus(), is(200));
        List<CustomerPojo> custs = response.readEntity(new GenericType<List<CustomerPojo>>() {});
        assertThat(custs, is(not(empty())));
        assertThat(custs, hasSize(custs.size()));
    }

    @Test
    public void test02_all_products_with_adminrole() throws JsonMappingException, JsonProcessingException {
        Builder request = webTarget.register(adminAuth).path(PRODUCT_RESOURCE_NAME)
            .request(MediaType.APPLICATION_JSON);
        Response response = request.get();
        assertThat(response.getStatus(), is(200));
        List<ProductPojo> custs = response.readEntity(new GenericType<List<ProductPojo>>() {});
        assertThat(custs, is(not(empty())));
        assertThat(custs, hasSize(custs.size()));
    }

    @Test
    public void test03_all_stores_with_adminrole() throws JsonMappingException, JsonProcessingException {
        Builder request = webTarget.register(adminAuth).path(STORE_RESOURCE_NAME)
            .request(MediaType.APPLICATION_JSON);
        Response response = request.get();
        assertThat(response.getStatus(), is(200));
        List<StorePojo> custs = response.readEntity(new GenericType<List<StorePojo>>() {});
        assertThat(custs, is(empty()));
        assertThat(custs, hasSize(0));
    }

    @Test
    public void test04_all_orders_with_adminrole() throws JsonMappingException, JsonProcessingException {
        Builder request = webTarget.register(adminAuth).path(ORDER_RESOURCE_NAME)
            .request(MediaType.APPLICATION_JSON);
        Response response = request.get();
        assertThat(response.getStatus(), is(200));
        List<OrderPojo> custs = response.readEntity(new GenericType<List<OrderPojo>>() {});
        assertThat(custs, is(empty()));
        assertThat(custs, hasSize(custs.size()));
    }

    @Test
    public void test05_all_customers_with_userrole() throws JsonMappingException, JsonProcessingException {
        Builder request = webTarget.register(userAuth).path(CUSTOMER_RESOURCE_NAME)
            .request(MediaType.APPLICATION_JSON);
        Response response = request.get();
        assertThat(response.getStatus(), is(403));
    }

    @Test
    public void test06_all_products_with_userrole() throws JsonMappingException, JsonProcessingException {
        Builder request = webTarget.register(userAuth).path(PRODUCT_RESOURCE_NAME)
            .request(MediaType.APPLICATION_JSON);
        Response response = request.get();
        assertThat(response.getStatus(), is(200));
        List<ProductPojo> custs = response.readEntity(new GenericType<List<ProductPojo>>() {});
        assertThat(custs, is(not(empty())));
        assertThat(custs, hasSize(custs.size()));
    }

    @Test
    public void test07_all_stores_with_userrole() throws JsonMappingException, JsonProcessingException {
        Builder request = webTarget.register(userAuth).path(STORE_RESOURCE_NAME)
            .request(MediaType.APPLICATION_JSON);
        Response response = request.get();
        assertThat(response.getStatus(), is(200));
        List<StorePojo> custs = response.readEntity(new GenericType<List<StorePojo>>() {});
        assertThat(custs, is(empty()));
        assertThat(custs, hasSize(0));
    }

    @Test
    public void test08_all_orders_with_userrole() throws JsonMappingException, JsonProcessingException {
        Builder request = webTarget.register(userAuth).path(ORDER_RESOURCE_NAME)
            .request(MediaType.APPLICATION_JSON);
        Response response = request.get();
        assertThat(response.getStatus(), is(200));
        List<OrderPojo> custs = response.readEntity(new GenericType<List<OrderPojo>>() {});
        assertThat(custs, is(empty()));
        assertThat(custs, hasSize(0));
    }

    @Test
    public void test09_all_customers_with_norole() throws JsonMappingException, JsonProcessingException {
        Builder request = webTarget.path(CUSTOMER_RESOURCE_NAME).request(MediaType.APPLICATION_JSON);
        Response response = request.get();
        assertThat(response.getStatus(), is(401));
    }

    @Test
    public void test10_all_products_with_norole() throws JsonMappingException, JsonProcessingException {
        Builder request = webTarget.path(PRODUCT_RESOURCE_NAME).request(MediaType.APPLICATION_JSON);
        Response response = request.get();
        assertThat(response.getStatus(), is(200));
        List<ProductPojo> custs = response.readEntity(new GenericType<List<ProductPojo>>() {});
        assertThat(custs, is(not(empty())));
        assertThat(custs, hasSize(custs.size()));
    }

    @Test
    public void test11_all_stores_with_norole() throws JsonMappingException, JsonProcessingException {
        Builder request = webTarget.path(STORE_RESOURCE_NAME).request(MediaType.APPLICATION_JSON);
        Response response = request.get();
        assertThat(response.getStatus(), is(200));
        List<StorePojo> custs = response.readEntity(new GenericType<List<StorePojo>>() {});
        assertThat(custs, is(empty()));
        assertThat(custs, hasSize(0));
    }

    @Test
    public void test12_all_orders_with_norole() throws JsonMappingException, JsonProcessingException {
        Builder request = webTarget.path(ORDER_RESOURCE_NAME).request(MediaType.APPLICATION_JSON);
        Response response = request.get();
        assertThat(response.getStatus(), is(200));
        List<OrderPojo> custs = response.readEntity(new GenericType<List<OrderPojo>>() {});
        assertThat(custs, is(empty()));
        assertThat(custs, hasSize(0));
    }
    

    @Test
    public void test13_deleteStoreById_userRole() throws JsonMappingException, JsonProcessingException {
        StorePojo newStore = new StorePojo();
        newStore.setStoreName("Store to be delete");
        Response response = webTarget
            .register(userAuth)
            .path(STORE_RESOURCE_NAME)
            .request()
            .post(Entity.json(newStore));
        assertThat(response.getStatus(),is(OK.getStatusCode()));
       
        StorePojo s = response.readEntity(new GenericType<StorePojo>() {});
       
        
        int id = s.getId();
       
        Response responseDel = webTarget
            .register(userAuth)
            .path(STORE_RESOURCE_NAME + "/" + id)
            .request()
            .delete();
        assertThat(responseDel.getStatus(),is(FORBIDDEN.getStatusCode()));
      
    }
    @Test
    public void test14_deleteStoreById_adminRole() throws JsonMappingException, JsonProcessingException {
        StorePojo newStore = new StorePojo();
        newStore.setStoreName("Store to be delete");
        Response response = webTarget
            .register(adminAuth)
            .path(STORE_RESOURCE_NAME)
            .request()
            .post(Entity.json(newStore));
        assertThat(response.getStatus(),is(OK.getStatusCode()));
       
        StorePojo s = response.readEntity(new GenericType<StorePojo>() {});
       
       
        int id = s.getId();
       
        Response responseDel = webTarget
            .register(userAuth)
            .path(STORE_RESOURCE_NAME + "/" + id)
            .request()
            .delete();
        assertThat(responseDel.getStatus(),is(OK.getStatusCode()));
      
    }
    
    
    
    @Test
    public void test15_deleteStoreById_noRole() throws JsonMappingException, JsonProcessingException {
        StorePojo newStore = new StorePojo();
        newStore.setStoreName("Store to be delete");
        Response response = webTarget
            .path(STORE_RESOURCE_NAME)
            .request()
            .post(Entity.json(newStore));
        assertThat(response.getStatus(),is(UNAUTHORIZED.getStatusCode()));
       
        StorePojo s = response.readEntity(new GenericType<StorePojo>() {});
       
       
        int id = s.getId();
       
        Response responseDel = webTarget
            
            .path(STORE_RESOURCE_NAME + "/" + id)
            .request()
            .delete();
        assertThat(responseDel.getStatus(),is(UNAUTHORIZED.getStatusCode()));
      
    }
    
    
     
     @Test
     public void test16_add_customer_by_admin_role() throws JsonMappingException, JsonProcessingException {
     Builder request = webTarget.register(adminAuth).path(CUSTOMER_RESOURCE_NAME)
     .request(MediaType.APPLICATION_JSON);
     Response response = request.post(Entity.entity(getMockCustomer(), MediaType.APPLICATION_JSON_TYPE));
     assertThat(response.getStatus(), is(200));
     
    
     }
     @Test
     public void test16_add_customerr_by_user_role() throws JsonMappingException, JsonProcessingException {
     Builder request = webTarget.register(userAuth).path(CUSTOMER_RESOURCE_NAME)
     .request(MediaType.APPLICATION_JSON);
     Response response = request.post(Entity.entity(getMockCustomer(), MediaType.APPLICATION_JSON_TYPE));
     assertThat(response.getStatus(), is(200));
     
     }
     @Test
     public void test16_add_customerr_by_no_role() throws JsonMappingException, JsonProcessingException {
     Builder request = webTarget.path(CUSTOMER_RESOURCE_NAME)
     .request(MediaType.APPLICATION_JSON);
     Response response = request.post(Entity.entity(getMockCustomer(), MediaType.APPLICATION_JSON_TYPE));
     assertThat(response.getStatus(), is(401));
     }
     @Test
     public void test20_getCustomerById() throws JsonMappingException, JsonProcessingException {
     Builder request = webTarget.register(adminAuth).path(CUSTOMER_RESOURCE_NAME)
     .request(MediaType.APPLICATION_JSON);
     CustomerPojo cust = getMockCustomer();
     Response response = request.post(Entity.entity(cust, MediaType.APPLICATION_JSON_TYPE));
     assertThat(response.getStatus(), is(200));
     CustomerPojo customerPojo = response.readEntity(CustomerPojo.class);
     assertNotNull(customerPojo);
     response = webTarget.register(adminAuth).path(CUSTOMER_RESOURCE_NAME).path("/"+customerPojo.getId() + "")
     .request(MediaType.APPLICATION_JSON).get();
     assertThat(response.getStatus(), is(200));
     customerPojo = response.readEntity(CustomerPojo.class);
     assertNotNull(customerPojo);
     request = webTarget.register(adminAuth).path(CUSTOMER_RESOURCE_NAME).path("/"+customerPojo.getId() + "")
     .request(MediaType.APPLICATION_JSON);
     response = request.delete();
     assertThat(response.getStatus(), is(204));
     }
     

    @Test
    public void test22_delete_customer_userRole() throws JsonMappingException, JsonProcessingException {
        CustomerPojo newStore = new CustomerPojo();
        newStore.setFirstName("Store to be delete");
        Response response = webTarget.register(adminAuth)
            .path(CUSTOMER_RESOURCE_NAME)
            .request()
            .post(Entity.json(newStore));
        assertThat(response.getStatus(),is(OK.getStatusCode()));
       
        CustomerPojo s = response.readEntity(new GenericType<CustomerPojo>() {});
       
       
        int id = s.getId();
       
        Response responseDel = webTarget
            .register(userAuth)
            .path(CUSTOMER_RESOURCE_NAME + "/" + id)
            .request()
            .delete();
        assertThat(responseDel.getStatus(),is(204));
    }
    
    @Test
    public void test23_delete_customer_adminRole() throws JsonMappingException, JsonProcessingException {
        CustomerPojo newStore = new CustomerPojo();
        newStore.setFirstName("Store to be delete");
        Response response = webTarget.register(adminAuth)
            .path(CUSTOMER_RESOURCE_NAME)
            .request()
            .post(Entity.json(newStore));
        assertThat(response.getStatus(),is(OK.getStatusCode()));
       
        CustomerPojo s = response.readEntity(new GenericType<CustomerPojo>() {});
       
       
        int id = s.getId();
       
        Response responseDel = webTarget
            .register(adminAuth)
            .path(CUSTOMER_RESOURCE_NAME + "/" + id)
            .request()
            .delete();
        assertThat(responseDel.getStatus(),is(204));
    }
    
    @Test
    public void test24_delete_customer_noRole() throws JsonMappingException, JsonProcessingException {
        CustomerPojo newStore = new CustomerPojo();
        newStore.setFirstName("Store to be delete");
        Response response = webTarget.register(adminAuth)
            .path(CUSTOMER_RESOURCE_NAME)
            .request()
            .post(Entity.json(newStore));
        assertThat(response.getStatus(),is(OK.getStatusCode()));
       
        CustomerPojo s = response.readEntity(new GenericType<CustomerPojo>() {});
       
       
        int id = s.getId();
       
        Response responseDel = webTarget
            .path(CUSTOMER_RESOURCE_NAME + "/" + id)
            .request()
            .delete();
        assertThat(responseDel.getStatus(),is(204));
    }
    
    
     @Test
     public void test25_add_product_by_admin_role() throws JsonMappingException, JsonProcessingException {
     Builder request = webTarget.register(adminAuth).path(PRODUCT_RESOURCE_NAME)
     .request(MediaType.APPLICATION_JSON);
     ProductPojo pojo = getMockProduct();
     Response response = request.post(Entity.entity(pojo, MediaType.APPLICATION_JSON_TYPE));
     assertThat(response.getStatus(), is(200));
     ProductPojo productPojo = (ProductPojo)response.readEntity(ProductPojo.class);
     assertNotNull(productPojo);
     request = webTarget.register(adminAuth).path(PRODUCT_RESOURCE_NAME).path("/"+productPojo.getId())
     .request(MediaType.APPLICATION_JSON);
     response = request.delete();
     assertThat(response.getStatus(), is(200));
     }
     
     @Test
     public void test25_add_product_by_user_role() throws JsonMappingException, JsonProcessingException {
     Builder request = webTarget.register(userAuth).path(PRODUCT_RESOURCE_NAME)
     .request(MediaType.APPLICATION_JSON);
     ProductPojo pojo = getMockProduct();
     Response response = request.post(Entity.entity(pojo, MediaType.APPLICATION_JSON_TYPE));
     assertThat(response.getStatus(), is(200));
     
     }
     @Test
     public void test25_add_product_by_no_role() throws JsonMappingException, JsonProcessingException {
     Builder request = webTarget.path(PRODUCT_RESOURCE_NAME)
     .request(MediaType.APPLICATION_JSON);
     ProductPojo pojo = getMockProduct();
     Response response = request.post(Entity.entity(pojo, MediaType.APPLICATION_JSON_TYPE));
     assertThat(response.getStatus(), is(200));
     ProductPojo productPojo = (ProductPojo)response.readEntity(ProductPojo.class);
     assertNotNull(productPojo);
     request = webTarget.register(adminAuth).path(PRODUCT_RESOURCE_NAME).path("/"+productPojo.getId())
     .request(MediaType.APPLICATION_JSON);
     response = request.delete();
     assertThat(response.getStatus(), is(200));
     }
    //
     @Test
     public void test26_update_product() throws JsonMappingException, JsonProcessingException {
     Builder request = webTarget.register(adminAuth).path(PRODUCT_RESOURCE_NAME)
     .request(MediaType.APPLICATION_JSON);
     ProductPojo pojo = getMockProduct();
     Response response = request.post(Entity.entity(pojo, MediaType.APPLICATION_JSON_TYPE));
     assertThat(response.getStatus(), is(200));
     ProductPojo productPojo = (ProductPojo)response.readEntity(ProductPojo.class);
     assertNotNull(productPojo);
     productPojo.setSerialNo("989-998-2892");
     response = request.put(Entity.entity(pojo, MediaType.APPLICATION_JSON_TYPE));
     assertThat(response.getStatus(), is(200));
     response = webTarget.register(adminAuth).path(PRODUCT_RESOURCE_NAME).path("/"+productPojo.getId())
     .request(MediaType.APPLICATION_JSON).delete();
     
     assertThat(response.getStatus(), is(200));
     }
     @Test
     public void test27_get_product_by_id() throws JsonMappingException, JsonProcessingException {
     Builder request = webTarget.register(adminAuth).path(PRODUCT_RESOURCE_NAME)
     .request(MediaType.APPLICATION_JSON);
     ProductPojo pojo = getMockProduct();
     Response response = request.post(Entity.entity(pojo, MediaType.APPLICATION_JSON_TYPE));
     assertThat(response.getStatus(), is(200));
     ProductPojo productPojo = (ProductPojo)response.readEntity(ProductPojo.class);
     assertNotNull(productPojo);
     request = webTarget.register(userAuth).path(PRODUCT_RESOURCE_NAME).path("/"+productPojo.getId())
     .request(MediaType.APPLICATION_JSON);
     response = request.get();
     assertThat(response.getStatus(), is(200));
     productPojo = (ProductPojo)response.readEntity(ProductPojo.class);
     assertNotNull(productPojo);
     response = webTarget.register(adminAuth).path(PRODUCT_RESOURCE_NAME).path("/"+productPojo.getId())
     .request(MediaType.APPLICATION_JSON).delete();
    
     assertThat(response.getStatus(), is(200));
     }
     @Test
     public void test28_delete_product_by_adminRole() throws JsonMappingException, JsonProcessingException {
     Builder request = webTarget.register(adminAuth).path(PRODUCT_RESOURCE_NAME)
     .request(MediaType.APPLICATION_JSON);
     ProductPojo pojo = getMockProduct();
     Response response = request.post(Entity.entity(pojo, MediaType.APPLICATION_JSON_TYPE));
     assertThat(response.getStatus(), is(200));
     ProductPojo productPojo = (ProductPojo)response.readEntity(ProductPojo.class);
     assertNotNull(productPojo);
     response = webTarget.register(adminAuth).path(PRODUCT_RESOURCE_NAME).path("/"+productPojo.getId()+ "")
     .request(MediaType.APPLICATION_JSON).delete();
    
     assertThat(response.getStatus(), is(200));
     }
     @Test
     public void test28_delete_product_by_userRole() throws JsonMappingException, JsonProcessingException {
     Builder request = webTarget.register(userAuth).path(PRODUCT_RESOURCE_NAME)
     .request(MediaType.APPLICATION_JSON);
     ProductPojo pojo = getMockProduct();
     Response response = request.post(Entity.entity(pojo, MediaType.APPLICATION_JSON_TYPE));
     assertThat(response.getStatus(), is(200));
     ProductPojo productPojo = (ProductPojo)response.readEntity(ProductPojo.class);
     assertNotNull(productPojo);
     response = webTarget.register(userAuth).path(PRODUCT_RESOURCE_NAME).path("/"+productPojo.getId()+ "")
     .request(MediaType.APPLICATION_JSON).delete();
    
     assertThat(response.getStatus(), is(403));
     }
     
     @Test
     public void test28_delete_product_by_noRole() throws JsonMappingException, JsonProcessingException {
     Builder request = webTarget.path(PRODUCT_RESOURCE_NAME)
     .request(MediaType.APPLICATION_JSON);
     ProductPojo pojo = getMockProduct();
     Response response = request.post(Entity.entity(pojo, MediaType.APPLICATION_JSON_TYPE));
     assertThat(response.getStatus(), is(200));
     ProductPojo productPojo = (ProductPojo)response.readEntity(ProductPojo.class);
     assertNotNull(productPojo);
     response = webTarget.path(PRODUCT_RESOURCE_NAME).path("/"+productPojo.getId()+ "")
     .request(MediaType.APPLICATION_JSON).delete();
    
     assertThat(response.getStatus(), is(401));
     }
    //
     @Test
     public void test29_add_store_by_adminRole() throws JsonMappingException, JsonProcessingException {
     Builder request = webTarget.register(adminAuth).path(STORE_RESOURCE_NAME)
     .request(MediaType.APPLICATION_JSON);
     StorePojo pojo = getMockStore();
     Response response = request.post(Entity.entity(pojo, MediaType.APPLICATION_JSON_TYPE));
     assertThat(response.getStatus(), is(200));
     StorePojo storePojo = (StorePojo)response.readEntity(StorePojo.class);
     assertNotNull(storePojo);
     response = webTarget.register(adminAuth).path(STORE_RESOURCE_NAME).path("/"+storePojo.getId())
     .request(MediaType.APPLICATION_JSON).delete();
    
     assertThat(response.getStatus(), is(200));
     }
     @Test
     public void test29_add_store_by_useRole() throws JsonMappingException, JsonProcessingException {
     Builder request = webTarget.register(userAuth).path(STORE_RESOURCE_NAME)
     .request(MediaType.APPLICATION_JSON);
     StorePojo pojo = getMockStore();
     Response response = request.post(Entity.entity(pojo, MediaType.APPLICATION_JSON_TYPE));
     assertThat(response.getStatus(), is(200));
     
     }
     @Test
     public void test29_add_store_by_noRole() throws JsonMappingException, JsonProcessingException {
     Builder request = webTarget.path(STORE_RESOURCE_NAME)
     .request(MediaType.APPLICATION_JSON);
     StorePojo pojo = getMockStore();
     Response response = request.post(Entity.entity(pojo, MediaType.APPLICATION_JSON_TYPE));
     assertThat(response.getStatus(), is(401));
     StorePojo storePojo = (StorePojo)response.readEntity(StorePojo.class);
     assertNotNull(storePojo);
     response = webTarget.register(adminAuth).path(STORE_RESOURCE_NAME).path("/"+storePojo.getId())
     .request(MediaType.APPLICATION_JSON).delete();
    
     assertThat(response.getStatus(), is(200));
     }
    //
     @Test
     public void test30_update_store_adminRole() throws JsonMappingException, JsonProcessingException {
     Builder request = webTarget.register(adminAuth).path(STORE_RESOURCE_NAME)
     .request(MediaType.APPLICATION_JSON);
     StorePojo pojo = getMockStore();
     Response response = request.post(Entity.entity(pojo, MediaType.APPLICATION_JSON_TYPE));
     assertThat(response.getStatus(), is(200));
     StorePojo storePojo = (StorePojo)response.readEntity(StorePojo.class);
     assertNotNull(storePojo);
     storePojo.setStoreName("ABC Store");
     response = request.put(Entity.entity(pojo, MediaType.APPLICATION_JSON_TYPE));
     assertThat(response.getStatus(), is(200));
     request = webTarget.register(adminAuth).path(PRODUCT_RESOURCE_NAME).path(storePojo.getId() + "")
     .request(MediaType.APPLICATION_JSON);
     response = request.delete();
     assertThat(response.getStatus(), is(200));
     }
     @Test
     public void test30_update_store_by_userRole() throws JsonMappingException, JsonProcessingException {
     Builder request = webTarget.register(userAuth).path(STORE_RESOURCE_NAME)
     .request(MediaType.APPLICATION_JSON);
     StorePojo pojo = getMockStore();
     Response response = request.post(Entity.entity(pojo, MediaType.APPLICATION_JSON_TYPE));
     assertThat(response.getStatus(), is(200));
     StorePojo storePojo = (StorePojo)response.readEntity(StorePojo.class);
     assertNotNull(storePojo);
     storePojo.setStoreName("ABC Store");
     response = request.put(Entity.entity(pojo, MediaType.APPLICATION_JSON_TYPE));
     assertThat(response.getStatus(), is(200));
     request = webTarget.path(PRODUCT_RESOURCE_NAME).path(storePojo.getId() + "")
     .request(MediaType.APPLICATION_JSON);
     response = request.delete();
     assertThat(response.getStatus(), is(403));
     }
     @Test
     public void test30_update_store_by_noRole() throws JsonMappingException, JsonProcessingException {
     Builder request = webTarget.path(STORE_RESOURCE_NAME)
     .request(MediaType.APPLICATION_JSON);
     StorePojo pojo = getMockStore();
     Response response = request.post(Entity.entity(pojo, MediaType.APPLICATION_JSON_TYPE));
     assertThat(response.getStatus(), is(401));
     StorePojo storePojo = (StorePojo)response.readEntity(StorePojo.class);
     assertNotNull(storePojo);
     storePojo.setStoreName("ABC Store");
     response = request.put(Entity.entity(pojo, MediaType.APPLICATION_JSON_TYPE));
     assertThat(response.getStatus(), is(200));
     request = webTarget.register(adminAuth).path(PRODUCT_RESOURCE_NAME).path(storePojo.getId() + "")
     .request(MediaType.APPLICATION_JSON);
     response = request.delete();
     assertThat(response.getStatus(), is(200));
     }
    // @Test
     public void test31_get_store_by_id() throws JsonMappingException, JsonProcessingException {
     Builder request = webTarget.register(userAuth).path(STORE_RESOURCE_NAME)
     .request(MediaType.APPLICATION_JSON);
     StorePojo pojo = getMockStore();
     Response response = request.post(Entity.entity(pojo, MediaType.APPLICATION_JSON_TYPE));
     assertThat(response.getStatus(), is(200));
     StorePojo storePojo = (StorePojo)response.getEntity();
     assertNotNull(storePojo);
     request = webTarget.register(userAuth).path(STORE_RESOURCE_NAME).path(storePojo.getId() + "")
     .request(MediaType.APPLICATION_JSON);
     response = request.get();
     assertThat(response.getStatus(), is(200));
     storePojo = (StorePojo)response.getEntity();
     assertNotNull(storePojo);
     request = webTarget.register(adminAuth).path(STORE_RESOURCE_NAME).path(storePojo.getId() + "")
     .request(MediaType.APPLICATION_JSON);
     response = request.delete();
     assertThat(response.getStatus(), is(200));
     }
     
     @Test
     public void test35_null_add_customer() throws JsonMappingException, JsonProcessingException {
     Builder request = webTarget.register(userAuth).path(CUSTOMER_RESOURCE_NAME)
     .request(MediaType.APPLICATION_JSON);
     Response response = request.post(null);
     assertThat(response.getStatus(), is(204));
     }
    
     @Test
     public void test36_null_add_product() throws JsonMappingException, JsonProcessingException {
     Builder request = webTarget.register(userAuth).path(PRODUCT_RESOURCE_NAME)
     .request(MediaType.APPLICATION_JSON);
     Response response = request.post(null);
     assertThat(response.getStatus(), is(400));
     }
    
     @Test
     public void test37_null_add_store() throws JsonMappingException, JsonProcessingException {
     Builder request = webTarget.register(userAuth).path(STORE_RESOURCE_NAME)
     .request(MediaType.APPLICATION_JSON);
     Response response = request.post(null);
     assertThat(response.getStatus(), is(400));
     }
    
     @Test
     public void test38_null_add_order() throws JsonMappingException, JsonProcessingException {
         Response response = webTarget.register(adminAuth).path(ORDER_RESOURCE_NAME)
     .request(MediaType.APPLICATION_JSON).post(null);
     assertThat(response.getStatus(), is(405));
     }

    // Get mock customer
    public CustomerPojo getMockCustomer() {
        CustomerPojo expected = new CustomerPojo();
        expected.setId(1);
        expected.setPhoneNumber("899-229-2242");
        expected.setFirstName("Maria");
        expected.setLastName("Johnson");
        expected.setEmail("mjohnson@gmail.com");
        return expected;
    }

    /**
     * Billing address
     * 
     * @return
     */
    public BillingAddressPojo getBillingAddress() {
        BillingAddressPojo billingAddress = new BillingAddressPojo();
        billingAddress.setId(2);
        billingAddress.setAlsoShipping(false);
        billingAddress.setCity("California");
        billingAddress.setCountry("USA");
        billingAddress.setState("CA");
        billingAddress.setPostal("90093");
        billingAddress.setStreet("LG Street");
        return billingAddress;
    }

    /**
     * Shipping address
     * 
     * @return
     */
    public ShippingAddressPojo getShippingAddress() {
        ShippingAddressPojo shippingAddress = new ShippingAddressPojo();
        shippingAddress.setId(1);
        shippingAddress.setCity("California");
        shippingAddress.setCountry("USA");
        shippingAddress.setState("CA");
        shippingAddress.setPostal("90099");
        shippingAddress.setStreet("Pop John Street");
        return shippingAddress;
    }

    /**
     * Get the mock order
     * 
     * @param actualCustomer
     * @param actualProduct
     * @return
     */
    public OrderPojo getMockOrder(CustomerPojo actualCustomer, ProductPojo actualProduct) {
        OrderPojo expectedOrder = new OrderPojo();
        expectedOrder.setId(1);
        expectedOrder.setOwningCustomer(actualCustomer);
        expectedOrder.setDescription("New year gift");
        OrderLinePk linePk = new OrderLinePk();
        linePk.setOrderLineNo(1);
        OrderLinePojo orderLine = new OrderLinePojo();
        orderLine.setProduct(actualProduct);
        orderLine.setPk(linePk);
        orderLine.setAmount(89.0);
        orderLine.setPrice(89.0);
        expectedOrder.addOrderline(orderLine);
        actualCustomer.getOrders().add(expectedOrder);
        return expectedOrder;
    }

    /**
     * Get the mock store
     * 
     * @return
     */
    public StorePojo getMockStore() {
        StorePojo expected = new StorePojo();
        expected.setId(1);
        expected.setStoreName("GK Mart");
        return expected;
    }

    /**
     * Get mock product
     * 
     * @return
     */
    public ProductPojo getMockProduct() {
        ProductPojo expected = new ProductPojo();
        expected.setId(1);
        expected.setSerialNo("863-733-3833");
        expected.setDescription(
            "Elmer’s Crunchy Slime Kit | Slime Supplies Include Metallic Liquid Glue, Clear Liquid Glue, Crunchy Magical Liquid Slime Activator, 4 Count");
        return expected;
    }
}