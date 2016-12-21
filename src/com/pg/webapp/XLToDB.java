package com.pg.webapp;
    import java.io.*;
    import org.apache.poi.hssf.usermodel.HSSFWorkbook;
    import org.apache.poi.hssf.usermodel.HSSFSheet;
    import org.apache.poi.ss.usermodel.*;
    import org.apache.poi.xssf.usermodel.XSSFSheet;
    import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.pg.webapp.database.DbConnection;
import com.vaadin.addon.spreadsheet.Spreadsheet;
import com.vaadin.ui.UI;

import java.util.*;
    import java.sql.*; 

    public class XLToDB {  
    	SheetView sheetView;
//        public static final String INSERT_RECORDS = "INSERT INTO test(ID, NAME, VALUE1, VALUE2, VALUE3, VALUE4, VALUE5) VALUES(?,?,?,?,?,?,?)";
//        private static String GET_COUNT = "SELECT COUNT(*) FROM test";
    	 
    	public static final String INSERT_RECORDS = "INSERT INTO test666 VALUES(?,?,?,?,?,?,?,?,?)";
//    	public static final String INSERT_RECORDS="";
    	private static String DELETE_RECORDS = "TRUNCATE test666";
    	private static String GET_COUNT = "SELECT COUNT(*) FROM test666";
           private static String GET_TABLE = "SELECT * FROM huawei_2g_ran LIMIT 50";


       
              public void insertRecords(){

                /* Create Connection objects */
            Connection con = null;
            PreparedStatement prepStmt = null;
            java.sql.Statement stmt = null;
            int count = 0;
//            ArrayList<String> mylist = new ArrayList<String>();

            try{
            DbConnection DBHelper=new DbConnection();
                con = DBHelper.getConnection();
                System.out.println("Connection :: ["+con+"]");
                prepStmt = con.prepareStatement(DELETE_RECORDS);
                stmt = con.createStatement();
                prepStmt.executeQuery();
                prepStmt = con.prepareStatement(INSERT_RECORDS);
                stmt = con.createStatement();
//                ResultSet result = stmt.executeQuery(GET_RECORDS);
//                while(result.next()) {
//
//                    int val = result.getInt(1);
//                    System.out.println(val);
//                    count = val+1;
//
//                }


                //prepStmt.setInt(1,count);

                /* We should now load excel objects and loop through the worksheet data */
//                FileInputStream fis = new FileInputStream(new File(sheetView.getFilePath()+sheetView.getFileName()));
                FileInputStream fis = new FileInputStream(new File("C:/Users/rampa/Desktop/testsheets/test.xlsx"));
                System.out.println("FileInputStream Object created..! ");
                 /* Load workbook */
                XSSFWorkbook workbook = new XSSFWorkbook (fis);
                System.out.println("XSSFWorkbook Object created..! ");
                /* Load worksheet */
                XSSFSheet sheet = workbook.getSheetAt(0);
                System.out.println("XSSFSheet Object created..! ");
                   // we loop through and insert data
                Iterator ite = sheet.rowIterator();
                System.out.println("Row Iterator invoked..! ");

                   while(ite.hasNext()) {
                            Row row = (Row) ite.next(); 
                            System.out.println("Row value fetched..! ");
                            Iterator<Cell> cellIterator = row.cellIterator();
                            System.out.println("Cell Iterator invoked..! ");
                            int index=1;
                                    while(cellIterator.hasNext()) {

                                            Cell cell = cellIterator.next();
//                                            System.out.println("getting cell value..! ");

                                            switch(cell.getCellType()) { 
                                            case Cell.CELL_TYPE_STRING: //handle string columns
                                                    prepStmt.setString(index, cell.getStringCellValue());                                                                                     
                                                    break;
                                            case Cell.CELL_TYPE_NUMERIC: //handle double data
                                                int i = (int)cell.getNumericCellValue();
                                                prepStmt.setInt(index, (int) cell.getNumericCellValue());
                                                    break;
                                            }
                                            index++;



                                    }
                    //we can execute the statement before reading the next row
                    prepStmt.executeUpdate();
                    }
                   /* Close input stream */
                   fis.close();
                   /* Close prepared statement */
                   prepStmt.close();

                   /* Close connection */
                   con.close();
                   workbook.close();

            }catch(Exception e){
                e.printStackTrace();            
            }

            }
              
              public void insertRecordsFromSheet(){

                  /* Create Connection objects */
              Connection con = null;
              PreparedStatement prepStmt = null;
              java.sql.Statement stmt = null;
              int count = 0;
//              ArrayList<String> mylist = new ArrayList<String>();

              try{
              DbConnection DBHelper=new DbConnection();
                  con = DBHelper.getConnection();
                  System.out.println("Connection :: ["+con+"]");
                  prepStmt = con.prepareStatement(DELETE_RECORDS);
                  prepStmt.execute();
                  stmt = con.createStatement();
                  System.out.println("All Records are Updated");
                  prepStmt = con.prepareStatement(INSERT_RECORDS);
                  stmt = con.createStatement();
//                  ResultSet result = stmt.executeQuery(GET_RECORDS);
//                  while(result.next()) {
//
//                      int val = result.getInt(1);
//                      System.out.println(val);
//                      count = val+1;
//
//                  }


                  //prepStmt.setInt(1,count);

                  /* We should now load excel objects and loop through the worksheet data */
//                  FileInputStream fis = new FileInputStream(new File(sheetView.getFilePath()+sheetView.getFileName()));
//                  FileInputStream fis = new FileInputStream(new File("C:/Users/rampa/Desktop/testsheets/test.xlsx"));
//                  System.out.println("FileInputStream Object created..! ");
//                   /* Load workbook */
                  Workbook workbook = getAppUI().getSpreadsheet_dao().getSpreadsheet().getWorkbook();
                  System.out.println("XSSFWorkbook Object created..! ");
                  /* Load worksheet */
                  Sheet sheet = ((Workbook) workbook).getSheetAt(0);
                  System.out.println("XSSFSheet Object created..! ");
                     // we loop through and insert data
                  Iterator ite = sheet.rowIterator();
                  System.out.println("Row Iterator invoked..! ");

                     while(ite.hasNext()) {
                              Row row = (Row) ite.next(); 
                              System.out.println("Row value fetched..! ");
                              Iterator<Cell> cellIterator = row.cellIterator();
                              System.out.println("Cell Iterator invoked..! ");
                              int index=1;
                                      while(cellIterator.hasNext()) {
                                    	  Cell cell = cellIterator.next();
                                    	  if(cell.getRichStringCellValue()!=null){                                             
//                                              System.out.println("getting cell value..! ");

                                              switch(cell.getCellType()) { 
                                              case Cell.CELL_TYPE_STRING: //handle string columns
                                                      prepStmt.setString(index, cell.getStringCellValue());                                                                                     
                                                      break;
                                              case Cell.CELL_TYPE_NUMERIC: //handle double data
                                                  int i = (int)cell.getNumericCellValue();
                                                  prepStmt.setInt(index, (int) cell.getNumericCellValue());
                                                      break;
                                              }
                                              index++;
                                      }
                                      }
                      //we can execute the statement before reading the next row
                      prepStmt.executeUpdate();
                      }
                     /* Close input stream */
//                     fis.close();
                     /* Close prepared statement */
                     prepStmt.close();

                     /* Close connection */
                     con.close();
//                     workbook.close();

              }catch(Exception e){
                  e.printStackTrace();            
              }

              }
              
              public ResultSet getRecords() throws ClassNotFoundException, SQLException{
            	  Connection con = null;
//                  PreparedStatement prepStmt = null;
                  java.sql.Statement stmt = null;
//                  int count = 0;
            	  DbConnection DBHelper=new DbConnection();
                  con = DBHelper.getConnection();
                  System.out.println("Connection :: ["+con+"]"+" Established");
//                  prepStmt = con.prepareStatement(GET_TABLE);
                  stmt = con.createStatement();
                  ResultSet result = stmt.executeQuery(GET_TABLE);

                  return result;
              }
              SpreadsheetDemoUI getAppUI() {
          		return (SpreadsheetDemoUI) UI.getCurrent();
          	}

    }

