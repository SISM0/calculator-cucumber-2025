package calculator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A very simple calculator in Java
 * University of Mons - UMONS
 * Software Engineering Lab
 * Faculty of Sciences
 *
 * @author tommens (adapted)
 */
public class Main {

	/** Helper to format doubles without trailing .0 when possible */
	private static String format(double d) {
		if (d == (long) d) {
			return String.format("%d", (long) d);
		} else {
			return String.valueOf(d);
		}
	}

	/** Print expression + result */
	private static void printAndEval(Calculator c, Expression e) {
		System.out.println("Expression: " + e);
		double res = c.eval(e);
		System.out.println("Result:      " + format(res));
		System.out.println();
	}

	/** Print expression details + result */
	private static void printDetailsAndEval(Calculator c, Expression e) {
		System.out.println("Expression:        " + e);
		System.out.println("Depth / Ops / Nbs: "
				+ e.countDepth() + " / " + e.countOps() + " / " + e.countNbs());
		double res = c.eval(e);
		System.out.println("Eval result:       " + format(res));
		System.out.println();
	}

	public static void main(String[] args) {
		Calculator c = new Calculator();
		Expression e;

		try {
			// 1) Simple number
			e = new MyNumber(8);
			printAndEval(c, e);

			// 2) Plus(3,4,5)
			List<Expression> p1 = List.of(new MyNumber(3), new MyNumber(4), new MyNumber(5));
			e = new Plus(p1);
			printDetailsAndEval(c, e);

			// 3) Minus(5,3)
			List<Expression> p2 = List.of(new MyNumber(5), new MyNumber(3));
			e = new Minus(p2);
			printAndEval(c, e);

			// 4) Times( Plus(3,4,5), Minus(5,3) )
			List<Expression> p3 = List.of(
					new Plus(p1),
					new Minus(p2)
			);
			e = new Times(p3);
			printDetailsAndEval(c, e);

			// 5) Divides( Plus(3,4,5), Minus(5,3), MyNumber(5) )
			List<Expression> p4 = List.of(
					new Plus(p1),
					new Minus(p2),
					new MyNumber(5)
			);
			e = new Divides(p4);
			printAndEval(c, e);

			// 6) Square(7)
			e = new Square(List.of(new MyNumber(7)));
			printAndEval(c, e);

			// 7) Power(2,6)
			e = new Power(List.of(new MyNumber(2), new MyNumber(6)));
			printAndEval(c, e);

			// 8) Sqrt(4)
			e = new Sqrt(List.of(new MyNumber(4)));
			printAndEval(c, e);

			// 9) Modulo(18,5)
			e = new Modulo(List.of(new MyNumber(18), new MyNumber(5)));
			printAndEval(c, e);

			// 10) Factorial(4)
			e = new Factorial(List.of(new MyNumber(4)));
			printAndEval(c, e);

			// 11) Fibonacci(4)
			e = new Fibonacci(List.of(new MyNumber(4)));
			printAndEval(c, e);

		} catch (IllegalConstruction ex) {
			System.err.println("Error constructing expression: " + ex.getMessage());
		}
	}
}
