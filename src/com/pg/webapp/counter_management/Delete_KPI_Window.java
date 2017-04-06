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
public class Delete_KPI_Window extends VerticalLayout {
	protected HorizontalLayout delete_Kpi_hl;
	protected VerticalLayout kpiContainer2;
	protected HorizontalLayout delete_technology_hl;
	protected Label update_technology_label;
	protected Label delete_technology_label_value;
	protected VerticalLayout delete_code_vl;
	protected Label update_aggregations_label;
	protected CheckBox delete_cellCode_CB;
	protected CheckBox delete_siteCode_CB;
	protected CheckBox delete_bscCode_CB;
	protected HorizontalLayout delete_kpi_name_hl;
	protected TextField delete_kpi_Name;
	protected CheckBox delete_dynamic_thresholding;
	protected HorizontalLayout delete_formulabar_hl;
	protected TextArea delete_formula_Area;
	protected ComboBox delete_counters_ComboBox;
	protected NativeButton delete_addButton;
	protected HorizontalLayout delete_buttons_hl;
	protected NativeButton delete_saveButton;
	protected NativeButton delete_validateButton;

	public Delete_KPI_Window() {
		Design.read(this);
	}
}