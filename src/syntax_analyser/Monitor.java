import model.CFG;
import model.ParseTable;
import model.Status;
import model.support.Action;
import model.support.Production;
import model.type.ActionType;

import java.util.Queue;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * @author Hiki
 * @create 2017-11-16 19:35
 */
public class Monitor {

	/**
	 * The input sequence.
	 */
	private Queue<String> inputs;
	/**
	 * Parse table, refer to [ParseTable]
	 */
	private ParseTable table;
	/**
	 * The stack storing the status ids.
	 */
	private Stack<Integer> statusIds;
	/**
	 * Context-free grammar.
	 */
	private CFG cfg;

	public Monitor(String cfgFile, String inputFile) {
		cfg = CFG.loadFromFile(cfgFile);
		inputs = InputLoader.loadInput(inputFile);
		table = ParseTable.build(cfg);
		statusIds = new Stack<>();
	}

	/**
	 * Analyze the input sequence with the parse table and print every screenshot of the process.
	 */
	public void analyze(){
		// Init the status stack.
		statusIds.push(0);
		// Init the log.
		System.out.format("%2s%16s%12s%20s\n", "", "stack", "input", "action");
		// Init the counter.
		int counter = 0;
		// Move upon the input sequence and focus on each symbol.
		while (!inputs.isEmpty()){
			// Fetch the current action.
			int curSid = statusIds.peek();
			String curSymbol = inputs.peek();
			Action action = null;
			try {
				action = table.target(curSid, curSymbol);
			} catch (Exception e){
				System.out.println("error while processing the symbol [" + curSymbol + "] at state [" + curSid + "]");
				System.exit(-1);
			}
			// Append the log.
			System.out.format("%2d%16s%12s", counter, statusIds.stream().map(e->String.valueOf(e)).collect(Collectors.toList()).toString(), String.join("", inputs));
			// judge the action and log.
			ActionType type = action.getType();
			switch (type){
				case Shift:
					statusIds.push(action.getId());
					System.out.format("%20s\n", "shift");
					inputs.remove();
					break;
				case Reduce:
					Production production = cfg.getProductionById(action.getId());
					int count = production.getRight().size();
					for (int i = 0; i < count; i++)
						statusIds.pop();
					int t = statusIds.peek();
					Status next = table.getStatuses().get(t).goTo(production.getLeft());
					statusIds.push(table.getStatuses().indexOf(next));
					System.out.format("%20s\n", "reduce: " + production.toString());
					break;
				case Accept:
					System.out.format("%20s", "accept");
					return;
				default:
					System.err.println("Error!");
					return;
			}
			counter++;
		}

	}


}
