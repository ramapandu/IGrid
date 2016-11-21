package com.pg.webapp;
    import java.io.*;
    import org.apache.poi.hssf.usermodel.HSSFWorkbook;
    import org.apache.poi.hssf.usermodel.HSSFSheet;
    import org.apache.poi.ss.usermodel.*;
    import org.apache.poi.xssf.usermodel.XSSFSheet;
    import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.pg.webapp.database.DbConnection;
import com.vaadin.ui.UI;

import java.util.*;
    import java.sql.*; 

    public class XLToDB {  
    	SheetView sheetView;
//        public static final String INSERT_RECORDS = "INSERT INTO test(ID, NAME, VALUE1, VALUE2, VALUE3, VALUE4, VALUE5) VALUES(?,?,?,?,?,?,?)";
//        private static String GET_COUNT = "SELECT COUNT(*) FROM test";
    	 
    	public static final String INSERT_RECORDS = "INSERT INTO test4(`Country`,`City`,`test`,`County`,`State`,`DC`,`Device_Type`,`Vendor`,`KUG`,`LOB`,`Cust_PoC`) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
           private static String GET_COUNT = "SELECT COUNT(*) FROM test4";
           private static String GET_TABLE = "SELECT * FROM test4";


       
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
                prepStmt = con.prepareStatement(INSERT_RECORDS);
                stmt = con.createStatement();
                ResultSet result = stmt.executeQuery(GET_COUNT);
                while(result.next()) {

                    int val = result.getInt(1);
                    System.out.println(val);
                    count = val+1;

                }


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
                                    while(cellIterator.hasNext() &index<=11) {

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
              
              public ResultSet getRecords() throws ClassNotFoundException, SQLException{
            	  Connection con = null;
//                  PreparedStatement prepStmt = null;
                  java.sql.Statement stmt = null;
//                  int count = 0;
            	  DbConnection DBHelper=new DbConnection();
                  con = DBHelper.getConnection();
                  System.out.println("Connection :: ["+con+"]");
//                  prepStmt = con.prepareStatement(GET_TABLE);
                  stmt = con.createStatement();
                  ResultSet result = stmt.executeQuery(GET_TABLE);
//                  while(result.next()) {
//
////                      int val = result.getInt(1);
////                      System.out.println(result.getString(1));
////                      System.out.println(result.getString(2));
////                      System.out.println(result.getString(3));
////                      count = val+1;
//
//                  } 
                  return result;
              }
              SpreadsheetDemoUI getAppUI() {
          		return (SpreadsheetDemoUI) UI.getCurrent();
          	}

    }

