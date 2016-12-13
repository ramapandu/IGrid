package com.pg.webapp;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellReference;
import org.matheclipse.core.basic.Config;
import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.MathMLUtilities;
import org.matheclipse.parser.client.SyntaxError;
import org.matheclipse.parser.client.math.MathException;
import org.scilab.forge.jlatexmath.ParseException;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.ibm.icu.util.StringTokenizer;
import com.pg.webapp.symja.InEqualityExample;
import com.pg.webapp.symja.SymjaInterface;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

import net.sourceforge.jeuclid.MathMLParserSupport;
import net.sourceforge.jeuclid.MutableLayoutContext;
import net.sourceforge.jeuclid.context.LayoutContextImpl;
import net.sourceforge.jeuclid.context.Parameter;
import net.sourceforge.jeuclid.converter.Converter;

public class SymjaUI extends SymjaInterface {
	
	private static final long serialVersionUID = -6005778405260648961L;
	private SheetView sheetView;
	Sheet activeSheet;
//	TextArea symjaInputArea;
//	Label symjaText;
	HorizontalLayout hl,symjaLayout;
	 Document doc;
	 BufferedImage bi;
		Image img;
	public SymjaUI() {
	initComponents();
	populateColumns();
	generateRendering();
	sheetView=getAppUI().getSheetView();
	activeSheet=getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet();
	}
	
	private void generateRendering() {
formulaInputArea.addValueChangeListener(new ValueChangeListener() {
	
	@Override
	public void valueChange(ValueChangeEvent event) {
		InEqualityExample iEx=new InEqualityExample();
//		renderArea.setValue(iEx.toJavaForm(formulaInputArea.getValue()));
//		renderFormula();--------TEST-------
		try {
			renderFormulaForMathML();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
});
		
	}

	private void initComponents() {
		symjaLayout=new HorizontalLayout();
		symjaLayout.setHeight("330px");
		symjaLayout.addComponent(hSplitPanel);
//		symjaLayout.setSizeFull();
		symjaSubmitButton.addStyleName("topbarbuttons");
		symjaSubmitButton.setImmediate(true);
		symjaSubmitButton.setEnabled(true);
		symjaSubmitButton.addClickListener(new ClickListener(){

			private static final long serialVersionUID = -8158301975694183254L;

			@Override
			public void buttonClick(ClickEvent event) {
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
		return symjaLayout;
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
			//---TEST-HEADERNAMES----	result=iEx.caliculate(getConvertedFormula(activeSheet.getRow(i)));
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
	
	private String  getConvertedFormula2(Row row){
		String formulaString=formulaInputArea.getValue();
		String s=formulaInputArea.getValue();
		 System.out.println("Formula:"+s);
		List<String> operatorList = new ArrayList<String>();
		 List<String> operandList = new ArrayList<String>();
		 StringTokenizer st = new StringTokenizer(formulaString, "+-*/%(){[]}0123456789^$#sqrtabcdefghijklmnopuvwxyz", true);
		 while (st.hasMoreTokens()) {
		    String token = st.nextToken();

		    if ("+-*/%(){[]}0123456789^$#sqrtabcdefghijklmnopuvwxyz".contains(token)) {
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
//			 cr=new CellReference(operandList.get(i));
//			 cr=new CellReference(operandList.get(i));
			 if(operandList.size()>0){
			 if(formulaString.contains(operandList.get(i))){
					for (int k=0;k<(getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getRow(0).getLastCellNum()-1);k++) {
						if(getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getRow(0)!=null){
						if(getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getRow(0).getCell(i)!=null){
//							colC.addItem(getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getRow(0).getCell(i).getStringCellValue());
						if(getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getRow(0).getCell(k).getStringCellValue().contains(operandList.get(i)))
							cellValue=getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getRow(0).getCell(k).getStringCellValue();
						}
						}
					}
//				 row.getCell(cr.getCol()).setCellType(Cell.CELL_TYPE_STRING);
//					 cellValue=row.getCell(cr.getCol()).getStringCellValue();
				 System.out.println("Cell:" + cellValue);
				 formulaString=formulaString.replace(operandList.get(i),cellValue);
				 }
			 }
//		 }
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
		if(getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getRow(0)!=null){
		if(getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getRow(0).getCell(i)!=null){
//			colA.addItem(getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getRow(0).getCell(i).getStringCellValue());
//			
//			colB.addItem(getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getRow(0).getCell(i).getStringCellValue());
			
			colC.addItem(getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getRow(0).getCell(i).getStringCellValue());
		}
		}
	}	
	
}

private String convertToMathML(){
	StringWriter stw = null;
	try { 
		// false -> distinguish between upper- and lowercase identifiers:
		Config.PARSER_USE_LOWERCASE_SYMBOLS = false;
		// false -> switch to Mathematica syntax mode:
		EvalEngine engine = new EvalEngine(false);
		// don't use m: prefix for mathml tags / don't print MathML header information
		MathMLUtilities mathUtil = new MathMLUtilities(engine, false, false);

		
//		 stw = new StringWriter();
//		mathUtil.toMathML("MatrixForm[{{a,b},{c,d}}]", stw);
//		// print: <math><mrow><mo>(</mo><mtable><mtr><mtd><mi>a</mi></mtd><mtd><mi>b</mi></mtd></mtr><mtr><mtd><mi>c</mi></mtd><mtd><mi>d</mi></mtd></mtr></mtable><mo>)</mo></mrow></math>
//		System.out.println(stw.toString());
		
		 stw = new StringWriter();
//		mathUtil.toMathML("Sum[i, {i,1,n}]", stw);      ----TEST
		mathUtil.toMathML(formulaInputArea.getValue(), stw); 
		// print: <math><mrow><munderover><mo>&#x2211;</mo><mrow><mi>i</mi><mo>=</mo><mn>1</mn></mrow><mi>n</mi></munderover><mi>i</mi></mrow></math> 
		System.out.println(stw.toString());
		
//		 stw = new StringWriter();
//		mathUtil.toMathML("MatrixForm[{{a,b},{c,d}}]", stw);
		// print: <math><mrow><mo>(</mo><mtable><mtr><mtd><mi>a</mi></mtd><mtd><mi>b</mi></mtd></mtr><mtr><mtd><mi>c</mi></mtd><mtd><mi>d</mi></mtd></mtr></mtable><mo>)</mo></mrow></math>
//		System.out.println(stw.toString());
	} catch (SyntaxError e) {
		// catch Symja parser errors here
		System.out.println(e.getMessage());
	} catch (MathException me) {
		// catch Symja math errors here
		System.out.println(me.getMessage());
	} catch (Exception e) {
		e.printStackTrace();
	}	

	return stw.toString();
}

private void renderFormulaForMathML() throws SAXException, ParserConfigurationException, IOException{
	doc=null;
	bi=null;
	img=null;
//	hl=null;
 doc = MathMLParserSupport.parseString(convertToMathML());
			             MutableLayoutContext params = new LayoutContextImpl(
			                    LayoutContextImpl.getDefaultLayoutContext());
			            params.setParameter(Parameter.MATHSIZE, 35f);
			            bi = Converter.getInstance().render(doc, params,
			                    BufferedImage.TYPE_3BYTE_BGR);
	
//	JEuclidView jv=new JEuclidView();
//	BufferedImage image = null;
   img=new Image("",createStreamResource(bi));
    hl=new HorizontalLayout();
    
    hl.addComponent(img);
    hl.markAsDirty();
    renderPanel.setContent(hl);
}

private void renderFormula() throws ParseException{

	  {
	        try {
	            // get the text
	         //-------   String latex = this.latexSource.getText();
String latex=formulaInputArea.getValue();
	            // create a formula
	            TeXFormula formula = new TeXFormula(latex);

	            // render the formla to an icon of the same size as the formula.
	            TeXIcon icon = formula
	                    .createTeXIcon(TeXConstants.STYLE_DISPLAY, 20);

	            // insert a border
	            icon.setInsets(new Insets(5, 5, 5, 5));

	            // now create an actual image of the rendered equation
	            BufferedImage image = new BufferedImage(icon.getIconWidth(),
	                    icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
	            Graphics2D g2 = image.createGraphics();
	            g2.setColor(Color.white);
	            g2.fillRect(0, 0, icon.getIconWidth(), icon.getIconHeight());
	            JLabel jl = new JLabel();
	            jl.setForeground(new Color(0, 0, 0));
	            icon.paintIcon(jl, g2, 0, 0);
	      
	                 Image img=new Image("",createStreamResource(image));
	                 HorizontalLayout hl=new HorizontalLayout();
	                 
	         hl.addComponent(img);
	            renderPanel.setContent(hl);
	        } catch (Exception ex) {
	            ex.printStackTrace();
	            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error",
	                    JOptionPane.INFORMATION_MESSAGE);
	        }
	  }
		
}
private StreamResource createStreamResource(final BufferedImage bi) {
    return new StreamResource(new StreamSource() {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1606243370705307890L;

		@Override
        public InputStream getStream() {

            try {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ImageIO.write(bi, "png", bos);
                return new ByteArrayInputStream(bos.toByteArray());
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }, "dateImage.png");
}

SpreadsheetDemoUI getAppUI() {
	return (SpreadsheetDemoUI) UI.getCurrent();
}

	}