package com.algonquincollege.cst8277.models;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2020-12-06T06:49:44.043-0500")
@StaticMetamodel(SecurityRole.class)
public class SecurityRole_ {
	public static volatile SingularAttribute<SecurityRole, Integer> id;
	public static volatile SingularAttribute<SecurityRole, String> roleName;
	public static volatile SetAttribute<SecurityRole, SecurityUser> users;
}