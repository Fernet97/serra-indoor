package serverlocale;

import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

public class Client {
	
	static Logger logger = Logger.getLogger("global");
	private static String daspedire;
	private static Scanner myObj;

		
		public static void main(String[] args) {
			
		    myObj = new Scanner(System.in);  

			try {
				System.out.println("****** SERVER LOCALE *****");
				Socket socket = new Socket("192.168.1.11",8080);
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
				
				do{
					System.out.println("Spedisci qualcosa al server aziendale: ");
				    daspedire = myObj.nextLine();
					out.writeObject(daspedire);
					System.err.println(in.readObject());
				
				}while(!daspedire.equals("esci"));
				
				
				socket.close();

			
			} catch(EOFException e) {
				logger.severe("Problemi di connessione:"+ e.getMessage());
				e.printStackTrace();}
			
			catch (Throwable t) {
				logger.severe("Lanciata Throwable:"+ t.getMessage());
				t.printStackTrace();
			}
				
			
			
		}
	}


