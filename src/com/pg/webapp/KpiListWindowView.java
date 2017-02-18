package com.pg.webapp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellReference;

import com.ibm.icu.util.StringTokenizer;
import com.pg.webapp.symja.InEqualityExample;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

@SuppressWarnings("serial")
public class KpiListWindowView extends KpiListWindow {

	private Window kpiSelectionWindow;
	CheckBox item1;
	CheckBox item2;
	CheckBox item3;
	CheckBox item4;
	CheckBox item5;
	String result;
	Button calculateButton;
	String appliedFormula;
	Sheet activeSheet=getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet();
	private static final long serialVersionUID = -5375296245378412605L;

	public KpiListWindowView() {

		kpiSelectionWindow = new Window("KPI Selection");
		kpiSelectionWindow.setHeight(500, Unit.PIXELS);
		kpiSelectionWindow.setWidth(1000, Unit.PIXELS);
		kpiSelectionWindow.addStyleName("settings");
		kpiSelectionWindow.setContent(kpiSelectionContainer);
		kpiSelectionWindow.setModal(true);
		getKpiList();
		getButtons();
	}

	private void getButtons() {
		buttonsContainer.addComponent(getCalculateButton());
	}
	
	public Button getCalculateButton() {
		calculateButton = new Button("CALCULATE");
		calculateButton.addStyleName("topbarbuttons");
		calculateButton.addClickListener(new ClickListener() {

			

			@Override
			public void buttonClick(ClickEvent event) {
				calculateResults();
			}
		});
		return calculateButton;
	}
	
	private void calculateResults(){
		
		InEqualityExample iEx=new InEqualityExample();

		//GET Last Column Num
		Row r = getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getRow(getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getFirstRowNum());
		int lastColumnNum=0;
		lastColumnNum= r.getLastCellNum()-1;

		//ADD Column at end
		for (int j=1;j<(getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getLastRowNum()-1);j++) {
			if(activeSheet.getRow(j)!=null){

		result="";
//		result=iEx.caliculate(getConvertedFormula(activeSheet.getRow(j),f1)); //TEST3
//		float result = 5 + 3 * (4 - 1) / 2.0f;
		Double res=0.0;
		res=eval(getConvertedFormula(activeSheet.getRow(j),appliedFormula));
		getAppUI().getSpreadsheet_dao().getSpreadsheet().createCell(j,lastColumnNum+1,res);
		//activeSheet.getRow(j).getCell(lastColumnNum).setCellValue(result);
//		getAppUI().getSpreadsheet_dao().getSpreadsheet().createCell(j,lastColumnNum+1,result); //TEST3
			}
			getAppUI().getSpreadsheet_dao().getSpreadsheet().refreshAllCellValues();
		}

		//
//		for (int i=0;i<(getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getLastRowNum()-1);i++) {
//			if(activeSheet.getRow(i)!=null){
////			 activeSheet.getRow(i).getCell(cellIndexC).setCellValue(iEx.caliculate( activeSheet.getRow(i).getCell(cellIndexA).toString()+formulaInputArea.getValue().toString()+ activeSheet.getRow(i).getCell(cellIndexB).toString()));
//				result="";
//			//---TEST-HEADERNAMES----	result=iEx.caliculate(getConvertedFormula(activeSheet.getRow(i)));
//				result=iEx.caliculate(getConvertedFormula(activeSheet.getRow(i),f1));
//				activeSheet.getRow(i).getCell(lastColumnNum).setCellValue(result);
//				
//			}
//		}

//		iEx.caliculate(formulaArea.getValue());
	}

	public void getKpiList() {
		item1 = new CheckBox("KPI-1");
		item1.setImmediate(true);
		item2 = new CheckBox("KPI-2");
		item3 = new CheckBox("KPI-3");
		item4 = new CheckBox("KPI-4");
		item5 = new CheckBox("KPI-5");
		
		
		item1.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				appliedFormula="S/T";
				formulaArea.setValue(appliedFormula);
			}
		});

		item2.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				String f2="Counter A/Counter B";
				formulaArea.setValue(f2);
			}
		});
		
		

		item3.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				formulaArea.setValue("Counter A/Counter B");
			}
		});

		item4.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				formulaArea.setValue("Counter A/Counter B");
			}
		});

		item5.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				formulaArea.setValue("Counter A/Counter B");
			}
		});

		kpiListContainer.addComponent(item1);
		kpiListContainer.addComponent(item2);
//		kpiListContainer.addComponent(item3);
//		kpiListContainer.addComponent(item4);
//		kpiListContainer.addComponent(item5);
	}

	
	private String  getConvertedFormula(Row row,String formulaString){
//		String convFormula = "";
//		String formulaString=formulaInputArea.getValue();
//		String s=formulaInputArea.getValue();
//		 System.out.println("Formula:"+s);
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
		 StringTokenizer st = new StringTokenizer(formulaString, "+-*/%(){[]}^$#sqrtabcdefghijklmnopuvwxyz0123456789", true);
		 while (st.hasMoreTokens()) {
		    String token = st.nextToken();

		    if ("+-*/%(){[]}^$#sqrtabcdefghijklmnopuvwxyz0123456789".contains(token)) {
		       operatorList.add(token);
		    } else {
		       operandList.add(token);
		    }
		 }

		 System.out.println("Operators:" + operatorList);
		 System.out.println("Operands:" + operandList);
		 CellReference cr;
		 String cellValue = null;
		 for(int i=0;i<operandList.size();i++){
			 cr=new CellReference(operandList.get(i));
//			 System.out.println("CR:"+formulaString.contains(operandList.get(i))+" "+i);---OLD
			 if(operandList.size()>0){
			 if(formulaString.contains(operandList.get(i)) && row.getCell(cr.getCol())!=null){
				 row.getCell(cr.getCol()).setCellType(Cell.CELL_TYPE_STRING);
//				 if(row.getCell(cr.getCol()).getCellType()==Cell.CELL_TYPE_NUMERIC)--------OLD
//				 cellValue=row.getCell(cr.getCol()).getNumericCellValue();-----------------OLD
//				 else if(row.getCell(cr.getCol()).getCellType()==Cell.CELL_TYPE_STRING)----OLD
					 cellValue=row.getCell(cr.getCol()).getStringCellValue();
				 System.out.println("Cell:" + cellValue);
				 formulaString=formulaString.replace(operandList.get(i),cellValue);
			 }
		 }
		 }
		 System.out.println("Converted Formula:" + formulaString);
		return formulaString;
	}
	
	public static double eval(final String str) {
	    return new Object() {
	        int pos = -1, ch;

	        void nextChar() {
	            ch = (++pos < str.length()) ? str.charAt(pos) : -1;
	        }

	        boolean eat(int charToEat) {
	            while (ch == ' ') nextChar();
	            if (ch == charToEat) {
	                nextChar();
	                return true;
	            }
	            return false;
	        }

	        double parse() {
	            nextChar();
	            double x = parseExpression();
	            if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
	            return x;
	        }

	        // Grammar:
	        // expression = term | expression `+` term | expression `-` term
	        // term = factor | term `*` factor | term `/` factor
	        // factor = `+` factor | `-` factor | `(` expression `)`
	        //        | number | functionName factor | factor `^` factor

	        double parseExpression() {
	            double x = parseTerm();
	            for (;;) {
	                if      (eat('+')) x += parseTerm(); // addition
	                else if (eat('-')) x -= parseTerm(); // subtraction
	                else return x;
	            }
	        }

	        double parseTerm() {
	            double x = parseFactor();
	            for (;;) {
	                if      (eat('*')) x *= parseFactor(); // multiplication
	                else if (eat('/')) x /= parseFactor(); // division
	                else return x;
	            }
	        }

	        double parseFactor() {
	            if (eat('+')) return parseFactor(); // unary plus
	            if (eat('-')) return -parseFactor(); // unary minus

	            double x;
	            int startPos = this.pos;
	            if (eat('(')) { // parentheses
	                x = parseExpression();
	                eat(')');
	            } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
	                while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
	                x = Double.parseDouble(str.substring(startPos, this.pos));
	            } else if (ch >= 'a' && ch <= 'z') { // functions
	                while (ch >= 'a' && ch <= 'z') nextChar();
	                String func = str.substring(startPos, this.pos);
	                x = parseFactor();
	                if (func.equals("sqrt")) x = Math.sqrt(x);
	                else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
	                else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
	                else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
	                else throw new RuntimeException("Unknown function: " + func);
	            } else {
	                throw new RuntimeException("Unexpected: " + (char)ch);
	            }

	            if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

	            return x;
	        }
	    }.parse();
	}
	
	public String getAppliedFormula(){
		return formulaArea.getValue();
	}
	
	public Window getKpiSelectionWindow() {
kpiSelectionWindow.center();
		return kpiSelectionWindow;
	}
	
	SpreadsheetDemoUI getAppUI() {
		return (SpreadsheetDemoUI) UI.getCurrent();
	}

}