package serveraziendale;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

import interfaces.ArduinoSerial;



public class AziendaleControl  implements Runnable{
	
	static Logger logger = Logger.getLogger("loggerGlobale");

	Socket Socket;
	
	public  AziendaleControl(Socket serverSock) {
		Socket = serverSock;
	}

	
	public static void main(String[] args) {	
		
		System.out.println("****** SERVER AZIENDALE *****");
		
		try {
			ServerSocket serverSocket = new ServerSocket(8080);
			logger.info("Socket istanziato, ora accetto connessioni ...");
			
			while(true) {	// Attendendo la connessione ...
				Socket socket = serverSocket.accept();  // Bloccante ...
			    new Thread(new AziendaleControl(socket)).start(); // Crea nuovo thread con il nuovo client che si è appena connesso
			}

			
	} catch(EOFException e) {
		logger.severe("Problemi di connessione:"+ e.getMessage());
		e.printStackTrace();}
	
	catch (Throwable t) {
		logger.severe("Lanciata Throwable:"+ t.getMessage());
		t.printStackTrace();
	}
}	
	

	public void run() {
		String whoami;
		String mesricevuto;
		
		try {		
			logger.info("Accettata una connessione... attendo comandi");
			ObjectOutputStream outStream = new ObjectOutputStream(Socket.getOutputStream());
			ObjectInputStream inStream = new ObjectInputStream(Socket.getInputStream());
			
			whoami = inStream.readObject().toString();
			
			 // è un client Android/PC
		    if(whoami.equals("[Client][123-234-357-1112]")){
				System.out.println("Si è collegato il client: [Client][123-234-357-1112]");
				do {
					mesricevuto = inStream.readObject().toString();
					 System.err.println("Ho ricevuto da client: "+ mesricevuto);
					 /*
					  * O gli passo l'ip del corrispettivo server locale e poi se la vede lui a comunicare	VVV 
					  * O l'aziendale fa da tramite, aprendo un nuovo socket con corrispettivo Server e inoltra i messaggi del client al server locale
					  * 
					  */
					 

					 
				}while(!mesricevuto.equals("STOP"));				
					
			}
			
			// è un Server Locale
			if(whoami.equals("[Server Locale][123-234-357-1112]")) {	
				System.out.println("Si è collegato il Server: [Server Locale][123-234-357-1112]");
				do {
					 mesricevuto = inStream.readObject().toString(); // Lo dovrò inoltrare al client corrispondente
					 System.err.println("Ho ricevuto dal server locale: "+ mesricevuto);
					 

				}while(!mesricevuto.equals("STOP"));
				    
			}
			
			


			Socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Chiudo connessione HTTP
		 catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		   
		   
		   
	   }
	
}
