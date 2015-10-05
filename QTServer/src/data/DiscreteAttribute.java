/**
 * Concrete DiscreteAttribute class that models the a discrete categorical attribute
 *
 * @author Colaci Simone
 * @version 1.00, 30 Jun 2015
 */
package data;

import java.util.Set;
import java.util.TreeSet;
import java.util.Iterator;

public class DiscreteAttribute extends Attribute implements Iterable<Object>{
	/**
	 * @param serialVersionUID default constant
	 * @param values a Set<Object> of values
	 */
	private static final long serialVersionUID = 1L;
	private Set<Object> values;
	/**
	 * Class Constructor that sets the name and the index of the attribute
	 * and initialize values to default
	 * @param name attribute name
	 * @param index attribute index
	 */
	public DiscreteAttribute(String name, int index){
		super(name, index);
		this.values = new TreeSet<Object>();
	}
	/**
	 * Class Constructor that sets the name and the index of the attribute
	 * and initialize Set<Object> to the "values" argument value
	 * @param name attribute name
	 * @param index attribute index
	 * @param values Set<Object> to initialize this.values
	 */
	public DiscreteAttribute(String name, int index, Set<Object> values){
		super(name, index);
		this.values = values;
	}
	/**
	 * Method that returns the number of distinct values that this 
	 * discrete attribute could assume
	 * @return distinct values
	 */
	public int getNumberOfDistinctValues(){
		return values.size();
	}
	/**
	 * Method that returns an iterator
	 * @return iterator
	 */
	public Iterator<Object> iterator(){
		return values.iterator();
	}
}
