package calculator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

public class TestMinus {

	private static final double DELTA = 1e-10;
	private Minus op;
	private List<Expression> params;
	private final double v1 = 8.0, v2 = 6.0;

	@BeforeEach
	void setUp() throws IllegalConstruction {
		params = List.of(new MyNumber(v1), new MyNumber(v2));
		op = new Minus(params, Notation.INFIX);
	}

	@Test
	void testOpMethod() {
		assertEquals(v1 - v2, ((Operation) op).op(v1, v2), DELTA);
	}

	@Test
	void testEvalBinary() throws IllegalConstruction {
		assertEquals(v1 - v2, op.eval(), DELTA);
	}

	@Test
	void testEvalUnary() throws IllegalConstruction {
		Minus unary = new Minus(List.of(new MyNumber(v1)), Notation.PREFIX);
		assertEquals(-v1, unary.eval(), DELTA);
	}

	@Test
	void testToStringDefault() {
		assertEquals("( 8.0 - 6.0 )", op.toString());
	}

	@Test
	void testToStringExplicitPrefix() {
		assertEquals("- ( 8.0, 6.0 )", op.toString(Notation.PREFIX));
	}

	@Test
	void testToStringExplicitPostfix() {
		assertEquals("( 8.0, 6.0 ) -", op.toString(Notation.POSTFIX));
	}

	@Test
	void testNoOperands() {
		assertThrows(IllegalConstruction.class,
				() -> new Minus(List.of(), Notation.INFIX));
	}

	@Test
	void testEqualsAndHashCode() throws IllegalConstruction {
		Minus m1 = new Minus(params, Notation.INFIX);
		Minus m2 = new Minus(params, Notation.INFIX);
		assertEquals(m1, m2);
		assertEquals(m1.hashCode(), m2.hashCode());
	}
}
