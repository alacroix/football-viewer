package io.alacroix.entities.game;

import io.alacroix.entities.gamedesc.Player;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 *
 * @author Adrien Lacroix
 * @version 0.1.0
 */
public class Frame implements Comparable<Frame> {

	/**
	 * The frame id, randomly choosen
	 */
	private String _id;

	/**
	 * The id of the game, not incorporated in the processing
	 */
	private String _game;

	/**
	 * Timestamp as integer which is absolute or relative but must be in ms
	 */
	private Integer _ts;

	/**
	 * The id of the precedent frame (not used)
	 *
	 * @deprecated
	 */
	private String _last_id;

	/**
	 * Set of players data
	 */
	private List<PlayerData> _playersData;

	public Frame() {
		_playersData = new ArrayList<>();
	}

	public void setGame(String game) { _game = game; }
	public void setTimestamp(int ts) {
		_ts = ts;
	}
	public void setId(String id) {
		_id = id;
	}

	public boolean addPlayerData(PlayerData data) {
		return _playersData.add(data);
	}
	public List<PlayerData> getPlayersData() {
		return _playersData;
	}
	public String getId() {
		return _id;
	}

	@Override
	public String toString() {
		return "Frame<game: " + _game + ", ts: " + _ts + ", id: " + _id + ">";
	}

	@Override
	public int compareTo(Frame o) {
		return _ts.compareTo(o._ts);
	}

	public boolean isValid() {
		return _playersData.size() == 23;
	}
}
