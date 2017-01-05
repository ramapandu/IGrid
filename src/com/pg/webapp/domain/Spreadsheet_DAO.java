package com.pg.webapp.domain;

import java.io.Serializable;

import com.vaadin.addon.spreadsheet.Spreadsheet;

public class Spreadsheet_DAO implements Serializable {

	private static final long serialVersionUID = 7066463025553059192L;

	private Long id;
	private Spreadsheet spreadsheet;
	private String gridName;
	
	public String getGridName() {
		return gridName;
	}
	public void setGridName(String gridName) {
		this.gridName = gridName;
	}
	public Spreadsheet getSpreadsheet() {
		return spreadsheet;
	}
	public void setSpreadsheet(Spreadsheet spreadsheet) {
		this.spreadsheet = spreadsheet;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
}