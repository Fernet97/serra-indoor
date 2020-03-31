package serveraziendale;

import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

import interfaces.ArduinoSerial;



public class AziendaleControl {
	
	static Logger logger = Logger.getLogger("loggerGlobale");

	private static String ricevutoDaArduino;
	private static String messaggioAndroid;
	
	public static void main(String[] args) {	
		
		System.out.println("****** SERVER AZIENDALE *****");
		
		try {
			ServerSocket serverSocket = new ServerSocket(8080);
			logger.info("Socket istanziato, ora accetto connessioni ...");
			Socket socket = serverSocket.accept();  // Bloccante ...
			
			// Attendendo la connessione ...
			
			logger.info("Accettata una connessione... attendo comandi");
			ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());
	
			
			System.out.println("Mi sto collegando con arduino ...");
			
			ArduinoSerial arduinoserial = new ArduinoSerial("COM3");
			arduinoserial.initialize(); // Trova la porta seriale 
				
			ricevutoDaArduino = "";
			do {
				messaggioAndroid = (String) inStream.readObject();
			    System.out.println("Messaggio android da mandare ad arduino:"+messaggioAndroid);
				

			    for(int i = 0; i < messaggioAndroid.length(); i++)  //mando carattere x carattere
			    		arduinoserial.send(Character.toString(messaggioAndroid.charAt(i)));
			    
				arduinoserial.send(Character.toString('\n'));
				
		
				ricevutoDaArduino = arduinoserial.read();
				System.err.println("Arduino mi ha mandato:"+ricevutoDaArduino);
				outStream.writeObject("Arduino mi ha mandato:"+ricevutoDaArduino); // mando cio ho ottenuto al server locale

				
				
				}while(!messaggioAndroid.equals("esci"));
			    

			socket.close(); // Chiudo connessione HTTP

			
	} catch(EOFException e) {
		logger.severe("Problemi di connessione:"+ e.getMessage());
		e.printStackTrace();}
	
	catch (Throwable t) {
		logger.severe("Lanciata Throwable:"+ t.getMessage());
		t.printStackTrace();
	}
}	
	
}
