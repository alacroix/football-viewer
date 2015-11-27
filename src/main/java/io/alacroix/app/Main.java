package io.alacroix.app;

import com.sun.javafx.perf.PerformanceTracker;
import io.alacroix.controller.Controller;
import io.alacroix.entities.game.Game;
import io.alacroix.entities.gamedesc.GameDesc;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

/**
 * @author Adrien Lacroix
 * @version 0.1.0
 */
public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
		Parent root = loader.load();

		final Controller controller = loader.getController();

		primaryStage.addEventHandler(WindowEvent.WINDOW_SHOWN, new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent window) {
				controller.init();
			}
		});
		primaryStage.setTitle("Football Match Viewer");
		primaryStage.setScene(new Scene(root, 600, 400));
		primaryStage.show();
	}
}
