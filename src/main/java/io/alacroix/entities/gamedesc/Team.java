package io.alacroix.entities.gamedesc;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;

/**
 *
 *
 * @author Adrien Lacroix
 * @version 0.1.0
 */
public class Team {

	private int _id;
	private String _name;
	private String _color;

	public Team() {}

	public int getId() {
		return _id;
	}
	public String getName() {
		return _name;
	}
	public String getColor() {
		return _color;
	}

	public void setId(int id) {
		_id = id;
	}
	public void setName(String name) {
		_name = name;
	}
	public void setColor(String color) {
		_color = color;
	}

	@Override
	public String toString() {
		return "Team<id: " + _id + ", name: " + _name + ", color: " + _color + ">";
	}
}
