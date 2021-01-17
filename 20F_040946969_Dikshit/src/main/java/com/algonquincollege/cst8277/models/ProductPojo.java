/*****************************************************************
 * c******************o*******v******id******** File: AddressPojo.java Course
 * materials (20F) CST 8277 (Original Author) Mike Norman (Modified) @author
 * Student Name Dikshit Dikshit
 */
package com.algonquincollege.cst8277.models;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Description: model for the Product object
 */
@Entity(name = "PRODUCT")
@Table(name = "PRODUCT")
@AttributeOverride(name = "id", column = @Column(name = "PRODUCT_ID"))
public class ProductPojo extends PojoBase implements Serializable {
  private static final long serialVersionUID = 1L;

  protected String description;

  protected String serialNo;
  protected Set<StorePojo> stores;
  // JPA requires each @Entity class have a default constructor
  public ProductPojo() {
  }

  @Column(name = "DESCRIPTION")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
  @Column(name = "SERIALNUMBER")
  public String getSerialNo() {
    return serialNo;
  }
  public void setSerialNo(String serialNo) {
    this.serialNo = serialNo;
  }
  @JoinTable(name = "STORES_PRODUCTS", joinColumns = {
      @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID")}, inverseJoinColumns = {
          @JoinColumn(name = "STORE_ID", referencedColumnName = "STORE_ID")})
  @ManyToMany(fetch = FetchType.EAGER)
 
  public Set<StorePojo> getStores() {
    return stores;
  }

  public void setStores(Set<StorePojo> stores) {
    this.stores = stores;
  }

}