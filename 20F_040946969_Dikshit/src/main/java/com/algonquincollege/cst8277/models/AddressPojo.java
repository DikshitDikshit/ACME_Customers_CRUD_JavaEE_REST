/*****************************************************************
 * c******************o*******v******id******** File: AddressPojo.java Course
 * materials (20F) CST 8277 (Original Author) Mike Norman (Modified) @author
 * Student Name Dikshit Dikshit
 */
package com.algonquincollege.cst8277.models;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Description: model for the Address object
 */

@Entity(name = "CUSTOMERADDRESS")
@Table(name = "CUST_ADDR")
@AttributeOverride(name = "id", column = @Column(name = "ADDR_ID"))
public class AddressPojo extends PojoBase implements Serializable {
  /** explicit set serialVersionUID */
  private static final long serialVersionUID = 1L;
  protected String street;
  protected String city;
  protected String country;
  protected String postal;
  protected String state;
  /**
   * JPA requires each @Entity class have a default constructor
   */
  public AddressPojo() {
    super();
  }

  /**
   * @return the street
   */
  @Column(name = "STREET")
  public String getStreet() {
    return street;
  }

  /**
   * @param street the street to set
   */
  public void setStreet(String street) {
    this.street = street;
  }

  /**
   * @return the country
   */
  @Column(name = "COUNTRY")
  public String getCountry() {
    return country;
  }

  /**
   * @param country the country to set
   */
  public void setCountry(String country) {
    this.country = country;
  }

  /**
   * @return the postal
   */
  @Column(name = "POSTAL_CODE")
  public String getPostal() {
    return postal;
  }

  /**
   * @param postal the postal to set
   */
  public void setPostal(String postal) {
    this.postal = postal;
  }

  /**
   * @return the state
   */
  @Column(name = "STATE")
  public String getState() {
    return state;
  }

  /**
   * @param state the state to set
   */
  public void setState(String state) {
    this.state = state;
  }
  @Column(name = "CITY")
  public String getCity() {
    return city;
  }
  public void setCity(String city) {
    this.city = city;
  }

}