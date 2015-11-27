package io.alacroix.ui;

import javafx.scene.layout.Region;

/**
 *
 *
 * @author Adrien Lacroix
 * @version 0.1.0
 */
public interface ScalableShape {
	void updateScale(Region region, double scale_X, double scale_Y);
}
