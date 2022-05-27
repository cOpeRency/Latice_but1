package latice.vue;

import java.io.File;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.Optional;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import latice.model.players.Player;
import latice.model.system.GameManager;
import latice.model.tiles.SpecialTile;
import latice.model.tiles.TypeOfSpecialTile;

public class PlayerFX implements Serializable{
	private transient SimpleIntegerProperty p1PointsProperty;
	private Player playerSource;
	private transient Button extraMoveButton;
	private transient Label playerStackSize;
	private transient Label lblPoints;
	private transient Label lblName;
	private transient Button btnExchange;
	private transient Button btnBuyTile;
	private transient Button btnValidate;
	private transient VBox vbInfos;
	private transient ImageView imgProfilePicture;
	
	public PlayerFX(Player player) {
		this.playerSource = player;
		this.p1PointsProperty = new SimpleIntegerProperty();
		this.p1PointsProperty.addListener(new ChangeListener<Number>() {
			@Override
			public void changed(
					ObservableValue<? extends Number> observable, Number oldPoints, Number newPoints) {
						System.out.println("Point changed");
					}
		});
		
		initPlayerInfo();
	}

	private void initPlayerInfo() {
		this.lblName = new Label(playerSource.getName());
		this.lblName.setTextFill(javafx.scene.paint.Color.WHITE);
		this.lblName.setFont(Font.font(null, FontWeight.NORMAL, 35));
		this.lblName.setMinHeight(50);
		
		this.imgProfilePicture = new ImageView();
		try {
			File fichier = new File("src/main/resources/profilePictures/face.png");
			this.imgProfilePicture.setImage(new Image(fichier.toURI().toURL().toString(), 150, 150, true, true));
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		this.lblPoints = new Label();
		this.lblPoints.textProperty().bind(getSimpleIntegerProperty().asString());
		this.lblPoints.setTextFill(javafx.scene.paint.Color.WHITE);
		this.lblPoints.setFont(Font.font(null, FontWeight.NORMAL, 35));
		this.lblPoints.setMinHeight(50);
		
		this.playerStackSize = new Label();
		this.playerStackSize.setTextFill(javafx.scene.paint.Color.WHITE);
		this.playerStackSize.setFont(Font.font(null, FontWeight.NORMAL, 35));
		this.playerStackSize.setMinHeight(50);
		this.playerStackSize.setText("Tiles left : 37");
		
		this.extraMoveButton = new Button("");
		this.extraMoveButton.setPrefSize(80, 80);
		this.extraMoveButton.setStyle("-fx-background-image: url('themes/pokemon/extraButton.png');"+"-fx-background-size: 80px;");
		this.extraMoveButton.setDisable(true);
		this.extraMoveButton.setFont(Font.font(null, FontWeight.NORMAL, 27));
		this.extraMoveButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent event) {
		    	playerSource.setAblilityToPutATile(true);
	    		playerSource.getRack().getRackFX().createCanPlayEffect(true);
		    	playerSource.addPoints(-2);
		    	extraMoveButton.setDisable(true);
		    	btnExchange.setDisable(true);
		    	setPointProperty();
		    }
		});
		
		this.btnExchange = new Button("");
		this.btnExchange.setPrefSize(80, 80);
		this.btnExchange.setStyle("-fx-background-image: url('themes/pokemon/exchangeButton.png');"+"-fx-background-size: 80px;");
		this.btnExchange.setFont(Font.font(null, FontWeight.NORMAL, 27));
		this.btnExchange.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent event) {
		    	if (!playerSource.isAbleToPutATile() && playerSource.getPoints()>=2) {
		    		
		    		Alert dialog = new Alert(AlertType.NONE);
					dialog.setTitle("Attention");
					dialog.setHeaderText(null);
					dialog.setContentText("Vous avez déjà joué tous vos coups sur ce tour.\nVoulez vous dépenser 2 points pour échanger votre rack ?");

					ButtonType btnTypeChoix1 = new ButtonType("Oui");
					ButtonType btnTypeChoix2 = new ButtonType("Non");
					dialog.getButtonTypes().addAll(btnTypeChoix1, btnTypeChoix2);
					
					Optional<ButtonType> choix = dialog.showAndWait();
					if(choix.get() == btnTypeChoix1) {
			    		playerSource.getRack().exchange(playerSource.getStack());
			    		playerSource.getRack().getRackFX().setRack(playerSource.getRack().getTiles());
						playerSource.addPoints(-2);
						setPointProperty();
				    	disableExchangeButton();
				    	extraMoveButton.setDisable(!(playerSource.getPoints()>=2));
				    	
					} else if (choix.get() == btnTypeChoix2) {
					}
		    		
		    		
		    		
		    	} else {
		    		playerSource.getRack().exchange(playerSource.getStack());
		    		playerSource.getRack().getRackFX().setRack(playerSource.getRack().getTiles());
		    		playerSource.setAblilityToPutATile(false);
		    		playerSource.getRack().getRackFX().createCanPlayEffect(false);
			    	disableExchangeButton();
			    	extraMoveButton.setDisable(!(playerSource.getPoints()>=2));
			    	
		    	}
		    	
		    	GameVisual.resetPlayingTileEffect();
		    	GameVisual.lockPlayingTiles();
		    	
		    }


		});
		
		this.btnBuyTile = new Button("");
		this.btnBuyTile.setPrefSize(80, 80);
		this.btnBuyTile.setStyle("-fx-background-image: url('themes/pokemon/buyButton.png');"+"-fx-background-size: 80px;");
		this.btnBuyTile.setFont(Font.font(null, FontWeight.NORMAL, 27));
		this.btnBuyTile.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent event) {
		    	if (playerSource.getRack().rackLength()<5 && playerSource.getPoints()>=1) {
		    		
		    		Alert dialog = new Alert(AlertType.NONE);
					dialog.setTitle("Choisissez");
					dialog.setHeaderText(null);
					dialog.setContentText("Pour l'achat de quelle tuile voulez-vous dépenser 6 points ?");

					ButtonType btnTypeChoix1 = new ButtonType("Vent");
					ButtonType btnTypeChoix2 = new ButtonType("Foudre");
					ButtonType btnTypeChoix3 = new ButtonType("Retour");
					
					dialog.getButtonTypes().addAll(btnTypeChoix1, btnTypeChoix2, btnTypeChoix3);
					
					Optional<ButtonType> choix = dialog.showAndWait();
					if(choix.get() == btnTypeChoix1) {
			    		playerSource.getRack().addTile(new SpecialTile(TypeOfSpecialTile.WIND));
			    		playerSource.getRack().getRackFX().setRack(playerSource.getRack().getTiles());
			    		playerSource.addPoints(-1);
			    		updateBuyTileButtonDisability();
			    		updateExtraMoveButtonDisability();
			    		setPointProperty();
				    	
					} else if (choix.get() == btnTypeChoix2) {
			    		playerSource.getRack().addTile(new SpecialTile(TypeOfSpecialTile.THUNDER));
			    		playerSource.getRack().getRackFX().setRack(playerSource.getRack().getTiles());
			    		playerSource.addPoints(-1);
			    		updateBuyTileButtonDisability();
			    		updateExtraMoveButtonDisability();
			    		setPointProperty();
					}
		    	}
		    }
		});
		
		this.btnValidate = new Button("Valider");
		this.btnValidate.setPrefSize(170, 70);
		this.btnValidate.setFont(Font.font(null, FontWeight.NORMAL, 35));

		HBox hbButtons = new HBox();
		hbButtons.getChildren().addAll(this.extraMoveButton,this.btnExchange,this.btnBuyTile);
		hbButtons.setSpacing(10);
		hbButtons.setAlignment(Pos.CENTER);
		this.vbInfos = new VBox();
		//this.vbInfos.setPrefWidth(400);
		this.vbInfos.getChildren().addAll(this.lblName,this.imgProfilePicture,this.lblPoints,this.playerStackSize,hbButtons,this.btnValidate);
		this.vbInfos.setAlignment(Pos.CENTER);
		//this.vbInfos.setPadding(new Insets(0, 20, 0, 20));
	}
	
	
	private void updateExtraMoveButtonDisability() {
		if (playerSource.getPoints()>=2) {
			extraMoveButton.setDisable(false);
		} else {
			extraMoveButton.setDisable(true);
		}
		
	}
	
	public void updateBuyTileButtonDisability() {
		if (playerSource.getPoints()>=1) {
			btnBuyTile.setDisable(false);
		} else {
			btnBuyTile.setDisable(true);
		}
		
	}
	
	public Button getBuyTileButton() {
		return this.btnBuyTile;
	}

	public Button getExtraMoveButton() {
		return this.extraMoveButton;
	}
	
	public void setExtraMoveButtonDisability(boolean activated) {
		this.extraMoveButton.setDisable(activated);
	}
	
	public void setPointProperty() {
		this.p1PointsProperty.set(this.playerSource.getPoints());
	}

	public SimpleIntegerProperty getSimpleIntegerProperty() {
		return this.p1PointsProperty;
	}

	public Label getStackSizeLabel() {
		return playerStackSize;
	}

	public Label getLblPoints() {
		return lblPoints;
	}

	public Button getBtnExchange() {
		return btnExchange;
	}
	
	public void disableExchangeButton() {
		if (playerSource.getPoints()<2)
			btnExchange.setDisable(true);
	}
	
	public void enableExchangeButton() {
		btnExchange.setDisable(false);
	}
	
	public Button getBtnValidate() {
		return btnValidate;
	}

	public VBox getVbInfos() {
		return vbInfos;
	}
	
	
	
}
