package io.alacroix.app;

import io.alacroix.controller.Controller;
import io.alacroix.entities.game.Frame;
import io.alacroix.entities.game.PlayerData;
import io.alacroix.ui.PlayerShape;
import javafx.application.Platform;
import javafx.concurrent.Task;

import java.util.LinkedList;
import java.util.Map;

/**
 * @author Adrien Lacroix
 * @version 0.1.0
 */
public class TaskPlayer extends Task<Void> {

	private Controller _controller;

	private LinkedList<Frame> _frames;
	private Frame _currentFrame;

	private int initTimestamp;

	private Map<Integer, PlayerShape> _actors;

	public TaskPlayer(Controller controller, LinkedList<Frame> frames) {
		_controller = controller;
		_frames = frames;
		_currentFrame = _frames.getFirst();
		initTimestamp = _currentFrame.getTimestamp();

	}

	public void addActors(Map<Integer, PlayerShape> actors) {
		_actors = actors;
	}

	public Frame getCurrentFrame() {
		return _currentFrame;
	}

	@Override
	protected Void call() throws Exception {
		// if no actors
		if (_actors.size() == 0) {
			System.err.println("No actors for the scene");
			return null;
		}

		System.out.println(_frames.size());

		for (Frame frame : _frames) {
			// if cancelled during play
			if (isCancelled())
				break;

			_currentFrame = frame;

			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					for (PlayerData data : _currentFrame.getPlayersData()) {
						// get corresponding actor
						final PlayerShape shape = _actors.get(data.getPlayer().getId());
						// move the shape
						_controller.moveDynamicShape(shape, data.getX(), data.getY());
					}
				}
			});

			Thread.sleep(10);
		}

		return null;
	}
}
