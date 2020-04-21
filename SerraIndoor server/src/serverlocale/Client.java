package serverlocale;

import java.io.EOFException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
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
				System.out.println("****** CLIENT PC *****");
				
	            String hostname = "easy-fut5al.homepc.it";
	            InetAddress addr = InetAddress.getByName(hostname);

	            System.out.println("eccolooo:"+ addr);
					
				Socket socket = new Socket(addr.toString(),8080);
				
				// Attendendo la connessione con il server aziendale ...
				
				ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());
		
				outStream.writeObject("[Client][123-234-357-1112]");  // Dici al server aziendale che sei un server locale
				
				System.out.println("Connessione con il server ok.");

				
				do{
					System.out.println("Spedisci qualcosa al server aziendale: ");
				    daspedire = myObj.nextLine();
				    outStream.writeObject(daspedire);
					System.err.println(inStream.readObject());
				
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


