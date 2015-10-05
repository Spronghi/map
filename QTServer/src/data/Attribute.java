/**
 * Abstract Attribute class that models the attribute entity
 *
 * @author Colaci Simone
 * @version 1.00, 30 Jun 2015
 */
package data;

import java.io.Serializable;

public class Attribute implements Serializable{
	/**
	 * @param serialVersionUID default constant
	 * @param name symbolic attribute name
	 * @param index index of the symbolic attribute
	 */
	private static final long serialVersionUID = 1L;
	protected String name;
	protected int index;
	
	/**
	 * Class Constructor that initialize name and index of the attribute
	 * 
	 * @param name	symbolic attribute name
	 * @param index	index of the symbolic attribute
	 */
	Attribute(String name, int index){
		this.name = name;
		this.index = index;
	}
	/**
	 * Method that returns the symbolic name
	 * @return	symbolic name
	 */
	public String getName(){
		return name;
	}
	/**
	 * Method that returns the index of the symbolic name
	 * @return	index of the symbolic name
	 */
	public int getIndex(){
		return index;
	}
	/**
	 * Method that returns the symbolic name of this attribute
	 * @return	symbolic name
	 */
	public String toString(){
		return name;
	}
}
