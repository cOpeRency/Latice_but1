package latice.model;

import java.io.File;
import java.net.MalformedURLException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class Box {
	private StackPane stackPane;
	private BoxType boxType;
	
	public Box(BoxType boxType) {
		this.stackPane = new StackPane();
		this.boxType = boxType;
	}
	
	public StackPane InitializeStackPane() {
		File fichier = new File("C:\\Users\\thoni\\Desktop\\Homework\\S2\\java\\SAE_ressources\\bg_sun.png");
		String urlFichier;
		try {
			urlFichier = fichier.toURI().toURL().toString();
			Image img = new Image(urlFichier, 60, 60, true, true);
			ImageView imgView = new ImageView(img);
			this.stackPane.getChildren().add(imgView);
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
		return this.stackPane;
	}
}
