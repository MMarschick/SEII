package connection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;
import java.util.regex.Pattern;

public abstract class Messages 
{
	//Edit Test
	protected int wait=0;
	
	//Variablen
    protected static Socket socket; //zentraler Socket
    protected char delimiter='|';	  //Definition eines Delimiters zur Trennung einer Nachricht
    protected String playerName;	  //Name eines Players
    protected String playerPW;	  //Passwort eines Players
    protected BufferedWriter bw;	  //BufferedWriter zum Senden an Server
    protected BufferedReader br;    //BufferedReader zum Empfangen von Server
    
    protected String opponentName;
    protected String playerOne;
    protected String playerTwo;
    
    //Setter-Methoden
    public String getPlayerName(){return playerName;}
    public void setPlayerOne(String playerOne){this.playerOne=playerOne;}
    public void setPlayerTwo(String playerTwo){this.playerTwo=playerTwo;}
    public void setPlayerName(String playerName){this.playerName=playerName;}
    public void setPlayerPW(String playerPW){this.playerPW=playerPW;}
    public void setOpponentName(String opponentName){this.opponentName=opponentName;}
    public void setBufferedWriter(BufferedWriter bw){this.bw=bw;}
    public void setBufferedReader(BufferedReader br){this.br=br;}
	
	
	//Methode als zentrale Kommunikationsstelle (Output zum Server)
    protected void sendMessage(String mode, String sendMessage)
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
//    		while(!gotBoard)
//    		{
//    			gotBoard=Boolean.valueOf(br.readLine());
//    			if(!gotBoard){gotBoard=synchBoard(board);}
//    		}
//    		return gotBoard;
    		br.readLine();
    		return true;
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
    
    //warten auf turn
    public boolean waitingTurn()
    {
    	sendMessage("waitingTurn", playerOne+"|"+playerTwo+"|"+playerName);
    	try
    	{
    		if(br.readLine().equals("true")){return true;}
    	}
    	catch (Exception e){System.out.println(e.getMessage());}
    	return false;
    }
    
    //Aktualisiere den Game-String von Game playerOne/playerTwo
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
		sendMessage("game", playerOne+"|"+playerTwo);
		try{return br.readLine();}
		catch (Exception e){System.out.println(e.getMessage());}
		return null;
	}
}
