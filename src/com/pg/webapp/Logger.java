package com.pg.webapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.pg.webapp.database.DbConnection;
import com.vaadin.ui.UI;

public class Logger {  
    	SheetView sheetView;
    	 
    	public static final String INSERT_RECORDS = "INSERT INTO logtable VALUES(?,?,?,?,?)";

//    	public static final String INSERT_NEW_COLUMN = "INSERT INTO test666 VALUES(?,?,?,?,?,?,?,?,?)";

           private static String GET_LOGS = "SELECT * FROM logtable";
       
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
               
               
                stmt = con.createStatement();
                prepStmt = con.prepareStatement(INSERT_RECORDS);
                prepStmt.executeQuery();
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
               
                  
                    //we can execute the statement before reading the next row
                    prepStmt.executeUpdate();
                   /* Close input stream */
                 

            }catch(Exception e){
                e.printStackTrace();            
            }

            }
              
              
              public ResultSet getLogs() throws ClassNotFoundException, SQLException{
            	  Connection con = null;
//                  PreparedStatement prepStmt = null;
                  java.sql.Statement stmt = null;
//                  int count = 0;
            	  DbConnection DBHelper=new DbConnection();
                  con = DBHelper.getConnection();
                  System.out.println("Connection :: ["+con+"]"+" Established");
//                  prepStmt = con.prepareStatement(GET_TABLE);
                  stmt = con.createStatement();
                  ResultSet result = stmt.executeQuery(GET_LOGS);

                  return result;
              }
              SpreadsheetDemoUI getAppUI() {
          		return (SpreadsheetDemoUI) UI.getCurrent();
          	}

    }

