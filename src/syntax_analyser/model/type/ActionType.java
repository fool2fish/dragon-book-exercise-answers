package model.type;

/**
 * @author Hiki
 * @create 2017-11-15 20:25
 */
public enum ActionType {

	Shift("s"),

	Reduce("r"),

	Accept("a"),

	Goto("");

	private String action;

	ActionType(String action){
		this.action = action;
	}

	@Override
	public String toString(){
		return this.action;
	}

}
