/*****************************************************************
 * c******************o*******v******id******** File: AddressPojo.java Course
 * materials (20F) CST 8277 (Original Author) Mike Norman (Modified) @author
 * Student Name Dikshit Dikshit
 */
package com.algonquincollege.cst8277.models;

import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Description: model for the Store object
 */
@Entity(name = "STORES")
@Table(name = "STORES")
@AttributeOverride(name = "id", column = @Column(name = "STORE_ID"))
public class StorePojo extends PojoBase {
    /**
     * Serial version uid
     */
    private static final long serialVersionUID = 5777087283532481526L;
    protected String storeName;
    protected Set<ProductPojo> products;

    // JPA requires each @Entity class have a default constructor
    public StorePojo() {
    }

    @Column(name = "STORE_NAME")
    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    /**
     * @return the products
     */
    @ManyToMany(mappedBy = "stores", fetch = FetchType.EAGER)
    public Set<ProductPojo> getProducts() {
        return products;
    }

    /**
     * @param products
     *            the products to set
     */
    public void setProducts(Set<ProductPojo> products) {
        this.products = products;
    }
}
