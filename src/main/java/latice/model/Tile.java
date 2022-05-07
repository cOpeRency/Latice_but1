package latice.model;

import java.io.File;
import java.net.MalformedURLException;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;

public class Tile extends ImageView implements EventHandler<MouseEvent>{
	private final Shape shape;
	private final Color color;
	public static Tile NO = null;
	private Rack parentRack;
	private static String HOVER_EFFECT = "-fx-effect: dropshadow(three-pass-box, rgba(200,200,0,0.8), 15, 0.6, 0, 0);";
	private static String SHADOW_EFFECT = "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0.4, 0, 0);";
	private String imagePath = "src/main/resources/themes/classic/";
	
	public Tile(Shape shape, Color color) {
		super();
		this.shape = shape;
		this.color = color;
		this.imagePath = this.imagePath+this.shape.code()+"_"+this.color.code()+".png";
		setTileImage();
		setTileEffects();
		
		setOnDragDetected(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent event) {
		    	Dragboard dragboard = startDragAndDrop(TransferMode.MOVE);
		        setStyle(SHADOW_EFFECT);
		        
		        ClipboardContent content = new ClipboardContent();
		        content.putImage(getImage());
		        content.putString(shape.toString()+"_"+color.toString());
		        dragboard.setContent(content);
		        event.consume();
		    }
		});
		
		setOnDragDone(new EventHandler<DragEvent>() {
		    @Override
		    public void handle(DragEvent event) {
		    	if (event.getTransferMode() == TransferMode.MOVE)
			    	exitRack();
		        event.consume();
		    }
		});
	}

	public void exitRack() {
    	parentRack.removeTile(this);
	}
	
	private void setTileEffects() {
		this.setStyle(SHADOW_EFFECT);
		this.setOnMouseEntered(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent t) {
		        setStyle(HOVER_EFFECT);
		    }
		});
		this.setOnMouseExited(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent t) {
		        setStyle(SHADOW_EFFECT);
		    }
		});
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
	
	public void setParentRack(Rack parent) {
		this.parentRack = parent;
	}
	
	public String toString() {
		return this.shape.code()+" "+this.color.code();
	}

	@Override
	public void handle(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}
}
