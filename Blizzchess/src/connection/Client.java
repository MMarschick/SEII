package connection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.regex.Pattern;

import game.GameParser;
 
public class Client
{
	//Edit Test
	private int wait=0;
	
	//Variablen
    private static Socket socket; //zentraler Socket
    private char delimiter='|';	  //Definition eines Delimiters zur Trennung einer Nachricht
    private String playerName;	  //Name eines Players
    private String playerPW;	  //Passwort eines Players
    private BufferedWriter bw;	  //BufferedWriter zum Senden an Server
    private BufferedReader br;    //BufferedReader zum Empfangen von Server
    
    private String opponentName;
    private String playerOne;
    private String playerTwo;
    
    //Setter-Methoden
    public void setPlayerName(String playerName){this.playerName=playerName;}
    public void setPlayerPW(String playerPW){this.playerPW=playerPW;}
    public void setBufferedWriter(BufferedWriter bw){this.bw=bw;}
    public void setBufferedReader(BufferedReader br){this.br=br;}
    
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
    	catch(Exception e){e.printStackTrace();}
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
    	catch(Exception e){e.printStackTrace();}
    }
        
    //Methode zum Einloggen eines Players
    //Boolean Rueckgabewert zur Ueberpruefung, ob der Login erfolgreich wahr
    public boolean checkLogin()
    {
    	boolean loginTry=false;
		sendMessage("login", playerName+delimiter+playerPW);
    	try
    	{
    		if(br.readLine().equals("true")){loginTry=true;}
    	}
    	catch(Exception e){e.printStackTrace();}
		return loginTry;
    }
    
    //Methode zum Ausloggen eines Players
    //Boolean Rueckgabewert zur Ueberpruefung, ob der Login erfolgreich wahr
    public boolean checkLogout()
    {
    	boolean logoutTry=false;
    	sendMessage("logout", playerName);
    	try
    	{
    		if(br.readLine().equals("true")){logoutTry=true;}
    	}
    	catch(Exception e){e.printStackTrace();}
		return logoutTry;
    }
    
    //Methode zum erhalten von offenen Spielen des Spielers
    //Rueckgabewert sind alle Spiele in einem String; Delemiter: "|"
    public String getOpenGames()
    {
    	String openGames="";
		sendMessage("openGames", playerName);
    	try{openGames=br.readLine();}
    	catch(Exception e){e.printStackTrace();}
    	return openGames;
    }
    
    //Methode zum Synchronisieren des Boards
    //Boolean Rueckgabewert zur Ueberpruefung, ob das Board synchronisiert werden konnte
    public boolean synchBoard(String board)
    {
		boolean gotBoard=false;
		sendMessage("synchBoard", playerOne+"|"+playerTwo+"|"+board);
    	try
    	{
    		while(!gotBoard)
    		{
    			gotBoard=Boolean.valueOf(br.readLine());
    			if(!gotBoard){gotBoard=synchBoard(board);}
    		}
    		return gotBoard;
    	}
    	catch(Exception e){e.printStackTrace();}
    	return gotBoard;
    }
    
    //erstelle neuen Spieler
    public boolean createPlayer()
    {
    	boolean createTry=false;
    	try
    	{
    		sendMessage("createPlayer", playerName+"|"+playerPW);
    		if(br.readLine().equals("true")){createTry=true;}
    	}
    	catch(Exception e){e.printStackTrace();}
    	return createTry;
    }
    
    //suche neues Spiel
    public String searchGame()
    {
    	sendMessage("searchGame", playerName);
    	try
    	{
    		String[] returnMessage = br.readLine().split(Pattern.quote("|"));
    		if(returnMessage[0].equals("true"))
    		{
    			if(returnMessage[1].equals(playerName))
    			{
    				opponentName=returnMessage[2];
    				playerOne=playerName;
    				playerTwo=opponentName;
    			}
    			else
    			{
    				opponentName=returnMessage[1];
    				playerOne=opponentName;
    				playerTwo=playerName;
    			}
    			if(returnMessage[3].equals(playerName)){return returnMessage[4];}
    			else{return opponentName;}
    		}
    	}
    	catch(Exception e){System.out.println(e.getMessage());}
    	return "false";
    }
    
    //warte auf einen suchenden Spieler
    public String waitForGame()
    {
    	sendMessage("waitForGame", playerName+"|"+opponentName);
    	try
    	{
    		if(br.readLine().equals("true")){return "true";}
    	}
    	catch(Exception e){System.out.println(e.getMessage());}
    	return "false";
    }
    
    //warten auf turn
    public boolean waitForTurn()
    {
    	sendMessage("waitForTurn", playerOne+"|"+playerTwo+"|"+playerName);
    	try
    	{
    		if(br.readLine().equals("true")){return true;}
    	}
    	catch (Exception e){System.out.println(e.getMessage());}
    	return false;
    }
    
    public boolean synchGame()
    {
    	sendMessage("synchGame", playerOne+"|"+playerTwo+"|"+opponentName);
    	try
    	{
    		if(br.readLine().equals("true")){return true;}
    	}
    	catch(Exception e){System.out.println(e.getMessage());}
    	return false;
    }
    
    //Erhalte ein Spiel mit playerOne/playerTwo
	public String getGame() 
	{
		playerOne=playerName;
		playerTwo="XXGamerXX";
		opponentName="XXGamerXX";
		sendMessage("game", playerOne+"|"+playerTwo);
		try{return br.readLine();}
		catch (Exception e){System.out.println(e.getMessage());}
		return null;
	}
    
    //Schliessen des Sockets/der Verbindung
    public void closeSocket()
    {
    	try{socket.close();}
    	catch(Exception e){e.printStackTrace();}
    }
    
    //Edit fuer Tests
    public Client (String test){}
    public String returnTest()
    {
    	String[] testArray = new String[4];
    	testArray[0]="true|wait";
    	testArray[1]="true|false";
    	testArray[2]="true|"+GameParser.DEFAULT_STRING;
    	testArray[3]="true|anders";
    	return(testArray[(int)(Math.random()*4)]);
//    	return "true|false";   	
    }
    
    public int waitTest()
    {
    	if(wait>1)
    	{
    		wait=0;
    	}
    	return wait++;
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
}
