package latice.audio;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import latice.vue.GameVisual;

public class musicManager {

	public static MediaPlayer getBackgroundMusic(){
		 String musicFile = "src/main/resources/musics/"+GameVisual.getTheme()+"/music1.mp3";
         Media sound = new Media(new File(musicFile).toURI().toString());
         MediaPlayer mediaPlayer = new MediaPlayer(sound);
         return mediaPlayer;
	}
}
