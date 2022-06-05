package latice.audio;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import latice.vue.GameVisual;

public class musicManager {
	private static Double loopStart = 0.0;

	public static MediaPlayer getBackgroundMusic(){
		 String musicFile = "src/main/resources/musics/"+GameVisual.getTheme()+"/music1.mp3";
         Media sound = new Media(new File(musicFile).toURI().toString());
         MediaPlayer mediaPlayer = new MediaPlayer(sound);
         
         
         Double loopEnd = 0.0;
         switch (GameVisual.getTheme()) {
         case "pokemon":
        	 musicManager.loopStart = 17.6;
             loopEnd = 263.5;
             break;
         case "one_piece":
        	 musicManager.loopStart = 25.415;
             loopEnd = 111.361;
             break;
         case "zelda":
        	 musicManager.loopStart = 69.1;
             loopEnd = 183.2;
             break;
        	 
         }
         mediaPlayer.setStopTime(Duration.seconds(loopEnd));
         mediaPlayer.setOnEndOfMedia(new Runnable() {
               public void run() {
            	   mediaPlayer.seek(Duration.seconds(musicManager.loopStart));
               }
           });
         return mediaPlayer;
	}
}
