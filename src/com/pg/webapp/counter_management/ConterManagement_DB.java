package com.pg.webapp.counter_management;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.pg.webapp.SpreadsheetDemoUI;
import com.pg.webapp.database.DbConnection;
import com.vaadin.ui.UI;

public class ConterManagement_DB {

	private static String GET_TABLE = "SELECT * FROM global_active_formula_references LIMIT 15";
	private static String DELETE_RECORDS = "TRUNCATE global_active_formula_references";
	
	
	
	
	public ResultSet get_CM_Records() throws ClassNotFoundException, SQLException{
  	  Connection con = null;
//        PreparedStatement prepStmt = null;
        java.sql.Statement stmt = null;
//        int count = 0;
  	  DbConnection DBHelper=new DbConnection();
        con = DBHelper.getConnection();
        System.out.println("Connection :: ["+con+"]"+" Established");
//        prepStmt = con.prepareStatement(GET_TABLE);
        stmt = con.createStatement();
        ResultSet result = stmt.executeQuery(GET_TABLE);

        return result;
    }
	
	public void insert_CM_Record(String tableName,String[] rowData) throws ClassNotFoundException, SQLException{
	  	  Connection con = null;
	        java.sql.Statement stmt = null;
	  	  DbConnection DBHelper=new DbConnection();
	        con = DBHelper.getConnection();
	        System.out.println("Connection :: ["+con+"]"+" Established");
//	     for(String[] rowData:myStringArray){
	        stmt = con.createStatement();
//	        String all = org.apache.commons.lang3.StringUtils.join(
//                    rowData, ",");
//        	tableName="huawei_2g_ran";
            String createTableStr = "INSERT INTO "
                    + tableName +" "+"values"+ " (" +"'"+ rowData[0]+ "'"+","+"'"+ rowData[1]+ "'"+","+"'"+ rowData[2]+ "'"+","+"'"+ rowData[3]+ "'"+","+"'"+ rowData[4]+ "'"+ ")";

//            System.out.println("Sheet DB update started");
            System.out.println("STATEMENT: "+ createTableStr);
            stmt.executeUpdate(createTableStr);
            System.out.println("Record Inserted into DB ");
//	     }
            System.out.println("Sheet DB has been updated");
	    }
	
    SpreadsheetDemoUI getAppUI() {
		return (SpreadsheetDemoUI) UI.getCurrent();
	}
}
