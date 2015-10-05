/**
 * Single Thread class
 *
 * @author Colaci Simone
 * @version 1.00, 30 Jun 2015
 */
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

import util.ClusteringRadiusException;
import util.EmptyDatasetException;
import data.Data;
import mining.QTMiner;

public class ServerOneClient extends Thread {
	/**
	 * @param CLUSTER_FILES_PATH default constant
	 * @param client client socket
	 * @param in ObjectInputStream variable to receive informations from client
	 * @param out ObjectOutputStream variable to send informations to a client
	 * @param qt instance of QTMiner class, that cluster informations
	 */
	private static final String CLUSTER_FILES_PATH = "clusterset";
	private Socket client;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private QTMiner qt;
	/**
	 * Class constructor
	 * 
	 * @param client client socket
	 */
	public ServerOneClient(Socket client) throws IOException{
		this.client = client;
		in = new ObjectInputStream(client.getInputStream());
		out = new ObjectOutputStream(client.getOutputStream());
		this.start();
	}
	/**
	 * Default method to start the Thread. It has a simple menu.
	 */
	public void run(){
		System.out.println("Serving client "+client.getInetAddress());
		do{
			try {
				switch((int)in.readObject()){
				case 1:
					storeTableFromDb();
					break;
				case 2:
					clusteringFromFile();
					break;
				case 3:
					return;
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}catch(IOException e){
				try {
					out.close();
					client.close();
					in.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} while(true);
	}
	/**
	 * Method to receive the name of the table on db, the radius,
	 * and the type of distance to calculate
	 */
	public void storeTableFromDb() throws ClassNotFoundException, IOException{
		String tableName=(String)in.readObject();
		out.writeObject("OK");
		double radius = (double)in.readObject();
		String distance = (String)in.readObject();
		out.writeObject("OK");
		clusteringFromDbTable(tableName, radius, distance);
	}
	/**
	 * Method that send to the client the result of clustering process
	 * 
	 * @param tableName table name on db
	 * @param radius radius of clustering process
	 * @param distance type of distance to calculate
	 */
	public void clusteringFromDbTable(String tableName, double radius, String distance) throws ClassNotFoundException, IOException{
		try {
			qt= new QTMiner(radius, distance);
			Data data = new Data(tableName);
			out.writeObject(qt.compute(data));
			out.writeObject(qt.getClusterArray().toString(data, distance));
		} catch (ClusteringRadiusException | EmptyDatasetException e ) {
			// TODO Auto-generated catch block
			out.writeObject(e.getMessage());
			return;
		}
		out.writeObject("OK");
		writeToFile(tableName, radius, distance);
		
	}
	/**
	 * Method to receive the name of the table in file, the radius,
	 * and the type of distance to calculate, read the right file,
	 * write the result in a new file and then send to the client 
	 * the result of the clustering process
	 */
	public void clusteringFromFile() throws ClassNotFoundException, IOException{
		String tableName=(String)in.readObject();
		double radius=(double)in.readObject();
		String distance=(String)in.readObject();
		if(readFromFile(tableName, radius, distance)){
			out.writeObject("OK");
			writeToFile(tableName,radius,distance);
			Data data = new Data(tableName);
			try{
				out.writeObject(qt.getClusterArray().toString(data, distance));
			}catch(NullPointerException e){
				out.writeObject("Data not found\n");
				return;
			}
		}else{
			out.writeObject("File not found");
			out.writeObject("Data not found\n");
		}
	}
	/**
	 * Method that reads the right file for clustering process
	 * 
	 * @param tableName name of the table
	 * @param radius radius of clustering process
	 * @param distance type of distance to calculate
	 */
	private boolean readFromFile(String tableName,double radius,String distance){
		String file = CLUSTER_FILES_PATH + "_" + tableName + "_" + radius + "_" + distance;
		try {
			qt = new QTMiner(file);
		} catch (IOException  e) {
			// TODO Auto-generated catch block
			return false;
		}
		return true;
	}
	/**
	 * Method that writes the result of clustering process in a new file
	 * @param tableName name of the table
	 * @param radius radius of clustering process
	 * @param distance type of distance to calculate
	 */
	void writeToFile(String tableName,double radius,String distance) throws FileNotFoundException, IOException{
		String file = CLUSTER_FILES_PATH + "_" + tableName + "_" + radius + "_" + distance;
		qt.save(file);
	}
}
