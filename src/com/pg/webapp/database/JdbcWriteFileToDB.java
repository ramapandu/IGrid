
package com.pg.webapp.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;

public class JdbcWriteFileToDB {
 
    public void WriteFileToDB(String filePath,String fileName) throws OpenXML4JException {
        String url = "jdbc:mysql://localhost:3306/igrid";
        String user = "root";
        String password = "123456";
   
 
        try {
        	
        	String fileFullPath = filePath+fileName;
        	InputStream inputStream = new FileInputStream(new File(fileFullPath));
        	 Connection conn = DriverManager.getConnection(url, user, password);
        	String sql = "INSERT INTO excelfiles (file) values (?)";
        	PreparedStatement statement = conn.prepareStatement(sql);
        	statement.setBlob(1, inputStream);
        	statement.executeUpdate();
           System.out.println("File Saved To DB!!!");
            }
           
         catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

       

    }
}
