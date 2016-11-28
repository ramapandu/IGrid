package com.pg.webapp.symja;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.TextArea;
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
public class SymjaInterface extends HorizontalLayout {
	protected HorizontalSplitPanel hSplitPanel;
	protected FormLayout symjaContainer;
	protected TextArea formulaInputArea;
	protected ComboBox colC;
	protected Button symjaSubmitButton;
	protected TextArea renderArea;

	public SymjaInterface() {
		Design.read(this);
	}
}
