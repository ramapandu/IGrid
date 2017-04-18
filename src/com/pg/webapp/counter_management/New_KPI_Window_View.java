package com.pg.webapp.counter_management;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import com.pg.webapp.SpreadsheetDemoUI;
import com.pg.webapp.XLToDB;
import com.pg.webapp.database.JdbcReadFile;
import com.vaadin.addon.spreadsheet.Spreadsheet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.GridLayout.Area;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

public class New_KPI_Window_View extends New_KPI_Window {

	private static final long serialVersionUID = -2162503814830257799L;
	private Window newKpiWindow;
	New_KPI_Window_2_View nkwv2;
	Update_KPI_Window_View ukwv;
	Delete_KPI_Window_View dkwv;
	Button update_Button, submit_Button, delete_Button;
	String[][] myStringArray = new String[15][5];
	int row_Index=0;
	String gridName;
	ConterManagement_DB cmdb;

	public New_KPI_Window_View() throws ClassNotFoundException, SQLException {
		newKpiWindow = new Window("Counter Management-NEW KPI");
		newKpiWindow.setHeight(700, Unit.PIXELS);
		newKpiWindow.setWidth(1000, Unit.PIXELS);
		newKpiWindow.addStyleName("settings");
		newKpiWindow.setContent(new_Kpi_Container);
		newKpiWindow.setModal(true);
		buildTabSheet();
		buildGridButtons();
generateSheetPreview();

getKpiDataFromDB();
		
	}

	private void getKpiDataFromDB() throws ClassNotFoundException, SQLException {
cmdb=new ConterManagement_DB(); 
		ResultSet rs=cmdb.get_CM_Records();		
		ResultSetMetaData rsmd = rs.getMetaData();
		gridName=rsmd.getTableName(1);
		rs.last();
		rs.beforeFirst();
		
		ResultSetMetaData meta = rs.getMetaData();
		
		int row=1;
		while(rs.next()&&row<=15){
		 
			myStringArray[row][0]=rs.getString(1);
			myStringArray[row][1]=rs.getString(2);
			myStringArray[row][2]=rs.getString(3);
			myStringArray[row][3]=rs.getString(4);
			myStringArray[row][4]=rs.getString(5);			
			row++;
		}
		
//		createButtons();
		for (int row_Index = 0; row_Index < 15;row_Index++) {
			if (new_kpi_gridLayout1.getComponent(0, row_Index) instanceof Label) {
			} else {
				if(myStringArray[row_Index][0]!=null &&myStringArray[row_Index][0]!=""){
				createButtons();
				String kpi_Name = myStringArray[row_Index][3];
				Label kpi_label = new Label("KPI" + "" + String.valueOf(row_Index+1) + "<" + kpi_Name + ">");
				new_kpi_gridLayout1.addComponent(kpi_label, 0, row_Index);
				new_kpi_gridLayout1.addComponent(update_Button, 1, row_Index);
				new_kpi_gridLayout1.addComponent(submit_Button, 2, row_Index);
				new_kpi_gridLayout1.addComponent(delete_Button, 3, row_Index);
//				insertKpiIntoArray(index,getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getSheetName(), "KPI" + String.valueOf(index), tagName, aggregation, formula);
//			row_Index=index;
//				break;
			}
			}
		}
	
		
		
	}

	private void generateSheetPreview() throws ClassNotFoundException, SQLException {
//		Spreadsheet previewSheet=new Spreadsheet();
//		previewSheet=getAppUI().getSpreadsheet_dao().getSpreadsheet();
		
		
		 XLToDB obj = new XLToDB();  //----TEST1
//		 obj.insertRecords();
		ResultSet rs= obj.getRecords(); 
		java.sql.ResultSetMetaData rsmd = rs.getMetaData();
		 System.out.println("Table name"+rsmd.getTableName(1));
		gridName=rsmd.getTableName(1);
		int totalColumns = rsmd.getColumnCount();
		rs.last();
		int totalRows=0;
		totalRows=rs.getRow();
		rs.beforeFirst();
		
		System.out.println("total columns: "+totalColumns+"total rows: "+totalRows);
		 JdbcReadFile jrf=new JdbcReadFile();
		 
		Spreadsheet previewSheet=new Spreadsheet(totalRows,totalColumns+1);
		previewSheet.setSheetName(0, gridName); //SET GRID NAME
//		 Spreadsheet s=new Spreadsheet(jrf.LoadFileFromDB(filePath, fileName));
		previewSheet.setSizeFull();
		previewSheet.setHeight("300px");
		
		previewSheet.setActiveSheetIndex(0);
//		int i=1;
		//----------------------------TEST1-----------------------
		java.sql.ResultSetMetaData meta = rs.getMetaData();
		
		
		
//		gridName=getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getSheetName();
//		System.out.println("Grid Name: "+gridName);
		previewSheet.getActiveSheet().createRow(0);
		for(int j=1;j<=totalColumns;j++){
			System.out.println("Column:"+meta.getColumnLabel(j)+"Num:"+j);
			previewSheet.getActiveSheet().getRow(0).createCell(j-1).setCellValue(meta.getColumnLabel(j));		 
		}
		
		int row=1;
		while(rs.next()){
			previewSheet.getActiveSheet().createRow(row);
			for(int j=1;j<=totalColumns;j++){
//				System.out.println(s.getActiveSheet().getRow(i));
//				System.out.println(rs.getString(j));
				if(row==0)
					previewSheet.getActiveSheet().getRow(row).createCell(j-1).setCellValue(meta.getColumnLabel(j));
				else
					previewSheet.getActiveSheet().getRow(row).createCell(j-1).setCellValue(rs.getString(j));
		 
			}
			row++;
		}
		
		
		preview_vl.addComponent(previewSheet);
	}

	private void buildGridButtons() {
		// new_kpi_gridLayout1.addComponent(component, column, row);
//		createButtons();
	}

	private void buildTabSheet() {
		new_kpi_tabsheet.addSelectedTabChangeListener(new SelectedTabChangeListener() {

			private static final long serialVersionUID = -1568161407214112841L;

			@Override
			public void selectedTabChange(SelectedTabChangeEvent event) {
				com.vaadin.ui.JavaScript.eval("setTimeout(function(){prettyPrint();},300);");
			}
		});

		create_kpi_btn1.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = 8008951408085779254L;

			@Override
			public void buttonClick(ClickEvent event) {
				nkwv2 = new New_KPI_Window_2_View();

				getAppUI().getUI().addWindow(nkwv2.getNewKpiWindow2());
			}
		});

		create_kpi_btn2.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = 8812568271742255387L;

			@Override
			public void buttonClick(ClickEvent event) {

			}
		});

		create_kpi_btn3.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = -8758507220017352677L;

			@Override
			public void buttonClick(ClickEvent event) {

			}
		});

		create_kpi_btn4.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = 7909759170470719983L;

			@Override
			public void buttonClick(ClickEvent event) {

			}
		});

		create_kpi_btn5.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = -1083591838761762511L;

			@Override
			public void buttonClick(ClickEvent event) {

			}
		});

	}

	public Window getNewKpiWindow() {
		newKpiWindow.center();
		return newKpiWindow;
	}

	SpreadsheetDemoUI getAppUI() {
		return (SpreadsheetDemoUI) UI.getCurrent();
	}

	public void createButtons() {
		Label kpi_label;
		String kpi_Name = "";
		
		update_Button = new Button("Update");
		submit_Button = new Button("Submit");
		delete_Button = new Button("Delete");
		
		update_Button.addStyleName("update-button-cm");
submit_Button.addStyleName("submit-button-cm");
delete_Button.addStyleName("delete-button-cm");

		update_Button.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1937340336318912863L;

			@Override
			public void buttonClick(ClickEvent event) {
				ukwv = new Update_KPI_Window_View();
				new_kpi_gridLayout1.getComponentIterator().equals(update_Button.getConnectorId());
				
				Area a = new_kpi_gridLayout1.getComponentArea(event.getButton());
				int button_Index=a.getRow1();
				System.out.println("Button Click index: "+button_Index);
				String[] row=myStringArray[button_Index];
				System.out.println("UPDATE-1 "+row[0]+" "+row[1]+" "+row[2]+" "+row[3]+" "+row[4]);
				getAppUI().getUI().addWindow(ukwv.getUpdateWindow(row));

			}
		});

		submit_Button.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -4770467594962004515L;

			@Override
			public void buttonClick(ClickEvent event) {
				Area a = new_kpi_gridLayout1.getComponentArea(event.getButton());
				row_Index=a.getRow1();
			}
		});

		delete_Button.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -6728816866679274968L;

			@Override
			public void buttonClick(ClickEvent event) {
				Area a = new_kpi_gridLayout1.getComponentArea(event.getButton());
				row_Index=a.getRow1();
				dkwv = new Delete_KPI_Window_View();
				String[] row=myStringArray[row_Index];
				getAppUI().getUI().addWindow(dkwv.getDeleteWindow(row));
			}
		});

		
	}
	
public void addButtonsToGrid(){
	Label kpi_label;
	String kpi_Name = "";
	
	int gridSize = 0;
	gridSize = new_kpi_gridLayout1.getRows();
	System.out.println("Grid size: " + gridSize);
	for (int index = 1; index <= 15; index++) {
		if (new_kpi_gridLayout1.getComponent(0, row_Index) instanceof Label) {
		} else {
			kpi_Name=myStringArray[row_Index][3];
           System.out.println("kpi name: "+kpi_Name+" row index: "+row_Index);

			kpi_label = new Label("KPI" + "" + String.valueOf(row_Index) + "<" + kpi_Name + ">");
			new_kpi_gridLayout1.addComponent(kpi_label, 0, row_Index);
			new_kpi_gridLayout1.addComponent(update_Button, 1, row_Index);
			new_kpi_gridLayout1.addComponent(submit_Button, 2, row_Index);
			new_kpi_gridLayout1.addComponent(delete_Button, 3, row_Index);
//			insertKpiIntoArray(index,getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getSheetName(), "KPI" + String.valueOf(index), tagName, aggregation, formula);
//		row_Index=index;
			break;
		}
	}
}

	public void insertKpiIntoArray(String technology, String displayName, String aggregation,
			String formula) throws ClassNotFoundException, SQLException {
         if(row_Index<=15)
             row_Index++;

		myStringArray[row_Index][0] = technology;
		myStringArray[row_Index][1] = "KPI"+(row_Index);
		myStringArray[row_Index][2] = aggregation;
		myStringArray[row_Index][3] = displayName;
		myStringArray[row_Index][4] = formula;
		       System.out.println("INSERT "+myStringArray[row_Index][3]+" "+row_Index);
		       printAllKpiData();
		       cmdb.insert_CM_Record(gridName, myStringArray[row_Index]);

	}
	
	private void printAllKpiData() {
for(String[] array:myStringArray){
	    System.out.println(array[0]+" "+array[1]+" "+array[2]+" "+array[3]+" "+array[4]+"/n");
	
}
	}

	public void updateKpiData(String technology, String displayName, String aggregation,
			String formula) {

		myStringArray[row_Index][0] = technology;
		myStringArray[row_Index][1] = "KPI"+(row_Index);
		myStringArray[row_Index][2] = aggregation;
		myStringArray[row_Index][3] = displayName;
		myStringArray[row_Index][4] = formula;
		       System.out.println("UPDATE-3 "+myStringArray[row_Index][3]+" "+row_Index);

	}
	
	public void deleteKpiData(){
		myStringArray[row_Index][0] ="";
		myStringArray[row_Index][1] ="";
		myStringArray[row_Index][2] ="";
		myStringArray[row_Index][3] ="";
		myStringArray[row_Index][4] ="";
		
		new_kpi_gridLayout1.removeComponent(0,row_Index);
		new_kpi_gridLayout1.removeComponent(1,row_Index);
		new_kpi_gridLayout1.removeComponent(2,row_Index);
		new_kpi_gridLayout1.removeComponent(3,row_Index);
	}

}
