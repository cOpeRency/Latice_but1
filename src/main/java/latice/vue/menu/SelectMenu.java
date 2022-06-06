package latice.vue.menu;

import java.io.File;
import java.net.MalformedURLException;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import latice.application.LoadingScreen;
import latice.vue.GameTheme;
import latice.vue.GameVisual;

public class SelectMenu {
	private static Scene sceneMenu;
	private static AnchorPane apMenu;
	private static ImageView imgMenu;
	private static Button btnStart;
	private static Label lblTheme;
	private static Button btnPokemonTheme;
	private static Button btnOnePieceTheme;
	private static Button btnZeldaTheme;
	private static Button btnDbzTheme;
	private static TextField tfNameJ1;
	private static TextField tfNameJ2;
	private static Spinner<Integer> spCycles;
	private static CheckBox cbUseCycles;
	
	private static final String IMAGE_PATH = "src/main/resources/system/selectMenu.png";

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
		btnStart.setLayoutY(650);
		
		lblTheme = new Label("Theme : Pokémon");
    	GameVisual.setTheme(GameTheme.POKEMON);
    	lblTheme.setMinSize(320, 50);
    	lblTheme.setAlignment(Pos.CENTER);
    	lblTheme.setLayoutX(480);
    	lblTheme.setLayoutY(580);
    	lblTheme.setTextFill(javafx.scene.paint.Color.WHITE);
    	lblTheme.setFont(Font.font(null, FontWeight.NORMAL, 35));
		
		btnPokemonTheme = new Button("Pokemon");
		btnPokemonTheme.setMinSize(200, 40);
		btnPokemonTheme.setLayoutX(75);
		btnPokemonTheme.setLayoutY(270);
		btnPokemonTheme.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent event) {
		    	GameVisual.setTheme(GameTheme.POKEMON);
		    	lblTheme.setText("Theme : Pokémon");
		    }
		});
		
		btnOnePieceTheme = new Button("One Piece");
		btnOnePieceTheme.setMinSize(200, 40);
		btnOnePieceTheme.setLayoutX(395);
		btnOnePieceTheme.setLayoutY(270);
		btnOnePieceTheme.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent event) {
		    	GameVisual.setTheme(GameTheme.ONE_PIECE);
		    	lblTheme.setText("Theme : One Piece");
		    }
		});
		
		btnZeldaTheme = new Button("The Legend of Zelda");
		btnZeldaTheme.setMinSize(200, 40);
		btnZeldaTheme.setLayoutX(710);
		btnZeldaTheme.setLayoutY(270);
		btnZeldaTheme.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent event) {
		    	GameVisual.setTheme(GameTheme.ZELDA);
		    	lblTheme.setText("Theme : The Legend of Zelda");
		    }
		});
		
		btnDbzTheme = new Button("Dragon Ball Z");
		btnDbzTheme.setMinSize(200, 40);
		btnDbzTheme.setLayoutX(1025);
		btnDbzTheme.setLayoutY(270);
		btnDbzTheme.setDisable(true);
		
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
		
		spCycles = new Spinner(1,20,0,1);
		spCycles.setEditable(false);
		spCycles.setLayoutX(880);
		spCycles.setLayoutY(480);
		spCycles.setDisable(true);
		
		cbUseCycles = new CheckBox();
		cbUseCycles.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent event) {
				spCycles.setDisable(!cbUseCycles.isSelected());
		    }
		});
		
		Label txtCbCycle = new Label("Utiliser des cycles ?");
		txtCbCycle.setTextFill(javafx.scene.paint.Color.WHITE);
		txtCbCycle.setFont(Font.font(null, FontWeight.BOLD, 15));
		
		HBox cbHb = new HBox();
		cbHb.getChildren().add(cbUseCycles);
		cbHb.getChildren().add(txtCbCycle);
		cbHb.setLayoutX(550);
		cbHb.setLayoutY(460);
		
		Label txtCycle = new Label("cycle(s)");
		txtCycle.setTextFill(javafx.scene.paint.Color.WHITE);
		txtCycle.setFont(Font.font(null, FontWeight.BOLD, 15));
		
		HBox hbCycles = new HBox();
		hbCycles.getChildren().add(spCycles);
		hbCycles.getChildren().add(txtCycle);
		hbCycles.setLayoutX(550);
		hbCycles.setLayoutY(480);
		
		apMenu.getChildren().add(btnStart);
		apMenu.getChildren().add(btnPokemonTheme);
		apMenu.getChildren().add(btnOnePieceTheme);
		apMenu.getChildren().add(btnZeldaTheme);
		apMenu.getChildren().add(btnDbzTheme);
		apMenu.getChildren().add(lblTheme);
		apMenu.getChildren().add(tfNameJ1);
		apMenu.getChildren().add(tfNameJ2);
		apMenu.getChildren().add(hbCycles);
		apMenu.getChildren().add(cbHb);
		sceneMenu = new Scene(apMenu,1280,720);
		return sceneMenu;
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

	public static Spinner<Integer> getSpCycles() {
		return spCycles;
	}

	public static CheckBox getCbUseCycles() {
		return cbUseCycles;
	}
	
	
}
