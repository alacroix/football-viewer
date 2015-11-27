package io.alacroix.controller;

import io.alacroix.ui.FootballField;
import io.alacroix.utils.Constants;
import io.alacroix.entities.game.Frame;
import io.alacroix.entities.game.PlayerData;
import io.alacroix.ui.PlayerShape;
import io.alacroix.utils.TimeUtils;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.*;

/**
 *
 * @author Adrien Lacroix
 * @version 0.1.0
 */
public class Controller {

	public Pane fieldPane;
	public Label currentTime;
	public Label totalTime;
	public Button toggleButton;

	private FootballField footballField;

	private LinkedList<Frame> _frames;
	private Map<Integer, PlayerShape> actors = new HashMap<>();

	private int firstTimestamp;
	private boolean isInitialized = false;

	private double scale_X = 1.0;
	private double scale_Y = 1.0;

	public Controller() {
		footballField = new FootballField();
	}

	public void loadGame(LinkedList<Frame> frames) {
		_frames = frames;
		firstTimestamp = _frames.getFirst().getTimestamp();
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				int totalMs = _frames.getLast().getTimestamp() - firstTimestamp;
				totalTime.setText(TimeUtils.beautifyMilliseconds(totalMs));
			}
		});
		resetGame(null);
	}

	public void toggleGame(ActionEvent actionEvent) {
		if (!isInitialized) {
			System.err.println("Load game before play.");
			return;
		}

		new Thread(new Runnable() {
			@Override
			public void run() {
				// for each frame
				for (final Frame f : _frames) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							currentTime.setText(TimeUtils.beautifyMilliseconds(f.getTimestamp() - firstTimestamp));
						}
					});

					// for each player's data
					for (final PlayerData data : f.getPlayersData()) {
						// get corresponding actor
						final PlayerShape s = actors.get(data.getPlayer().getId());

						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								s.moveTo(data.getX() * scale_X + fieldPane.getWidth() / 2.0,
										data.getY() * scale_Y + fieldPane.getHeight() / 2.0);
							}
						});

					}
					// sleep
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	public void resetGame(ActionEvent actionEvent) {
		actors.clear();
		fieldPane.getChildren().clear();

		fieldPane.getChildren().addAll(footballField.getComponents());

		// get first frame
		Frame first = _frames.getFirst();

		// for each player data, set on the field
		for (PlayerData data : first.getPlayersData()) {
			PlayerShape s = new PlayerShape(data);

			s.moveTo(data.getX() * scale_X + fieldPane.getWidth() / 2.0,
					data.getY() * scale_Y + fieldPane.getHeight() / 2.0);

			// add to the scene
			fieldPane.getChildren().add(s);

			// add to controller
			actors.put(data.getPlayer().getId(), s);
		}

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				currentTime.setText("00:00");
			}
		});

		isInitialized = true;
	}



	/**
	 * Compute scales and add listeners for eventual resizes
	 */
	public void init() {
		recomputeScales(fieldPane.getWidth(), fieldPane.getHeight());

		fieldPane.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
				recomputeScales(newSceneWidth.doubleValue(), null);
			}
		});
		fieldPane.heightProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
				recomputeScales(null, newSceneHeight.doubleValue());
			}
		});
	}

	/**
	 * Compute the new scales for the field and replace actors if needed
	 *
	 * @param width the new width or null if width hasn't change
	 * @param height the new height or null if height hasn't change
	 */
	private void recomputeScales(Double width, Double height) {
		if (width != null) {
			scale_X = fieldPane.getWidth() / (Constants.FIELD_WIDTH + Constants.FIELD_PADDING);
		}
		if (height != null) {
			scale_Y = fieldPane.getHeight() / (Constants.FIELD_HEIGHT + Constants.FIELD_PADDING);
		}

		footballField.draw(fieldPane, scale_X, scale_Y);
	}
}
