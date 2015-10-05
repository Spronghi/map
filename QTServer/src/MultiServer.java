/**
 * Multithreading server
 *
 * @author Colaci Simone
 * @version 1.00, 30 Jun 2015
 */

import java.io.IOException;
import java.util.Date;
import java.net.*;

public class MultiServer {
	/**
	 * @param port number of the port
	 */
	private int port = 8080;
	/**
	 * Class constructor that sets the number of the port
	 * @param port number of the port
	 */
	public MultiServer(int port){
		this.port = port;
		try {
			this.run();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Default method to initialize the server. It waits until
	 * a client request is done and generates new threads
	 */
	private void run() throws IOException{
		ServerSocket server=new ServerSocket(port);
		System.out.println("Server pending PORT::["+port+"]");
		Socket client = null;
		try{
			while(true){
				client = server.accept();
				System.out.println(new Date().toString());
				new ServerOneClient(client);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			System.out.println("closing server");
			server.close();
		}
	}
	/**
	 * Main server method
	 */
	public static void main(String[] args) throws Exception{
		int port=new Integer(args[0]).intValue();
		new MultiServer(port);
	}
}
