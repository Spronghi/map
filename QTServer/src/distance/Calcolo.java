/**
 * Class that returns the interfaces IntDistanceEdit and IntDistanceEuclidea.
 *
 * @author Colaci Simone
 * @version 1.00, 30 Jun 2015
 */
package distance;

public class Calcolo {
	/**
	 * @param i instance of ImplDistance that implements IntDistanceEdit and IntDistanceEuclidea interfaces
	 */
	static ImplDistance i = new ImplDistance();
	/**
	 * Method that returns the interface to calculate euclidean distance
	 * @return interface to calculate euclidean distance
	 */
	public static IntDistanceEuclidea distanceE(){
		return i;
	}
		/**
	 * Method that returns the interface to calculate edit distance
	 * @return interface to calculate edit distance
	 */
	public static IntDistanceEdit distanceEdit(){
		return i;
	}
}