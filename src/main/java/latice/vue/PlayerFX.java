package latice.vue;

import java.io.Serializable;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import latice.model.Player;

public class PlayerFX implements Serializable{
	private transient SimpleIntegerProperty p1PointsProperty;
	private Player playerSource;
	private transient Button extraMoveButton;
	
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
		this.extraMoveButton = new Button("Action sup.");
		this.extraMoveButton.setPrefSize(250, 60);
		this.extraMoveButton.setDisable(true);
		this.extraMoveButton.setFont(Font.font(null, FontWeight.NORMAL, 27));
		this.extraMoveButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent event) {
		    	playerSource.setAblilityToPutATile(true);
		    	playerSource.addPoints(-2);
		    	extraMoveButton.setDisable(true);
		    	setPointProperty();
		    }
		});
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
}
