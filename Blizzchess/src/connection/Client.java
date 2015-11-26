package connection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
 
public class Client
{
	//Variablen
    private static Socket socket; //zentraler Socket
    private char delimiter='|';	  //Definition eines Delimiters zur Trennung einer Nachricht
    private String playerName;	  //Name eines Players
    private String playerPW;	  //Passwort eines Players
    private BufferedWriter bw;	  //BufferedWriter zum Senden an Server
    private BufferedReader br;    //BufferedReader zum Empfangen von Server
    
    //Setter-Methoden
    public void setPlayerName(String playerName)
    {
    	this.playerName=playerName;
    }
    public void setPlayerPW(String playerPW)
    {
    	this.playerPW=playerPW;
    }
    public void setBufferedWriter(BufferedWriter bw)
    {
    	this.bw=bw;
    }
    public void setBufferedReader(BufferedReader br)
    {
    	this.br=br;
    }
    
    //Methode als zentrale Kommunikationsstelle (Output zum Server)
    private void sendMessage(String mode, String sendMessage)
    {
    	try
    	{
    		System.out.println(mode + "|" + sendMessage);
    		bw.write(mode+delimiter+sendMessage+"\n");
    		bw.flush();
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    
    //Methode zum Verschicken einer bisher neutralen Nachricht
    //Kann evtl. entfernt werden
    public String recieveMessage()
    {
    	String message="";
    	sendMessage("message", "Test");
    	try
    	{
    		message=br.readLine();
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	return message;
    }
    
    //Methode zum Einloggen eines Players
    //Boolean Rueckgabewert zur Ueberpruefung, ob der Login erfolgreich wahr
    public boolean checkLogin()
    {
    	boolean loginTry=false;
    	try
    	{
    		sendMessage("login", playerName+delimiter+playerPW);
    		if(br.readLine().equals("true"))
    		{
    			loginTry=true;
    		}
    		else
    		{
    			
    		}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
		return loginTry;
    }
    
    //Methode zum Ausloggen eines Players
    //Boolean Rueckgabewert zur Ueberpruefung, ob der Login erfolgreich wahr
    public boolean checkLogout()
    {
    	boolean logoutTry=false;
    	try
    	{
    		sendMessage("logout", playerName);
    		if(br.readLine().equals("true"))
    		{
    			logoutTry=true;
    		}
    		else
    		{
    			
    		}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
		return logoutTry;
    }
    
    //Methode zum erhalten von offenen Spielen des Spielers
    //Rueckgabewert sind alle Spiele in einem String; Delemiter: "|"
    public String getOpenGames()
    {
    	String openGames="";
    	try
    	{
    		sendMessage("openGames", playerName);
    		openGames=br.readLine();
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	return openGames;
    }
    
    //Methode zum Synchronisieren des Boards
    //Boolean Rueckgabewert zur Ueberpruefung, ob das Board synchronisiert werden konnte
    public boolean synchBoard(String board)
    {
    	try
    	{
    		sendMessage("synchBoard", board);
    		boolean gotBoard=false;
    		while(!gotBoard)
    		{
    			gotBoard=Boolean.valueOf(br.readLine());
    			if(!gotBoard)
    			{
    				gotBoard=synchBoard(board);
    			}
    		}
    		return gotBoard;
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	return false;
    }
    public boolean createPlayer()
    {
    	boolean createTry=false;
    	try
    	{
    		sendMessage("createPlayer", playerName+"|"+playerPW);
    		if(br.readLine().equals("true"))
    		{
    			createTry=true;
    		}
    		else
    		{
    			
    		}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	return createTry;
    }
    
//    public boolean turn()
//    {
//    	try
//    	{
//    		if(br.readLine().equals("true"))
//    		{
//    			return true;
//    		}
//    	}
//    	catch(Exception e)
//    	{
//    		System.out.println(e.getMessage());
//    	}
//    	return false;
//    }
    
    public boolean searchGame()
    {
    	//turn
    	sendMessage("searchGame", playerName);
    	try
    	{
    		if(br.readLine().equals("true"))
    		{
    			return true;
    		}
    	}
    	catch(Exception e)
    	{
    		System.out.println(e.getMessage());
    	}
    	return false;
    }
    
    //in newGame:
    //neue methode nach searchNewGame verlagern
    //am ende eine while (true) setzen
    //bei 2 if abfragen überprüfen, ob es erfolgreich ist
    //(update wurde ausgeführt; spiel ist bereits vorhanden)
    //dann return true;;; vllt doch gameString? + PlayerOne + PlayerTwo? + Turn?
    public boolean waitForGame()
    {
    	sendMessage("waitForGame", playerName);
    	try
    	{
    		if(br.readLine().equals("true"))
    		{
    			return true;
    		}
    	}
    	catch(Exception e)
    	{
    		System.out.println(e.getMessage());
    	}
    	return false;
    }
    
//    case "searchGame":
//    	returnMessage=searchingGame(abc[1]);
//    	break;
//    	
//    public String searchingGame(String playerName)
//    {
//    	return dataAccessor.search(playerName);
//    }
//    
//    public String search(String playerName)
//    {
//    	rs...
//    	if(online && searching)
//    	{
//    		boolean p1Free true;
//    		if(playerOne.equals(""))
//    		{
//    			setPlayerOne(playerName);
//    			p1Free=false;
//    		}
//    		if(playerTwo.equals("")&&!p1Free)
//    		{
//    			setPlayerTwo(playerName);
//    		}
//    		if(!PlayerOne.equals("")&&!PlayerTwo.equals(""))
//    		{
//    			if(Mathrandom >= 0.5)
//    			{
//    				setTurn(PlayerOne);
//    			}
//    			else
//    			{
//    				setTurn(PlayerTwo);
//    			}
//    			createGame(where PlayerOne PlayerTwo turn)
//    			return "new";
//    		}
//    		else
//    		{
//    			return "wait";
//    		}
//    	}
//    	else
//    	{
//    		return "";
//    	}
//    }
    
    public Client()
    {
    	try
    	{
    		//Socket fuer Connection
//    		String host = "169.254.106.0";
    		String host = "192.168.0.239";
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
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    
    //Schliessen des Sockets/der Verbindung
    public void closeSocket()
    {
    	try
    	{
    		socket.close();
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
}
