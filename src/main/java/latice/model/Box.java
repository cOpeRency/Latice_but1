package latice.model;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;

public class Box extends StackPane {
	private StackPane stackPane;
	private BoxType boxType;
	private String imgURL;
	private Tile tile;
	private Position position;
	private GameBoard gameboard;
	public static String VALID_HOVER_EFFECT = "-fx-effect: dropshadow(three-pass-box, rgba(255,255,0,0.8), 15, 0.8, 0, 0);";
	public static String NOT_VALID_HOVER_EFFECT = "-fx-effect: dropshadow(three-pass-box, rgba(150,0,0,0.8), 15, 0.8, 0, 0);";
	public static String NO_EFFECT = "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0), 0, 0, 0, 0);";
	
	public Box(BoxType boxType, GameBoard gameboard,Position position) {
		this.stackPane = new StackPane();
		this.boxType = boxType;
		this.tile = Tile.NO;
		this.gameboard = gameboard;
		this.position = position;
		
		initBoxImage();
		initDragSystem();
		
		
	}

	private void initBoxImage() {
		if (this.boxType == BoxType.SUN) {
			this.imgURL = "src/main/resources/themes/pokemon/bg_sun.png";
		} else if (this.boxType == BoxType.MOON) {
			this.imgURL = "src/main/resources/themes/pokemon/bg_moon.png";
		} else {
			this.imgURL = "src/main/resources/themes/pokemon/bg_sea.png";
		}
		String urlFichier;
		try {
			File fichier = new File(imgURL);
			urlFichier = fichier.toURI().toURL().toString();
			Image img = new Image(urlFichier, 62, 62, true, true);
			ImageView imgView = new ImageView(img);
			this.getChildren().add(imgView);
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initDragSystem() {
		setOnDragEntered(new EventHandler<DragEvent>() {
		    @Override
		    public void handle(DragEvent event) {
		    	if ((gameboard.getBox(new Position(4, 4)).tile!=null ||(position.column()==4 && position.row()==4))&&tile==null) {
			    	if (event.getDragboard().hasImage()) {
				        setStyle(VALID_HOVER_EFFECT);
				        toFront();
			    	}
		    	} else {
			        setStyle(NOT_VALID_HOVER_EFFECT);
			        toFront();
		    	}
		        event.consume();
		    }
		});
		setOnDragExited(new EventHandler<DragEvent>() {
		    @Override
		    public void handle(DragEvent event) {
		        setStyle(NO_EFFECT);
		        event.consume();
		    }
		});
		
		
		setOnDragOver(new EventHandler<DragEvent>() {
		    @Override
		    public void handle(DragEvent event) {
		    	Dragboard dragboard = event.getDragboard();
		    	String[] tileData = dragboard.getString().split("_");
		    	if (checkValidity(Shape.valueOf(tileData[0]),Color.valueOf(tileData[1]))) {
		    		if (dragboard.hasImage()) {
		    			event.acceptTransferModes(TransferMode.MOVE);
		    		}
		    	}
		        event.consume();
		    }
		});
		setOnDragDropped(new EventHandler<DragEvent>() {
		    @Override
		    public void handle(DragEvent event) {
		    	Dragboard dragboard = event.getDragboard();
		    	boolean success = false;
		    	if (dragboard.hasImage()) {
		    		success = true;
		    	}
		    	if (dragboard.hasString()) {
		    		String[] tileData = dragboard.getString().split("_");
		    		tile = new Tile(Shape.valueOf(tileData[0]),Color.valueOf(tileData[1]));
					getChildren().add(tile);
		    	}
		    	
		    	event.setDropCompleted(success);
		        event.consume();
		    }
		});
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
	
	public List<Box> getAdjacentBoxes(){
		List<Box> listBoxes = new ArrayList<>();
		
		if (position.row()>0) {
			listBoxes.add(this.gameboard.getBox(new Position(position.row()-1,position.column())));
		}
		if (position.row()<8) {
			listBoxes.add(this.gameboard.getBox(new Position(position.row()+1,position.column())));
		}
		
		if (position.column()>0) {
			listBoxes.add(this.gameboard.getBox(new Position(position.row(),position.column()-1)));
		}
		
		if (position.column()<8) {
			listBoxes.add(this.gameboard.getBox(new Position(position.row(),position.column()+1)));
		}
		
		return listBoxes;
	}
	
	public boolean checkValidity(Shape shape, Color color) {
		if ((position.column()==4 && position.row()==4)&&tile==null) {
			return true;
		}
		
		if (gameboard.getBox(new Position(4, 4)).tile==null) {
			return false;
		}

		Integer numberOfNullTiles = 0;
		List<Box> listBoxes = getAdjacentBoxes();
		for (Box box : listBoxes) {
			//System.out.println(box.getTile().toString());
			//System.out.println(box.getTile().getShape()+shape.toString());
			if (box.getTile()!=null) {
				if (box.getTile().getShape()!=shape && box.getTile().getColor()!=color) {				
					return false;
				}
			} else {
				numberOfNullTiles = numberOfNullTiles + 1;
			}
		}
		
		if (numberOfNullTiles==4) {
			return false;
		}
		return true;
	}
}
