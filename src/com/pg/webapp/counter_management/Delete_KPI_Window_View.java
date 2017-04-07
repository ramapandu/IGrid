package com.pg.webapp.counter_management;

import com.pg.webapp.SpreadsheetDemoUI;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class Delete_KPI_Window_View extends Delete_KPI_Window {
Window delete_Kpi_Winow;
New_KPI_Window_View nkwv;;
	public Delete_KPI_Window_View(){
		delete_Kpi_Winow = new Window("Counter Management-NEW KPI");
		delete_Kpi_Winow.setHeight(700, Unit.PIXELS);
		delete_Kpi_Winow.setWidth(1000, Unit.PIXELS);
		delete_Kpi_Winow.addStyleName("settings");
		delete_Kpi_Winow.setContent(delete_Kpi_hl);
		delete_Kpi_Winow.setModal(true);
	}

public Window getDeleteWindow(String row[]){
	delete_kpi_Name.setReadOnly(false);
	System.out.println("delete "+row[0]+" "+row[1]+" "+row[2]+" "+row[3]+" "+row[4]);
	delete_technology_label_value.setValue(row[0]);
	delete_cellCode_CB.setValue(true);
	delete_siteCode_CB.setValue(true);
	delete_bscCode_CB.setValue(true);
	delete_kpi_Name.setValue(row[3]);
	delete_formula_Area.setValue(row[4]);
	//SET data read-only
	delete_cellCode_CB.setReadOnly(true);
	delete_siteCode_CB.setReadOnly(true);
	delete_bscCode_CB.setReadOnly(true);
	delete_kpi_Name.setReadOnly(true);
	delete_formula_Area.setReadOnly(true);
	createDeleteButtons();
	return delete_Kpi_Winow;
}

private void createDeleteButtons() {
	delete_deleteButton.addClickListener(new ClickListener() {
		private static final long serialVersionUID = -6870412160671836168L;

		@Override
		public void buttonClick(ClickEvent event) {
			getAppUI().getNewKpiMainWindow().deleteKpiData();
			delete_Kpi_Winow.close();
		}
	});	
	
	delete_cancelButton.addClickListener(new ClickListener() {
		private static final long serialVersionUID = -6870412160671836168L;

		@Override
		public void buttonClick(ClickEvent event) {
			delete_Kpi_Winow.close();
		}
	});	
}
SpreadsheetDemoUI getAppUI() {
	return (SpreadsheetDemoUI) UI.getCurrent();
}
}
