package model.support;

import model.type.ActionType;

/**
 * @author Hiki
 * @create 2017-11-16 9:09
 */
public class Action {

	/**
	 * Action model.type, refer to enum [ActionType].
	 */
	private ActionType type;
	/**
	 * Status id or production id.
	 */
	private int id;

	public Action(ActionType type, int id) {
		this.type = type;
		this.id = id;
	}

	public ActionType getType() {
		return type;
	}

	public int getId() {
		return id;
	}
}
