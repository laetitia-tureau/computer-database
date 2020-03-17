package com.excilys.formation.java.cdb.persistence;

import java.sql.Connection;
import java.sql.DriverManager;

public final class MysqlConnect {

	public Connection connexion;
	public static MysqlConnect database;
	
	private final static String URL = "jdbc:mysql://localhost:3306/";
	private final static String DB_NAME = "computer-database-db";
	private final static String DRIVER = "com.mysql.cj.jdbc.Driver";
	private final static String USER_NAME = "admincdb";
	private final static String PASSWORD = "qwerty1234";

	/** 
	 * Creates a connexion to mysql database.
	 */
	private MysqlConnect() {
		try {
			Class.forName(DRIVER).newInstance();
			this.connexion = (Connection)DriverManager.getConnection(URL+DB_NAME,USER_NAME,PASSWORD);
		} catch (Exception sqle ) {
			sqle.printStackTrace();
		}
	}
	
   /**
    * @return MysqlConnect Database connection object
    */
   public static synchronized MysqlConnect getDbConnection() {
       if (database == null) {
    	   database = new MysqlConnect();
       }
       return database;
   }
}
