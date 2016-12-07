package com.pg.webapp.database;


 
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
 
public class JdbcInsertFileTwo {
 
    public void importFile(String filePath,String fileName) {
        String url = "jdbc:mysql://localhost:3306/igrid";
        String user = "root";
        String password = "123456";
 
//        String filePath = "C:/Users/rampa/Desktop/testsheets/sheet.xlsx";
 
//        try {
//            Connection conn = DriverManager.getConnection(url, user, password);
// 
//            String sql = "INSERT INTO excelfiles (file) values (LOAD_FILE(?))";
//            PreparedStatement statement = conn.prepareStatement(sql);
// 
//            statement.setString(1, filePath);
// 
//            int row = statement.executeUpdate();
//            if (row > 0) {
//                System.out.println("A File was inserted into DB.");
//            }
//            conn.close();
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
        
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
 
            String sql = "INSERT INTO excelfiles (file) values (?)";
            PreparedStatement statement = conn.prepareStatement(sql);
           
            InputStream inputStream = new FileInputStream(new File(filePath,fileName));
 
            statement.setBlob(1, inputStream);
 
            int row = statement.executeUpdate();
            if (row > 0) {
                System.out.println("A contact was inserted with photo image.");
            }
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}