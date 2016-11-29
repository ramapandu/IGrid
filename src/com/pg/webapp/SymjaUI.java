package com.pg.webapp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellReference;

import com.ibm.icu.util.StringTokenizer;
import com.pg.webapp.symja.InEqualityExample;
import com.pg.webapp.symja.SymjaInterface;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

public class SymjaUI extends SymjaInterface {
	
	private static final long serialVersionUID = -6005778405260648961L;
	private SheetView sheetView;
	Sheet activeSheet;
//	TextArea symjaInputArea;
//	Label symjaText;
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
		hl.addComponent(hSplitPanel);
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
//                 if (colA.getValue().equals(text)) {
//                    cellIndexA=cell.getColumnIndex();
//                    System.out.println(cellIndexA);
//                   
//                 }
//                 else if (colB.getValue().equals(text)) {
//                     cellIndexB=cell.getColumnIndex();
//                     System.out.println(cellIndexB);
//                    
//                  }
                 if (colC.getValue().equals(text)) {
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
		String result="";
		for (int i=1;i<(getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getLastRowNum()-1);i++) {
			if(activeSheet.getRow(i)!=null){
//			 activeSheet.getRow(i).getCell(cellIndexC).setCellValue(iEx.caliculate( activeSheet.getRow(i).getCell(cellIndexA).toString()+formulaInputArea.getValue().toString()+ activeSheet.getRow(i).getCell(cellIndexB).toString()));
				result="";
				result=iEx.caliculate(getConvertedFormula(activeSheet.getRow(i)));
				activeSheet.getRow(i).getCell(cellIndexC).setCellValue(result);
				
			}
		}
        
//        activeSheet.getRow(0).getCell(cellIndex).setCellValue(newValue.getValue().toString());	
//        settingsWindow.close();
        getAppUI().getCurrent().getPage().reload();	
		
	}

	private String  getConvertedFormula(Row row){
//		String convFormula = "";
		String formulaString=formulaInputArea.getValue();
		String s=formulaInputArea.getValue();
		 System.out.println("Formula:"+s);
//		String a="A",b="2345";
////		CellReference cr=new  CellReference("");
//		
//		if(s.contains(a)){
//			convFormula=s.replace(a,b);
//			  System.out.println("Converted String:"+convFormula);
//		}
		
		//TEST----------------
		List<String> operatorList = new ArrayList<String>();
		 List<String> operandList = new ArrayList<String>();
		 StringTokenizer st = new StringTokenizer(formulaString, "+-*/(){[]}sqrt0123456789", true);
		 while (st.hasMoreTokens()) {
		    String token = st.nextToken();

		    if ("+-/*(){[]}'sqrt'0123456789".contains(token)) {
		       operatorList.add(token);
		    } else {
		       operandList.add(token);
		    }
		 }

		 System.out.println("Operators:" + operatorList);
		 System.out.println("Operands:" + operandList);
		 CellReference cr;
		 String cellValue;
		 for(int i=0;i<operandList.size();i++){
			 cr=new CellReference(operandList.get(i));
//			 System.out.println("CR:"+formulaString.contains(operandList.get(i))+" "+i);
			 if(operandList.size()>0){
			 if(formulaString.contains(operandList.get(i)) && row.getCell(cr.getCol())!=null){
				 cellValue=row.getCell(cr.getCol()).getStringCellValue();
				 System.out.println("Cell:" + cellValue);
				 formulaString=formulaString.replace(operandList.get(i),cellValue);
			 }
		 }
		 }
		 System.out.println("Converted Formula:" + formulaString);
		return formulaString;
	}
	
	private void evaluate(){
		FormulaEvaluator evaluator = getAppUI().getSpreadsheet_dao().getSpreadsheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
		// suppose your formula is in B3
		CellReference cellReference = new CellReference("B3"); 
		Row row = getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getRow(cellReference.getRow());
		Cell cell = row.getCell(cellReference.getCol()); 
       String cellValue;
		if (cell!=null) {
		    switch (evaluator.evaluateFormulaCell(cell)) {
		        case Cell.CELL_TYPE_BOOLEAN:
		            System.out.println(cell.getBooleanCellValue());
		            break;
		        case Cell.CELL_TYPE_NUMERIC:
		            System.out.println(cell.getNumericCellValue());
		            break;
		        case Cell.CELL_TYPE_STRING:
		            System.out.println(cell.getStringCellValue());
		            break;
		        case Cell.CELL_TYPE_BLANK:
		            break;
		        case Cell.CELL_TYPE_ERROR:
		            System.out.println(cell.getErrorCellValue());
		            break;

		        // CELL_TYPE_FORMULA will never occur
		        case Cell.CELL_TYPE_FORMULA: 
		            break;
		    }
		}
	}
	
	
private void populateColumns() {
	//COLUMN A
	for (int i=0;i<(getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getRow(0).getLastCellNum()-1);i++) {
//		System.out.println(getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getRow(0).getCell(i).getStringCellValue());
		if(getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getRow(0).getCell(i).getStringCellValue()!=null){
		
//			colA.addItem(getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getRow(0).getCell(i).getStringCellValue());
//			
//			colB.addItem(getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getRow(0).getCell(i).getStringCellValue());
			
			colC.addItem(getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getRow(0).getCell(i).getStringCellValue());
		
		}
	}	
	
}
	

SpreadsheetDemoUI getAppUI() {
	return (SpreadsheetDemoUI) UI.getCurrent();
}

	}