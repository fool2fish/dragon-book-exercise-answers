package model;

import model.support.Action;
import model.support.Item;
import model.type.ActionType;

import java.util.*;

/**
 * @author Hiki
 * @create 2017-11-16 9:05
 */
public class ParseTable {

	/**
	 * [Action] or [Goto] while encountering a terminal.
	 */
	private Action[][] actions;
	/**
	 * Status list.
	 */
	private List<Status> statuses;
	/**
	 * Symbols including ts and nts.
	 */
	private List<String> symbols;

	public ParseTable(Action[][] actions, List<Status> statuses, List<String> symbols) {
		this.actions = actions;
		this.statuses = statuses;
		this.symbols = symbols;
	}

	public static ParseTable build(CFG cfg){
		// initialize the symbols, separating index and status list.
		List<String> symbols = cfg.terminals;
		int sep = symbols.size();
		symbols.addAll(cfg.nonTerminals);
		DFA dfa = DFA.buildDFA(Status.defaultFromCFG(cfg));
		List<Status> statuses = dfa.getStatusList();
		// define the size of the table
		int row = statuses.size();
		int col = symbols.size();
		// initialize the table
		Action[][] actions = new Action[row][col];
		// build the table line by line
		for (int i = 0; i < row; i++){
			Status curStatus = statuses.get(i);
			// shift or goto
			Map<String, Status> nextStatuses = curStatus.getNextStatuses();
			for (String symbol : nextStatuses.keySet()){
				int sid = dfa.getSid(nextStatuses.get(symbol));
				int j = symbols.indexOf(symbol);
				ActionType type = CFG.isTerminal(symbol) ? ActionType.Shift : ActionType.Goto;
				actions[i][j] = new Action(type, sid);
			}
			// reduce
			if (curStatus.isReducible()){
				for (Item item : curStatus.getItems()){
					if (curStatus.isReducible(item)){
						List<String> follows = curStatus.getFollows(item);
						int rid = item.getId();
						for (String follow : follows){
							if (!cfg.isStart(cfg.getProductionById(rid).getLeft())) {
								int j = symbols.indexOf(follow);
								actions[i][j] = new Action(ActionType.Reduce, rid);
							}
						}
					}
				}
			}
			// accept
			if (curStatus.goTo(CFG.END_SYMBOL).isAcceptable()){
				actions[i][sep-1] = new Action(ActionType.Accept, 0);
			}
		}
		return new ParseTable(actions, statuses, symbols);
	}

	public Action target(int sid, String symbol) throws Exception{
		int index = symbols.indexOf(symbol);
		if (index < 0) throw new Exception(String.valueOf(index));
		Action action = actions[sid][index];
		if (action == null) throw new Exception("action is null");
		return action;
	}

	@Override
	public String toString(){
		// the symbols
		StringBuilder builder = new StringBuilder();
		builder.append(" \t").append(String.join("\t", symbols)).append("\n");
		for (int i = 0; i < actions.length; i++){
			builder.append(i).append("\t");
			for (int j = 0; j < actions[0].length; j++){
				if (actions[i][j] == null)
					builder.append(" \t");
				else
					builder.append(actions[i][j].getType().toString() + actions[i][j].getId() + "\t");
			}
			builder.append("\n");
		}
		return builder.toString();
	}

	public List<Status> getStatuses() {
		return statuses;
	}

}
