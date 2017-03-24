package com.pg.webapp.counter_management;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;

/** 
 * !! DO NOT EDIT THIS FILE !!
 * 
 * This class is generated by Vaadin Designer and will be overwritten.
 * 
 * Please make a subclass with logic and additional interfaces as needed,
 * e.g class LoginView extends LoginDesign implements View { }
 */
@DesignRoot
@AutoGenerated
@SuppressWarnings("serial")
public class Update_KPI_Window extends VerticalLayout {
	protected HorizontalLayout update_Kpi_hl;
	protected VerticalLayout updateKpiContainer2;
	protected HorizontalLayout update_technology_hl;
	protected Label update_technology_label;
	protected Label update_technology_label_value;
	protected VerticalLayout update_code_vl;
	protected Label update_aggregations_label;
	protected CheckBox update_cellCode_CB;
	protected CheckBox update_siteCode_CB;
	protected CheckBox update_bscCode_CB;
	protected HorizontalLayout update_kpi_name_hl;
	protected TextField update_kpi_Name;
	protected CheckBox update_dynamic_thresholding;
	protected HorizontalLayout update_formulabar_hl;
	protected TextArea update_formula_Area;
	protected ComboBox update_counters_ComboBox;
	protected NativeButton update_addButton;
	protected HorizontalLayout update_buttons_hl;
	protected NativeButton update_saveButton;
	protected NativeButton update_validateButton;

	public Update_KPI_Window() {
		Design.read(this);
	}
}
