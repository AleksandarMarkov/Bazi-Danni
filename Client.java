package kr;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.net.Socket;
import java.io.IOException;
import java.net.ConnectException;

public class Client {
	public static void main(String args[]){
	     Socket connection = null;
	     Scanner socketIn = null;
	     PrintWriter socketOut = null;
	     Scanner keyboardIn = new Scanner(System.in);
	     int port = 1234;
	     String host = "localhost";

	     try{
	       System.out.println("Connecting to server...");
	       try{
	         connection = new Socket(host, port); }
	       catch(ConnectException e){
	         System.err.println("Server connection failed.");
	         return; }
	       System.out.println("Server connection successful!");

	       socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
	       System.out.println(socketIn.nextLine());
	       
	       String command = null;
	       String sss=null;
	       socketOut = new PrintWriter(connection.getOutputStream(), true);
	       
	       String type;
	       String make;
	       String model;
	       String component_id;
	       String status;
	       String results;
	       String parts;
	       String price;
	       String id;
	       
	       do{
	         socketOut.flush();
	         System.out.print("Enter a command: ");
	         command = keyboardIn.nextLine();
	         socketOut.println(command.toLowerCase());
	         
	         switch(command){
	        
	         case "create":{
	        	 System.out.println("Enter Type:");
	        	 type=keyboardIn.nextLine();
	        	 socketOut.println(type);
	        	 
	        	 System.out.println("Enter Make:");
	        	 make=keyboardIn.nextLine();
	        	 socketOut.println(make);
	        	 
	        	 System.out.println("Enter Model:");
	        	 model=keyboardIn.nextLine();
	        	 socketOut.println(model);
	        	 
	        	 System.out.println("Enter Component ID:");
	        	 component_id=keyboardIn.nextLine();
	        	 socketOut.println(component_id);
	        	 
	        	 System.out.println("Enter Status:");
	        	 status=keyboardIn.nextLine();
	        	 socketOut.println(status);
	        	 
	        	 System.out.println("Enter Results:");
	        	 results=keyboardIn.nextLine();
	        	 socketOut.println(results);
	        	 
	        	 System.out.println("Enter Parts:");
	        	 parts=keyboardIn.nextLine();
	        	 socketOut.println(parts);
	        	 
	        	 System.out.println("Enter Price:");
	        	 price=keyboardIn.nextLine();
	        	 socketOut.println(price);
	        	 
	        	 System.out.println("Waiting for Server...");

	        	 System.out.println(socketIn.nextLine());
	        	 break;}
	         
	         case "view": {
	        	 while(!"####".equals(sss)) {
	        		sss=socketIn.nextLine();
	        		System.out.println(sss); } 
	         break;}
	         
	         case "update": {
	        	 System.out.println("Enter Component ID:");
	        	 id=keyboardIn.nextLine();
	        	 socketOut.println(id);
	        	 
	        	 System.out.println("Enter Type:");
	        	 type=keyboardIn.nextLine();
	        	 socketOut.println(type);
	        	 
	        	 System.out.println("Enter Make:");
	        	 make=keyboardIn.nextLine();
	        	 socketOut.println(make);
	        	 
	        	 System.out.println("Enter Model:");
	        	 model=keyboardIn.nextLine();
	        	 socketOut.println(model);
	        	 
	        	 System.out.println("Enter Component ID:");
	        	 component_id=keyboardIn.nextLine();
	        	 socketOut.println(component_id);
	        	 
	        	 System.out.println("Enter Status:");
	        	 status=keyboardIn.nextLine();
	        	 socketOut.println(status);
	        	 
	        	 System.out.println("Enter Results:");
	        	 results=keyboardIn.nextLine();
	        	 socketOut.println(results);
	        	 
	        	 System.out.println("Enter Parts:");
	        	 parts=keyboardIn.nextLine();
	        	 socketOut.println(parts);
	        	 
	        	 System.out.println("Enter Price:");
	        	 price=keyboardIn.nextLine();
	        	 socketOut.println(price);
	        	 
	        	 System.out.println(socketIn.nextLine());
	         break;}
	         
	         case "delete":{
	        	 System.out.println("Enter Component ID:");
	        	 id=keyboardIn.nextLine();
	        	 socketOut.println(id);
	        	 System.out.println(socketIn.nextLine());
	         break;}
	         
	         default:
	        	 System.out.println(socketIn.nextLine());
	        	 break; } }
	        	 
	       while (!command.equalsIgnoreCase("exit"));
	       System.out.println("Closing connection..."); }
	       
	     catch(IOException e) {
	       e.printStackTrace(); }
	     finally{
	       try{
	         if(socketIn!=null) socketIn.close();
	         if(socketOut!=null) socketOut.close();
	         if(connection!=null) connection.close();
	         if(keyboardIn!=null) keyboardIn.close(); }
	       catch(IOException e){
	         System.err.println("Cannot close socket!"); } } }
}
