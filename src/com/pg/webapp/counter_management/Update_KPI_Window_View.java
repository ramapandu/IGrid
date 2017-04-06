package com.pg.webapp.counter_management;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

import com.pg.webapp.SpreadsheetDemoUI;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class Update_KPI_Window_View extends Update_KPI_Window {
Window update_Kpi_Winow;
New_KPI_Window_View nkwv;
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
	createUpdateButtons();
	return update_Kpi_Winow;
}

private void createUpdateButtons() {
	update_saveButton.addClickListener(new ClickListener() {
		private static final long serialVersionUID = -6870412160671836168L;

		@Override
		public void buttonClick(ClickEvent event) {
			update_formula_Area.setImmediate(true);
			System.out.println("KPI updated");
			update_formula_Area.setValue(update_formula_Area.getValue() + update_counters_ComboBox.getValue());
			nkwv=getAppUI().getNewKpiMainWindow();
			nkwv.updateKpiData(update_technology_label_value.getValue(),update_kpi_Name.getValue(),String.valueOf(update_kpi_Name.getValue())+String.valueOf(update_cellCode_CB.getValue())+String.valueOf(update_siteCode_CB.getValue())+String.valueOf(update_siteCode_CB.getValue()),update_formula_Area.getValue());
		update_Kpi_Winow.close();
		}
	});
	
	update_addButton.addClickListener(new ClickListener() {
		private static final long serialVersionUID = -6870412160671836168L;

		@Override
		public void buttonClick(ClickEvent event) {
			update_formula_Area.setImmediate(true);
			System.out.println("KPI added to formulabar");
			update_formula_Area.setValue(update_formula_Area.getValue() + update_counters_ComboBox.getValue());

		}
	});	
	
	update_validateButton.addClickListener(new ClickListener() {
		private static final long serialVersionUID = -6870412160671836168L;

		@Override
		public void buttonClick(ClickEvent event) {
			System.out.println("KPI Validated");

		}
	});	
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
