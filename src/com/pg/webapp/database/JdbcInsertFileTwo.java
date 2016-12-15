package com.pg.webapp.database;


 
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
 
public class JdbcInsertFileTwo {
	 static String url = "jdbc:mysql://localhost:3306/igrid";
     static String user = "root";
     static String password = "123456";
     static Connection conn;
     List sheetData;
     String fullFilePath;
	static String tableName;
     
     
     public static Connection getDBConnection() throws SQLException{
    	 conn = DriverManager.getConnection(url, user, password);
    	 return conn;
     }
     
     
    @SuppressWarnings({ "resource", "rawtypes" })
	public void importFile(String filePath,String fileName) throws IOException {
    	fullFilePath=filePath+fileName;
    	 sheetData = new ArrayList();
          FileInputStream fis = null;
          try {
              fis = new FileInputStream(fullFilePath);
              XSSFWorkbook workbook = new XSSFWorkbook(fis);
              tableName=workbook.getSheetName(0);
              XSSFSheet sheet = workbook.getSheetAt(0);
              Iterator rows = sheet.rowIterator();
              while (rows.hasNext()) {
                  XSSFRow row = (XSSFRow) rows.next();
                  Iterator cells = row.cellIterator();
                  List data = new ArrayList();
                     while (cells.hasNext()) {
                     XSSFCell cell = (XSSFCell) cells.next();
                     data.add(cell);
                     }
                     sheetData.add(data);
              }
              
                     } catch (IOException e) {
                     e.printStackTrace();
                     } finally {
                     if (fis != null) {
                     fis.close();
                     }
                     }
         
      //  printSheetData();
          parseExcelData(sheetData);
    getCreateTable(parseExcelData(sheetData));
    }
    
    @SuppressWarnings("unchecked")
    private HashMap parseExcelData (List sheetData){
        HashMap<String,Integer> tableFields = new HashMap();
        List list = (List) sheetData.get(0);
        for (int j=0; j<list.size(); j++){
            Cell cell=(Cell) list.get(j);
            tableFields.put(cell.getStringCellValue(),cell.getCellType());
    }
        return tableFields; 
    }
    
    public void printSheetData(){
    	for (int i=0; i<sheetData.size();i++){
    		 System.out.print("\n ");
            List list = (List) sheetData.get(i);
              for (int j=0; j<list.size(); j++){
                Cell cell = (Cell) list.get(j);
                if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    System.out.print(cell.getNumericCellValue());
                    } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                    System.out.print(cell.getRichStringCellValue());
                    } else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
                    System.out.print(cell.getBooleanCellValue());
                    }
                    if (j < list.size() - 1) {
                    System.out.print(", ");
                    }
                 }
              System.out.print(i);
              }
           }
 
    private static String getCreateTable(HashMap<String, Integer> tableFields) {
        Iterator iter = tableFields.keySet().iterator();
//        Iterator cells = tableFields.keySet().iterator();
        String str = "";
        String[] allFields = new String[tableFields.size()];
        int i = 0;
        int k=1;
        while (iter.hasNext() && i<=100) {
            String fieldName = (String) iter.next();
        	String fieldName1="A"+Integer.toString(k);
            Integer fieldType = (Integer) tableFields.get(fieldName);

            switch (fieldType) {
            case Cell.CELL_TYPE_NUMERIC:
                str = fieldName1 + " INTEGER";
                break;
            case Cell.CELL_TYPE_STRING:
                str = fieldName1 + " BLOB";
                System.out.print(fieldName1);
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                str = fieldName1 + " INTEGER";
                break;
            default:
                str = "";
                break;
            }
            allFields[i++] = str;
       k++;
        }
        try {
            Statement stmt = getDBConnection().createStatement();

            try {
                String all = org.apache.commons.lang3.StringUtils.join(
                        allFields, ",");
                String createTableStr = "CREATE TABLE IF NOT EXISTS "
                        + tableName + " (" + all + ")"+"ENGINE=Innodb DEFAULT CHARSET=utf8";

                System.out.println("Create a new table in the database \n");
                System.out.println( createTableStr);
                stmt.executeUpdate(createTableStr);
            } catch (SQLException e) {
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState:     " + e.getSQLState());
                System.out.println("VendorError:  " + e.getErrorCode());
            }
        } catch (Exception e) 
        {
            System.out.println( ((SQLException) e).getSQLState() );
            System.out.println( e.getMessage() );
            e.printStackTrace();
        }
        return str;
    }
}
