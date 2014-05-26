package kr;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Serviz {
	
	static Scanner sc = new Scanner(System.in);
	
	private static Connection connect(String url, String user, String password){
	    Connection result = null;
	    try{
	      result = DriverManager.getConnection(url, user, password);}
	    catch(SQLException e){
	      e.printStackTrace();}
	    return result;}
	
	public static void main(String[] args){
	    String url = "jdbc:mysql://localhost:3306/kr";
	    String user = "admin";
	    String pass = "admin";
	    Connection link = Serviz.connect(url, user, pass);
	    if(link == null){
	      System.out.println("MySQL is not running.");
	      return;}
	    else{
	      System.out.println("Connection to MySQL server active."); }
	    
	Statement stmt = null;
    ResultSet resultSet = null; 
    ServerSocket serverSocket = null;
    Socket connection = null;
    Scanner socketIn = null;
    PrintWriter socketOut = null;
    int port = 1234;

    while(true){
      try {
        serverSocket = new ServerSocket(port);
        System.out.println("Waiting for incoming connection...");
        connection = serverSocket.accept();
        System.out.println(connection.getInetAddress().getHostName()+" has connected");

        System.out.println("Sending message...");
        socketOut = new PrintWriter(connection.getOutputStream(), true);
        socketOut.println("Possible commands: -create(create entry) -view(view all) -update(update record) -delete(delete record) -exit(exit program)");

        System.out.println("Awaiting command...");
        socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));

        String command = null;
        String id=null;
        String type = null;
        String make = null;
    	String model = null;
    	String component_id=null;
    	String status = null;
    	String results = null;
    	String parts = null;
    	String price = null;
    	
    	
        loop: do{
          socketOut.flush();
          command = socketIn.nextLine();
          switch(command){
          
            case "create": {
            		type = socketIn.nextLine();
            		make=socketIn.nextLine();
            		model=socketIn.nextLine();
            		component_id=socketIn.nextLine();
            		status=socketIn.nextLine();
            		results=socketIn.nextLine();
            		parts=socketIn.nextLine();
            		price=socketIn.nextLine();
            		try { //вкарване на данни в component
            	    	PreparedStatement preparedStatement;
            	    	preparedStatement = link.prepareStatement("INSERT INTO component(type, make, model) VALUES (?, ?, ?)");
            	    	preparedStatement.setString(1, type);
            	    	preparedStatement.setString(2, make);
            	    	preparedStatement.setString(3, model);
            	    	preparedStatement.executeUpdate();}
            	      catch(SQLException e){
            	        e.printStackTrace();}
            		 try { //вкарване на данни в service
                	    	PreparedStatement preparedStatement2;
                	    	preparedStatement2 = link.prepareStatement("INSERT INTO service(component_id, status, results, parts, price) VALUES (?, ?, ?, ?, ?)");
                	    	preparedStatement2.setString(1, component_id);
                	    	preparedStatement2.setString(2, status);
                	    	preparedStatement2.setString(3, results);
                	    	preparedStatement2.setString(4, parts);
                	    	preparedStatement2.setString(5, price);
                	    	preparedStatement2.executeUpdate();}
                	      catch(SQLException e){
                	        e.printStackTrace();}
            	             		
            	   socketOut.println("OK");
              break; }
            
            case "view": {
                try{ //вадене на данни
                    stmt = link.createStatement();
                    resultSet = stmt.executeQuery("SELECT a.id, a.type, a.make, a.model, b.status, b.results, b.parts, b.price FROM component as a, service as b where a.id=b.id");
                    while (resultSet.next()) {
                    	socketOut.print(resultSet.getInt("id"));
                        socketOut.print("  "+resultSet.getString("type"));
                        socketOut.print("  "+resultSet.getString("make"));
                        socketOut.print("  "+resultSet.getString("model"));
                        socketOut.print("  "+resultSet.getString("status"));
                        socketOut.print("  "+resultSet.getString("results"));
                        socketOut.print("  "+resultSet.getString("parts"));
                        socketOut.println("  "+resultSet.getDouble("price"));}}
                  catch(SQLException e){
                    e.printStackTrace();}
                socketOut.println("####");
            break;}
           
            case "update":{
            	id=socketIn.nextLine();
            	type = socketIn.nextLine();
        		make=socketIn.nextLine();
        		model=socketIn.nextLine();
        		component_id=socketIn.nextLine();
        		status=socketIn.nextLine();
        		results=socketIn.nextLine();
        		parts=socketIn.nextLine();
        		price=socketIn.nextLine();
        		try { //вкарване на данни в component
        	    	PreparedStatement preparedStatement;
        	    	preparedStatement = link.prepareStatement("UPDATE component set type=?, make=?, model=?  WHERE id=?");
        	    	preparedStatement.setString(1, type);
        	    	preparedStatement.setString(2, make);
        	    	preparedStatement.setString(3, model);
        	    	preparedStatement.setString(4, id);
        	    	preparedStatement.executeUpdate();}
        	      catch(SQLException e){
        	        e.printStackTrace();}
        		 try { //вкарване на данни в service
            	    	PreparedStatement preparedStatement2;
            	    	preparedStatement2 = link.prepareStatement("UPDATE service set component_id=?, status=?, results=?, parts=?, price=?  WHERE component_id=?");
            	    	preparedStatement2.setString(1, component_id);
            	    	preparedStatement2.setString(2, status);
            	    	preparedStatement2.setString(3, results);
            	    	preparedStatement2.setString(4, parts);
            	    	preparedStatement2.setString(5, price);
            	    	preparedStatement2.setString(6, id);
            	    	preparedStatement2.executeUpdate();}
            	      catch(SQLException e){
            	        e.printStackTrace();}
        	             		
        	   socketOut.println("OK");
            break;}
           
            case "delete": {
            	id=socketIn.nextLine();
            	try {
            	PreparedStatement preparedStatement;
    	    	preparedStatement = link.prepareStatement("delete from component  WHERE id=?");
    	    	preparedStatement.setString(1, id);}
    	    	catch(SQLException e){
         	        e.printStackTrace();}
            	socketOut.println("OK");
            break;}
            
            case "exit":
              socketOut.println("OK");
              break loop;
            default:
              socketOut.println("Unknown command.");
              break;}}
        while(!command.equalsIgnoreCase("exit"));
        System.out.println("Closing connection with: "+connection.getInetAddress().getHostName());}
      catch(IOException e) {
        e.printStackTrace(); }
    
      finally{
        try{
          if(stmt != null) stmt.close();
          if(resultSet != null) resultSet.close();
          if(link != null) link.close();}
        catch(SQLException e2){
          e2.printStackTrace();}
        try{
            if (socketIn!=null) socketIn.close();
            if (socketOut!=null) socketOut.close();
          if (connection!=null) connection.close();
          if (serverSocket!=null) serverSocket.close();}
        catch(IOException e){
          System.err.println("Socket can't be closed.");}}}}}
