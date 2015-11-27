package io.alacroix.controller;

import io.alacroix.app.TaskParser;
import io.alacroix.app.TaskPlayer;
import io.alacroix.entities.game.Game;
import io.alacroix.entities.gamedesc.GameDesc;
import io.alacroix.ui.DynamicShape;
import io.alacroix.ui.FootballField;
import io.alacroix.ui.ScalableShape;
import io.alacroix.utils.Constants;
import io.alacroix.entities.game.Frame;
import io.alacroix.entities.game.PlayerData;
import io.alacroix.ui.PlayerShape;
import io.alacroix.utils.TimeUtils;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;

import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * @author Adrien Lacroix
 * @version 0.1.0
 */
public class Controller {

	public Pane fieldPane;
	public Label currentTime;
	public Label totalTime;
	public Button toggleButton;
	public Slider framesSlider;

	private LinkedList<Frame> _frames;

	private FootballField footballField;
	private Map<Integer, PlayerShape> actors;

	private double scale_X = 1.0, scale_Y = 1.0;

	private boolean isInitialized = false;
	private boolean isPlaying = false;

	private TaskPlayer framesPlayer;
	private TaskParser taskParser;

	private Thread player;
	private Thread threadParser;

	private Thread uiThread;

	public Controller() {
		uiThread = Thread.currentThread();
		actors = new HashMap<>();
		footballField = new FootballField();

		parseFile();
		loadGame();
	}

	private void parseFile() {
		taskParser = new TaskParser();
		threadParser = new Thread(taskParser);
		threadParser.start();
	}

	public void init() {
		// add football field
		fieldPane.getChildren().addAll(footballField.getComponents());

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

		recomputeScales(fieldPane.getWidth(), fieldPane.getHeight());
	}

	public void loadGame() {
		final Controller me = this;
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					_frames = taskParser.get().getFrames();
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}

				framesPlayer = new TaskPlayer(me, _frames);
				player = new Thread(framesPlayer);

				// get first frame
				Frame first = _frames.getFirst();

				// for each player data, set on the field
				for (PlayerData data : first.getPlayersData()) {
					final PlayerShape s = new PlayerShape(data);

					s.moveTo(data.getX() * scale_X + fieldPane.getWidth() / 2.0,
							data.getY() * scale_Y + fieldPane.getHeight() / 2.0);

					// add to the scene
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							fieldPane.getChildren().add(s);
						}
					});

					// add to controller
					actors.put(data.getPlayer().getId(), s);
				}

				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						currentTime.setText("00:00");
						updatePlayersScale();
					}
				});

				framesPlayer.addActors(actors);

				isInitialized = true;
			}
		}).start();
	}

	public void toggleGame() {
		if (!isInitialized) {
			System.err.println("Load game before play.");
			return;
		}

		// want to pause
		if (isPlaying) {
			toggleButton.setText("▶");
			if (framesPlayer.isRunning())
				framesPlayer.cancel();
		} else {
			toggleButton.setText("❚❚");
			player.start();
		}
		isPlaying = !isPlaying;
	}

	public void moveDynamicShape(final DynamicShape shape, final double newX, final double newY) {
		shape.moveTo(newX * scale_X + fieldPane.getWidth() / 2.0,
				newY * scale_Y + fieldPane.getHeight() / 2.0);
	}

	private void updatePlayersScale() {
		// if no actors, do nothing
		if (actors.size() == 0) {
			return;
		}
		for (ScalableShape shape : actors.values()) {
			shape.updateScale(fieldPane, scale_X, scale_Y);
		}
		for (PlayerData data : framesPlayer.getCurrentFrame().getPlayersData()) {
			DynamicShape shape = actors.get(data.getPlayer().getId());
			moveDynamicShape(shape, data.getX(), data.getY());
		}
	}

	/**
	 * Compute the new scales for the field and replace actors if needed
	 *
	 * @param width  the new width or null if width hasn't change
	 * @param height the new height or null if height hasn't change
	 */
	private void recomputeScales(Double width, Double height) {
		if (width != null) {
			scale_X = fieldPane.getWidth() / (Constants.FIELD_WIDTH + Constants.FIELD_PADDING);
		}
		if (height != null) {
			scale_Y = fieldPane.getHeight() / (Constants.FIELD_HEIGHT + Constants.FIELD_PADDING);
		}

		footballField.updateScale(fieldPane, scale_X, scale_Y);
/*
		Platform.runLater(new Runnable() {
			@Override
			public void run() {

				updatePlayersScale();
			}
		});
		*/
	}

}
