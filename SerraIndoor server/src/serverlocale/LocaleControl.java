package serverlocale;

import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import interfaces.ArduinoSerial;

public class LocaleControl {
	

	private static String daspedire;
	private static String ricevuto;
	private static char ch;
	

	public static void main(String[] args) {
		
		System.out.println("****** SERVER LOCALE *****");
		
		try {
            String hostname = "easy-fut5al.homepc.it";
            InetAddress addr = InetAddress.getByName(hostname);
            String ip =  addr.toString().substring(addr.toString().indexOf('/')+1);

            System.out.println("Mi connetto al server aziendale con ip:"+ip);
			Socket socket = new Socket(ip,8080);
			
			// Attendendo la connessione con il server aziendale ...
			
			ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());
	
			outStream.writeObject("[Server Locale][123-234-357-1112]");  // Dici al server aziendale che sei un server locale

			
			System.out.println("Connessione con il server ok.\nMi sto collegando con arduino ...");
			
			ArduinoSerial arduinoserial = new ArduinoSerial("COM3");
			arduinoserial.initialize(); // Trova la porta seriale 
			
			
		    ricevuto = "";
			   
		    
		    do {
			    daspedire = inStream.readObject()+"\n"; // Messaggio inoltrato dal sistema aziendale
			    for(int i = 0; i < daspedire.length(); i++)  //mando carattere x carattere
			    		arduinoserial.send(Character.toString(daspedire.charAt(i)));
			    			  
	    		ch = '\0';
	    		do {
	    			if(arduinoserial.serialPort.getInputStream().available() !=0) {
		    			ch = (char) arduinoserial.serialPort.getInputStream().read();
		        	    ricevuto += ch;
		        	    }
	    			
	    		}while(ch != '\n');
	    		ch = '\0';			
				System.err.println("Arduino mi ha mandato:"+ricevuto);
				outStream.writeObject("Arduino mi ha mandato:"+ricevuto); // mando cio ho ottenuto al server aziendale
				ricevuto = "";
				
				}while(!daspedire.equals("x"));
			
			
			    

			socket.close(); // Chiudo connessione HTTP

			
	} catch(EOFException e) {
		e.printStackTrace();}
	
	catch (Throwable t) {
		t.printStackTrace();
	}
		
		

	}

	
	
}
