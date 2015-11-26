package io.alacroix.app;

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
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
		primaryStage.setTitle("Football Match Viewer");
		primaryStage.setScene(new Scene(root, 500, 300));
		primaryStage.show();
	}
}
