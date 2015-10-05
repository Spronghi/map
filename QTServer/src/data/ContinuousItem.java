/**
 * Class that extends Item class. 
 * It represents a <continuous attribute - continuous value> couple
 *
 * @author Colaci Simone
 * @version 1.00, 30 Jun 2015
 */
package data;
public class ContinuousItem extends Item{
	/**
	 * @param serialVersionUID default constant
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Class constructor
	 * @param attribute continuous attribute contained in this item
	 * @param value value of this item
	 */
	public ContinuousItem(Attribute attribute, Double value){
		super(attribute, value);
	}
}
