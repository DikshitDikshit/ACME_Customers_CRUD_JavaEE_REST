/*****************************************************************
 * c******************o*******v******id******** File: AddressPojo.java Course
 * materials (20F) CST 8277 (Original Author) Mike Norman (Modified) @author
 * Student Name Dikshit Dikshit
 */
package com.algonquincollege.cst8277.models;

import java.io.Serializable;

import javax.persistence.Column;

/**
 * Description: model for the BillingAddress object
 */
public class BillingAddressPojo extends AddressPojo implements Serializable {
  /** explicit set serialVersionUID */
  private static final long serialVersionUID = 1L;

  protected boolean isAlsoShipping;

  // JPA requires each @Entity class have a default constructor
  public BillingAddressPojo() {
  }
  @Column(name = "ALSOSHIPPING")
  public boolean isAlsoShipping() {
    return isAlsoShipping;
  }
  public void setAlsoShipping(boolean isAlsoShipping) {
    this.isAlsoShipping = isAlsoShipping;
  }

}