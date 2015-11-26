package io.alacroix.ui;

import io.alacroix.constants.Constants;
import io.alacroix.entities.game.PlayerData;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 *
 * @author Adrien Lacroix
 * @version 0.1.0
 */
public class PlayerShape extends Circle implements DynamicShape {

	private Integer id;

	public PlayerShape(PlayerData firstData) {
		// stroke
		this.setStroke(Color.BLACK);
		// radius
		if (Constants.BALL_ID == firstData.getPlayer().getId()) {
			this.setRadius(Constants.BALL_RADIUS);
		} else {
			this.setRadius(Constants.PLAYER_RADIUS);
		}
		// color
		Color color = Color.web(firstData.getPlayer().getTeam().getColor());
		this.setFill(color);
		// id
		id = firstData.getPlayer().getId();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}

		PlayerShape o = (PlayerShape) obj;
		return id.equals(o.id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public void moveTo(double x, double y) {
		this.setCenterX(x);
		this.setCenterY(y);
	}
}
