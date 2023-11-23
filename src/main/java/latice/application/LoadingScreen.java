package latice.application;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import static jdk.jfr.internal.SecuritySupport.getResourceAsStream;

public class LoadingScreen {

	public static Scene getScene() {
		AnchorPane root = new AnchorPane();

		ImageView imgView = new ImageView();
		try {
			Image img = new Image(getResourceAsStream("/system/loadBG.png"));
			imgView.setImage(img);
			root.getChildren().add(imgView);
		} catch (IOException e) {
            throw new RuntimeException(e);
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
