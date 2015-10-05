/**
 * Class that extends Exception class. It handle the absence of a value in the resultset.
 *
 * @author Colaci Simone
 * @version 1.00, 30 Jun 2015
 */
package database;

public class NoValueException extends Exception {
	private static final long serialVersionUID = 1L;
	/**
	 * Class default constructor
	 */
	public NoValueException(){
		super();
	}
	/**
	 * Class constructor that print a String message
	 */
	public NoValueException(String msg){
		super(msg);
	}
}
