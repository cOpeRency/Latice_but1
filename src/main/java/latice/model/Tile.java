package latice.model;

import java.io.File;
import java.net.MalformedURLException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Tile extends ImageView{
	private final Shape shape;
	private final Color color;
	public static Tile NO = null;
	private String imagePath = "src/main/resources/themes/classic/";
	
	public Tile(Shape shape, Color color) {
		super();
		this.shape = shape;
		this.color = color;
		this.imagePath = this.imagePath+this.shape.code()+"_"+this.color.code()+".png";
		setTileImage();
	}
	
	public Image setTileImage() {
		String urlFichier;
		try {
			File fichier = new File(this.imagePath);
			urlFichier = fichier.toURI().toURL().toString();
			Image img = new Image(urlFichier, 62, 62, true, true);
			setImage(img);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String toString() {
		return this.shape.code()+" "+this.color.code();
	}
}
