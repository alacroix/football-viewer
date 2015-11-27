package io.alacroix.app;

import io.alacroix.entities.game.Game;
import io.alacroix.entities.gamedesc.GameDesc;
import javafx.concurrent.Task;

/**
 * @author Adrien Lacroix
 * @version 0.1.0
 */
public class TaskParser extends Task<Game> {
	@Override
	protected Game call() throws Exception {
		long start = System.nanoTime();
		GameDesc gd = new GameDesc("res/tr-ft/tr-ft-gamedesc.json");
		Game g = new Game("res/tr-ft/tr-ft.json", gd);
		System.out.println("Parsing done in " + ((System.nanoTime() - start) / 1000000000.0) + "s");
		return g;
	}
}
