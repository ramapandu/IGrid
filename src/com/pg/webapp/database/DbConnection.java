package com.pg.webapp.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
	Connection connection;
  
	public DbConnection() throws ClassNotFoundException{
	
	
	}

	public Connection getConnection() throws ClassNotFoundException {
		System.out.println("-------- MySQL JDBC Connection Started------------");

		
		try {
			Class.forName("com.mysql.jdbc.Driver");
//			connection = DriverManager.getConnection("jdbc:mysql://192.168.10.171:3306/TELCO","tomcat", "password");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/igrid","root", "123456");
		} catch (SQLException e3) {
			System.out.println("Connection Failed! Check output console");
			e3.printStackTrace();
		}

		if (connection != null) {
			System.out.println("Database Connection established...");
		} else {
			System.out.println("Failed to make connection!");
		}
		return connection;
	}
	
	public void closeConnection() throws SQLException{
		connection.close();
	}
	
	
}