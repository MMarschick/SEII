package connection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
 
public class Client extends Messages
{ 
    public Client()
    {
    	try
    	{
    		//Socket fuer Connection
    		String host = "169.254.106.0";
//    		String host = "192.168.0.239";
    		int port = 25000;
    		InetAddress address = InetAddress.getByName(host);
    		socket = new Socket(address, port);
    		socket.setKeepAlive(true);
    		
    		//Writer fuer Strings an Server
    		OutputStream os = socket.getOutputStream();
    		OutputStreamWriter osw = new OutputStreamWriter(os);
    		setBufferedWriter(new BufferedWriter(osw));
    	
    		//Reader fuer Strings vom Server
    		InputStream is = socket.getInputStream();
    		InputStreamReader isr = new InputStreamReader(is);
    		setBufferedReader(new BufferedReader(isr));		
    	}
    	catch(Exception e){e.printStackTrace();}
    }
    
    //Schliessen des Sockets/der Verbindung
    public void closeSocket()
    {
    	try{socket.close();}
    	catch(Exception e){e.printStackTrace();}
    }
    
//    //Edit fuer Tests
//    public Client (String test){}
//    public String returnTest()
//    {
//    	String[] testArray = new String[4];
//    	testArray[0]="true|wait";
//    	testArray[1]="true|false";
//    	testArray[2]="true|"+GameParser.DEFAULT_STRING;
//    	testArray[3]="true|anders";
//    	return(testArray[(int)(Math.random()*4)]);
////    	return "true|false";   	
//    }
//    
//    public int waitTest()
//    {
//    	if(wait>1)
//    	{
//    		wait=0;
//    	}
//    	return wait++;
//    }
//    
//    //Methode zum Verschicken einer bisher neutralen Nachricht
//    //Kann evtl. entfernt werden
//    public String recieveMessage()
//    {
//    	String message="";
//    	sendMessage("message", "Test");
//    	try
//    	{
//    		message=br.readLine();
//    	}
//    	catch(Exception e)
//    	{
//    		e.printStackTrace();
//    	}
//    	return message;
//    }
}
