/*****************************************************************
 * c******************o*******v******id******** File: AddressPojo.java Course
 * materials (20F) CST 8277 (Original Author) Mike Norman (Modified) @author
 * Student Name Dikshit Dikshit
 */
package com.algonquincollege.cst8277.models;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Description: model for the OrderLine object
 */
@Entity(name = "ORDERLINE")
@Table(name = "ORDERLINE")
@AttributeOverride(name = "id", column = @Column(name = "ORDERLINE_NO"))
public class OrderLinePojo implements Serializable {
    private static final long serialVersionUID = 1L;
    protected OrderLinePk primaryKey;
    protected OrderPojo owningOrder;
    protected Double amount;
    protected Double price;
    protected ProductPojo product;

    // JPA requires each @Entity class have a default constructor
    public OrderLinePojo() {
    }

    @EmbeddedId
    public OrderLinePk getPk() {
        return primaryKey;
    }

    public void setPk(OrderLinePk primaryKey) {
        this.primaryKey = primaryKey;
    }

    @MapsId("owningOrderId")
    @ManyToOne(optional = false)
    public OrderPojo getOwningOrder() {
        return owningOrder;
    }

    public void setOwningOrder(OrderPojo owningOrder) {
        this.owningOrder = owningOrder;
    }

    @Column(name = "AMOUNT")
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID")
    @OneToOne
    public ProductPojo getProduct() {
        return product;
    }

    public void setProduct(ProductPojo product) {
        this.product = product;
    }

    @Column(name = "PRICE")
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}