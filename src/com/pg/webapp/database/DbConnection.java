package com.pg.webapp.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
	Connection connection;
  
	public DbConnection() throws ClassNotFoundException{
	System.out.println("-------- MySQL JDBC Connection Started------------");

	
	try {
		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/igrid","root", "123456");
//		connection = DriverManager.getConnection("jdbc:mysql:// ip-172-31-11-168.us-west-2.compute.internal:3306/igrid","root", "123456");

	} catch (SQLException e3) {
		System.out.println("Connection Failed! Check output console");
		e3.printStackTrace();
		return;
	}

	if (connection != null) {
		System.out.println("Database Connection established...");
	} else {
		System.out.println("Failed to make connection!");
	}
	
	}

	public Connection getConnection() {
		return connection;
	}
	
	
}