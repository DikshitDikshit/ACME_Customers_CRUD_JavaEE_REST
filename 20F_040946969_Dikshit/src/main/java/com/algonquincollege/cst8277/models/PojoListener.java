/*****************************************************************
 * c******************o*******v******id******** File: AddressPojo.java Course
 * materials (20F) CST 8277 (Original Author) Mike Norman (Modified) @author
 * Student Name Dikshit Dikshit
 */
package com.algonquincollege.cst8277.models;

import java.time.LocalDateTime;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * Implementation of Pojo listener
 */
public class PojoListener {
    @PrePersist
    public void setCreatedOnDate(PojoBase base) {
        LocalDateTime now = LocalDateTime.now();
        base.setCreatedDate(now);
        // might as well call setUpdatedDate as well
        base.setUpdatedDate(now);
    }

    @PreUpdate
    public void setUpdatedDate(PojoBase base) {
        base.setUpdatedDate(LocalDateTime.now());
    }
}