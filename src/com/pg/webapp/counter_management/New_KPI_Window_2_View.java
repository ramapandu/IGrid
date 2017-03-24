package com.pg.webapp.counter_management;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

import com.pg.webapp.SpreadsheetDemoUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

public class New_KPI_Window_2_View extends New_KPI_Window_2 {

	private static final long serialVersionUID = -7915451467867653645L;
	private Window newKpiWindow2;
	 New_KPI_Window_View nkwv;

	public New_KPI_Window_2_View() {
		newKpiWindow2 = new Window("Counter Management-NEW KPI");
		newKpiWindow2.setHeight(700, Unit.PIXELS);
		newKpiWindow2.setWidth(1000, Unit.PIXELS);
		newKpiWindow2.addStyleName("settings");
		newKpiWindow2.setContent(newKpiContainer2);
		newKpiWindow2.setModal(true);
		buildFormLayout();
	}

	private void buildFormLayout() {

	}

	public Window getNewKpiWindow2() {
		newKpiWindow2.center();
		final Sheet activeSheet = getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet();
		technology_label_value.setValue(activeSheet.getSheetName());
//		nkwv=new New_KPI_Window
		nkwv=getAppUI().getNewKpiMainWindow();
		
		for (int i = 3; i < (activeSheet.getRow(0).getLastCellNum() - 1); i++) {
			if (activeSheet.getRow(0).getCell(i) != null) { // default 0
				if (activeSheet.getRow(0).getCell(i).getCellType() == Cell.CELL_TYPE_STRING) {
					counters_ComboBox.addItem(activeSheet.getRow(0).getCell(i).getStringCellValue());
				} else if (activeSheet.getRow(0).getCell(i).getCellType() == Cell.CELL_TYPE_NUMERIC) {
					counters_ComboBox.addItem(activeSheet.getRow(0).getCell(i).getNumericCellValue());
				} else if (activeSheet.getRow(0).getCell(i).getCellType() == Cell.CELL_TYPE_BOOLEAN) {
					counters_ComboBox.addItem(activeSheet.getRow(0).getCell(i).getBooleanCellValue());
				}
			}
		}

		addButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -6870412160671836168L;

			@Override
			public void buttonClick(ClickEvent event) {
				formula_Area.setImmediate(true);
				System.out.println("KPI added to formulabar");
				formula_Area.setValue(formula_Area.getValue() + counters_ComboBox.getValue());

			}
		});

		saveButton.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 7357493404733580927L;

			@Override
			public void buttonClick(ClickEvent event) {
				if (cellCode_CB.isValid() && siteCode_CB.isValid() && bscCode_CB.isValid()) {
					if (kpi_Name.getValue() != "") {
						if (formula_Area.getValue() != "") {
							nkwv.createButtons();
							newKpiWindow2.close();
						}
					} else
						Notification.show("Please Enter a name for the KPI");
				}

				else
					Notification.show("Please check the Cell code, Site code and Bsc code");
			}
		});

		return newKpiWindow2;
	}

	SpreadsheetDemoUI getAppUI() {
		return (SpreadsheetDemoUI) UI.getCurrent();
	}

}
