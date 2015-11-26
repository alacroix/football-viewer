package io.alacroix.controller;

import io.alacroix.constants.Constants;
import io.alacroix.entities.game.Frame;
import io.alacroix.entities.game.PlayerData;
import io.alacroix.ui.PlayerShape;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import sun.java2d.loops.GraphicsPrimitive;

import java.util.*;

/**
 *
 *
 * @author Adrien Lacroix
 * @version 0.1.0
 */
public class Controller {

	public Pane fieldPane;

	private LinkedList<Frame> _frames;
	private Map<Integer, PlayerShape> actors;

	private boolean isInitialized = false;

	public Controller() {
		actors = new HashMap<>();
	}

	public void loadGame(LinkedList<Frame> frames) {
		_frames = frames;
		resetGame(null);
	}

	public void playGame(ActionEvent actionEvent) {
		if (!isInitialized) {
			System.err.println("Load game before play.");
			return;
		}

		new Thread(new Runnable() {
			@Override
			public void run() {
				// for each frame
				for(Frame f : _frames) {
					// for each player's data
					for (final PlayerData data : f.getPlayersData()) {
						// get corresponding actor
						final PlayerShape s = actors.get(data.getPlayer().getId());

						Platform.runLater(new Runnable() {
							                  @Override
							                  public void run() {
								                  s.moveTo(data.getX() * Constants.SCALE + fieldPane.getWidth() / 2.0,
										                  data.getY() * Constants.SCALE + fieldPane.getHeight() / 2.0);
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

		// get first frame
		Frame first = _frames.getFirst();

		// for each player data, set on the field
		for(PlayerData data : first.getPlayersData()) {
			PlayerShape s = new PlayerShape(data);

			s.moveTo(data.getX() * Constants.SCALE + fieldPane.getWidth() / 2.0,
					data.getY() * Constants.SCALE + fieldPane.getHeight() / 2.0);

			// add to the scene
			fieldPane.getChildren().add(s);

			// add to controller
			actors.put(data.getPlayer().getId(), s);
		}
		isInitialized = true;
	}
}
