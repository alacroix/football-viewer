package io.alacroix.ui;

import io.alacroix.utils.Constants;
import io.alacroix.entities.game.PlayerData;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 *
 * @author Adrien Lacroix
 * @version 0.1.0
 */
public class PlayerShape extends Circle implements DynamicShape, ScalableShape {

	/**
	 * id of the player
	 */
	private Integer id;

	public PlayerShape(PlayerData firstData) {
		// id
		id = firstData.getPlayer().getId();

		// stroke
		this.setStroke(Color.BLACK);

		if (Constants.BALL_ID == id) {
			this.setRadius(Constants.BALL_RADIUS);
		} else {
			this.setRadius(Constants.PLAYER_RADIUS);
		}

		// color
		Color color = Color.web(firstData.getPlayer().getTeam().getColor());
		this.setFill(color);
	}

	@Override
	public void moveTo(double x, double y) {
		this.setCenterX(x);
		this.setCenterY(y);
	}

	@Override
	public void updateScale(Region region, double scale_X, double scale_Y) {
		double median_scale = (scale_X + scale_Y) / 2.0;

		// if it's the ball
		if (Constants.BALL_ID == id) {
			this.setRadius(Constants.BALL_RADIUS * median_scale);
		} else {
			this.setRadius(Constants.PLAYER_RADIUS * median_scale);
		}
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
}
