/*****************************************************************c******************o*******v******id********
 * File: CustomerResource.java
 * Course materials (20F) CST 8277
 *
 * @author (original) Mike Norman
 * 
 * update by : I. Am. A. Student 040946969
 *
 */
package com.algonquincollege.cst8277.security;

import static java.util.Collections.emptySet;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.algonquincollege.cst8277.models.SecurityRole;
import com.algonquincollege.cst8277.models.SecurityUser;
import com.algonquincollege.cst8277.utils.MyConstants;

/*
 * Stateless Session bean should also be a Singleton
 */
@Stateless
public class CustomIdentityStoreJPAHelper {
    @PersistenceContext(unitName = MyConstants.PU_NAME)
    protected EntityManager em;

    public SecurityUser findUserByName(String username) {
        SecurityUser user = null;
        try {
            Query query = em.createQuery("Select u from SecurityUser u WHERE u.username = :username",
                SecurityUser.class);
            query.setParameter("username", username);
            user = (SecurityUser)query.getSingleResult();
        }
        catch (Exception e) {
            // e.printStackTrace();
        }
        return user;
    }

    public Set<String> findRoleNamesForUser(String username) {
        Set<String> roleNames = emptySet();
        SecurityUser securityUser = findUserByName(username);
        if (securityUser != null) {
            roleNames = securityUser.getRoles().stream().map(s -> s.getRoleName())
                .collect(Collectors.toSet());
        }
        return roleNames;
    }

    @Transactional
    public void saveSecurityUser(SecurityUser user) {
        em.persist(user);
    }

    @Transactional
    public void saveSecurityRole(SecurityRole role) {
        em.persist(role);
    }
}