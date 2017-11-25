package model;

import model.support.Item;
import model.support.Production;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Hiki
 * @create 2017-11-14 0:36
 */
public class Status {

	/**
	 * CFG for reference.
	 */
	private final CFG cfg;
	/**
	 * Items of this status.
	 */
	private List<Item> items;

	public Status(CFG cfg, List<Item> items) {
		this.cfg = cfg;
		this.items = items;
		this.closure();
	}

	public static Status defaultFromCFG(CFG cfg){
		List<Item> items = new ArrayList<>();
		items.add(Item.createDefaultItem(0));
		return new Status(cfg, items);
	}

	public List<String> getNextSymbols(){
		List<String> symbols = new ArrayList<>();
		for (Item item : items){
			int id = item.getId(), ptr = item.getPtr();
			if (!cfg.isValid(id, ptr))
				continue;
			String symbol = cfg.getSymbol(id, ptr);
			if (!symbols.contains(symbol))
				symbols.add(symbol);
		}
		return symbols;
	}

	public Map<String, Status> getNextStatuses(){
		Map<String, Status> nextStatuses = new LinkedHashMap<>();
		// build the map
		for (String symbol : getNextSymbols()){
			Status next = goTo(symbol);
			if (!next.isAcceptable())
				nextStatuses.put(symbol, next);
		}
		return nextStatuses;
	}

	public List<String> getFollows(Item item){
		String symbol = cfg.getProductionById(item.getId()).getLeft();
		return cfg.follow(symbol);
	}

	public CFG getCfg() {
		return cfg;
	}

	public List<Item> getItems() {
		return items;
	}

	public boolean isReducible(){
		for (Item item : items)
			if (isReducible(item))
				return true;
		return false;
	}

	public boolean isReducible(Item item){
		return cfg.isReducible(item.getId(), item.getPtr());
	}

	public boolean isAcceptable(){
		for (Item item : items){
			int id = item.getId();
			int ptr = item.getPtr();
			if (id == 0 && cfg.isReducible(id, ptr) && cfg.getSymbol(id, ptr-1) == CFG.END_SYMBOL)
				return true;
		}
		return false;
	}

	private void closure(){
		Queue<Item> queue = new LinkedList<>(items);
		while (!queue.isEmpty()){
			Item cur = queue.remove();
			int id = cur.getId(), ptr = cur.getPtr();
			if (!cfg.isValid(id, ptr))
				continue;
			String symbol = cfg.getSymbol(id, ptr);
			List<Integer> ids = cfg.getProductionIdsByLeft(symbol);
			List<Item> toAddItems = ids.stream().map(e -> Item.createDefaultItem(e)).collect(Collectors.toList());
			for (Item item : toAddItems){
				if (!items.contains(item)){
					items.add(item);
					queue.add(item);
				}
			}

		}
	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		for (Item item : items){
			Production production = cfg.getProductionById(item.getId());
			int ptr = item.getPtr();
			builder.append(production.toString()).append("\t").append(ptr).append("\n");
		}
		return builder.toString();
	}

	@Override
	public boolean equals(Object object){
		if (object instanceof Status){
			Status other = (Status) object;
			if (this.cfg.equals(other.getCfg()) && this.items.equals(other.getItems()))
				return true;
		}
		return false;
	}

	public Status goTo(String next){
		List<Item> nextItems = new ArrayList<>();
		for (Item item : items){
			int id = item.getId(), ptr = item.getPtr();
			if (!cfg.isValid(id, ptr))
				continue;
			if (cfg.getSymbol(id, ptr).equals(next)){
				Item newItem = new Item(id, ptr+1);
				if (!nextItems.contains(newItem))
					nextItems.add(newItem);
			}

		}
		return new Status(cfg, nextItems);
	}


}



