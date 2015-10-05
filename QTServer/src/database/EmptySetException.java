/**
 * Class that extends Exception class. It handle the empty resultset case.
 *
 * @author Colaci Simone
 * @version 1.00, 30 Jun 2015
 */
package database;

public class EmptySetException extends Exception{
	private static final long serialVersionUID = 1L;
	/**
	 * Class default constructor
	 */
	public EmptySetException(){
		super();
	}
	/**
	 * Class constructor that print a String message
	 */
	public EmptySetException(String msg){
		super(msg);
	}
}
