/**
 * Class that models the set of transactions
 *
 * @author Colaci Simone
 * @version 1.00, 30 Jun 2015
 */
package data;

import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;

import database.DatabaseConnectionException;
import database.EmptySetException;
import database.Example;
import database.DbAccess;
import database.NoValueException;
import database.QUERY_TYPE;
import database.TableData;
import database.TableSchema;

public class Data{
	/**
	 * @param data multidimensional List<Example> that contains the values of transactions
	 * @param numberOfExamples cardinality of the transactions set
	 * @param explanatorySet List<Attribute> that contains the attributes of each tuple
	 */
	private List<Example> data;
	private int numberOfExamples; 
	private List<Attribute> explanatorySet;
	/**
	 * Class constructor that take informations from a db
	 * and put these informations in data, set numberOfExamples,
	 * and explanatorySet
	 * @param nameTable table name in db
	 */
	public Data(String nameTable) {
		explanatorySet = new LinkedList<Attribute>();
		data = new ArrayList<Example>();
		
		DbAccess access = new DbAccess();
		try {
			access.initConnection();
		} catch (DatabaseConnectionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		TableData tableData = new TableData(access);
		
		try{
			data = tableData.getDistinctTransazioni(nameTable);
		} catch (EmptySetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		TableSchema tableSchema = null;
		try {
			tableSchema = new TableSchema(access, nameTable);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Set<Object> outlookValues = null;
		try {
			outlookValues = tableData.getDistinctColumnValues(nameTable, tableSchema.getColumn(Constants.OUTLOOK_INDEX));
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		explanatorySet.add(new DiscreteAttribute("outlook", Constants.OUTLOOK_INDEX, outlookValues));
		
		double min = 0.0;
		try {
			try {
				min = (float)(tableData.getAggregateColumnValue(nameTable, tableSchema.getColumn(Constants.TEMPERATURE_INDEX), QUERY_TYPE.MIN));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (NoValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		double max = 0.0;
		try {
			try {
				max = (float)(tableData.getAggregateColumnValue(nameTable, tableSchema.getColumn(Constants.TEMPERATURE_INDEX), QUERY_TYPE.MAX));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (NoValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		explanatorySet.add(new ContinuousAttribute("temperature", Constants.TEMPERATURE_INDEX, min, max));
		
		Set<Object> humidityValues = null;
		try {
			humidityValues = tableData.getDistinctColumnValues(nameTable, tableSchema.getColumn(Constants.HUMIDITY_INDEX));
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		explanatorySet.add(new DiscreteAttribute("humidity", Constants.HUMIDITY_INDEX, humidityValues));
		
		Set<Object> windValues = null;
		try {
			windValues = tableData.getDistinctColumnValues(nameTable, tableSchema.getColumn(Constants.WIND_INDEX));
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		explanatorySet.add(new DiscreteAttribute("wind", Constants.WIND_INDEX, windValues));
		
		Set<Object> playTennisValues = null;
		try {
			playTennisValues = tableData.getDistinctColumnValues(nameTable, tableSchema.getColumn(Constants.PLAY_TENNIS_INDEX));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		explanatorySet.add(new DiscreteAttribute("play tennis", Constants.PLAY_TENNIS_INDEX, playTennisValues));
		
		numberOfExamples = data.size();
	}
	/**
	 * Method that returns a value contained in data
	 * @param attributeIndex attributeIndex in the list
	 * @param exampleIndex exampleIndex in the list
	 * @return value of the example
	 */
	public Object getValue(int attributeIndex, int exampleIndex){
		return data.get(attributeIndex).get(exampleIndex);
	}
	/**
	 * Method that returns the number of examples
	 * contained in the data list
	 * @return number of examples
	 */
	public int getNumberOfExamples(){
		return numberOfExamples;
	}
	/**
	 * Method that returns the number of individual attributes
	 * @return cardinality of the attribute set
	 */
	public int getNumberOfExplanatoryAttributes(){
		return explanatorySet.size();
	}
	/**
	 * Method that returns the schema of individual attributes
	 * @return schema of individuals attributes
	 */
	public List<Attribute> getAttributeSchema(){
		return explanatorySet;
	}
	/**
	 * Method that returns a value contained in data
	 * @param attributeIndex attributeIndex in the list
	 * @param exampleIndex exampleIndex in the list
	 * @return value of the example
	 */
	public Object getAttributeValue(int attributeIndex, int exampleIndex){
		return data.get(attributeIndex).get(exampleIndex);
	}
	/**
	 * Method that create a String that contain the schema
	 * of the table and the transaction contained in data,
	 * enumerated conveniently
	 * @return String that models the object status
	 */
	public String toString(){
		String toReturn = new String();
		for(Attribute attribute : getAttributeSchema())
			toReturn += attribute.getName()+",";
		toReturn += '\n';
		for(int i=0;i<data.size();i++){
			toReturn += i +"::";
			for(int j=0;j<data.get(i).size();j++)
				toReturn += getValue(i, j)+",";
			toReturn += '\n';
		}
		return toReturn;
	}
	/**
	 * Method that creates and returns a Tuple object that models the
	 * sequence of <attribute - value> couple contained in the i-th 
	 * data row
	 * @param i index
	 * @return Tuple object that models the i-th data row
	 */
	public Tuple getItemSet(int i){
		Tuple tuple = new Tuple(getNumberOfExplanatoryAttributes());
		int exampleIndex=0;
		for(Attribute attribute : getAttributeSchema()){
			if(attribute instanceof DiscreteAttribute)
				tuple.add(new DiscreteItem((DiscreteAttribute)attribute, (String)getValue(i, exampleIndex)), exampleIndex);
			else if(attribute instanceof ContinuousAttribute)
				tuple.add(new ContinuousItem((ContinuousAttribute)attribute, (Double)getValue(i, exampleIndex)), exampleIndex);
			exampleIndex++;
		}
		return tuple;
	}
}
