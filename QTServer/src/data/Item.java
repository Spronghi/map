/**
 * Class that models a generic item
 *
 * @author Colaci Simone
 * @version 1.00, 30 Jun 2015
 */
package data;
import java.io.Serializable;
public abstract class Item implements Serializable{
	/**
	 * @param serialVersionUID default constant
	 * @param attribute attribute contained in this item
	 * @param value value of this item
	 */
	private static final long serialVersionUID = 1L;
	private Attribute attribute;
	private Object value;
	
	/**
	 * Class constructor
	 * @param attribute attribute contained in this item
	 * @param value value of this item
	 */
	public Item(Attribute attribute, Object value){
		this.attribute = attribute;
		this.value = value;
	}
	/**
	 * Method that returns the attribute
	 * @return attribute
	 */
	public Attribute getAttribute(){
		return attribute;
	}
	/**
	 * Method that returns the value of this item
	 * @return value
	 */
	public Object getValue(){
		return value;
	}
	/**
	 * Method that convert value in a String type
	 * @return value
	 */
	public String toString(){
		return value.toString();
	}
}
