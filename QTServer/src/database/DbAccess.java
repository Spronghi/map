/**
 * Class that menage the access on database.
 *
 * @author Colaci Simone
 * @version 1.00, 30 Jun 2015
 */
package database;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class DbAccess {
	/**
	 * @param DRIVER_CLASS_NAME driver name used to access on db
	 * @param DBMS name of DBMS
	 * @param SERVER ip where the server is located
	 * @param DATABASE name of db
	 * @param PORT number of the port where the db is running
	 * @param USER_ID name of the user that access on db
	 * @param PASSWORD password of the user that access on db
	 * @param conn instance of Connection class
	 */
	private String DRIVER_CLASS_NAME = "org.gjt.mm.mysql.Driver";
	private final String DBMS = "jdbc:mysql";
	private final String SERVER = "localhost";
	private final String DATABASE = "MapDB";
	private final String PORT = "3306";
	private final String USER_ID = "MapUser";
	private final String PASSWORD = "map";
	private Connection conn;
	/**
	 * Method that initializes a connection with db
	 * @throws DatabaseConnectionException exception
	 */
	public void initConnection() throws DatabaseConnectionException {
			String connectionString = DBMS+"://" + SERVER + ":" + PORT + "/" + DATABASE;
			try {
				Class.forName(DRIVER_CLASS_NAME).newInstance();
			} catch (Exception ex) {
				System.out.println("Driver not found: " + DRIVER_CLASS_NAME);
			}
			try {
				conn = DriverManager.getConnection(connectionString, USER_ID, PASSWORD);
			} catch (SQLException e) {
				System.out.println("Cannot connect to DB");
				e.printStackTrace();
			}
	}
	/**
	 * Methods that returns the instance of Connection class
	 * @return connection
	 */
	public Connection getConnection() { return conn;}
	/**
	 * Method that close the connection with db
	 */
	public void closeConnection(){
		try{
			conn.close();
		} catch(SQLException e){
			System.out.println("Cannot close to DB");
			e.getMessage();
		}
	}
}
