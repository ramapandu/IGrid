package com.pg.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import javax.security.sasl.AuthenticationException;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.pg.webapp.counter_management.CounterManagementView;
import com.pg.webapp.counter_management.New_KPI_Window_View;
import com.pg.webapp.database.JdbcInsertFileTwo;
import com.pg.webapp.database.JdbcReadFile;
import com.pg.webapp.security.LDAP_Test_3;
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
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.Upload.FinishedEvent;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class SheetView extends CustomComponent implements View {

	public static final String NAME = "sheet";
	private static final long serialVersionUID = 6714096000861957459L;
	Button logoutButton;
	Panel rootPanel;
	CellRangeAddress range;
	SpreadsheetFilterTable table;
	VerticalLayout rootLayout;
	Spreadsheet spreadsheet;
	HorizontalLayout topBar, sheetLayout;
	Button editButton, saveButton, downlaodButton, importButton,exportButton,settingsButton,symjaSubmitButton,showheadersButton,counterButton;
	File testSheetFile;
	Table logTable;
	Workbook logBook;
	Sheet logSheet;
	String filePath="C:/Users/rampa/Desktop/testsheets/";
	String fileName="sheet22.xlsx";
	String gridName;
	CounterManagementView cm;
	New_KPI_Window_View nkv;
	  JdbcInsertFileTwo jif;
	UploadXLFile uf;
//	TextArea symjaInputArea;
//	Label symjaText;
	boolean visible=true;

	private TabSheet tabSheet;

	VaadinSession ses;
	FileInputStream fis;

	@SuppressWarnings({ "unused" })
	public SheetView() throws ClassNotFoundException, SQLException, OpenXML4JException, URISyntaxException {
//		setSizeFull();
         rootPanel=new Panel("IGrid");
         rootPanel.setSizeFull();
         
		rootLayout = new VerticalLayout();
		rootLayout.setSizeFull();
		rootPanel.setContent(rootLayout);
		setCompositionRoot(rootPanel);
		CreateUI();
//       getAppUI().setSheetView(this);
	}

	private void CreateUI() throws ClassNotFoundException, SQLException, OpenXML4JException, URISyntaxException {
		getTopBar();
		rootLayout.addComponent(topBar);
		rootLayout.setExpandRatio(topBar,1f);
		FormulaBar fb=new FormulaBar();
		rootLayout.addComponent(fb.getMenuBar());
//		rootLayout.setExpandRatio(fb.getMenuBar(),2f);
		getSheetLayout();
		rootLayout.addComponent(sheetLayout);
		rootLayout.setExpandRatio(sheetLayout,3f);
		SymjaUI symja=new SymjaUI();
		rootLayout.addComponent(symja.getSymjaComponent());
		rootLayout.setExpandRatio(symja.getSymjaComponent(),4f);
	}

	public HorizontalLayout getSheetLayout() throws ClassNotFoundException, SQLException, OpenXML4JException, URISyntaxException {
		sheetLayout = new HorizontalLayout();
		sheetLayout.setSizeFull();
		sheetLayout.setSizeUndefined();
		sheetLayout.setHeight("80%");
		sheetLayout.setWidth("100%");
		sheetLayout.addComponent(getTabSheet());
		sheetLayout.setStyleName("sheetlayout");
		return sheetLayout;
	}

	private HorizontalLayout getTopBar() {
		topBar = new HorizontalLayout();
		getEditButton();
		getLogoutButton();
		getSaveButton();
		getDownloadButton();
		getImportButton();
		getExportButton();
		getSettingsButton();
		getCounterButton();
		getShowButton();
		getBrowseBox();
		final GridLayout grid = new GridLayout(9, 1);
		// grid.setWidth(400, Unit.PIXELS);
		grid.setHeight(35, Unit.PIXELS);

		grid.addComponent(editButton, 0, 0);
		grid.setComponentAlignment(editButton, Alignment.TOP_LEFT);

		grid.addComponent(saveButton, 1, 0);
		grid.setComponentAlignment(saveButton, Alignment.TOP_CENTER);

		grid.addComponent(downlaodButton, 2, 0);
		grid.setComponentAlignment(downlaodButton, Alignment.TOP_RIGHT);
		
		grid.addComponent(importButton, 3, 0);
		grid.setComponentAlignment(importButton, Alignment.TOP_RIGHT);

		grid.addComponent(exportButton, 4, 0);
		grid.setComponentAlignment(exportButton, Alignment.TOP_RIGHT);

		grid.addComponent(logoutButton, 5, 0);
		grid.setComponentAlignment(logoutButton, Alignment.TOP_RIGHT);
		
		grid.addComponent(settingsButton,6,0);
		grid.setComponentAlignment(settingsButton, Alignment.TOP_RIGHT);
		
		grid.addComponent(showheadersButton,7,0);
		grid.setComponentAlignment(showheadersButton, Alignment.TOP_RIGHT);

		grid.addComponent(counterButton,8,0);
		grid.setComponentAlignment(counterButton, Alignment.TOP_RIGHT);

		
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

	private void getBrowseBox() {
		
	}

	@SuppressWarnings("unchecked")
	private TabSheet getTabSheet() throws ClassNotFoundException, SQLException, OpenXML4JException, URISyntaxException {
		tabSheet = new TabSheet();

		tabSheet.addSelectedTabChangeListener(new SelectedTabChangeListener() {
			private static final long serialVersionUID = -1698363226401049948L;

			@Override
			public void selectedTabChange(SelectedTabChangeEvent event) {
				com.vaadin.ui.JavaScript
						.eval("setTimeout(function(){prettyPrint();},300);");
			}
		});
//		tabSheet.setSizeFull();
		tabSheet.setHeight("30%");
		tabSheet.setWidth("100%");
//		tabSheet.setSizeUndefined();
		tabSheet.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
		try {
			VerticalLayout vl=new VerticalLayout();
			
//			vl.addComponent(openSheet());
			
			vl.addComponent(openSheetFromDB());
			
//			vl.addComponent(openSheetFromDBTwo());//----TEST 1-----

			
			tabSheet.addTab(vl, "Sheet");
//			getLogSheet();   //TEST
			//getLogSheetFromDB();----------PG LAB TEST---------------------------------IMP
//			logTable.setPageLength(logTable.size()); //Test1----
//logTable.setImmediate(true);
			tabSheet.addTab(logTable, "Logs");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return tabSheet;
	}



	private Table getLogSheet() throws IOException {
		FileInputStream fs = new FileInputStream(
				"C:/Users/rampa/Desktop/testsheets/logs.xlsx");
		logTable = new Table();
		logTable.setWidth("900px");
		logTable.setHeight("800px");
		logTable.addContainerProperty("User", String.class, null);
		logTable.addContainerProperty("Old Value", String.class, null);
		logTable.addContainerProperty("New value", String.class, null);
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
	
	private Table getLogSheetFromDB() throws IOException, ClassNotFoundException, SQLException {
	
		logTable = new Table();
		logTable.setWidth("900px");
		logTable.setHeight("800px");
		logTable.addContainerProperty("User", String.class, null);
		logTable.addContainerProperty("Old Value", String.class, null);
		logTable.addContainerProperty("New Value", String.class, null);
		logTable.addContainerProperty("Date", String.class, null);
		// Spreadsheet logSheet= new Spreadsheet(fs);

		Logger lg=new Logger();
		ResultSet rs=lg.getLogs();
		
		while(rs.next()){
			
//			if (row.getRowNum() > 0)
			
				logTable.addItem(new Object[] {rs.getString(2).toString(),rs.getString(3).toString(),rs.getString(4).toString(),
						rs.getString(5).toString() },
						new Integer(rs.getString(1)));		 
			
			
		}
		getAppUI().getLogTable_dao().setLogTable(logTable);
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
				getUI().getPage().setLocation("/IGrid");
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
//                  Notification.show("Export function is not Availble now");
//                  JdbcInsertFileTwo jif=new JdbcInsertFileTwo();
//                  jif.importFile();
                  LDAP_Test_3 lt=new LDAP_Test_3();
//                 if( lt.performAuthentication())
//                	   Notification.show("SUCCESS!!!..................1");
//					
//						try {
//							if(lt.getListOfAllSamAccountName()){
//								
//								try {
//									 Notification.show("SUCCESS!!!..................2");
//								} catch (Exception e) {
//									// TODO Auto-generated catch block
//									e.printStackTrace();
//								}
//							}
							
							try {
								if(lt.LDAP_Test()){
									 Notification.show("SUCCESS!!!..................3");
								}
							} catch (AuthenticationException e) {
								e.printStackTrace();
							}

						}
//			}
			});
		return exportButton;
	}
	
	private Button getImportButton() {
		importButton = new Button("IMPORT");
		importButton.addStyleName("topbarbuttons");
		importButton.addClickListener(new ClickListener() {

			private static final long serialVersionUID = -7614812368402111788L;

			@SuppressWarnings("deprecation")
			@Override
			public void buttonClick(ClickEvent event) {
//                  Notification.show("Importing File ...");
//                JdbcInsertFileTwo jif=new JdbcInsertFileTwo();
//                jif.importFile();
                
                uf=new UploadXLFile();
                uf.uploadFile();
                uf.getUploadStream().addListener(new Upload.FinishedListener() {
        	        private static final long serialVersionUID = 1L;

        	        @Override
        	        public void uploadFinished(FinishedEvent event) {
        	        	File file=uf.getFile();
        	             jif=new JdbcInsertFileTwo();
        	             try {
        	            	 System.out.println("File Name"+uf.getFileName());
        					jif.importFile(file,uf.getFileName());
        				} catch (IOException e) {
        					e.printStackTrace();
        				}
        	        }
        	    });
//                Upload upload=uf.getUploadStream();
               
//                  LDAP_Test_3 lt=new LDAP_Test_3();
//                  try {
//try {
//						if(lt.getListOfAllSamAccountName()){
//							
//							try {
//								 Notification.show("SUCCESS!!!..................2");
//							} catch (Exception e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//						}
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//			}
//                  finally{
//                	  
//                  }}
			
			}
		});
		return importButton;
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
				updateGridInDB();
				try {
					updateLogsInDB();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		return saveButton;
	}
	
private void updateGridInDB(){
	JdbcInsertFileTwo jifl=new JdbcInsertFileTwo();
	jifl.deleteAllRecords(getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getSheetName());
	try {
		
//		saveSheetToDB(); //Test1
		 
	String[] row =new String[getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getRow(0).getLastCellNum()+1] ; 
	 System.out.println(row.length);
	int cellIndex;
	jifl.addColumnToGridTable(getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getSheetName(),"Result1");
	for(int k=1;k<getAppUI().getSpreadsheet_dao().getSpreadsheet().getLastRow();k++){
		 Iterator<Cell> cellIterator = getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getRow(k).iterator();
           
		 cellIndex=0;
		 Cell cell;
//		 insertNewColumnInTable();
//		 KpiListWindowView kl=new KpiListWindowView();
//		 jifl.addColumnToGridTable(getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getSheetName(), cm.getKpiWindowInstance().getAppliedFormula());
		 
		 while (cellIterator.hasNext()){
//            	row[cellIndex]="'"+cellIterator.next().getStringCellValue()+"'";
            	 cell=cellIterator.next();
            	switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC:
                	row[cellIndex]="'"+cell.getNumericCellValue()+"'";
                    break;
                case Cell.CELL_TYPE_STRING:
                	row[cellIndex]="'"+cell.getStringCellValue()+"'";
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                	row[cellIndex]="'"+cell.getBooleanCellValue()+"'";
                    break;
                default:
                	row[cellIndex]= "";
                    break;
                }
            	
            	cellIndex++;
//            	 System.out.println("row size: "+cellIndex);
            }
           
            jifl.updateSheetDB(row,getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getSheetName());
//		row=new String[getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getRow(0).getLastCellNum()];
	}
		
							
//		File tempFile2 = new File("C:/Users/rampa/Desktop/testsheets/logs.xlsx");
//		FileInputStream fis2 = new FileInputStream(tempFile2);
		

		
//		Collection<?> coll = logTable.getContainerDataSource().getItemIds();
//		Iterator<?> iterate=coll.iterator();
		
//		int i=logSheet.getLastRowNum();
//		logSheet=logBook.getSheetAt(0);
		
//        for(int x = 1; x <= coll.size(); x++){
//        	
//           Item item=logTable.getItem(iterate.next());
//            						
//			logSheet.getRow(i).getCell(0).setCellValue(item.getItemProperty("User").getValue().toString());
//			logSheet.getRow(i).getCell(0).setCellValue(item.getItemProperty("Action").getValue().toString());
//			logSheet.getRow(i).getCell(0).setCellValue(item.getItemProperty("Date").getValue().toString());
//								               				
//		}
	
	
//		FileOutputStream fos2 = new FileOutputStream(tempFile2);
//	    logBook.write(fos2);
//		fos2.flush();
//		fos2.close();
		
		Notification.show("Spreadsheet saved !!!");
							
	} catch (Exception e) {
		e.printStackTrace();
	}

	}
	
	private void insertNewColumnInTable() {
	
}

	private void updateLogsInDB() throws ClassNotFoundException, SQLException{
		Collection<?> coll = logTable.getContainerDataSource().getItemIds();
		Iterator<?> iterate=coll.iterator();
			String[] logRow =new String[5] ; 
			
			 Item item;
			Logger logObj=new Logger();
			while(iterate.hasNext()){
				 item=logTable.getItem(iterate.next());
				logRow[0]="NULL";
				logRow[1]="'"+item.getItemProperty("User").getValue().toString()+"'";
				logRow[2]="'"+item.getItemProperty("Old Value").getValue().toString()+"'";
				logRow[3]="'"+item.getItemProperty("New Value").getValue().toString()+"'";
				logRow[4]="'"+item.getItemProperty("Date").getValue().toString()+"'";
				logObj.updateLoggerDB(logRow);
			}
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
	
	private Button getShowButton() {
		showheadersButton = new Button("Show/Hide Headers");
		showheadersButton.addStyleName("topbarbuttons");
		showheadersButton.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 4407233311308428766L;

			@Override
			public void buttonClick(ClickEvent event) {
				if(visible==true){
				getAppUI().getSpreadsheet_dao().getSpreadsheet().setRowColHeadingsVisible(false);
				visible=false;
				}
				else if(visible==false){
					getAppUI().getSpreadsheet_dao().getSpreadsheet().setRowColHeadingsVisible(true);
					visible=true;
					}
			}
		});
		return showheadersButton;
	}
	
	private Button getCounterButton() {
		counterButton = new Button("Counter Mgmt");
		counterButton.addStyleName("topbarbuttons");
		counterButton.addClickListener(new ClickListener() {

			private static final long serialVersionUID = -8124443498179718980L;

			@Override
			public void buttonClick(ClickEvent event) {
//				cm=new CounterManagementView();
//				getAppUI().getUI().addWindow(cm.getCounterMgmtWindow());
				
				nkv=new New_KPI_Window_View();
				getAppUI().setNewKpiMainWindow(nkv);
				getAppUI().getUI().addWindow(nkv.getNewKpiWindow());
			}
		});
		return counterButton;
	}
	
	public Spreadsheet openSheet() throws URISyntaxException, IOException {
		fis = new FileInputStream(filePath+fileName);
		spreadsheet = new Spreadsheet(fis);
		fis.close();
		
		spreadsheet.setSizeFull();
		spreadsheet.setHeight("350px");
		getAppUI().getSpreadsheet_dao().setSpreadsheet(spreadsheet);
		getPopUpButtonsForSheet(spreadsheet.getActiveSheet());
//		changeHeaderColor(); //----TEST-1-----------
        
		spreadsheet.addSheetChangeListener(new SheetChangeListener() {

			private static final long serialVersionUID = -5585430837302587763L;

			@Override
			public void onSheetChange(SheetChangeEvent event) {
				spreadsheet.unregisterTable(table);
				getPopUpButtonsForSheet(spreadsheet.getActiveSheet());
//				changeHeaderColor();//----TEST-1-----------
			}
		});
		spreadsheet.addCellValueChangeListener(new CellValueChangeListener() {
		
			private static final long serialVersionUID = 1334987428943711253L;
                @Override
				public void onCellValueChange(CellValueChangeEvent event) {
//               updateLogTable(event);  //TEST1
                	updateLogTableDB(event);
			}
		});
		
		return spreadsheet;
	}
	
	private Spreadsheet openSheetFromDB() throws ClassNotFoundException, SQLException, IOException{
//		saveSheetToDB();     //----------TEST
		 XLToDB obj = new XLToDB();  //----TEST1
//		 obj.insertRecords();
		ResultSet rs= obj.getRecords(); 
		ResultSetMetaData rsmd = rs.getMetaData();
		 System.out.println("Table name"+rsmd.getTableName(1));
		gridName=rsmd.getTableName(1);
		int totalColumns = rsmd.getColumnCount();
		rs.last();
		int totalRows=0;
		totalRows=rs.getRow();
		rs.beforeFirst();
		
		System.out.println("total columns: "+totalColumns+"total rows: "+totalRows);
		 JdbcReadFile jrf=new JdbcReadFile();
		 
		Spreadsheet s=new Spreadsheet(totalRows,totalColumns+1);
		s.setSheetName(0, gridName); //SET GRID NAME
		getAppUI().getSpreadsheet_dao().setSpreadsheet(s);
//		 Spreadsheet s=new Spreadsheet(jrf.LoadFileFromDB(filePath, fileName));
		getAppUI().getSpreadsheet_dao().getSpreadsheet().setSizeFull();
		getAppUI().getSpreadsheet_dao().getSpreadsheet().setHeight("450px");
		
//		s.createNewSheet("TEST2", 100, totalColumns);
		getAppUI().getSpreadsheet_dao().getSpreadsheet().setActiveSheetIndex(0);
//		int i=1;
		//----------------------------TEST1-----------------------
		ResultSetMetaData meta = rs.getMetaData();
		
		
		
//		gridName=getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getSheetName();
//		System.out.println("Grid Name: "+gridName);
		getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().createRow(0);
		for(int j=1;j<=totalColumns;j++){
			System.out.println("Column:"+meta.getColumnLabel(j)+"Num:"+j);
			getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getRow(0).createCell(j-1).setCellValue(meta.getColumnLabel(j));		 
		}
		
		int row=1;
		while(rs.next()){
			getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().createRow(row);
			for(int j=1;j<=totalColumns;j++){
//				System.out.println(s.getActiveSheet().getRow(i));
//				System.out.println(rs.getString(j));
				if(row==0)
					getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getRow(row).createCell(j-1).setCellValue(meta.getColumnLabel(j));
				else
					getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getRow(row).createCell(j-1).setCellValue(rs.getString(j));
		 
			}
			row++;
		}
		getAppUI().getSpreadsheet_dao().getSpreadsheet().refreshAllCellValues();
		//-------------------TEST1--------------------
//		s.setDefaultColumnWidth(110);
//		getAppUI().getSpreadsheet_dao().setSpreadsheet(s);
//		getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().setDefaultColumnWidth(110);
		getPopUpButtonsForSheet(getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet());
//		changeHeaderColor();
//		getAppUI().getCurrent().getPage().reload();
		
		//FREEZING FIRST ROW
		
		getAppUI().getSpreadsheet_dao().getSpreadsheet().createFreezePane(1,0);
		System.out.println("First row is frozen");
		
		//FREEZING LAST COLUMN
//		getAppUI().getSpreadsheet_dao().getSpreadsheet().createFreezePane(1,totalColumns);
		
		
		
		getAppUI().getSpreadsheet_dao().getSpreadsheet().addSheetChangeListener(new SheetChangeListener() {

			private static final long serialVersionUID = -5585430837302587763L;

			@Override
			public void onSheetChange(SheetChangeEvent event) {
				getAppUI().getSpreadsheet_dao().getSpreadsheet().unregisterTable(table);
				getPopUpButtonsForSheet(getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet());
//				changeHeaderColor(); //----TEST-1-----------
			}
		});
		getAppUI().getSpreadsheet_dao().getSpreadsheet().addCellValueChangeListener(new CellValueChangeListener() {
		
			private static final long serialVersionUID = 1334987428943711253L;
                @Override
				public void onCellValueChange(CellValueChangeEvent event) {
//               updateLogTable(event);
                	System.out.println("Some values in Sheet are changed");
                	updateLogTableDB(event);
                }
		});
		printSheetHeaders();
//		s.getActiveSheet().getRow(0).createCell(8).setCellValue("TESTING");
		getAppUI().getSpreadsheet_dao().getSpreadsheet().setRowColHeadingsVisible(false);
		System.out.println("hidden??:"+getAppUI().getSpreadsheet_dao().getSpreadsheet().isColumnHidden(80));
		System.out.println("Row+Col hidden??:"+getAppUI().getSpreadsheet_dao().getSpreadsheet().isRowColHeadingsVisible());
		
		return getAppUI().getSpreadsheet_dao().getSpreadsheet();
		
	}
	
	
	private void printSheetHeaders() {
		for(int j=1;j<getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getRow(0).getLastCellNum();j++){
			System.out.println("Column:"+getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getRow(0).getCell(j).getStringCellValue()+"Num:"+j+"\t");
					 
		}		
	}

	private Spreadsheet openSheetFromDBTwo() throws ClassNotFoundException, SQLException, IOException, OpenXML4JException{
//		saveSheetToDB();     //----------TEST
		 XLToDB obj = new XLToDB();  //----TEST1
//		 obj.insertRecords();
//		ResultSet rs= obj.getRecords(); ----TEST1
//		ResultSetMetaData rsmd = rs.getMetaData();------TEST1
//		int totalColumns = rsmd.getColumnCount();--------TEST1
		
		 JdbcReadFile jrf=new JdbcReadFile();
		 
		 
//		Spreadsheet s=new Spreadsheet();----TEST1
		 Spreadsheet s=new Spreadsheet(jrf.LoadFileFromDB(filePath, fileName));
		s.setSizeFull();
		s.setHeight("450px");
		
//		s.createNewSheet("TEST2", 100, totalColumns);
		s.setActiveSheetIndex(0);
		
//		int i=1;
		//----------------------------TEST1-----------------------
//		ResultSetMetaData meta = rs.getMetaData();
//		s.getActiveSheet().createRow(0);
//		for(int j=1;j<=totalColumns;j++){
//			s.getActiveSheet().getRow(0).createCell(j-1).setCellValue(meta.getColumnLabel(j));		 
//		}
//		
//		int row=1;
//		while(rs.next()){
//			s.getActiveSheet().createRow(row);
//			for(int j=1;j<=totalColumns;j++){
////				System.out.println(s.getActiveSheet().getRow(i));
////				System.out.println(rs.getString(j));
//				if(row==0)
//					s.getActiveSheet().getRow(row).createCell(j-1).setCellValue(meta.getColumnLabel(j));
//				else
//		            s.getActiveSheet().getRow(row).createCell(j-1).setCellValue(rs.getString(j));
//		 
//			}
//			row++;
//		}
//		s.refreshAllCellValues();
		//-------------------TEST1--------------------
//		s.setDefaultColumnWidth(110);
		getAppUI().getSpreadsheet_dao().setSpreadsheet(s);
		getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().setDefaultColumnWidth(30);
		getPopUpButtonsForSheet(getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet());
//		changeHeaderColor();
//		getAppUI().getCurrent().getPage().reload();
		
		//FREEZING FIRST ROW
				Row r = s.getActiveSheet().getRow(s.getActiveSheet().getFirstRowNum());
				 int lastColumnNum=0;
				 lastColumnNum= r.getLastCellNum()-1;
				s.createFreezePane(1,0);
				System.out.println("First row is frozen");
//				System.out.println(s.getActiveSheet().get);
		getAppUI().getSpreadsheet_dao().getSpreadsheet().addSheetChangeListener(new SheetChangeListener() {

			private static final long serialVersionUID = -5585430837302587763L;

			@Override
			public void onSheetChange(SheetChangeEvent event) {
				getAppUI().getSpreadsheet_dao().getSpreadsheet().unregisterTable(table);
				getPopUpButtonsForSheet(getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet());
//				changeHeaderColor();//----TEST-1-----------
			}
		});
		getAppUI().getSpreadsheet_dao().getSpreadsheet().addCellValueChangeListener(new CellValueChangeListener() {
		
			private static final long serialVersionUID = 1334987428943711253L;
                @Override
				public void onCellValueChange(CellValueChangeEvent event) {
               updateLogTable(event);
			}	
		});
		
//		s.getActiveSheet().getRow(0).createCell(8).setCellValue("TESTING");
s.setRowColHeadingsVisible(false);
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
          if(r!=null)
        	c=r.getCell(new Integer(cr.getCol()));
        
			Date d = new Date();
			 
         logTable.addItem(new Object[] { getAppUI().getUser().getLoggedInUser(),
        		"Changed-"+element[2]+""+element[1]+" in sheet-"+
        				getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getSheetName()+" to ",c,d.toString() },
					new Integer(i+1));
        }
	}
	
	@SuppressWarnings("unchecked")
	private void updateLogTableDB(CellValueChangeEvent event) {
	 	Set<CellReference> changedCells = null;
    	changedCells = event.getChangedCells();
       
        Iterator<CellReference> iterator = changedCells.iterator();
        int i=logTable.size();
        System.out.println("no. of elements in log table: "+i);
        CellReference cr;
        Row r;
        Cell c;
        String[] element;
        while(iterator.hasNext()){
          cr=iterator.next();
          element =cr.getCellRefParts();
          r = getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getRow(Integer.valueOf(element[1])-1);
          c=null;
          if(r!=null)
        	c=r.getCell(new Integer(cr.getCol()));
        
			Date d = new Date();
			
			 System.out.println(getAppUI().getUser().getLoggedInUser().toString()+" "+
		        		element[2]+""+element[1]+" in sheet-"+
		        				getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getSheetName()+" to "+" "+c+" "+d.toString()+"row num:"+i);
         String elem2=element[2]+""+element[1]+" in sheet-"+
 				getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getSheetName()+" to ";
//			 logTable.addItem(new Object[] { getAppUI().getUser().getLoggedInUser().toString(),element[2]+""+element[1],c.toString(),"09-01-2017" },
//					new Integer(4));
         Object newItemId = logTable.addItem();
         Item row1 = logTable.getItem(newItemId);
         row1.getItemProperty("User").setValue(getAppUI().getUser().getLoggedInUser());
         row1.getItemProperty("Old Value").setValue(element[2]+""+element[1]);
         row1.getItemProperty("New Value").setValue(c.toString());
         row1.getItemProperty("Date").setValue(d.toString());
			 
//			 logTable.markAsDirtyRecursive();
			 System.out.println("table size:"+logTable.size());
        }
	}
	
	
	private void changeHeaderColor() {
		
		//TEST
		CellStyle headerStyle = getAppUI().getSpreadsheet_dao().getSpreadsheet().getWorkbook().createCellStyle();
		if(getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getRow(0)!=null &&getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getRow(0).getCell(1)!=null){
				for (int i=0;i<(getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getRow(0).getLastCellNum()-1);i++) {
//					cell=activeSheet.getRow(0).getCell(i);
//					if(getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getRow(0)!=null &&getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getRow(0).getCell(i)!=null){
					if(getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getRow(0).getCell(i).getStringCellValue()!=null){
					
						getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getRow(0).getCell(i).getCellStyle().setFillForegroundColor(IndexedColors.BLACK.getIndex());
//					activeSheet.getRow(0).getCell(i).getCellStyle().setFillBackgroundColor(IndexedColors.RED.getIndex());
					
					}
//				}
				}
//				}
		}
		
		
	}

	public void getPopUpButtonsForSheet(Sheet sheet)
			throws NullPointerException, ArrayIndexOutOfBoundsException {

		try {
			 Row r = getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getRow(getAppUI().getSpreadsheet_dao().getSpreadsheet().getActiveSheet().getFirstRowNum());
			 int lastColumnNum=0;
			 lastColumnNum= r.getLastCellNum()-1;
			 System.out.println("lastCol Num:"+lastColumnNum);
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
