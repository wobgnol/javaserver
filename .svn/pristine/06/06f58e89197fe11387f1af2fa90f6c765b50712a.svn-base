/*
*************************************************************************
* 21.12.2015 															*
* Copyright © 2015-2016 Tim Langenbrink, Stephan Wissing, 				*
* 						Jonas Klein-Hitpaß 								*
*************************************************************************
* 																		*
* Autor 	   : Tim Langenbrink										*
* Company 	   : / 														*
* File-Name    : PlaysheetPK.java 										*
* Beschreibung : Diese Klasse wird verwendet, um Hibernate die			*
* 				 Verwendung eines zusammengesetzten Primärschlüssels	*
* 				 zu ermöglichen											*
* 																		*
*************************************************************************
*/
package de.moaiosbeer.db.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

@Embeddable
public class PlaysheetPK implements Serializable{
	
	//@TableGenerator(name="Playsheet_V1_01_ID", table="Playsheet_V1_01_PK" , pkColumnName="Playsheet_V1_01_ID" ,pkColumnValue="Playsheet_V1_01_Value" , allocationSize=1)
	//@GeneratedValue(strategy=GenerationType.TABLE , generator="Playsheet_V1_01_ID")
	@Column(name="playsheetid")	
	long id;
	
	@Column(name="round")	
	int round;
	
	public PlaysheetPK()
	{
		round = 1;
		id = 1;
	}
		
	public PlaysheetPK(long psId,int round)
	{
		this.id = psId;
		this.round = round;
	}
	
	public void setId(long id)
	{
		this.id = id;
	}
	
	public long getId()
	{
		return this.id;
	}
	
	public void setRound(int round)
	{
		this.round = round;
	}
	
	public int getRound()
	{
		return round;
	}
}
