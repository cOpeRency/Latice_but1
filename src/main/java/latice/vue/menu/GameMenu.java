package latice.vue.menu;

import java.io.File;
import java.net.MalformedURLException;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import latice.vue.GameTheme;

public class GameMenu {
	private static Scene sceneMenu;
	private static AnchorPane apMenu;
	private static ImageView imgMenu;
	private static Button btnStart;
	private static final String IMAGE_PATH = "src/main/resources/system/mainMenu_bg.png";
	
	public static Scene getSceneMenu() {
		apMenu = new AnchorPane();
		String urlFichier;
		try {
			File fichier = new File(IMAGE_PATH);
			urlFichier = fichier.toURI().toURL().toString();
			Image img = new Image(urlFichier,1300,731,true,true);
			imgMenu = new ImageView(img);
			apMenu.getChildren().add(imgMenu);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		btnStart = new Button("Start");
		btnStart.setMinSize(320, 50);
		btnStart.setLayoutX(480);
		btnStart.setLayoutY(500);
		
		apMenu.getChildren().add(btnStart);
		sceneMenu = new Scene(apMenu,1280,720);
		return sceneMenu;
	}
	
	public static Button getBtnStart() {
		return btnStart;
	}
	
}
