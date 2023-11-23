package latice.vue.menu;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import latice.vue.GameTheme;

import static jdk.jfr.internal.SecuritySupport.getResourceAsStream;

public class GameMenu {
	private static Scene sceneMenu;
	private static AnchorPane apMenu;
	private static ImageView imgMenu;
	private static Button btnStart;
	private static final String IMAGE_PATH = "/system/mainMenu_bg.png";
	
	public static Scene getSceneMenu() {
		apMenu = new AnchorPane();
		try {
			Image img = new Image(getResourceAsStream(IMAGE_PATH),1300,731,true,true);
			imgMenu = new ImageView(img);
			apMenu.getChildren().add(imgMenu);
		} catch (IOException e) {
            throw new RuntimeException(e);
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
