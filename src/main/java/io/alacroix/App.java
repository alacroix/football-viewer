package io.alacroix;

import io.alacroix.entities.GameDesc;

import java.io.IOException;

/**
 *
 *
 * @author Adrien Lacroix
 * @version 0.1.0
 */
public class App {
	public static void main( String[] args ) throws IOException {
		GameDesc g = new GameDesc("res/of-mu/of-mu-gamedesc.json");
	}
}
