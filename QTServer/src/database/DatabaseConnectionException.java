/**
 * Class that extends Exception class. It handle a failed connection on database.
 *
 * @author Colaci Simone
 * @version 1.00, 30 Jun 2015
 */
package database;

public class DatabaseConnectionException extends Exception{
	/**
	 * @param serialVersionUID default constant
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Class default constructor
	 */
	public DatabaseConnectionException(){
		super();
	}
	/**
	 * Class constructor that print a String message
	 */
	public DatabaseConnectionException(String msg){
		super(msg);
	}
}
