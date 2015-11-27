package io.alacroix.ui;

import com.sun.tools.internal.jxc.ap.Const;
import io.alacroix.utils.Constants;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.HashSet;
import java.util.Set;

/**
 * Shapes representation of a football field
 *
 * @author Adrien Lacroix
 * @version 0.1.0
 */
public class FootballField {

	private Rectangle fieldBorder;
	private Line halfwayLine;
	private Circle centralCircle;

	private Set<Circle> spots;
	private Circle centralSpot;
	private Circle penaltySpotLeft, penaltySpotRight;

	private Set<Rectangle> penaltyAreas;
	private Rectangle penaltyAreaLeft, penaltyAreaRight;

	private Set<Rectangle> keeperAreas;
	private Rectangle keeperAreaLeft, keeperAreaRight;

	private Set<Shape> statics;

	public FootballField() {
		statics = new HashSet<>();
		spots = new HashSet<>();
		penaltyAreas = new HashSet<>();
		keeperAreas = new HashSet<>();

		fieldBorder = new Rectangle();
		halfwayLine = new Line();
		centralCircle = new Circle();
		centralSpot = new Circle();
		penaltySpotLeft = new Circle();
		penaltySpotRight = new Circle();
		penaltyAreaLeft = new Rectangle();
		penaltyAreaRight = new Rectangle();
		keeperAreaLeft = new Rectangle();
		keeperAreaRight = new Rectangle();

		spots.add(centralSpot);
		spots.add(penaltySpotLeft);
		spots.add(penaltySpotRight);

		penaltyAreas.add(penaltyAreaLeft);
		penaltyAreas.add(penaltyAreaRight);

		keeperAreas.add(keeperAreaLeft);
		keeperAreas.add(keeperAreaRight);

		statics.add(fieldBorder);
		statics.add(halfwayLine);
		statics.add(centralCircle);
		statics.addAll(penaltyAreas);
		statics.addAll(keeperAreas);
		statics.addAll(spots);

		for (Shape s : statics) {
			s.setStroke(Color.WHITE);
			s.setFill(Color.TRANSPARENT);
		}
	}

	public void draw(Region region, double scale_X, double scale_Y) {
		double median_scale = (scale_X + scale_Y) / 2.0;

		for (Shape s : statics) {
			s.setStrokeWidth(median_scale);
		}
		for (Circle c : spots) {
			c.setRadius(Constants.SPOT_RADIUS * median_scale);
			c.setStrokeWidth(median_scale);
			c.setCenterY(region.getHeight() / 2.0);
		}

		// borders
		fieldBorder.setWidth(Constants.FIELD_WIDTH * scale_X);
		fieldBorder.setHeight(Constants.FIELD_HEIGHT * scale_Y);
		fieldBorder.setX(Constants.FIELD_PADDING / 2 * scale_X);
		fieldBorder.setY(Constants.FIELD_PADDING / 2 * scale_Y);

		// medium line
		double xMediumLine = region.getWidth() / 2.0;
		halfwayLine.setStartX(xMediumLine);
		halfwayLine.setEndX(xMediumLine);
		halfwayLine.setStartY(Constants.FIELD_PADDING / 2 * scale_Y);
		halfwayLine.setEndY(region.getHeight() - Constants.FIELD_PADDING / 2 * scale_Y);

		// central circle
		centralCircle.setRadius(Constants.CENTRAL_RADIUS * median_scale);
		centralCircle.setCenterX(region.getWidth() / 2.0);
		centralCircle.setCenterY(region.getHeight() / 2.0);

		// central spot
		centralSpot.setCenterX(region.getWidth() / 2.0);

		// penalty spot left
		penaltySpotLeft.setCenterX((Constants.FIELD_PADDING / 2.0 + Constants.PENALTY_DISTANCE) * scale_X);
		// penalty spot right
		penaltySpotRight.setCenterX(region.getWidth() - (Constants.FIELD_PADDING / 2.0 + Constants.PENALTY_DISTANCE) * scale_X);

		// penalty areas
		for (Rectangle area : penaltyAreas) {
			area.setWidth(Constants.PENALTY_WIDTH * scale_X);
			area.setHeight(Constants.PENALTY_HEIGHT * scale_Y);
			area.setY(region.getHeight() / 2.0 - area.getHeight() / 2.0);
		}
		// left area
		penaltyAreaLeft.setX(Constants.FIELD_PADDING / 2.0 * scale_X);
		// right area
		penaltyAreaRight.setX(region.getWidth() - Constants.FIELD_PADDING / 2.0 * scale_X - penaltyAreaRight.getWidth());

		// keeper areas
		for (Rectangle area : keeperAreas) {
			area.setWidth(Constants.KEEPER_WIDTH * scale_X);
			area.setHeight(Constants.KEEPER_HEIGHT * scale_Y);
			area.setY(region.getHeight() / 2.0 - area.getHeight() / 2.0);
		}
		// left area
		keeperAreaLeft.setX(Constants.FIELD_PADDING / 2.0 * scale_X);
		// right area
		keeperAreaRight.setX(region.getWidth() - Constants.FIELD_PADDING / 2.0 * scale_X - keeperAreaRight.getWidth());
	}

	public Set<Shape> getComponents() {
		return statics;
	}
}
