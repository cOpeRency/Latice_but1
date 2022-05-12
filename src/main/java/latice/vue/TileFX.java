package latice.vue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.MalformedURLException;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import latice.application.GameMain;
import latice.model.Tile;

public class TileFX extends ImageView implements Serializable {
	private static final long serialVersionUID = 1L;
	private Tile tileSource;
	private boolean isLastTilePlayed;
	private static String HOVER_EFFECT = "-fx-effect: dropshadow(three-pass-box, rgba(200,200,0,0.8), 15, 0.6, 0, 0);";
	private static String NOT_FIXED_EFFECT = "-fx-effect: dropshadow(three-pass-box, rgba(200,0,200,0.8), 15, 0.6, 0, 0);";
	private static String LAST_TILE_PLAYED_EFFECT = "-fx-effect: dropshadow(three-pass-box, rgba(0,250,200,0.8), 15, 0.6, 0, 0);";
	private static String SHADOW_EFFECT = "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0.4, 0, 0);";
	
	
	public TileFX(Tile tile) {
		this.tileSource = tile;
		this.isLastTilePlayed = true;
		initDragAndDrop();
		setTileEffects();
		setTileImage();
	}
	
	public boolean isLastTilePlayed() {
		return isLastTilePlayed;
	}

	public void setLastTilePlayed(boolean isLastTilePlayed) {
		this.isLastTilePlayed = isLastTilePlayed;
		if (isLastTilePlayed) {
			setStyle(LAST_TILE_PLAYED_EFFECT);		    			
		} else {
			setStyle(NOT_FIXED_EFFECT);
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
	
	public Tile getTileSource() {
		return tileSource;
	}

	public void initDragAndDrop() {
		setOnDragDetected(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent event) {
		    	if (!tileSource.isLocked() && isLastTilePlayed) {
			    	Dragboard dragboard = startDragAndDrop(TransferMode.MOVE);
			        setStyle(SHADOW_EFFECT);
			        
			        ClipboardContent content = new ClipboardContent();
			        content.putImage(getImage());
			        content.putString(tileSource.getShape().toString()+"_"+tileSource.getColor().toString());
			        
			        //content.put(GameMain.TILE_DATA, tileSource);
			        ObjectOutputStream objectOutputStream = null;
			        try {
						final FileOutputStream fichier = new FileOutputStream("C:/Windows/Temp/tile.ser");
						objectOutputStream = new ObjectOutputStream(fichier);
						objectOutputStream.writeObject(tileSource);
						objectOutputStream.flush();
						
					} catch (IOException e) {
						e.printStackTrace();						
					} finally {
						if (objectOutputStream != null) {
							try {
								objectOutputStream.close();
							} catch (IOException ex) {
								ex.printStackTrace();
							}
						}
					}
			        
			        
			        dragboard.setContent(content);
			        event.consume();
			        
			        
			        if (tileSource.getParentBox()!=null) {
			        	tileSource.exitBox();
			    		tileSource.exitBoxFX();
			    	}
		    	}
		    }
		});
		
		setOnDragDone(new EventHandler<DragEvent>() {
		    @Override
		    public void handle(DragEvent event) {
		    	if (event.getTransferMode() == TransferMode.MOVE) {
			    	if (tileSource.getParentRack()!=null) {
			    		tileSource.exitRack();
			    		tileSource.exitRackFX();
				        setStyle(NOT_FIXED_EFFECT);
			    	}
		    	} else  if (tileSource.getParentBox()!=null) {
		    		tileSource.resetPosition();
		    		tileSource.resetPositionFX();
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
		    	if (tileSource.getParentRack() != null) {
			        setStyle(HOVER_EFFECT);
		    	}
		    }
		});
		this.setOnMouseExited(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent t) {
		    	if (tileSource.isLocked() || tileSource.getParentRack()!=null) {
		    		setStyle(SHADOW_EFFECT);
		    	} else {
		    		if (isLastTilePlayed) {
		    			setStyle(LAST_TILE_PLAYED_EFFECT);		    			
		    		} else {
		    			setStyle(NOT_FIXED_EFFECT);
		    		}
		    	}
		    }
		});
	}
	
}
