/**
 * Concrete ConstinuousAttribute class that models the a continuous numerical attribute
 *
 * @author Colaci Simone
 * @version 1.00, 30 Jun 2015
 */
package data;
public class ContinuousAttribute extends Attribute{
	/**
	 * @param serialVersionUID defau.t constant
	 * @param max max value
	 * @param min minimum value
	 */
	private static final long serialVersionUID = 1L;
	private double max;
	private double min;
	/**
	 * Class constructor
	 * @param name attribute name
	 * @param index attribute index
	 * @param min minimum value
	 * @param max max value
	 */
	public ContinuousAttribute(String name, int index, double min, double max){
		super(name, index);
		this.min = min;
		this.max = max;
	}
	/**
	 * Method that returns the scaled value of the continuous attribute
	 * @param v double value to convert
	 * @return scaled value
	 */
	public double getScaledValue(double v){
		return (v - min)/(max - min);
	}
}

