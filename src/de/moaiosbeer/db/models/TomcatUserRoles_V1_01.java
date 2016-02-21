/*
*************************************************************************
* 21.12.2015 															*
* Copyright © 2015-2016 Tim Langenbrink, Stephan Wissing, 				*
* 						Jonas Klein-Hitpaß 								*
*************************************************************************
* 																		*
* Autor 	   : Stephan Wissing 										*
* Company 	   : / 														*
* File-Name    : TomcatUserRoles_V1_01.java 							*
* Beschreibung : Diese Klasse bildet die Tomcat-Rollen mithilfe von 	*
* 				 Hibernate auf die Datenbank ab und stellt alle 		*
* 				 benötigten Funktionen zur Verwaltung der Rollen		*
* 				 zur Verfügung											*
* 																		*
*************************************************************************
*/
package de.moaiosbeer.db.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table (name="TomcatUserRoles_V1_01")
public class TomcatUserRoles_V1_01 {
	
	@Id
	@TableGenerator(name="TomcatUserRoles_V1_01_ID", table="TomcatUserRoles_V1_01_PK" , pkColumnName="TomcatUserRoles_V1_01_ID" ,pkColumnValue="TomcatUserRoles_V1_01_Value" , allocationSize=1)
	@GeneratedValue(strategy=GenerationType.TABLE , generator="TomcatUserRoles_V1_01_ID")
	@Column(name="role_id")
	private long role_id;
	

	public long getRole_id() {
		return role_id;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	@Column(name="rolename")
	private String rolename;

}
