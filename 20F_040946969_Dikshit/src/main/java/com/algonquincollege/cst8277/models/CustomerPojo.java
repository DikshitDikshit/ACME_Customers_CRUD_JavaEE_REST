/*****************************************************************
 * c******************o*******v******id******** File: AddressPojo.java Course
 * materials (20F) CST 8277 (Original Author) Mike Norman (Modified) @author
 * Student Name Dikshit Dikshit
 */
package com.algonquincollege.cst8277.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
//import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Description: model for the Customer object The CustomerPojo class
 * demonstrates several JPA features:
 * <ul>
 * <li>OneToOne relationship
 * <li>OneToMany relationship
 * </ul>
 */
@Entity(name = "CUSTOMER")
@Table(name = "CUSTOMER")
public class CustomerPojo extends PojoBase implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String phoneNumber;
    protected AddressPojo shippingAddress;
    protected AddressPojo billingAddress;
    protected List<OrderPojo> orders;
    //protected SecurityUser securityUser;

    // JPA requires each @Entity class have a default constructor
    public CustomerPojo() {
    }

    @Column(name = "FNAME")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "LNAME")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "EMAIL")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "PHONENUMBER")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @JoinColumn(name = "SHIPPING_ADDR", referencedColumnName = "ADDR_ID")
    @OneToOne(cascade = CascadeType.ALL)
    public AddressPojo getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(AddressPojo shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    @OneToMany(mappedBy = "owningCustomer")
    public List<OrderPojo> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderPojo> orders) {
        this.orders = orders;
    }

    @JoinColumn(name = "BILLING_ADDR", referencedColumnName = "ADDR_ID")
    @OneToOne(cascade = CascadeType.ALL)
    public AddressPojo getBillingAddress() {
        return billingAddress;
    }

    /**
     * Set Billing address
     * 
     * @param billingAddress
     *            - Billing address
     */
    public void setBillingAddress(AddressPojo billingAddress) {
        this.billingAddress = billingAddress;
    }

    /**
     * @return the securityUser
     */
//    @JoinColumn(name = "SECURITYUSER_SECURITY_USER_ID", referencedColumnName = "USER_ID")
//    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
//    public SecurityUser getSecurityUser() {
//        return securityUser;
//    }
//
//    /**
//     * @param securityUser
//     *            the securityUser to set
//     */
//    public void setSecurityUser(SecurityUser securityUser) {
//        this.securityUser = securityUser;
//    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Customer [id=").append(id).append(", ");
        if (firstName != null) {
            builder.append("firstName=").append(firstName).append(", ");
        }
        if (lastName != null) {
            builder.append("lastName=").append(lastName).append(", ");
        }
        if (phoneNumber != null) {
            builder.append("phoneNumber=").append(phoneNumber).append(", ");
        }
        if (email != null) {
            builder.append("email=").append(email).append(", ");
        }
        builder.append("]");
        return builder.toString();
    }
}