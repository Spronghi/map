/**
 * Class that represents a set of clusters
 * 
 * @author Colaci Simone
 * @version 1.00, 30 Jun 2015
 */
package mining;
import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;
import java.util.Iterator;

import data.Data;

public class ClusterSet implements Iterable<Cluster>, Serializable{
	/**
	 * @param serialVersionUID default constant
	 * @param clusterSet set of clusters
	 */
	private static final long serialVersionUID = 1L;
	private Set<Cluster> clusterSet;
	/**
	 * Class constructor.
	 * Initialize clusterSet to a TreeSet<Cluster>
	 */
	public ClusterSet(){
		clusterSet = new TreeSet<Cluster>();
	}
	/**
	 * Method that adds a cluster to clusterSet
	 * @param cluster cluster to add
	 */
	public void add(Cluster cluster){
		clusterSet.add(cluster);
	}
	/**
	 * Method that returns an iterator to clusterSet
	 * @return iterator
	 */
	public Iterator<Cluster> iterator(){
		return clusterSet.iterator();
	}
	/**
	 * Method that return the centroid of each clusters in clusterSet
	 * @return String that contains the centroid of each clusters in clusterSet
	 */
	public String toString(){
		String toReturn = new String();
		for(Cluster c: clusterSet){
			toReturn+=c.getCentroid();
		}
		return toReturn;
	}
	/**
	 * Method that return the String representation of clusterSet
	 * @param data Set containing the examples
	 * @param typeDistance type of distance to calculate ("edit" or "euclidea") 
	 * @return String representation of clusterSet
	 */
	public String toString(Data data, String typeDistance){
		String str="";
		int i=0;
		for(Cluster cluster : clusterSet){
			if(cluster!=null)
				str+=i+":"+cluster.toString(data, typeDistance)+"\n";
			i++;
		}
		return str;
	}
}
