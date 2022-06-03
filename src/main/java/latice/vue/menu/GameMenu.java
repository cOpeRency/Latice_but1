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
	private static ComboBox<String> cbTheme;
	private static TextField tfNameJ1;
	private static TextField tfNameJ2;
	private static final String IMAGE_PATH = "src/main/resources/system/mainMenu_bg.png";
	private static final String POKEMON = "Pokémon";
	private static final String ONE_PIECE = "One Piece";
	private static final String ZELDA = "Zelda";
	
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
		
		cbTheme = new ComboBox<String>();
		cbTheme.setMinSize(280, 40);
		cbTheme.setLayoutX(500);
		cbTheme.setLayoutY(570);
		cbTheme.getItems().addAll(POKEMON,ONE_PIECE,ZELDA);
		cbTheme.setValue(POKEMON);
		
		tfNameJ1 = new TextField();
		tfNameJ1.setPromptText("Nom du joueur 1");
		tfNameJ1.setMinSize(240, 40);
		tfNameJ1.setLayoutX(100);
		tfNameJ1.setLayoutY(630);
		
		tfNameJ2 = new TextField();
		tfNameJ2.setPromptText("Nom du joueur 2");
		tfNameJ2.setMinSize(240, 40);
		tfNameJ2.setLayoutX(940);
		tfNameJ2.setLayoutY(630);
		
		apMenu.getChildren().add(btnStart);
		apMenu.getChildren().add(cbTheme);
		apMenu.getChildren().add(tfNameJ1);
		apMenu.getChildren().add(tfNameJ2);
		sceneMenu = new Scene(apMenu,1280,720);
		return sceneMenu;
	}

	public static GameTheme getComboBoxTheme() {
		switch (cbTheme.getValue()) {
		case POKEMON:
			return GameTheme.POKEMON;
		case ONE_PIECE:
			return GameTheme.ONE_PIECE;
		case ZELDA:
			return GameTheme.ZELDA;
		default :
			return GameTheme.POKEMON;
		}
	}
	
	public static Button getBtnStart() {
		return btnStart;
	}

	public static TextField getTfNameJ1() {
		return tfNameJ1;
	}

	public static TextField getTfNameJ2() {
		return tfNameJ2;
	}
	
}
