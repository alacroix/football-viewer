package io.alacroix.entities.game;

import io.alacroix.entities.gamedesc.Player;

/**
 *
 *
 * @author Adrien Lacroix
 * @version 0.1.0
 */
public class PlayerData {
	/**
	 * Position of the player
	 */
	private double _x, _y;

	/**
	 * Distance from player to ball in meters
	 */
	private double _distToBall;

	/**
	 * If the player is possessing the ball
	 */
	private boolean _poss;

	/**
	 * Player represented by these data
	 */
	private Player _player;

	public PlayerData() {}

	public void setX(double x) {
		_x = x;
	}
	public void setY(double y) {
		_y = y;
	}
	public void setPlayer(Player p) {
		_player = p;
	}
	public void setPoss(boolean hasBall) {
		_poss = hasBall;
	}

	public double getX() {
		return _x;
	}
	public double getY() {
		return _y;
	}
	public Player getPlayer() {
		return _player;
	}
	public boolean hasBall() {
		return _poss;
	}

	@Override
	public String toString() {
		return "PlayerData<player: " + _player.getId() + ", x: " +  _x + ", y: " + _y + ">";
	}
}
