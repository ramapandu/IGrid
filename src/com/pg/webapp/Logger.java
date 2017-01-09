package com.pg.webapp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.pg.webapp.database.DbConnection;
import com.vaadin.ui.UI;

public class Logger {  
    	SheetView sheetView;
    	static String logTableName;
    	 
           private static String GET_LOGS = "SELECT * FROM logtable";
           private static String DELETE_LOGS = "SELECT * FROM logtable";
       
           public void deleteLogs() throws SQLException, ClassNotFoundException{
        	   java.sql.Statement stmt = null;
         	  DbConnection DBHelper=new DbConnection();
               System.out.println("DB Connection Established");
               stmt = DBHelper.getConnection().createStatement();
               stmt.executeQuery(DELETE_LOGS);
              DBHelper.closeConnection();
           }
      
              
              public void updateLoggerDB(String[] rowData) throws ClassNotFoundException, SQLException {
              deleteLogs();
                      try {
                      	 DbConnection dbConnhelper=new DbConnection();
                      	Statement stmt = dbConnhelper.getConnection().createStatement();
                      	String all = org.apache.commons.lang3.StringUtils.join(
                                  rowData, ",");
                      	logTableName="logtable";
                          String createTableStr = "INSERT INTO "
                                  + logTableName +" "+"values"+ " (" + all + ")";

                          System.out.println("Logger DB update started");
                          System.out.println( createTableStr);
                          stmt.executeUpdate(createTableStr);
                          System.out.println("Logger DB has been updated");
                      } 	
                      catch(Exception e){
                      	e.printStackTrace();
                      }
          	}
              
              public ResultSet getLogs() throws ClassNotFoundException, SQLException{
                  java.sql.Statement stmt = null;
            	  DbConnection DBHelper=new DbConnection();
                  System.out.println("DB Connection Established");
                  stmt = DBHelper.getConnection().createStatement();
                  ResultSet result = stmt.executeQuery(GET_LOGS);
                  return result;
              }
              SpreadsheetDemoUI getAppUI() {
          		return (SpreadsheetDemoUI) UI.getCurrent();
          	}

    }

