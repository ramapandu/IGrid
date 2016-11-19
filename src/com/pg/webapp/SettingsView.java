package com.pg.webapp;

import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

public class SettingsView extends Settings2 {
	
	private static final long serialVersionUID = -6774707392861879835L;
	
	private Window settingsWindow;
	SheetView sheetView;
	Sheet activeSheet;

	public SettingsView() {
		settingsWindow=new Window("Settings");
		settingsWindow.setHeight(400,Unit.PIXELS);
		settingsWindow.setWidth(500, Unit.PIXELS);
		settingsWindow.addStyleName("settings");
//		formLayout.setSizeFull();
		settingsWindow.setContent(formLayout);
		sheetView=getAppUI().getSheetView();
		activeSheet=getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet();
//		int i=0;
//		
//		while(activeSheet.iterator().hasNext()){
//		headerCombo.addItem(activeSheet.iterator().next().getCell(i).getStringCellValue());
//		i++;
//		
//		}
		for (int i=0;i<(activeSheet.getRow(0).getLastCellNum()-1);i++) {
			if(activeSheet.getRow(0).getCell(i).getStringCellValue()!=null){
			
				headerCombo.addItem(activeSheet.getRow(0).getCell(i).getStringCellValue());
			
			}
		}
		
		submit.addClickListener(new ClickListener()
		{
			private static final long serialVersionUID = 8729659917800598157L;

			@Override
			public void buttonClick(ClickEvent event)
			{
				if(path.getValue()!=null &&filename.getValue()!=null){
				sheetView.setFilePath(path.getValue());
				sheetView.setFileName(filename.getValue());
				Notification.show("Path has been Updated");
				}
				else
					Notification.show("Path or File name not valid");
			}
		});
		
		headerSubmit.addClickListener(new ClickListener()
		{
			private static final long serialVersionUID = -6870412160671836168L;

			@Override
			public void buttonClick(ClickEvent event)
			{
				Notification.show("Headers Updated");
				int cellIndex=0;
				Iterator<Cell> cellIterator = activeSheet.getRow(0).cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    if(cell.getCellType()==Cell.CELL_TYPE_STRING){ 
                        String text = cell.getStringCellValue();
                         if (headerCombo.getValue().equals(text)) {
                            cellIndex=cell.getColumnIndex();
                            break;
                         }
                       }
                    }
                activeSheet.getRow(0).getCell(cellIndex).setCellValue(newValue.getValue().toString());	
                settingsWindow.close();
                getAppUI().getCurrent().getPage().reload();				
			}
		});

	}


	SpreadsheetDemoUI getAppUI() {
		return (SpreadsheetDemoUI) UI.getCurrent();
	}


	public void openSettingsWindow() {
		getAppUI().addWindow(settingsWindow);
		settingsWindow.setModal(true);	
	}

}