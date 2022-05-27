package latice.application;

import java.io.File;
import java.net.MalformedURLException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import latice.vue.GameVisual;

public class LoadingScreen {

	public static Scene getScene() {
		AnchorPane root = new AnchorPane();

		ImageView imgView = new ImageView();
		String urlFichier;
		try {
			File fichier = new File("src/main/resources/system/loadBG.png");
			urlFichier = fichier.toURI().toURL().toString();
			Image img = new Image(urlFichier,1300,731,true,true);
			imgView.setImage(img);
			root.getChildren().add(imgView);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
		Label lblLoading = new Label("Chargement...");
		lblLoading.setLayoutX(820);
		lblLoading.setLayoutY(620);
		lblLoading.setTextFill(javafx.scene.paint.Color.WHITE);
		lblLoading.setFont(Font.font(null, FontWeight.BOLD, 55));
		lblLoading.setMinHeight(100);
		
		root.getChildren().add(lblLoading);
		
		
		Scene scene = new Scene(root,1280,720);
		
		return scene;
	}
}
