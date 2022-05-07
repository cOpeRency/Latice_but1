package latice.model;

import java.io.File;
import java.net.MalformedURLException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class Box {
	private StackPane stackPane;
	private BoxType boxType;
	private String imgURL;
	private Tile tile;
	
	public Box(BoxType boxType) {
		this.stackPane = new StackPane();
		this.boxType = boxType;
		this.tile = Tile.NO;
	}
	
	public StackPane InitializeStackPane() {
		if (this.boxType == BoxType.SUN) {
			this.imgURL = "src/main/resources/themes/classic/bg_sun.png";
		} else if (this.boxType == BoxType.MOON) {
			this.imgURL = "src/main/resources/themes/classic/bg_moon.png";
		} else {
			this.imgURL = "src/main/resources/themes/classic/bg_sea.png";
		}
		String urlFichier;
		try {
			File fichier = new File(imgURL);
			urlFichier = fichier.toURI().toURL().toString();
			Image img = new Image(urlFichier, 62, 62, true, true);
			ImageView imgView = new ImageView(img);
			this.stackPane.getChildren().add(imgView);
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.stackPane;
	}
	
	public Tile getTile() {
		return this.tile;
	}
	
	public void setTile(Tile tile) {
		this.tile = tile;
	}
}
