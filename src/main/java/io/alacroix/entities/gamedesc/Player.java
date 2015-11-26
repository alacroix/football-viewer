package io.alacroix.entities.gamedesc;

/**
 *
 *
 * @author Adrien Lacroix
 * @version 0.1.0
 */
public class Player {

	private int _id;
	private Team _team;

	public Player() {}

	public int getId() {
		return _id;
	}
	public Team getTeam() {
		return _team;
	}

	public void setId(int id) {
		_id = id;
	}
	public void setTeam(Team team) {
		_team = team;
	}

	@Override
	public String toString() {
		return "Player<id: " + _id + ", team: " + _team.getId() + ">";
	}
}
