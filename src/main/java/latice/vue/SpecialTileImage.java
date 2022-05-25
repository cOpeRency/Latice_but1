package latice.vue;

import java.io.File;
import java.io.Serializable;
import java.net.MalformedURLException;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import latice.application.GameMain;
import latice.model.BoardTile;
import latice.model.GameManager;
import latice.model.SpecialTile;

public class SpecialTileImage extends ImageView implements Serializable {
	private static final long serialVersionUID = 1L;
	private SpecialTile tileSource;
	private static String HOVER_EFFECT = "-fx-effect: dropshadow(three-pass-box, rgba(200,200,0,0.8), 15, 0.6, 0, 0);";
	public static String SHADOW_EFFECT = "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0.4, 0, 0);";
	
	
	public SpecialTileImage(SpecialTile tile) {
		this.tileSource = tile;
		initDragAndDrop();
		setTileEffects();
		setTileImage();
	}
	
	public void hideTile() {
		String urlFichier;
		try {
			File fichier = new File("src/main/resources/themes/pokemon/back.png");
			urlFichier = fichier.toURI().toURL().toString();
			Image img = new Image(urlFichier, 62, 62, true, true);
			setImage(img);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}	
	
	public void setTileImage() {
		String urlFichier;
		try {
			File fichier = new File(this.tileSource.getImagePath());
			urlFichier = fichier.toURI().toURL().toString();
			Image img = new Image(urlFichier, 62, 62, true, true);
			setImage(img);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}	
	
	
	
	public SpecialTile getTileSource() {
		return tileSource;
	}
	
	
	
	public void initDragAndDrop() {
		setOnDragDetected(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent event) {
		    	if (!tileSource.getParentRack().isLocked() && tileSource.getParentRack().getOwner().isAbleToPutATile()) {
			    	Dragboard dragboard = startDragAndDrop(TransferMode.MOVE);
			        setStyle(SHADOW_EFFECT);
			        
			        ClipboardContent content = new ClipboardContent();
			        content.putImage(getImage());
			        content.putString(tileSource.getType().toString());
			        //content.put(GameMain.TILE_DATA, tileSource);

			        dragboard.setContent(content);
			        event.consume();
		    	}
		    }


		});
		
		setOnDragDone(new EventHandler<DragEvent>() {
		    @Override
		    public void handle(DragEvent event) {
		    	if (event.getTransferMode() == TransferMode.MOVE) {
			    	if (tileSource.getParentRack()!=null) {
			    		tileSource.exitRack();
			    		tileSource.getParentRack().getRackFX().getChildren().remove(tileSource.getTileFX());
			    	}
		    	}
		        event.consume();
		    }
		});
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
}
