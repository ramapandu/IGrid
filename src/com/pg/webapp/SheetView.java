package com.pg.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.pg.webapp.symja.InEqualityExample;
import com.vaadin.addon.spreadsheet.Spreadsheet;
import com.vaadin.addon.spreadsheet.Spreadsheet.CellValueChangeEvent;
import com.vaadin.addon.spreadsheet.Spreadsheet.CellValueChangeListener;
import com.vaadin.addon.spreadsheet.Spreadsheet.SheetChangeEvent;
import com.vaadin.addon.spreadsheet.Spreadsheet.SheetChangeListener;
import com.vaadin.addon.spreadsheet.SpreadsheetFilterTable;
import com.vaadin.data.Item;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class SheetView extends CustomComponent implements View {

	public static final String NAME = "sheet";
	private static final long serialVersionUID = 6714096000861957459L;
	Button logoutButton;

	CellRangeAddress range;
	SpreadsheetFilterTable table;
	VerticalLayout rootLayout;
	Spreadsheet spreadsheet;
	HorizontalLayout topBar, sheetLayout;
	Button editButton, saveButton, downlaodButton, exportButton,settingsButton,symjaSubmitButton;
	File testSheetFile;
	Table logTable;
	Workbook logBook;
	Sheet logSheet;
	String filePath="C:/Users/rampa/Desktop/testsheets/";
	String fileName="test.xlsx";
	TextArea symjaInputArea;
	Label symjaText;
	

	private TabSheet tabSheet;

	VaadinSession ses;
	FileInputStream fis;

	@SuppressWarnings({ "unused" })
	public SheetView() throws ClassNotFoundException, SQLException {
		setSizeFull();

		rootLayout = new VerticalLayout();
		setCompositionRoot(rootLayout);
		CreateUI();
       getAppUI().setSheetView(this);
	}

	private void CreateUI() throws ClassNotFoundException, SQLException {

		rootLayout.addComponent(getTopBar());
		rootLayout.addComponent(getSheetLayout());
	}

	public HorizontalLayout getSheetLayout() throws ClassNotFoundException, SQLException {
		sheetLayout = new HorizontalLayout();
		sheetLayout.setSizeFull();
		sheetLayout.setHeight("100%");
		sheetLayout.addComponent(getTabSheet());
		sheetLayout.addStyleName("sheetlayout");
		return sheetLayout;
	}

	private HorizontalLayout getTopBar() {
		topBar = new HorizontalLayout();
		getEditButton();
		getLogoutButton();
		getSaveButton();
		getDownloadButton();
		getExportButton();
		getSettingsButton();
		final GridLayout grid = new GridLayout(6, 1);
		// grid.setWidth(400, Unit.PIXELS);
		grid.setHeight(35, Unit.PIXELS);

		grid.addComponent(editButton, 0, 0);
		grid.setComponentAlignment(editButton, Alignment.TOP_LEFT);

		grid.addComponent(saveButton, 1, 0);
		grid.setComponentAlignment(saveButton, Alignment.TOP_CENTER);

		grid.addComponent(downlaodButton, 2, 0);
		grid.setComponentAlignment(downlaodButton, Alignment.TOP_RIGHT);

		grid.addComponent(exportButton, 3, 0);
		grid.setComponentAlignment(exportButton, Alignment.TOP_RIGHT);

		grid.addComponent(logoutButton, 4, 0);
		grid.setComponentAlignment(logoutButton, Alignment.TOP_RIGHT);
		
		grid.addComponent(settingsButton,5,0);
		grid.setComponentAlignment(settingsButton, Alignment.TOP_RIGHT);

		topBar.setPrimaryStyleName("topbar");
		// topBar.addComponent(getEditButton());
		// topBar.addComponent(getSaveButton());
		// topBar.addComponent(getDownloadButton());
		// topBar.addComponent(getExportButton());
		// topBar.setComponentAlignment(exportButton,Alignment.TOP_CENTER);

		// topBar.addComponent(logoutButton);
		// topBar.setComponentAlignment(logoutButton,Alignment.TOP_RIGHT);

		// topBar.setComponentAlignment(logoutButton, Alignment.BOTTOM_RIGHT);
		topBar.addComponent(grid);
		return topBar;
	}

	@SuppressWarnings("unchecked")
	private TabSheet getTabSheet() throws ClassNotFoundException, SQLException {
		tabSheet = new TabSheet();

		tabSheet.addSelectedTabChangeListener(new SelectedTabChangeListener() {
			private static final long serialVersionUID = -1698363226401049948L;

			@Override
			public void selectedTabChange(SelectedTabChangeEvent event) {
				com.vaadin.ui.JavaScript
						.eval("setTimeout(function(){prettyPrint();},300);");
			}
		});
		tabSheet.setSizeFull();
		tabSheet.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
		try {
//			tabSheet.addTab(openSheet(), "Sheet");------TEST
			VerticalLayout vl=new VerticalLayout();
			
			
			vl.addComponent(openSheetFromDB());
			vl.addComponent(getSymjaComponent());
//			tabSheet.addTab(openSheetFromDB(), "Sheet");-----TEST for symja
			tabSheet.addTab(vl, "Sheet");
			getLogSheet();
			logTable.setPageLength(logTable.size());

			tabSheet.addTab(logTable, "Logs");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return tabSheet;
	}

	private Component getSymjaComponent() {
		HorizontalLayout hl=new HorizontalLayout();
		hl.setHeight("150px");
		symjaInputArea=new TextArea();
		symjaInputArea.setValue("TEST");
		symjaInputArea.setHeight("150px");
		symjaInputArea.setWidth("100%");
		hl.addComponent(symjaInputArea);
		hl.addComponent(getSymjaSubmitButton());
		symjaText=new Label("Symja");
		symjaText.setImmediate(true);
//		InEqualityExample ex=new InEqualityExample();
//		symjaText.setValue(ex.caliculate());
		hl.addComponent(symjaText);
		return hl;
	}
	
	private Button getSymjaSubmitButton() {
		symjaSubmitButton = new Button("CALICULATE");
		symjaSubmitButton.addStyleName("topbarbuttons");
		symjaSubmitButton.setImmediate(true);
		symjaSubmitButton.setEnabled(true);
		symjaSubmitButton.addClickListener(new ClickListener(){

			private static final long serialVersionUID = -8158301975694183254L;

			@Override
			public void buttonClick(ClickEvent event) {
				InEqualityExample ex=new InEqualityExample();
				symjaText.setValue(ex.caliculate(symjaInputArea.getValue()));
			}
		});
		return symjaSubmitButton;
	}

	private Table getLogSheet() throws IOException {
		FileInputStream fs = new FileInputStream(
				"C:/Users/rampa/Desktop/testsheets/logs.xlsx");
		logTable = new Table();
		logTable.setWidth("900px");
		logTable.setHeight("800px");
		logTable.addContainerProperty("User", String.class, null);
		logTable.addContainerProperty("Action", String.class, null);
		logTable.addContainerProperty("Date", String.class, null);
		// Spreadsheet logSheet= new Spreadsheet(fs);

		 logBook = new XSSFWorkbook(fs);
		logSheet = logBook.getSheetAt(0);
		int i = 0;
		for (Row row : logSheet) {
			// for (Cell cell : row) {
			if (row.getRowNum() > 0)
				logTable.addItem(new Object[] { row.getCell(0).toString(),
						row.getCell(1).toString(), row.getCell(2).toString() },
						new Integer(i));
			// }
			i++;
		}
		fs.close();
//       logBook.close();
		return logTable;
	}

	private Button getLogoutButton() {
		logoutButton = new Button("Logout", new Button.ClickListener() {

			private static final long serialVersionUID = 2718672708618255597L;

			@Override
			public void buttonClick(ClickEvent event) {
				getSession().close();
				((SpreadsheetDemoUI) UI.getCurrent()).getUser()
						.setLoggedInUser(null);
				getUI().getPage().setLocation("/webapp-v5");
			}
		});
		logoutButton.addStyleName("topbarbuttons");
		return logoutButton;
	}

	private Button getExportButton() {
		exportButton = new Button("EXPORT");
		exportButton.addStyleName("topbarbuttons");
		exportButton.addClickListener(new ClickListener() {

			private static final long serialVersionUID = -7614812368402111788L;

			@Override
			public void buttonClick(ClickEvent event) {

			}
		});
		return exportButton;
	}

	private Button getDownloadButton() {
		downlaodButton = new Button("DOWNLOAD");
		downlaodButton.addStyleName("topbarbuttons");
		downlaodButton.addClickListener(new ClickListener() {

			private static final long serialVersionUID = -8158301975694183254L;

			@Override
			public void buttonClick(ClickEvent event) {
				File file = new File(filePath,
						"test.xlsx");
				FileResource resource = new FileResource(file);
				FileDownloader fileDownloader = new FileDownloader(resource);
				fileDownloader.extend(downlaodButton);
			}
		});
		return downlaodButton;
	}

	private Button getEditButton() {
		editButton = new Button("EDIT");
		editButton.addStyleName("topbarbuttons");
		editButton.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 483869047651452271L;

		@Override
			public void buttonClick(ClickEvent event) {
				enableEdit();
			}
		});
		return editButton;
	}

//	public Button getSaveButton() {
//		saveButton = new Button("SAVE");
//		saveButton.addStyleName("topbarbuttons");
//		saveButton.addClickListener(new ClickListener() {
//
//			private static final long serialVersionUID = 1792550832130526578L;
//
//			@Override
//			public void buttonClick(ClickEvent event) {
//				try {
//					File tempFile = new File(filePath,fileName);
//					FileOutputStream fos = new FileOutputStream(tempFile);
//					getAppUI().getSpreadsheet_dao().getSpreadsheet().write(fos);
////					getAppUI().
//					fos.flush();
//					fos.close();
//					
//										
//					File tempFile2 = new File("C:/Users/rampa/Desktop/testsheets/logs.xlsx");
//					FileInputStream fis2 = new FileInputStream(tempFile2);
//					
////					Spreadsheet s=new Spreadsheet(fis2);
////					Workbook wb;
////					int i=s.getLastRow();
//					
//					Collection<?> coll = logTable.getContainerDataSource().getItemIds();
//					Iterator<?> iterate=coll.iterator();
//					
//					int i=logSheet.getLastRowNum();
//					logSheet=logBook.getSheetAt(0);
//					
//	                for(int x = 1; x <= coll.size(); x++){
//	                	
//	                   Item item=logTable.getItem(iterate.next());
//	                    
////	                   System.out.println(item.getItemProperty(1).getValue().toString());
////	                   System.out.println(item.getItemProperty(2).getValue().toString());
////	                   System.out.println(item.getItemProperty(3).getValue().toString());
//	                    						
//						logSheet.getRow(i).getCell(0).setCellValue(item.getItemProperty("User").getValue().toString());
//						logSheet.getRow(i).getCell(0).setCellValue(item.getItemProperty("Action").getValue().toString());
//						logSheet.getRow(i).getCell(0).setCellValue(item.getItemProperty("Date").getValue().toString());
//						
////						logSheet.createRow(i);
////						System.out.println("sheet "+logSheet.getRow(i).getCell(0).getStringCellValue());
////						System.out.println("table "+logTable.getContainerDataSource().getItem(k).getItemProperty(1).getValue().toString());
//						
//						
////						logSheet.getRow(i).getCell(0).setCellValue(logTable.getContainerDataSource().getItem(x).getItemProperty(1).getValue().toString());
////						logSheet.getRow(i).getCell(0).setCellValue(logTable.getContainerDataSource().getItem(x).getItemProperty(2).getValue().toString());
////						logSheet.getRow(i).getCell(0).setCellValue(logTable.getContainerDataSource().getItem(x).getItemProperty(3).getValue().toString());
//								
////						logSheet.getRow(i+1).getCell(0).setCellValue(logTable.getItem(k).getItemProperty(1).getValue().toString());
////						logSheet.getRow(i+1).getCell(1).setCellValue(logTable.getItem(k).getItemProperty(2).getValue().toString());
////						logSheet.getRow(i+1).getCell(2).setCellValue(logTable.getItem(k).getItemProperty(3).getValue().toString());
////						if(i<k)
//						
//	               				
//					}
//				
//					FileOutputStream fos2 = new FileOutputStream(tempFile2);
//				    logBook.write(fos2);
//					fos2.flush();
//					fos2.close();
//					
//					Notification.show("Spreadsheet saved !!!");
//					
////					logBook.close();
////					Byte[] bytes;
////					ByteArrayInputStream bis=new ByteArrayInputStream(lo
////					logBook.write(fos2);
////					fos2.flush();
////					fos2.close();
////					ExcelExport excelExport;
//					
////					File tempFile2 = new File("C:/Users/rampa/Desktop/testsheets/logs.xlsx");
////					org.apache.poi.openxml4j.opc.OPCPackage opc = 
////							   org.apache.poi.openxml4j.opc.OPCPackage.open(tempFile2);
////							org.apache.poi.xssf.usermodel.XSSFWorkbook wb =
////							   new org.apache.poi.xssf.usermodel.XSSFWorkbook(opc);
////							java.io.FileOutputStream fileOut = new java.io.FileOutputStream(tempFile2);
////							wb.write(fileOut);
////							opc.close();
////							fileOut.close(); 
//							
////							FileInputStream fis = null; 
////							try { 
////							  fis = new FileInputStream(inputFilePath ); 
////							  XSSFWorkbook workbook = new XSSFWorkbook(fis); 
//					
//					// ------getAppUI().getLogTable().setLogTable(logTable);
//					// ByteArrayOutputStream bos = new ByteArrayOutputStream();
//					// spreadsheet.write(bos);
//					// byte[] data = bos.toByteArray();
//					// bos.close();
//					
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//		return saveButton;
//	}

	public Button getSaveButton() {
		saveButton = new Button("SAVE");
		saveButton.addStyleName("topbarbuttons");
		saveButton.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1792550832130526578L;

			@Override
			public void buttonClick(ClickEvent event) {
				try {
//					File tempFile = new File(filePath,fileName);
//					FileOutputStream fos = new FileOutputStream(tempFile);
//					getAppUI().getSpreadsheet_dao().getSpreadsheet().write(fos);
//					fos.flush();
//					fos.close();
					saveSheetToDB();
										
					File tempFile2 = new File("C:/Users/rampa/Desktop/testsheets/logs.xlsx");
					FileInputStream fis2 = new FileInputStream(tempFile2);
					
//					Spreadsheet s=new Spreadsheet(fis2);
//					Workbook wb;
//					int i=s.getLastRow();
					
					Collection<?> coll = logTable.getContainerDataSource().getItemIds();
					Iterator<?> iterate=coll.iterator();
					
					int i=logSheet.getLastRowNum();
					logSheet=logBook.getSheetAt(0);
					
	                for(int x = 1; x <= coll.size(); x++){
	                	
	                   Item item=logTable.getItem(iterate.next());
	                    						
						logSheet.getRow(i).getCell(0).setCellValue(item.getItemProperty("User").getValue().toString());
						logSheet.getRow(i).getCell(0).setCellValue(item.getItemProperty("Action").getValue().toString());
						logSheet.getRow(i).getCell(0).setCellValue(item.getItemProperty("Date").getValue().toString());
											               				
					}
				
					FileOutputStream fos2 = new FileOutputStream(tempFile2);
				    logBook.write(fos2);
					fos2.flush();
					fos2.close();
					
					Notification.show("Spreadsheet saved !!!");
										
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		return saveButton;
	}

	private Button getSettingsButton() {
		settingsButton = new Button("Settings");
		settingsButton.addStyleName("topbarbuttons");
		settingsButton.addClickListener(new ClickListener() {

			private static final long serialVersionUID = -826402340367265552L;

			@Override
			public void buttonClick(ClickEvent event) {
//			getAppUI().getCurrent().addWindow(window);
//				getAppUI().getNavigator().navigateTo("settings");	
				SettingsView settingsView=new SettingsView();
				settingsView.openSettingsWindow();
			}
		});
		return settingsButton;
	}
	
	public Spreadsheet openSheet() throws URISyntaxException, IOException {
		fis = new FileInputStream(filePath+fileName);
		spreadsheet = new Spreadsheet(fis);
		fis.close();
		
		spreadsheet.setSizeFull();
		spreadsheet.setHeight("550px");
		getAppUI().getSpreadsheet_dao().setSpreadsheet(spreadsheet);
		getPopUpButtonsForSheet(spreadsheet.getActiveSheet());
		changeHeaderColor();
        
		spreadsheet.addSheetChangeListener(new SheetChangeListener() {

			private static final long serialVersionUID = -5585430837302587763L;

			@Override
			public void onSheetChange(SheetChangeEvent event) {
				spreadsheet.unregisterTable(table);
				getPopUpButtonsForSheet(spreadsheet.getActiveSheet());
				changeHeaderColor();
			}
		});
		spreadsheet.addCellValueChangeListener(new CellValueChangeListener() {
		
			private static final long serialVersionUID = 1334987428943711253L;
                @Override
				public void onCellValueChange(CellValueChangeEvent event) {
               updateLogTable(event);
			}
		});
		
		return spreadsheet;
	}
	
	private Spreadsheet openSheetFromDB() throws ClassNotFoundException, SQLException{
//		saveSheetToDB();     //----------TEST
		 XLToDB obj = new XLToDB();
//		 obj.insertRecords();
		ResultSet rs= obj.getRecords();
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnsNumber = rsmd.getColumnCount();
		
		Spreadsheet s=new Spreadsheet();
		s.setSizeFull();
		s.setHeight("400px");
		
//		s.createNewSheet("TEST2", 100, columnsNumber);
		s.setActiveSheetIndex(0);
		int i=0;
		while(rs.next()){
			s.getActiveSheet().createRow(i);
			for(int j=1;j<columnsNumber;j++){
//				System.out.println(s.getActiveSheet().getRow(i));
//				System.out.println(rs.getString(j));
		s.getActiveSheet().getRow(i).createCell(j-1).setCellValue(rs.getString(j));
		 
			}
		i++;
		}
		s.refreshAllCellValues();
//		s.setDefaultColumnWidth(110);
		getAppUI().getSpreadsheet_dao().setSpreadsheet(s);
		getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().setDefaultColumnWidth(110);
		getPopUpButtonsForSheet(getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet());
//		changeHeaderColor();
//		getAppUI().getCurrent().getPage().reload();
		
		getAppUI().getSpreadsheet_dao().getSpreadsheet().addSheetChangeListener(new SheetChangeListener() {

			private static final long serialVersionUID = -5585430837302587763L;

			@Override
			public void onSheetChange(SheetChangeEvent event) {
				getAppUI().getSpreadsheet_dao().getSpreadsheet().unregisterTable(table);
				getPopUpButtonsForSheet(getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet());
				changeHeaderColor();
			}
		});
		getAppUI().getSpreadsheet_dao().getSpreadsheet().addCellValueChangeListener(new CellValueChangeListener() {
		
			private static final long serialVersionUID = 1334987428943711253L;
                @Override
				public void onCellValueChange(CellValueChangeEvent event) {
               updateLogTable(event);
			}
		});
		
		s.getActiveSheet().getRow(0).createCell(8).setCellValue("TESTING");
		return s;
		
	}

	private void saveSheetToDB(){
		 XLToDB obj = new XLToDB();
         obj.insertRecordsFromSheet();
         
	}
	
	private void updateLogTable(CellValueChangeEvent event) {
	 	Set<CellReference> changedCells = null;
    	changedCells = event.getChangedCells();
       
        Iterator<CellReference> iterator = changedCells.iterator();
        int i=logTable.size();
        CellReference cr;
        Row r;
        Cell c;
        String[] element;
        while(iterator.hasNext()){
          cr=iterator.next();
          element =cr.getCellRefParts();
          r = getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getRow(Integer.valueOf(element[1])-1);
          c=null;
        	c=r.getCell(new Integer(cr.getCol()));
        
			Date d = new Date();
			 
         logTable.addItem(new Object[] { getAppUI().getUser().getLoggedInUser(),
        		"Changed value in Cell-"+element[2]+""+element[1]+" in sheet-"+
        				getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getSheetName()+" to "+c,d.toString() },
					new Integer(i+1));
        }					
	}
	
	private void changeHeaderColor() {
		
		//TEST
		CellStyle headerStyle = getAppUI().getSpreadsheet_dao().getSpreadsheet().getWorkbook().createCellStyle();
		
				for (int i=0;i<(getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getRow(0).getLastCellNum()-1);i++) {
//					cell=activeSheet.getRow(0).getCell(i);
					if(getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getRow(0).getCell(i).getStringCellValue()!=null){
					
						getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getRow(0).getCell(i).getCellStyle().setFillForegroundColor(IndexedColors.BLACK.getIndex());
//					activeSheet.getRow(0).getCell(i).getCellStyle().setFillBackgroundColor(IndexedColors.RED.getIndex());
					
					}
				}
//				}
				
		
		
	}

	public void getPopUpButtonsForSheet(Sheet sheet)
			throws NullPointerException, ArrayIndexOutOfBoundsException {

		try {
			 Row r = getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getRow(getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getFirstRowNum());
			 int lastColumnNum=0;
			 lastColumnNum= r.getLastCellNum()-1;

			range = new CellRangeAddress(0, sheet.getLastRowNum(), 0, lastColumnNum);
//			System.out.println("FIRSTROW:"+sheet.getFirstRowNum() + " LASTROW:"
//					+ sheet.getLastRowNum() + " LASTCOLUMN:" + lastColumnNum+" SHEET:"+sheet.getSheetName());
			// Create a table in the range
			 table = new SpreadsheetFilterTable(
					getAppUI().getSpreadsheet_dao().getSpreadsheet(), getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet(), range);
			table.getPopupButtons();
	
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}

	public void getPopUpButtonsForAllSheets() {
		for (int i = 0; i < getAppUI().getSpreadsheet_dao().getSpreadsheet().getNumberOfSheets(); i++) {
			Sheet s = getAppUI().getSpreadsheet_dao().getSpreadsheet().getWorkbook().getSheetAt(i);
		
			getPopUpButtonsForSheet(s);
		}
	}

	// @Override
	// public void valueChange(ValueChangeEvent event) {
	// // spreadsheet.addSheetChangeListener(selectedSheetChangeListener);
	// // selectedSheetChangeListener = new SheetChangeListener() {
	// // @Override
	// // public void onSheetChange(SheetChangeEvent event) {
	// //// getPopUpButtons();
	// // getPopUpButtons(spreadsheet.getActiveSheet());
	// // }
	// // };
	// // Object value = event.getProperty().getValue();
	// // open(value);
	// //// getPopUpButtons();
	// }

	public void enableEdit() {
		// if(editButton.isEnabled())
		// spreadsheet.setActiveSheetProtected(null);
	}

	// @Override
	// public void valueChange(ValueChangeEvent event) {
	// // TODO Auto-generated method stub
	//
	// }

	// private void open(Object value) {
	// tabSheet.removeAllComponents();
	// if (value instanceof File) {
	// // openFile((File) value);
	//
	// }
	// }

	private Component getUserProfile() {
		HorizontalLayout profileLayout = new HorizontalLayout();
		profileLayout.setStyleName("contentbar");
		Table profileTable = new Table();
		profileTable.setHeight("400px");
		profileTable.setWidth("250px");
		profileTable.setSizeUndefined();
		profileTable.addContainerProperty("Detail", String.class, null);
		profileTable.addContainerProperty("Value", String.class, null);
		profileTable.addItem(new Object[] { "Name :", "" }, 1);
		profileTable.addItem(new Object[] { "User Name :", "" }, 2);
		profileTable.addItem(new Object[] { "Password :", "******" }, 3);
		profileTable.addItem(new Object[] { "Email Id:", "" }, 4);

		profileLayout.addComponent(profileTable);
		return profileLayout;
	}

	@Override
	public void enter(ViewChangeEvent event) {

	}

	SpreadsheetDemoUI getAppUI() {
		return (SpreadsheetDemoUI) UI.getCurrent();
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
