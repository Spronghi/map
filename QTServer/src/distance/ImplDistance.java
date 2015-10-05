/**
 * Class that implements IntDistanceEdit and IntDistanceEuclidea interfaces.
 *
 * @author Colaci Simone
 * @version 1.00, 30 Jun 2015
 */
package distance;

import data.DiscreteAttribute;
import data.ContinuousAttribute;
import data.Item;
import data.ContinuousItem;
import data.DiscreteItem;

public class ImplDistance implements IntDistanceEdit, IntDistanceEuclidea {
	/**
	 * Methods that returns the euclidean (continuous or discrete) distance between o1 and o2
	 * @param o1 first item
	 * @param o2 second item
	 * @return euclidean distance
	 */
	@Override
	public Double Euclidea(Item o1, Item o2) {
		if(o1 instanceof ContinuousItem && o2 instanceof ContinuousItem){
			return continuousDistance(o1, o2);
		} else if(o1 instanceof DiscreteItem && o2 instanceof DiscreteItem){
			if(o1.getValue().equals(o2.getValue()))
				return 0.0;
			else
				return 1.0;
		}
		return null;
	}
	/**
	 * Methods that returns the edit (continuous or discrete) distance between o1 and o2
	 * @param o1 first item
	 * @param o2 second item
	 * @return edit distance
	 */
	@Override
	public Double Edit(Item o1, Item o2) {
		if(o1 instanceof ContinuousItem && o2 instanceof ContinuousItem){
			return continuousDistance(o1, o2);
		} else if(o1 instanceof DiscreteItem && o2 instanceof DiscreteItem){
			int o1Pos=0, o2Pos=0, i=0;
			for(Object iterator : (DiscreteAttribute)o1.getAttribute()){
				if(iterator.equals(o1.getValue()))
					o1Pos = i;
				if(iterator.equals(o2.getValue()))
					o2Pos = i;
				i++;
			}
			return Math.abs((o1Pos-o2Pos)/(i-1.0));
		}
		return null;
	}
	/**
	 * Method that calculates the continuous (discrete or continuous)
	 * distance between o1 and o2
	 * @param o1 first item
	 * @param o2 second item
	 * @return continuous distance
	 */
	private Double continuousDistance(Item o1, Item o2) {
		return Math.abs(((ContinuousAttribute)o1.getAttribute()).getScaledValue((double)o1.getValue())
				- ((ContinuousAttribute)o2.getAttribute()).getScaledValue((double) o2.getValue()));
	}
}
