/*****************************************************************c******************o*******v******id********
 * File: CustomerService.java
 * Course materials (20F) CST 8277
 *
 * @author (original) Mike Norman
 * 
 * update by : I. Am. A. Student 040nnnnnnn
 *
 */
package com.algonquincollege.cst8277.ejb;

//import static com.algonquincollege.cst8277.models.SecurityRole.ROLE_BY_NAME_QUERY;
//import static com.algonquincollege.cst8277.utils.MyConstants.DEFAULT_KEY_SIZE;
//import static com.algonquincollege.cst8277.utils.MyConstants.DEFAULT_PROPERTY_ALGORITHM;
//import static com.algonquincollege.cst8277.utils.MyConstants.DEFAULT_PROPERTY_ITERATIONS;
//import static com.algonquincollege.cst8277.utils.MyConstants.DEFAULT_SALT_SIZE;
//import static com.algonquincollege.cst8277.utils.MyConstants.DEFAULT_USER_PASSWORD;
//import static com.algonquincollege.cst8277.utils.MyConstants.DEFAULT_USER_PREFIX;
//import static com.algonquincollege.cst8277.utils.MyConstants.PARAM1;
//import static com.algonquincollege.cst8277.utils.MyConstants.PROPERTY_ALGORITHM;
//import static com.algonquincollege.cst8277.utils.MyConstants.PROPERTY_ITERATIONS;
//import static com.algonquincollege.cst8277.utils.MyConstants.PROPERTY_KEYSIZE;
//import static com.algonquincollege.cst8277.utils.MyConstants.PROPERTY_SALTSIZE;
//import static com.algonquincollege.cst8277.utils.MyConstants.USER_ROLE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
//import javax.transaction.Transactional;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import javax.transaction.Transactional;

import com.algonquincollege.cst8277.models.AddressPojo;
import com.algonquincollege.cst8277.models.CustomerPojo;
import com.algonquincollege.cst8277.models.OrderLinePojo;
import com.algonquincollege.cst8277.models.OrderLinePojo_;
import com.algonquincollege.cst8277.models.OrderPojo;
import com.algonquincollege.cst8277.models.OrderPojo_;
import com.algonquincollege.cst8277.models.ProductPojo;
import com.algonquincollege.cst8277.models.SecurityRole;
import com.algonquincollege.cst8277.models.SecurityUser;
import com.algonquincollege.cst8277.models.ShippingAddressPojo;
import com.algonquincollege.cst8277.models.StorePojo;
import com.algonquincollege.cst8277.utils.MyConstants;

/**
 * Stateless Singleton Session Bean - CustomerService
 */
@Singleton
public class CustomerService implements Serializable {
    private static final long serialVersionUID = 1L;
    @PersistenceContext(name = MyConstants.PU_NAME)
    protected EntityManager em;
    @Inject
    protected Pbkdf2PasswordHash pbAndjPasswordHash;

    /**
     * Get All customers
     * 
     * @return List of customers
     */
    public List<CustomerPojo> getAllCustomers() {
        List<CustomerPojo> allCustomers = new ArrayList<CustomerPojo>();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<CustomerPojo> q = cb.createQuery(CustomerPojo.class);
            Root<CustomerPojo> c = q.from(CustomerPojo.class);
            q.select(c);
            TypedQuery<CustomerPojo> q2 = em.createQuery(q);
            allCustomers.addAll(q2.getResultList());
        }
        catch (Exception e) {
        }
        return allCustomers;
    }

    /**
     * Get customer by id
     * 
     * @param custPK
     * @return
     */
    public CustomerPojo getCustomerById(int custPK) {
        return em.find(CustomerPojo.class, custPK);
    }

    /**
     * Persist customer
     * 
     * @param newCustomer
     * @return
     */
    @Transactional
    public CustomerPojo persistCustomer(CustomerPojo newCustomer) {
        em.persist(newCustomer);
        return newCustomer;
    }

    /**
     * Build new Customer
     * 
     * @param newCustomerWithIdTimestamps
     */
//    @Transactional
//    public void buildUserForNewCustomer(CustomerPojo newCustomerWithIdTimestamps) {
//        SecurityUser userForNewCustomer = new SecurityUser();
//        userForNewCustomer.setUsername(DEFAULT_USER_PREFIX + "" + newCustomerWithIdTimestamps.getId());
//        Map<String, String> pbAndjProperties = new HashMap<>();
//        pbAndjProperties.put(PROPERTY_ALGORITHM, DEFAULT_PROPERTY_ALGORITHM);
//        pbAndjProperties.put(PROPERTY_ITERATIONS, DEFAULT_PROPERTY_ITERATIONS);
//        pbAndjProperties.put(PROPERTY_SALTSIZE, DEFAULT_SALT_SIZE);
//        pbAndjProperties.put(PROPERTY_KEYSIZE, DEFAULT_KEY_SIZE);
//        pbAndjPasswordHash.initialize(pbAndjProperties);
//        String pwHash = pbAndjPasswordHash.generate(DEFAULT_USER_PASSWORD.toCharArray());
//        userForNewCustomer.setPwHash(pwHash);
//        userForNewCustomer.setCustomer(newCustomerWithIdTimestamps);
//        SecurityRole userRole = em.createNamedQuery(ROLE_BY_NAME_QUERY, SecurityRole.class)
//            .setParameter(PARAM1, USER_ROLE).getSingleResult();
//        userForNewCustomer.getRoles().add(userRole);
//        userRole.getUsers().add(userForNewCustomer);
//        em.persist(userForNewCustomer);
//    }

    /**
     * Set Customer address
     * 
     * @param custId
     *            - Customer id
     * @param newAddress
     *            -Customer Address
     * @return Customer pojo
     */
    @Transactional
    public CustomerPojo setAddressFor(int custId, AddressPojo newAddress) {
        CustomerPojo updatedCustomer = em.find(CustomerPojo.class, custId);
        if (newAddress instanceof ShippingAddressPojo) {
            updatedCustomer.setShippingAddress(newAddress);
        }
        else {
            updatedCustomer.setBillingAddress(newAddress);
        }
        em.merge(updatedCustomer);
        return updatedCustomer;
    }
    
    

    /**
     * Get All products
     * 
     * @return List of products
     */
    public List<ProductPojo> getAllProducts() {
        List<ProductPojo> allProducts = new ArrayList<ProductPojo>();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<ProductPojo> q = cb.createQuery(ProductPojo.class);
            Root<ProductPojo> c = q.from(ProductPojo.class);
            q.select(c);
            TypedQuery<ProductPojo> q2 = em.createQuery(q);
            allProducts.addAll(q2.getResultList());
        }
        catch (Exception e) {
        }
        return allProducts;
    }

    /**
     * Get the product by id
     * 
     * @param prodId
     *            - Product id
     * @return Product Pojo
     */
    public ProductPojo getProductById(int prodId) {
        return em.find(ProductPojo.class, prodId);
    }

    /**
     * Get all stores
     * 
     * @return List of stores
     */
    public List<StorePojo> getAllStores() {
        List<StorePojo> allStores = new ArrayList<StorePojo>();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<StorePojo> q = cb.createQuery(StorePojo.class);
            Root<StorePojo> c = q.from(StorePojo.class);
            q.select(c);
            TypedQuery<StorePojo> q2 = em.createQuery(q);
            allStores.addAll(q2.getResultList());
        }
        catch (Exception e) {
        }
        return allStores;
    }

    /**
     * Get the store by id
     * 
     * @param id
     *            - Store Id
     * @return Store by id
     */
    public StorePojo getStoreById(int id) {
        return em.find(StorePojo.class, id);
    }

    /**
     * Get the Order by id
     * 
     * @param id
     *            - Order By id
     * @return Order by id
     */
    public OrderPojo getOrderbyId(int id) {
        return em.find(OrderPojo.class, id);
    }

    /**
     * Get all orders
     * 
     * @return List of orders
     */
    public List<OrderPojo> getAllOrders() {
        List<OrderPojo> allOrders = new ArrayList<OrderPojo>();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<OrderPojo> q = cb.createQuery(OrderPojo.class);
            Root<OrderPojo> c = q.from(OrderPojo.class);
            q.select(c);
            TypedQuery<OrderPojo> q2 = em.createQuery(q);
            allOrders.addAll(q2.getResultList());
        }
        catch (Exception e) {
        }
        return allOrders;
    }

    /**
     * Update the product
     * 
     * @param pojo
     *            - Product pojo
     * @return Updated product
     */
    @Transactional
    public ProductPojo updateProduct(ProductPojo pojo) {
        if (null != pojo) {
            if (null != getProductById(pojo.getId())) {
                em.merge(pojo);
            }
        }
        return pojo;
    }

    /**
     * Update the Store
     * 
     * @param pojo
     *            - Store pojo
     * @return Updated store
     */
    @Transactional
    public StorePojo updateStore(StorePojo pojo) {
        if (null != pojo) {
            if (null != getStoreById(pojo.getId())) {
                em.merge(pojo);
            }
        }
        return pojo;
    }

    /**
     * Update the Order
     * 
     * @param pojo
     *            - Order pojo
     * @return Updated order
     */
    @Transactional
    public OrderPojo updateOrder(OrderPojo pojo) {
        if (null != pojo) {
            if (null != getOrderbyId(pojo.getId())) {
                em.merge(pojo);
            }
        }
        return pojo;
    }

    /**
     * Delete the customer
     * 
     * @param id
     *            - Customer to be deleted
     */
    @Transactional
    public CustomerPojo deletCustomer(int id) {
        CustomerPojo customerPojo = em.find(CustomerPojo.class, id);
       // CustomerPojo customerPojo = getCustomerById(id);
       if(customerPojo != null)
            em.remove(customerPojo);
            
       
        return customerPojo;
    }

    /**
     * Delete of the store
     * 
     * @param id
     *            - Order to be deleted
     */
    @Transactional
    public StorePojo deletStore(int id) {
        StorePojo storePojo = getStoreById(id);
        if (null != storePojo) {
            em.remove(storePojo);
        }
        return storePojo;
    }

    /**
     * Delete the order
     * 
     * @param id
     *            - Id of the Order
     */
    @Transactional
    public OrderPojo deleteOrder(int id) {
        OrderPojo orderPojo = getOrderbyId(id);
        if (null != orderPojo) {
            em.remove(orderPojo);
        }
        return orderPojo;
    }

    /**
     * Delete the product
     * 
     * @param id
     *            - Id of the product to be deleted
     */
    @Transactional
    public ProductPojo deleteProduct(int id) {
        ProductPojo productPojo = getProductById(id);
        if (null != productPojo) {
            em.remove(productPojo);
        }
        return productPojo;
    }

    /**
     * Save product
     * 
     * @param pojo
     *            - Product pojo
     * @return Saved product
     */
    @Transactional
    public ProductPojo persistProduct(ProductPojo pojo) {
        em.persist(pojo);
        return pojo;
    }

    /**
     * Persist Store
     * 
     * @param pojo
     *            - Store pojo
     * @return Saved Store
     */
    @Transactional
    public StorePojo persistStore(StorePojo pojo) {
        em.persist(pojo);
        return pojo;
    }

    /**
     * Persist order
     * 
     * @param pojo
     *            - Order pojo
     * @return
     */
    @Transactional
    public OrderPojo persistOrder(OrderPojo pojo) {
        em.persist(pojo);
        return pojo;
    }
    
    public OrderPojo getOrderById(int orderId) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<OrderPojo> q1 = cb.createQuery(OrderPojo.class);
            Root<OrderPojo> root = q1.from(OrderPojo.class);
            q1.where(cb.equal((root.get(OrderPojo_.id)), orderId));
            
            TypedQuery<OrderPojo> tq = em.createQuery(q1);
            OrderPojo order = tq.getSingleResult();
            return order;
        }
        catch (Exception e) {
            return null;
        }
    }
    
    
    
    
    
    
    public List<OrderLinePojo> getAllOrderLine() {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<OrderLinePojo> q = cb.createQuery(OrderLinePojo.class);
            Root<OrderLinePojo> c = q.from(OrderLinePojo.class);
            q.select(c);
            TypedQuery<OrderLinePojo> q2 = em.createQuery(q);
            List<OrderLinePojo> allOrderLines = q2.getResultList();
            return allOrderLines;
        }
        catch (Exception e) {
            return null;
        }
    }
    
    public OrderLinePojo getOrderLineById(int orderLineId) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<OrderLinePojo> q1 = cb.createQuery(OrderLinePojo.class);
            Root<OrderLinePojo> root = q1.from(OrderLinePojo.class);
            q1.where(cb.equal((root.get(OrderLinePojo_.pk)), orderLineId));
            
            TypedQuery<OrderLinePojo> tq = em.createQuery(q1);
            OrderLinePojo order = tq.getSingleResult();
            return order;
        }
        catch (Exception e) {
            return null;
        }
    }
    
    
    
    @Transactional
    public boolean deleteOrderLine(int orderLineId) {
        OrderLinePojo or = em.find(OrderLinePojo.class, orderLineId);
        boolean flag = false;
        try {
            if(or != null) {
                em.remove(or);
                flag= true;
            }
            return flag;
            
        }catch(Exception e){
            
            flag= false;
            return flag;
        }
    }
    
    
    @Transactional
    public OrderLinePojo updateOrderLine(OrderLinePojo  or) {
        try {
            
            return em.merge(or);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
       
    }
    
    @Transactional
    public void createOrder(OrderPojo or) {
        em.persist(or);
    }
    

    @Transactional
    public void createOrderLine(OrderLinePojo or) {
        em.persist(or);
    }
    
    
    
    
    
    
    
    
    
    
    
}