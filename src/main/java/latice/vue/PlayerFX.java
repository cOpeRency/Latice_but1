package latice.vue;

import java.io.Serializable;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import latice.model.Player;

public class PlayerFX implements Serializable{
	private transient SimpleIntegerProperty p1PointsProperty;
	private Player playerSource;
	
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
	}
	
	
	public void setPointProperty() {
		this.p1PointsProperty.set(this.playerSource.getPoints());
	}

	public SimpleIntegerProperty getSimpleIntegerProperty() {
		return this.p1PointsProperty;
	}
}
