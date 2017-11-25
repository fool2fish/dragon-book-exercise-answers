package model;

import model.support.Production;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 *
 * Data structure of context-free grammar.
 *
 * @author Hiki
 * @create 2017-11-14 0:28
 */
public class CFG {

	/**
	 * Defined symbols.
	 */
	public final static String END_SYMBOL = "$";
	public final static String EPSILON = "ε";
	/**
	 * Symbols.
	 */
	public final List<String> terminals;
	public final List<String> nonTerminals;
	/**
	 * Context-free grammar.
	 */
	private final List<Production> productions;


	public CFG(List<Production> inputs) {
		augment(inputs);
		addTag(inputs);
		this.productions = inputs;
		List<String> ts = new ArrayList<>();
		List<String> nts = new ArrayList<>();
		for (Production production : productions){
			for (String symbol : production.getSymbols()) {
				if (isTerminal(symbol) && !symbol.equals(END_SYMBOL) && !ts.contains(symbol))
					ts.add(symbol);
				else
					if (!nts.contains(symbol))
						nts.add(symbol);
			}
		}
		ts.add(END_SYMBOL);
		terminals = ts;
		nonTerminals = nts;
	}

	public static CFG loadFromFile(String fileName){
		// Load the file to a reader.
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream is =  classLoader.getResourceAsStream(fileName);
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		// Read the stream into a list.
		List<Production> productions = new ArrayList<>();
		String line = null;
		try {
			while ((line = br.readLine()) != null){
				// Split the line to match a production.
				String left = line.split("->")[0];
				List<String> right = Arrays.asList(line.split("->")[1].split(" "));
				productions.add(new Production(left, right));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new CFG(productions);
	}

	/*
	 * =================================================================
	 * Getting methods.
	 * =================================================================
	 */

	public List<Production> getProductions() {
		return productions;
	}

	public List<Integer> getProductionIdsByLeft(String left){
		List<Integer> ids = new ArrayList<>();
		for (int i = 0; i < productions.size(); i++)
			if (productions.get(i).getLeft().equals(left))
				ids.add(i);
		return ids;
	}

	public Production getProductionById(int id){
		return productions.get(id);
	}

	public String getSymbol(int id, int ptr){
		if (id >= productions.size() || ptr >= productions.get(id).getRight().size())
			throw new IndexOutOfBoundsException();
		return productions.get(id).getRight().get(ptr);
	}

	/*
	 * =================================================================
	 * Judging methods.
	 * =================================================================
	 */

	public boolean isValid(int id, int ptr){
		return id < productions.size() && ptr < productions.get(id).getRight().size();
	}

	public boolean isReducible(int id, int ptr){
		return id < productions.size() && ptr == productions.get(id).getRight().size();
	}

	public boolean isStart(String symbol){
		if (productions.isEmpty())
			return false;
		return symbol.equals(getProductionById(0).getLeft());
	}

	public static boolean isTerminal(String symbol){
		if (isEpsilon(symbol))
			return true;
		for (int i = 0; i < symbol.length(); i++){
			if (Character.isUpperCase(symbol.charAt(i)))
				return false;
		}
		return true;
	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		productions.forEach(e -> builder.append(e.toString() + "\n"));
		return builder.toString();
	}

	@Override
	public boolean equals(Object object){
		if (object instanceof CFG){
			CFG other = (CFG) object;
			if (this.productions.equals(other.getProductions()))
				return true;
		}
		return false;
	}

	public List<String> follow(String symbol){
		List<String> res = new ArrayList<>();
		follow(symbol, res);
		return res;
	}

	/*
	 * =================================================================
	 * The supporting methods are below.
	 * =================================================================
	 */

	private List<String> first(String symbol){
		List<String> res = new ArrayList<>();
		first(symbol, res);
		return res;
	}

	private boolean first(String symbol, List<String> ctn){
		boolean epsilonFlag = false;
		if (isTerminal(symbol)){
			if (isEpsilon(symbol))
				return true;
			else
				if (!ctn.contains(symbol))
					ctn.add(symbol);
		}

		List<Integer> ids = getProductionIdsByLeft(symbol);
		for (int id : ids){
			List<String> symbols = getProductionById(id).getRight();
			for (String sb : symbols){
				if (!first(sb, ctn)) break;
				else epsilonFlag = true;
			}
		}
		return epsilonFlag;
	}

	private void follow(String symbol, List<String> ctn){
		if (isStart(symbol) && !ctn.contains(END_SYMBOL))
				ctn.add(END_SYMBOL);
		// traverse all the productions
		for (Production production : productions){
			// find out the index of the symbol
			List<String> symbols = production.getRight();
			int n = symbols.size();
			for (int i = 0; i < n; i++){
				if (symbols.get(i).equals(symbol)){
					if (i == n-1){
						if (!production.getLeft().equals(symbol))
							follow(production.getLeft(), ctn);
					}
					else{
						List<String> firstSymbols = first(symbols.get(i+1));
						if (firstSymbols.contains(EPSILON)){
							firstSymbols.remove(EPSILON);
							if (!production.getLeft().equals(symbol))
								follow(production.getLeft(), ctn);
						}
						for (String firstSymbol : firstSymbols)
							if (!ctn.contains(firstSymbol))
								ctn.add(firstSymbol);
					}
				}

			}
		}
	}

	private static boolean isEpsilon(String symbol){
		return symbol.equals(EPSILON);
	}

	private static void augment(List<Production> inputs) {
		if (inputs.isEmpty()){
			System.err.println("The file is empty.");
			System.exit(-1);
		}
		String left = inputs.get(0).getLeft();
		List<String> right = new ArrayList<>();
		right.add(left); right.add(END_SYMBOL); left += "`";
		Production augmentedProduction = new Production(left, right);
		inputs.add(0, augmentedProduction);
	}

	private static void addTag(List<Production> inputs){
		for (Production production : inputs){
			// deep copy
			List<String> right = new ArrayList<>(production.getRight());
//			right.add("$");
			production.setRight(right);
		}
//		inputs.stream().forEach(e->e.getRight().add("$"));
	}

}
