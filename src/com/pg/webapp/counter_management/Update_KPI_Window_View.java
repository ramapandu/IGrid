package com.pg.webapp.counter_management;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

import com.pg.webapp.SpreadsheetDemoUI;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

public class Update_KPI_Window_View extends Update_KPI_Window {
Window update_Kpi_Winow;
	public Update_KPI_Window_View(){
		update_Kpi_Winow = new Window("UPDATE KPI");
		update_Kpi_Winow.setHeight(700, Unit.PIXELS);
		update_Kpi_Winow.setWidth(1000, Unit.PIXELS);
		update_Kpi_Winow.addStyleName("settings");
		update_Kpi_Winow.setContent(update_Kpi_hl);
		update_Kpi_Winow.setModal(true);
	}

public Window getUpdateWindow(String row[]){
	update_kpi_Name.setReadOnly(false);
	System.out.println("UPDATE "+row[0]+" "+row[1]+" "+row[2]+" "+row[3]+" "+row[4]);
	update_technology_label_value.setValue(row[0]);
	update_cellCode_CB.setValue(true);
	update_siteCode_CB.setValue(true);
	update_bscCode_CB.setValue(true);
	update_kpi_Name.setValue(row[3]);
	update_kpi_Name.setReadOnly(true);
	update_formula_Area.setValue(row[4]);
	populateCounters();
	return update_Kpi_Winow;
}

private void populateCounters() {
	final Sheet activeSheet = getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet();
	String[] parts;
	for (int i = 3; i < (activeSheet.getRow(0).getLastCellNum() - 1); i++) {
		if (activeSheet.getRow(0).getCell(i) != null) { // default 0
			
			if (activeSheet.getRow(0).getCell(i).getCellType() == Cell.CELL_TYPE_STRING) {
				if(activeSheet.getRow(0).getCell(i).getStringCellValue().contains("C_")){
					parts = activeSheet.getRow(0).getCell(i).getStringCellValue().split("_");
				update_counters_ComboBox.addItem(parts[1]);
				}
				else if(activeSheet.getRow(0).getCell(i).getStringCellValue().contains("L_")){
					//DO NOTHING->Dont add kpi with L_
				}
				else
					update_counters_ComboBox.addItem(activeSheet.getRow(0).getCell(i).getStringCellValue());
				}
				
			else if (activeSheet.getRow(0).getCell(i).getCellType() == Cell.CELL_TYPE_NUMERIC) {
				update_counters_ComboBox.addItem(activeSheet.getRow(0).getCell(i).getNumericCellValue());
			} else if (activeSheet.getRow(0).getCell(i).getCellType() == Cell.CELL_TYPE_BOOLEAN) {
				update_counters_ComboBox.addItem(activeSheet.getRow(0).getCell(i).getBooleanCellValue());
			}
		}
		}	
}

SpreadsheetDemoUI getAppUI() {
	return (SpreadsheetDemoUI) UI.getCurrent();
}
}
