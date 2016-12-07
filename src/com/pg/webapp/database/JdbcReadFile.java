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


 
/**
 * This program demonstrates how to read file data from database and save the
 * data into a file on disk.
 * @author www.codejava.net
 *
 */
public class JdbcReadFile {
    private static final int BUFFER_SIZE = 4096;
 
    public InputStream LoadFileFromDB(String filePath,String fileName) {
        String url = "jdbc:mysql://localhost:3306/igrid";
        String user = "root";
        String password = "123456";
        File excelFile = null;
        InputStream inputStream = null;
       
 
//        String filePath = "D:/Photos/Tom.jpg";
 
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
// String fullFilePath
            String sql = "SELECT file FROM  excelfiles where file_id=9 ORDER  BY file_id DESC LIMIT  1;";
            PreparedStatement statement = conn.prepareStatement(sql);
           
 
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                Blob blob = result.getBlob("file");
                inputStream = blob.getBinaryStream();
//               excelFile=new File(filePath,fileName);
//                OutputStream outputStream = new FileOutputStream(excelFile);
//                
//                int bytesRead = -1;
//                byte[] buffer = new byte[BUFFER_SIZE];
//                while ((bytesRead = inputStream.read(buffer)) != -1) {
//                    outputStream.write(buffer, 0, bytesRead);
//                }
 
                inputStream.close();
//                outputStream.close();
                System.out.println("File saved");
            }
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
       
//        return excelFile;
        return inputStream;
    }
}