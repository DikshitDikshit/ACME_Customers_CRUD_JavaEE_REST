/*****************************************************************
 * c******************o*******v******id******** File: AddressPojo.java Course
 * materials (20F) CST 8277 (Original Author) Mike Norman (Modified) @author
 * Student Name Dikshit Dikshit
 */
package com.algonquincollege.cst8277.models;

import java.io.Serializable;

/**
 * Description: model for the ShippingAddress object
 */

public class ShippingAddressPojo extends AddressPojo implements Serializable {

  /**
   * Serial version UID
   */
  private static final long serialVersionUID = -741412714317445630L;

  // JPA requires each @Entity class have a default constructor
  public ShippingAddressPojo() {
  }

}