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


/**
 *
 * @author Fernet
 * 
 * COSE DA FARE:
 * provare di collegarsi in remoto tramite nome dominio.
 * usare magari un implementazione alternativa migliore alle rxtx
 *
 */

public class AziendaleControl  implements Runnable{
	
	static Logger logger = Logger.getLogger("loggerGlobale");

	Socket Socket;

	public static volatile String mesricevuto ="";
	
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
	
	// Crea nuovo thread con il nuovo client che si è appena connesso
	public void run() {
		String whoami;
		
		try {		
			System.out.println("Accettata una connessione... attendo comandi");
			ObjectOutputStream outStream1 = new ObjectOutputStream(Socket.getOutputStream());
			ObjectInputStream inStream1 = new ObjectInputStream(Socket.getInputStream());
			
			whoami = inStream1.readObject().toString();
			
			 // è un client Android/PC
		    if(whoami.equals("[Client][123-234-357-1112]")){
				System.out.println("Si è collegato il client: [Client][123-234-357-1112]");

				do {
					mesricevuto  = inStream1.readObject().toString();
					 System.err.println("Ho ricevuto da client: "+ mesricevuto);	 
				}while(!mesricevuto.equals("STOP"));				
					
			}
		    
		    		
			// è un Server Locale
			if(whoami.equals("[Server Locale][123-234-357-1112]")) {	
				System.out.println("Si è collegato il Server: [Server Locale][123-234-357-1112]");

				do {					 

					if(mesricevuto!= "") { //Se il client ha "riempito un messaggio ..
						System.out.println("Messaggio che sto inviando al Server..:"+mesricevuto);
						outStream1.writeObject(mesricevuto);
						mesricevuto =""; //Svuota buffer, aspetta che lo ri riempie il client
					}
				
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
