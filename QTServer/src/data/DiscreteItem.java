/**
 * Class that extends Item class. 
 * It represents a <discrete attribute - discrete value> couple
 *
 * @author Colaci Simone
 * @version 1.00, 30 Jun 2015
 */
package data;
public class DiscreteItem extends Item{
	/**
	 * @param serialVersionUID default constant
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Class constructor
	 * @param attribute discrete attribute contained in this item
	 * @param value value of this item
	 */
	public DiscreteItem(Attribute attribute, String value){
		super(attribute, value);
	}
}
