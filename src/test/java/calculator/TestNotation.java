package calculator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestNotation {

	@Test
	void testPrefixNotation() throws IllegalConstruction {
		Expression plus = new Plus(List.of(new MyNumber(8.0), new MyNumber(6.0)), Notation.PREFIX);
		assertEquals("+ ( 8.0, 6.0 )", plus.toString());

		Expression times = new Times(
				List.of(new MyNumber(3.0), new MyNumber(4.0), new MyNumber(5.0)),
				Notation.PREFIX
		);
		assertEquals("* ( 3.0, 4.0, 5.0 )", times.toString());
	}

	@Test
	void testInfixNotation() throws IllegalConstruction {
		Expression minus = new Minus(List.of(new MyNumber(8.0), new MyNumber(6.0)), Notation.INFIX);
		assertEquals("( 8.0 - 6.0 )", minus.toString());

		Expression divides = new Divides(List.of(new MyNumber(7.0), new MyNumber(3.0)), Notation.INFIX);
		assertEquals("( 7.0 / 3.0 )", divides.toString());
	}

	@Test
	void testPostfixNotation() throws IllegalConstruction {
		Expression fact = new Factorial(List.of(new MyNumber(4.0)), Notation.POSTFIX);
		assertEquals("( 4.0 ) !", fact.toString());

		Expression sqrt = new Sqrt(List.of(new MyNumber(16.0)), Notation.POSTFIX);
		assertEquals("( 16.0 ) sqrt", sqrt.toString());
	}

	@Test
	void testMixedVariadic() throws IllegalConstruction {
		Operation complex = new Plus(
				List.of(new MyNumber(2.0), new MyNumber(3.0), new MyNumber(4.0)),
				Notation.INFIX
		);
		assertEquals("( 2.0 + 3.0 + 4.0 )", complex.toString());
		assertEquals("+ ( 2.0, 3.0, 4.0 )", complex.toString(Notation.PREFIX));
		assertEquals("( 2.0, 3.0, 4.0 ) +", complex.toString(Notation.POSTFIX));
	}
}
