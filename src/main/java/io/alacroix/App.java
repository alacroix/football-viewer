package io.alacroix;

import io.alacroix.entities.game.Game;
import io.alacroix.entities.gamedesc.GameDesc;

import java.io.IOException;

/**
 *
 *
 * @author Adrien Lacroix
 * @version 0.1.0
 */
public class App {
	public static void main( String[] args ) throws IOException {
		long start = System.nanoTime();
		GameDesc gd = new GameDesc("res/of-mu/of-mu-gamedesc.json");
		Game g = new Game("res/of-mu/of-mu-sample.json", gd);
		System.out.println("Parsing done in " + ((System.nanoTime() - start) / 1000000000.0) + "s");
	}
}
