package tools;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundPlayer
{
	MediaPlayer audio;
	double volume;
	
	public SoundPlayer(double volume)
	{
		audio = new MediaPlayer(
				new Media("http://www.testhalter.de/"));
		audio.setVolume(volume);
		this.volume=volume;
	}

	//Methode um ein Track (lokal in "sounds") abzuspielen
	public void playTrack(String track)
	{
		audio.stop();
		audio = new MediaPlayer(
				new Media(
				new File("sounds\\"+track).toURI().toString()));
		audio.setVolume(volume);
		audio.play();
	}
	
	//Methode um ein random Track (lokal in "sounds") abzuspielen
	public void playRandomTrack(String[] tracks) 
	{
		audio.stop();
		audio = new MediaPlayer(
				new Media(
				new File("sounds\\" + tracks[(int) (Math.random() * tracks.length)])
				.toURI().toString()));
		audio.setVolume(volume);
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
