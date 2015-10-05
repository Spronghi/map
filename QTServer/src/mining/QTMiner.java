/**
 * Class that include the implements of the QT algorithm
 * 
 * @author Colaci Simone
 * @version 1.00, 30 Jun 2015
 */
package mining;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import util.ClusteringRadiusException;
import util.EmptyDatasetException;
import data.Tuple;
import data.Data;

public class QTMiner {
	/**
	 * @param clusterSet set of clusters
	 * @param radius radius of clusters
	 * @param distance type of distance to calculate ("edit" or "euclidea") 
	 */
	private ClusterSet clusterSet;
	private double radius;
	private String distance;
	/**
	 * Class constructor.
	 * @param radius radius of clusters
	 * @param distance type of distance to calculate ("edit" or "euclidea") 
	 */
	public QTMiner(double radius, String distance){
		clusterSet = new ClusterSet();
		this.radius = radius;
		this.distance = distance.toLowerCase();
	}
	/**
	 * Class constructor that initialize the miner from a file
	 * @param fileName path to the file
	 * @throws FileNotFoundException file not found exception
	 */
	public QTMiner(String fileName) throws FileNotFoundException{
		ObjectInputStream stream=null;
		try {
			File file= new File(fileName);
			FileInputStream in = new FileInputStream(file);
	        stream = new ObjectInputStream(in);
			clusterSet = (ClusterSet)stream.readObject();
			stream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Method that returns the set of clusters
	 * @return set of clusters
	 */
	public ClusterSet getClusterArray(){
		return clusterSet;
	}
	/**
	 * Method that returns the type of distance
	 * @return type of distance
	 */
	public String getTypeDistance(){
		return distance;
	}
	/**
	 * Method that builds a cluster for each tuple in data that isn't clustered
	 * and return the more populated candidate cluster
	 * @param data set of tuples to organize in a cluster
	 * @param isClustered boolean information about the clustering status of each tuple
	 * @return the more populated candidate cluster
	 */
	private Cluster buildCandidateCluster(Data data, boolean isClustered[]) {
		Cluster toReturn=null;
		for(int indexOne=0;indexOne<isClustered.length;indexOne++)
			if(!isClustered[indexOne]){
				Cluster newCluster = new Cluster(data.getItemSet(indexOne));
				Tuple tupleOne = data.getItemSet(indexOne);
				for(int indexTwo=0;indexTwo<isClustered.length;indexTwo++)
					if(!isClustered[indexTwo]){
						Tuple tupleTwo = data.getItemSet(indexTwo);
						if(tupleOne.getDistance(tupleOne, tupleTwo, getTypeDistance()) <= radius)
							newCluster.addData(indexTwo);
				}
				if (toReturn == null || newCluster.getSize() > toReturn.getSize()) 
					toReturn = newCluster;
			}
		return toReturn;
	}
	/**
	 * Methods that implements the TQ algorithm:
	 * 1) build a cluster for each tuple that isn't clustered, including
	 * 	  in the cluster the points (still-not-clustered in another cluster)
	 * 	  that fall down the spherical neighborhood of the tuple that has
	 * 	  radius "this.radius";
	 * 2) save the candidate cluster more populated and remove all the references
	 * 	  to this cluster contained in the set of still-not-clustered set;
	 * 3) return to the step 1 until there are still-not-clustered tuples.
	 * 
	 * @param data list of examples
	 * @return number of clusters discovered
	 * @throws ClusteringRadiusException exception
	 * @throws EmptyDatasetException exception
	 */
	public int compute(Data data) throws ClusteringRadiusException, EmptyDatasetException{
		if (data.getNumberOfExamples() == 0)
			throw new EmptyDatasetException("Empty dataset");
		int numClusters=0;
		boolean isClustered[] = new boolean[data.getNumberOfExamples()];
		for(int i=0;i<isClustered.length;i++)
			isClustered[i]=false;
		int countClustered=0;
		while(countClustered != data.getNumberOfExamples()) {
			Cluster popolousCluster = buildCandidateCluster(data, isClustered);
			clusterSet.add(popolousCluster);
			numClusters++;
			for(Integer i : popolousCluster)
				isClustered[i] = true;
			countClustered+=popolousCluster.getSize();
		}
		if(numClusters == 1)
			throw new ClusteringRadiusException("["+data.getNumberOfExamples()+" tuples in one cluster]");
		
		return numClusters;
	}
	/**
	 * Method that saves the result of clustering process on a file
	 * @param fileName file name
	 * @throws FileNotFoundException exception
	 */
	public void save(String fileName) throws FileNotFoundException{
		ObjectOutputStream stream;
		try {
			File file= new File(fileName);
			FileOutputStream out = new FileOutputStream(file.getAbsolutePath());
			stream = new ObjectOutputStream(out);
			stream.writeObject(clusterSet);
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
