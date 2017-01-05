package com.pg.webapp.database;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;

public class JdbcReadFile {
    private static final int BUFFER_SIZE = 4096;
 
    public InputStream LoadFileFromDB(String filePath,String fileName) throws OpenXML4JException {
        String url = "jdbc:mysql://localhost:3306/igrid";
        String user = "root";
        String password = "123456";
        File excelFile = null;
        InputStream inputStream = null;
        InputStream finalStream = null;
        InputStream sheetStream;
     
 
 
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
//            String sql = "SELECT file FROM  excelfiles where file_id=9 ORDER  BY file_id DESC LIMIT  1;";
            String sql = "SELECT file FROM  excelfiles where file_id=8 ORDER  BY file_id DESC LIMIT  1;"; //---TEST1-----
            PreparedStatement statement = conn.prepareStatement(sql);
           
 
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                Blob blob = result.getBlob("file");
                inputStream = blob.getBinaryStream();
                excelFile=new File(filePath,fileName);
                //-----TEST1---------------------------------
            
//                OutputStream outputStream = new FileOutputStream(excelFile);
//                
//                int bytesRead = -1;
//                byte[] buffer = new byte[BUFFER_SIZE];
//                while ((bytesRead = inputStream.read(buffer)) != -1) {
//                    outputStream.write(buffer, 0, bytesRead);
//                }
 //---------------TEST1------------------
                inputStream.close();
//                outputStream.close();  //---TEST1------
                System.out.println("File saved");
                
                //---STREAM TEST----
                
//                OPCPackage opc = OPCPackage.open(excelFile);
//                XSSFReader xssfReader = new XSSFReader(opc);
//                SharedStringsTable sst = xssfReader.getSharedStringsTable();
//                XSSFReader.SheetIterator itr = (XSSFReader.SheetIterator)xssfReader.getSheetsData();
//                while(itr.hasNext()) {
//                    sheetStream = itr.next();
////                    if(itr.getSheetName().equals(sheetName)) {  // Or you can keep track of sheet numbers
//                    if(itr.getSheetName()!=null) {  
//                    finalStream = sheetStream;
//                    System.out.println("Sheet:"+itr.getSheetName());
////                        return;
//                    } else {
//                        sheetStream.close();
//                        finalStream.close();
//                        System.out.println("All Streams Closed");
//                    }
//                }
                //STREAM TEST----
            }
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
       
//        return excelFile;
        return inputStream; //----TEST1-----
    }
}