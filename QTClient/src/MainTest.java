/**
 * A class to test clustering process
 *
 * @author Colaci Simone
 * @version 1.00, 30 Jun 2015
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

import keyboardinput.Keyboard;

public class MainTest {
	/**
	 * MainTest class
	 * 
	 * @param out ObjectOutputStream variable to stream out informations to the server
	 * @param in ObjectInputStream variable to receive informations from the server
	 * @param socket Socket to communicate with server
	 */
	private ObjectOutputStream out;
	private ObjectInputStream in ; // stream con richieste del client
	private Socket socket;
	/**
	 * Class constructor
	 * 
	 * @param ip String to initialize the address of the client
	 * @param port server port
	 */
	public MainTest(String ip, int port) throws IOException{
		InetAddress addr = InetAddress.getByName(ip); //ip
		System.out.println("addr = " + addr);
		socket = new Socket(addr, port); //Port
		System.out.println(socket);
		
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());	; // stream con richieste del client
	}
	/**
	 * A simple menu
	 */
	private int menu(){
		int answer;
		do{
			System.out.println("(1) Load clusters from file");
			System.out.println("(2) Load data from db");
			System.out.print("(1/2):");
			answer=Keyboard.readInt();
		}
		while(answer<=0 || answer>2);
		return answer;
		
	}
	/**
	 * Method that take from keyboard the informations that server needs
	 * for the clustering process
	 */
	private String clusteringFromFile() throws SocketException,ServerException,IOException,ClassNotFoundException{
		out.writeObject(2);
		System.out.print("Table name:");
		String tabName=Keyboard.readString();
		double radius=1.0;
		do{
			System.out.print("Radius:");
			radius=Keyboard.readDouble();
		} while(radius<=0);
		String distance="";
		do{
			System.out.print("Distance:");
			distance=Keyboard.readString();
		} while(distance.compareToIgnoreCase("edit")!=0 && distance.compareToIgnoreCase("euclidea")!=0);
		out.writeObject(tabName);
		out.writeObject(radius);
		out.writeObject(distance);
		String result = (String)in.readObject();
		if(result.equals("OK"))
			return (String)in.readObject();
		else throw new ServerException(result);
	}
	/**
	 * Method that take from keyboard the informations that server needs
	 * to read table from a db and cluster it
	 */
	private void storeTableFromDb() throws SocketException,ServerException,IOException,ClassNotFoundException{
		out.writeObject(1);
		System.out.print("Table name:");
		String tableName=Keyboard.readString();
		out.writeObject(tableName);
		String result = (String)in.readObject();
		if(!result.equals("OK"))
			throw new ServerException(result);
		double radius=1.0;
		do{
			System.out.print("Radius:");
			radius=Keyboard.readDouble();
		} while(radius<=0);
		out.writeObject(radius);
		String distance="";
		do{
			System.out.print("Distance:");
			distance=Keyboard.readString();
		} while(distance.compareToIgnoreCase("edit")!=0 && distance.compareToIgnoreCase("euclidea")!=0);
		out.writeObject(distance);
		result = (String)in.readObject();
		if(!result.equals("OK"))
			throw new ServerException(result);
		clusteringFromDbTable(tableName, radius, distance);
	}
	/**
	 * Method that receive the cluster informations from server
	 * 
	 * @param tableName table name on db
	 * @param radius radius for cluster process
	 * @param distance type of distance to calculate
	 */
	private void clusteringFromDbTable(String tableName, double radius, String distance) throws SocketException,ServerException,IOException,ClassNotFoundException{
		int numCluster = (Integer)in.readObject();
		String data =(String)in.readObject();
		String result = (String)in.readObject();
		if(result.equals("OK")){
			System.out.println("Number of Clusters:"+ numCluster);
			System.out.println(data);
		} else throw new ServerException(result);
	}
	/**
	 * Main method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String ip=args[0];
		int port=new Integer(args[1]).intValue();
		MainTest main=null;
		
		try{
			main=new MainTest(ip,port);
		}catch(IOException e){
			System.out.println(e);
			return;
		}
		do{
			int menuAnswer=main.menu();
			switch(menuAnswer) {
			case 1:
				try {
					String kmeans=main.clusteringFromFile();
					System.out.println(kmeans);
				}catch(SocketException e){
					System.out.println(e);
					return;
				}catch(FileNotFoundException e){
					System.out.println(e);
					return;
				}catch(IOException e){
					System.out.println(e);
					return;
				}catch(ClassNotFoundException e){
					System.out.println(e);
					return;
				}catch(ServerException e){
					System.out.println(e.getMessage());
				}
				break;
			case 2:
				while(true){
					try{
						main.storeTableFromDb();
						break; //esce fuori dal while
					}catch(SocketException e){
						System.out.println(e);
						return;
					}catch(FileNotFoundException e){
						System.out.println(e);
						return;
					}catch(IOException e){
						System.out.println(e);
						return;
					}catch(ClassNotFoundException e){
						System.out.println(e);
						return;
					}catch(ServerException e){
						System.out.println(e.getMessage());
					}
				}
				break;
				default:
				System.out.println("Invalid option!");
			}
			System.out.print("would you choose a new operation from menu?(y/n)");
			if(Keyboard.readChar()!='y')
				break;
		}while(true);
	}
}



