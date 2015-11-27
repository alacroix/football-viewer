package io.alacroix.entities.game;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import io.alacroix.entities.gamedesc.GameDesc;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

/**
 *
 *
 * @author Adrien Lacroix
 * @version 0.1.0
 */
public class Game {

	private GameDesc _gd;

	private JsonParser jp;
	private JsonToken current;

	private LinkedList<Frame> frames;
	private int framesSkipped = 0;

	public Game(String pathGame, GameDesc gd) {
		_gd = gd;
		frames = new LinkedList<>();

		try {
			JsonFactory factory = new MappingJsonFactory();
			jp = factory.createParser(new File(pathGame));

			// get root token
			current = jp.nextToken();
			if (current != JsonToken.START_ARRAY) {
				System.err.println("Error: root should be array: quiting.");
				return;
			}

			while ((current = jp.nextToken()) != JsonToken.END_ARRAY) {
				if (!parseFrame()) {
					System.err.println("Error: invalid frame: quiting.");
					return;
				}
			}
			if (framesSkipped != 0)
				System.err.println(framesSkipped + " frames skipped.");
		} catch (IOException e) {
			System.err.println("Invalid file");
		}
	}

	private boolean parseFrame() throws IOException {
		// get object start
		if (current != JsonToken.START_OBJECT) {
			System.err.println("Error: token should be object start: quiting.");
			return false;
		}

		Frame f = new Frame();

		while (jp.nextToken() != JsonToken.END_OBJECT) {
			String fieldName = jp.getCurrentName();
			// move from field name to field value
			current = jp.nextToken();

			switch (fieldName) {
				case "game":
					f.setGame(jp.getText());
					break;
				case "ts":
					f.setTimestamp(jp.getIntValue());
					break;
				case "_id":
					f.setId(jp.getText());
					break;
				case "data":
					parsePlayerData(f);
					break;
				default:
					//System.out.println("Unprocessed property: " + fieldName);
					jp.skipChildren();
			}
		}
		if (f.isValid()) {
			return frames.add(f);
		} else {
			framesSkipped++;
			return true;
		}
	}

	public boolean parsePlayerData(Frame f) throws IOException {
		if (current == JsonToken.START_ARRAY) {
			// For each of the records in the array
			while (jp.nextToken() != JsonToken.END_ARRAY) {
				PlayerData data = new PlayerData();
				// read the record into a tree model,
				// this moves the parsing position to the end of it
				JsonNode node = jp.readValueAsTree();
				// And now we have random access to everything in the object
				data.setPlayer(_gd.getPlayers().get(node.get("id").asInt()));
				data.setX(node.get("x").asDouble());
				data.setY(node.get("y").asDouble());
				data.setPoss(node.get("poss").asBoolean());

				if (!f.addPlayerData(data)) {
					return false;
				}
			}
		} else {
			System.err.println("Error: records should be an array: skipping.");
			jp.skipChildren();
		}

		return true;
	}

	public LinkedList<Frame> getFrames() {
		return frames;
	}
}
