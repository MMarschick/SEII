package tools;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundPlayer
{
	MediaPlayer audio;
	public SoundPlayer()
	{
		audio = new MediaPlayer(new Media("http://www.testhalter.de/"));
		audio.setVolume(0.5);
	}

	//Methode um ein Track (lokal in "sounds") abzuspielen
	public void playTrack(String track)
	{
		audio = new MediaPlayer(new Media(new File("sounds\\"+track).toURI().toString()));
		audio.stop();
		audio.play();
	}
	
	//Stop-Methode
	public void stop(){audio.stop();}
	
	//Methode zum Abspielen; letzter verwendeter Track wird benutzt
	public void play()
	{
		audio.stop();
		audio.play();
	}
}
