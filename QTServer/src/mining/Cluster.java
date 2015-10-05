package mining;

import java.io.Serializable;
import data.Tuple;
import data.Data;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

class Cluster implements Iterable<Integer>, Comparable<Cluster>, Serializable{
	private static final long serialVersionUID = 1L;
	private Tuple centroid;
	private Set<Integer> clusteredData; 
	
	Cluster(){
		clusteredData=new HashSet<Integer>();
	}

	Cluster(Tuple centroid){
		this.centroid=centroid;
		clusteredData=new HashSet<Integer>();
	}

	Tuple getCentroid(){
		return centroid;
	}
	
	//return true if the tuple is changing cluster
	boolean addData(int id){
		return clusteredData.add(id);
	}
	
	//verifica se una transazione è clusterizzata nell'array corrente
	boolean contain(int id){
		return clusteredData.contains(id);
	}

	//remove the tuple that has changed the cluster
	void removeTuple(int id){
		clusteredData.remove(id);
	}

	int  getSize(){
		return clusteredData.size();
	}
	
	public Iterator<Integer> iterator(){
		return clusteredData.iterator();
	}
	public int compareTo(Cluster cluster){
		if(this.getSize() > cluster.getSize())
			return 1;
		return -1;
	}
	public String toString(){
		String str="Centroid=(";
		for(int i=0;i<centroid.getLength();i++)
			str+=centroid.get(i);
		str+=")";
		return str;
		
	}
	public String toString(Data data, String typeDistance){
		String toReturn = "Centroid=(";
		for(int i=0;i<centroid.getLength();i++)
			toReturn += centroid.get(i)+ " ";
		toReturn += ")\nExamples:\n";
		for(Integer iterator : clusteredData){
			toReturn += "[";
			for(int j=0; j < data.getNumberOfExplanatoryAttributes(); j++)
				toReturn += data.getValue(iterator, j) + " ";
			toReturn += "] dist=" 
						+ getCentroid().getDistance(getCentroid(), data.getItemSet(iterator), typeDistance) 
						+ "\n";
		}
		toReturn += "\nAvgDistance=" + getCentroid().avgDistance(data, getCentroid(), clusteredData, typeDistance);
		return toReturn;
	}
}
