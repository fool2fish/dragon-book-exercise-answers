package model;

import java.util.*;

/**
 * @author Hiki
 * @create 2017-11-14 15:57
 */
public class DFA {

	/**
	 * All the statuses and their ids.
	 */
	private List<Status> statusList;

	private DFA(List<Status> statusList) {
		this.statusList = statusList;
	}

	/**
	 * Build a DFA(graph) with a start status, using bfs to traverse.
	 * @param start the start status
	 * @return a DFA
	 */
	public static DFA buildDFA(Status start){
		List<Status> statusList = new ArrayList<>();
		// bfs
		Queue<Status> queue = new LinkedList<>();
		queue.add(start);
		while (!queue.isEmpty()){
			Status status = queue.remove();
			if (!statusList.contains(status)){
				statusList.add(status);
				queue.addAll(status.getNextStatuses().values());
			}
		}
		return new DFA(statusList);
	}

	public int getSid(Status status){
		return statusList.indexOf(status);
	}

	public List<Status> getStatusList() {
		return statusList;
	}

}
