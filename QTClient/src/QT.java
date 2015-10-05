//<applet code=QT width=500 height=750>
/**
 * Java Applet
 * An Applet that communicate with a server to 
 * cluster informations by database tables
 *
 * @author Colaci Simone
 * @version 1.00, 30 Jun 2015
 */
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class QT extends JApplet{
	/**
	 * Main Applet class
	 * 
	 * @param serialVersionUID Default constant
	 * @param out ObjectOutputStream variable to stream out informations to the server
	 * @param in ObjectInputStream variable to receive informations from the server
	 */
	private static final long serialVersionUID = 1L;
	ObjectOutputStream out;
	ObjectInputStream in; 
	
	private class TabbedPane extends JPanel{
		/**
		 * Main panel class 
		 * 
		 * @param serialVersionUID default constant
		 * @param panelDB Panel to load file from DB
		 * @param panelFile Panel to load file from file
		 */
		private static final long serialVersionUID = 1L;
		private JPanelCluster panelDB;
		private JPanelCluster panelFile;		
		private class JPanelCluster extends JPanel{
			/**
			 * TabbedPane skeleton
			 * 
			 * @param serialVersionUID default constant
			 * @param tableText JTextField to set the table DB name
			 * @param parameterText JTextField to set the radius value
			 * @param distanceText JTextField to set which distance to calculate
			 * @param clusterOutput JTextField to print output
			 * @param executeButton JButton to send the informations to the Applet
			 */
			private static final long serialVersionUID = 1L;
			private JTextField tableText;
			private JTextField parameterText;
			private JTextField distanceText;
			private JTextArea clusterOutput;
			private JButton executeButton;
			/**
			 * Class constructor
			 * 
			 * @param buttonName String to decide which panel stream (panelDB of panelFile)
			 * @param a java.awt.event.ActionListener for executeButton
			 */
			JPanelCluster(String buttonName, java.awt.event.ActionListener a){
				if(buttonName.equals("MINE")){
					tableText=new JTextField(20);
					parameterText=new JTextField(10);
					distanceText=new JTextField(10);
					clusterOutput=new JTextArea(20, 55);
					
					JPanel up = new JPanel();
					up.setLayout(new FlowLayout());
					up.add(new JLabel("Table:"));
					up.add(tableText);
					up.add(new JLabel("Parameter:"));
					up.add(parameterText);
					up.add(new JLabel("Distance:"));
					up.add(distanceText);
					add(up);
					
					JPanel center = new JPanel();
					center.setLayout(new FlowLayout());
					clusterOutput.setEditable(false);
					clusterOutput.setLineWrap (true);
					center.add(clusterOutput);
					JScrollPane scrollPane = new JScrollPane(clusterOutput);
					add(scrollPane);
					add(center);
					JPanel down = new JPanel();
					down.setLayout(new FlowLayout());
					executeButton = new JButton("WRITE ON FILE");
					executeButton.addActionListener(a);
					down.add(executeButton);
					add(down, BorderLayout.SOUTH);
  
				}else if(buttonName.equals("STORE FROM FILE")){
					tableText=new JTextField(20);
					parameterText=new JTextField(10);
					distanceText=new JTextField(10);
					clusterOutput=new JTextArea(20, 55);
					
					JPanel up = new JPanel();
					up.setLayout(new FlowLayout());
					up.add(new JLabel("Table:"));
					up.add(tableText);
					up.add(new JLabel("Parameter:"));
					up.add(parameterText);
					up.add(new JLabel("Distance:"));
					up.add(distanceText);
					add(up);
					
					JPanel center = new JPanel();
					center.setLayout(new FlowLayout());
					clusterOutput.setEditable(false);
					clusterOutput.setLineWrap (true);
					center.add(clusterOutput);
					JScrollPane scrollPane = new JScrollPane(clusterOutput);
					add(scrollPane);
					add(center);
					
					JPanel down = new JPanel();
					down.setLayout(new FlowLayout());
					executeButton = new JButton("WRITE ON FILE");
					executeButton.addActionListener(a);
					down.add(executeButton);
					add(down, BorderLayout.SOUTH);
				}
			}
		}
		/**
		 * Class costructor 
		 */
		TabbedPane() {
			super(new GridLayout(1, 1)); 
			JTabbedPane tabbedPane = new JTabbedPane();
			
			java.net.URL imgURL = getClass().getResource("img/db.jpg");
			try {
				imgURL = new java.net.URL("img/db.jpg");
			} catch (MalformedURLException e2) {
				e2.printStackTrace();
			}
			ImageIcon iconDB = new ImageIcon(imgURL);
			panelDB = new JPanelCluster("MINE", new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try{
						clusteringFromDBAction();
					} catch (SocketException e1) {
						System.out.println(e1);
						
					} catch (FileNotFoundException e1) {
						System.out.println(e1);
						
					} catch (IOException e1) {
						System.out.println(e1);
						
					} catch (ClassNotFoundException e1) {
						System.out.println(e1);
					}
				}
			});
	        tabbedPane.addTab("DB", iconDB, panelDB, "Does nothing");
	      
	        imgURL = getClass().getResource("img/file.jpg");
	        try {
				imgURL = new java.net.URL("img/file.jpg");
			} catch (MalformedURLException e2) {
				e2.printStackTrace();
			}
	        ImageIcon iconFile = new ImageIcon(imgURL);
	        panelFile = new JPanelCluster("STORE FROM FILE",new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try{
						clusteringFromFileAction();
					} catch (SocketException e1) {
						System.out.println(e1);
						
					} catch (FileNotFoundException e1) {
						System.out.println(e1);
						
					} catch (IOException e1) {
						System.out.println(e1);
						
					} catch (ClassNotFoundException e1) {
						System.out.println(e1);
					}
				}
	        });
	        tabbedPane.addTab("FILE", iconFile, panelFile, "Does nothing");
	        add(tabbedPane);
	        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		}
		/**
		 * Method that take information from a table located in a DB, a radius,
		 * and a type of distance to calculate and send these informations
		 * to a server that respond with number of clusters and the result of
		 * the clustering process
		 */
		private void clusteringFromDBAction()throws SocketException,IOException,ClassNotFoundException{
			String tableName;
			try{
				tableName=new String(panelDB.tableText.getText());
				if (!tableName.toLowerCase().equals("playtennis"))
					throw new Exception("Invalid table name");
			} catch(Exception e){
				JOptionPane.showMessageDialog(this,e.toString());
				return;
			}			double radius;
			try{
				radius=new Double(panelDB.parameterText.getText()).doubleValue();
				if (radius<=0)
					throw new NumberFormatException("Radius<=0");
			} catch(NumberFormatException e){
				JOptionPane.showMessageDialog(this,e.toString());
				return;
			}
			String distance;
			boolean rightDist=false;
			try{
				distance=new String(panelDB.distanceText.getText());
				if (distance.toLowerCase().equals("edit")||distance.toLowerCase().equals("euclidea"))
					rightDist = true;
				
				if(!rightDist)
					throw new Exception("Invalid distance");
			} catch(Exception e){
				JOptionPane.showMessageDialog(this,e.toString());
				return;
			}
			out.writeObject(1);
			out.writeObject(tableName);
			String result;
			try{
				result = (String)in.readObject();
				if(!result.equals("OK"))
					throw new Exception("Name table request failed");
			} catch(Exception e){
				JOptionPane.showMessageDialog(this,e.toString());
				return;
			}
			out.writeObject(radius);
			out.writeObject(distance);
			try{
				result = (String)in.readObject();
				if(!result.equals("OK"))
					throw new Exception("Radius request failed");
			} catch(Exception e){
				JOptionPane.showMessageDialog(this,e.toString());
				return;
			}
			int numCluster = (int)in.readObject();
			String data = (String)in.readObject();
			panelDB.clusterOutput.append("Number of clusters:"+numCluster+"\n"+data);
			panelDB.clusterOutput.append("============================================\n");
			try{
				result = (String)in.readObject();
				if(!result.equals("OK"))
					throw new Exception("store cluster in file request failed");
			} catch(Exception e){
				JOptionPane.showMessageDialog(this,e.toString());
				return;
			}
			JOptionPane.showMessageDialog(this, "Success");
		}
		/**
		 * Method that take information from a file, a radius,
		 * and a type of distance to calculate and send these informations
		 * to a server that respond with the result of the clustering process
		 */
		private void clusteringFromFileAction() throws SocketException,IOException,ClassNotFoundException{
			String tableName;
			try{
				tableName=new String(panelFile.tableText.getText());
				if (!tableName.toLowerCase().equals("playtennis"))
					throw new Exception("Invalid table name");
			} catch(Exception e){
				JOptionPane.showMessageDialog(this,e.toString());
				return;
			}
			double radius;
			try{
				radius=new Double(panelFile.parameterText.getText()).doubleValue();
				if (radius<=0)
					throw new NumberFormatException("Radius<=0");
			} catch(NumberFormatException e){
				JOptionPane.showMessageDialog(this,e.toString());
				return;
			}
			String distance;
			boolean rightDist=false;
			try{
				distance=new String(panelFile.distanceText.getText());
				if (distance.toLowerCase().equals("edit")||distance.toLowerCase().equals("euclidea"))
					rightDist = true;
				
				if(!rightDist)
					throw new Exception("Invalid distance");
			} catch(Exception e){
				JOptionPane.showMessageDialog(this,e.toString());
				return;
			}
			
			out.writeObject(2);
			
			out.writeObject(tableName);
			out.writeObject(radius);
			out.writeObject(distance);
			try{
				String result = (String)in.readObject();
				if(!result.equals("OK"))
					throw new Exception("clustering from file request failed");
			} catch(Exception e){
				JOptionPane.showMessageDialog(this,e.toString());
				return;
			}
			String data = "";
			try{
				data = (String)in.readObject();
			}catch(NullPointerException e){
				JOptionPane.showMessageDialog(this,e.toString());
				return;
			}
			panelFile.clusterOutput.append(data);
			panelFile.clusterOutput.append("============================================\n");
			JOptionPane.showMessageDialog(this, "Success");
		}
	}
	/**
	 * Default method to start the Applet
	 */
	public void init(){
		TabbedPane tab=new TabbedPane();
		getContentPane().setLayout(new GridLayout(1,1));
		getContentPane().add(tab);
		String ip="localhost";
		int port=8080;
		//String ip=this.getParameter("ServerIP");
		//int port=new Integer(this.getParameter("Port")).intValue();
		try {
			InetAddress addr = InetAddress.getByName(ip); //ip
			System.out.println("addr = " + addr);
			Socket socket = new Socket(addr, port); //Port
			System.out.println(socket);
			
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream()); 
		}catch(IOException e){
			System.exit(0);
		}
		
	}
}
