package calculator;

import java.util.List;
import visitor.Visitor;

/**
 * Classe abstraite représentant une opération
 * (binaire, unaire ou variadique) sur des Expression.
 */
public abstract class Operation implements Expression {
	protected final List<Expression> args;
	protected String symbol;
	protected double neutral;
	protected Notation notation;

	public Operation(List<Expression> args, Notation notation) throws IllegalConstruction {
		if (args == null || args.isEmpty()) {
			throw new IllegalConstruction("Operation requires at least one operand");
		}
		if (notation == null) {
			throw new IllegalConstruction("Notation cannot be null");
		}
		this.args = List.copyOf(args);
		this.notation = notation;
	}

	public Operation(List<Expression> args) throws IllegalConstruction {
		this(args, Notation.INFIX);
	}

	@Override
	public double eval() throws IllegalConstruction {
		if (args.size() == 1) {
			double r = args.get(0).eval();
			return op(neutral, r);
		}
		double result = args.get(0).eval();
		for (int i = 1; i < args.size(); i++) {
			result = op(result, args.get(i).eval());
		}
		return result;
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

	@Override
	public String toString() {
		return toString(this.notation);
	}

	public String toString(Notation newNotation) {
		StringBuilder sb = new StringBuilder();
		List<String> vals = new java.util.ArrayList<>();
		for (Expression e : args) {
			try {
				vals.add(Double.toString(e.eval()));
			} catch (IllegalConstruction ex) {
				vals.add(e.toString());
			}
		}
		switch (newNotation) {
			case PREFIX -> {
				sb.append(symbol).append(" ( ");
				sb.append(String.join(", ", vals));
				sb.append(" )");
			}
			case INFIX -> {
				sb.append("( ");
				sb.append(String.join(" " + symbol + " ", vals));
				sb.append(" )");
			}
			case POSTFIX -> {
				sb.append("( ");
				sb.append(String.join(", ", vals));
				sb.append(" ) ").append(symbol);
			}
		}
		return sb.toString();
	}

	@Override
	public int countDepth() {
		int max = 0;
		for (Expression e : args) {
			int d = e.countDepth();
			if (d > max) {
				max = d;
			}
		}
		return max + 1;
	}

	@Override
	public int countOps() {
		int cnt = 1;
		for (Expression e : args) {
			cnt += e.countOps();
		}
		return cnt;
	}

	@Override
	public int countNbs() {
		int cnt = 0;
		for (Expression e : args) {
			cnt += e.countNbs();
		}
		return cnt;
	}

	public List<Expression> getArgs() {
		return args;
	}

	public double getNeutral() {
		return neutral;
	}

	public abstract double op(double l, double r);

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Operation)) return false;
		Operation that = (Operation) o;
		return getClass().equals(that.getClass())
				&& symbol.equals(that.symbol)
				&& args.equals(that.args);
	}

	@Override
	public int hashCode() {
		int res = symbol.hashCode();
		res = 31 * res + args.hashCode();
		return res;
	}


}
