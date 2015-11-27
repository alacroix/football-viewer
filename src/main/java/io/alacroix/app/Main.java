package io.alacroix.app;

import io.alacroix.controller.Controller;
import io.alacroix.entities.game.Game;
import io.alacroix.entities.gamedesc.GameDesc;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 *
 *
 * @author Adrien Lacroix
 * @version 0.1.0
 */
public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		long start = System.nanoTime();
		GameDesc gd = new GameDesc("res/tr-ft/tr-ft-gamedesc.json");
		Game g = new Game("res/tr-ft/tr-ft.json", gd);
		System.out.println("Parsing done in " + ((System.nanoTime() - start) / 1000000000.0) + "s");

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
		Parent root = loader.load();

		primaryStage.setTitle("Football Match Viewer");
		primaryStage.setScene(new Scene(root, 600, 400));
		primaryStage.show();

		Controller c = loader.getController();
		c.init();
		c.loadGame(g.getFrames());
	}
}
