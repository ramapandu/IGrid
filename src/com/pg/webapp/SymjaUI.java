package com.pg.webapp;

import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

import com.pg.webapp.symja.InEqualityExample;
import com.pg.webapp.symja.SymjaInterface;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.UI;

public class SymjaUI extends SymjaInterface {
	
	private static final long serialVersionUID = -6005778405260648961L;
	private SheetView sheetView;
	Sheet activeSheet;
	TextArea symjaInputArea;
	Label symjaText;
	HorizontalLayout hl;
	public SymjaUI() {
	initComponents();
	populateColumns();
	sheetView=getAppUI().getSheetView();
	activeSheet=getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet();
	}
	
	private void initComponents() {
		hl=new HorizontalLayout();
		hl.setHeight("330px");
		hl.addComponent(symjaContainer);
//		hl.setSizeFull();
		symjaSubmitButton.addStyleName("topbarbuttons");
		symjaSubmitButton.setImmediate(true);
		symjaSubmitButton.setEnabled(true);
		symjaSubmitButton.addClickListener(new ClickListener(){

			private static final long serialVersionUID = -8158301975694183254L;

			@Override
			public void buttonClick(ClickEvent event) {
				InEqualityExample ex=new InEqualityExample();
//				symjaText.setValue(ex.caliculate(symjaInputArea.getValue()));
				
			applyFormula();
			}
		});
//		symjaInputArea=new TextArea();
//		symjaInputArea.setValue("TEST");
//		symjaInputArea.setHeight("150px");
//		symjaInputArea.setWidth("100%");
//		hl.addComponent(symjaInputArea);
//		hl.addComponent(getSymjaSubmitButton());
//		symjaText=new Label("Symja");
//		symjaText.setImmediate(true);
//		InEqualityExample ex=new InEqualityExample();
//		symjaText.setValue(ex.caliculate());
//		hl.addComponent(symjaText);	
	}

	Component getSymjaComponent() {
		return hl;
	}
	
//	private Button getSymjaSubmitButton() {
//		symjaSubmitButton.addStyleName("topbarbuttons");
//		symjaSubmitButton.setImmediate(true);
//		symjaSubmitButton.setEnabled(true);
//		symjaSubmitButton.addClickListener(new ClickListener(){
//
//			private static final long serialVersionUID = -8158301975694183254L;
//
//			@Override
//			public void buttonClick(ClickEvent event) {
//				InEqualityExample ex=new InEqualityExample();
////				symjaText.setValue(ex.caliculate(symjaInputArea.getValue()));
//			applyFormula();
//			}
//		});
//		return symjaSubmitButton;
//	}

	private void applyFormula() {
		
		
		Notification.show("Headers Updated");
		int cellIndexA = 0,cellIndexB=0,cellIndexC=0;
		Iterator<Cell> cellIterator = activeSheet.getRow(0).cellIterator();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
//            if(cell.getCellType()==Cell.CELL_TYPE_STRING){ 
                String text = cell.getStringCellValue();
                 if (colA.getValue().equals(text)) {
                    cellIndexA=cell.getColumnIndex();
                    System.out.println(cellIndexA);
                   
                 }
                 else if (colB.getValue().equals(text)) {
                     cellIndexB=cell.getColumnIndex();
                     System.out.println(cellIndexB);
                    
                  }
                 else if (colC.getValue().equals(text)) {
                     cellIndexC=cell.getColumnIndex();
                     System.out.println(cellIndexC);
                    
                  }
                 Notification.show("Please check the Selected properties");
//               }
//            cellIndexA++;
//            cellIndexB++;
//            cellIndexC++;
            }
        
//      ArrayList<String> A=new ArrayList();
//		ArrayList<String> B=new ArrayList();
//		ArrayList<String> C=new ArrayList();
		InEqualityExample iEx=new InEqualityExample();
		for (int i=1;i<(getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getLastRowNum()-1);i++) {
			if(activeSheet.getRow(i)!=null)
			 activeSheet.getRow(i).getCell(cellIndexC).setCellValue(iEx.caliculate( activeSheet.getRow(i).getCell(cellIndexA).toString()+formulaInputArea.getValue().toString()+ activeSheet.getRow(i).getCell(cellIndexB).toString()));
		}
        
//        activeSheet.getRow(0).getCell(cellIndex).setCellValue(newValue.getValue().toString());	
//        settingsWindow.close();
        getAppUI().getCurrent().getPage().reload();	
		
	}

private void populateColumns() {
	//COLUMN A
	for (int i=0;i<(getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getRow(0).getLastCellNum()-1);i++) {
//		System.out.println(getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getRow(0).getCell(i).getStringCellValue());
		if(getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getRow(0).getCell(i).getStringCellValue()!=null){
		
			colA.addItem(getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getRow(0).getCell(i).getStringCellValue());
			
			colB.addItem(getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getRow(0).getCell(i).getStringCellValue());
			
			colC.addItem(getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getRow(0).getCell(i).getStringCellValue());
		
		}
	}	
	
}
	

SpreadsheetDemoUI getAppUI() {
	return (SpreadsheetDemoUI) UI.getCurrent();
}

	}