package com.pg.webapp.counter_management;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Window;

public class Delete_KPI_Window_View extends Delete_KPI_Window {
Window delete_Kpi_Winow;
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
	delete_kpi_Name.setReadOnly(true);
	delete_formula_Area.setValue(row[4]);
	return delete_Kpi_Winow;
}
}
