package model.support;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hiki
 * @create 2017-11-14 0:25
 */
public class Production {

	/**
	 * The left[src] of the production.
	 */
	private String left;
	/**
	 * The right[result] of the production.
	 */
	private List<String> right;

	public Production(String left, List<String> right) {
		this.left = left;
		this.right = right;
	}

	public List<String> getSymbols(){
		List<String> symbols = new ArrayList<>(right);
		symbols.add(left);
		return symbols;
	}

	public String getLeft() {
		return left;
	}

	public List<String> getRight() {
		return right;
	}

	public void setRight(List<String> right) {
		this.right = right;
	}

	@Override
	public String toString(){
		StringBuilder res = new StringBuilder(left + "->");
		right.stream().forEach(e -> res.append(e));
		if (res.charAt(res.length()-1) == '$')
			res.deleteCharAt(res.length()-1);
		return res.toString();
	}
}
