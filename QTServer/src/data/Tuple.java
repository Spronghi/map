/**
 * Class that represents a tuple on a sequences of <attribute - value> couples
 *
 * @author Colaci Simone
 * @version 1.00, 30 Jun 2015
 */
package data;

import java.util.Set;
import java.io.Serializable;
import distance.Calcolo;

public class Tuple implements Serializable{
	/**
	 * @param serialVersionUID default constant
	 * @param tupleArray an array of items
	 */
	private static final long serialVersionUID = 1L;
	private Item[] tupleArray;
	/**
	 * Class constructor
	 * @param size set the tupleArray size
	 */
	public Tuple(int size){
		tupleArray = new Item[size];
	}
	/**
	 * Method that returns the numbers of items
	 * contained in arraySize
	 * @return numbers of items
	 */
	public int getLength(){
		return tupleArray.length;
	}
	/**
	 * Method that returns the item in position i
	 * @param i position of the item in tupleArray
	 * @return item in position i
	 */
	public Item get(int i){
		Item toReturn = null;
		try{
			toReturn = tupleArray[i];
		} catch (IllegalArgumentException e) {
			System.err.println("Wrong parameter");
		}
		return toReturn;
	}
	/**
	 * Method that add an item on tupleArray
	 * @param cluster item to add
	 * @param i position
	 */
	public void add(Item cluster, int i){
		try{
			tupleArray[i] = cluster;
		} catch (IllegalArgumentException e) {
			System.err.println("Wrong parameter");
		}
	}
	/**
	 * Method that determinate the distance between obj1 and obj2
	 * @param obj1 first tuple
	 * @param obj2 second tuple
	 * @param typeDistance type of distance to calculate ("edit" or "euclidea")
	 * @return distance between obj1 and obj2
	 */
	public double getDistance(Tuple obj1,Tuple obj2, String typeDistance) {
		double toReturn=0.00;
		if(typeDistance.equals("euclidea"))
			for (int i = 0; i < obj1.getLength(); i++)
				toReturn += Calcolo.distanceE().Euclidea(obj1.get(i), obj2.get(i));
		else if(typeDistance.equals("edit"))
			for (int i = 0; i < obj1.getLength(); i++) 
				toReturn += Calcolo.distanceEdit().Edit(obj1.get(i), obj2.get(i));
		return toReturn;
	}
	/**
	 * Method that return the mean of the distances between the tp1 and the
	 * tuples obtainable by the data row with index clusteredData
	 * @param data List<Example> that contains all the examples
	 * @param tp1 current tuple
	 * @param clusteredData Set<Integer> that contain the set of clustered items
	 * @param typeDistance type of distance to calculate ("edit" or "euclidea") 
	 * @return mean of the distances
	 */
	public double avgDistance(Data data, Tuple tp1, Set<Integer> clusteredData, String typeDistance){
		double p = 0.0, sumD = 0.0;
		for(Integer i : clusteredData){
			double d = getDistance(data.getItemSet(i), tp1, typeDistance);
			sumD += d;
		}
		p = sumD/clusteredData.size();
		return p;
	}
}
