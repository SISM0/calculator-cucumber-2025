package calculator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestDivides {

	private static final double DELTA = 1e-10;
	private Divides op;
	private List<Expression> params;
	private final double v1 = 7.0, v2 = 3.0;

	@BeforeEach
	void setUp() throws IllegalConstruction {
		params = List.of(new MyNumber(v1), new MyNumber(v2));
		op = new Divides(params, Notation.INFIX);
	}

	@Test
	void testOpMethod() {
		assertEquals(v1 / v2, ((Operation) op).op(v1, v2), DELTA);
	}

	@Test
	void testEval() throws IllegalConstruction {
		assertEquals(v1 / v2, op.eval(), DELTA);
	}

	@Test
	void testToStringDefault() {
		assertEquals("( 7.0 / 3.0 )", op.toString());
	}

	@Test
	void testToStringExplicitPrefix() {
		assertEquals("/ ( 7.0, 3.0 )", op.toString(Notation.PREFIX));
	}

	@Test
	void testToStringExplicitPostfix() {
		assertEquals("( 7.0, 3.0 ) /", op.toString(Notation.POSTFIX));
	}

	@Test
	void testNoOperands() {
		assertThrows(IllegalConstruction.class,
				() -> new Divides(List.of(), Notation.INFIX));
	}

	@Test
	void testWrongNumberOfOperands() {
		assertThrows(IllegalConstruction.class,
				() -> new Divides(List.of(new MyNumber(1), new MyNumber(2), new MyNumber(3)), Notation.INFIX));
	}

	@Test
	void testEqualsAndHashCode() throws IllegalConstruction {
		Divides d1 = new Divides(params, Notation.INFIX);
		Divides d2 = new Divides(params, Notation.INFIX);
		assertEquals(d1, d2);
		assertEquals(d1.hashCode(), d2.hashCode());
	}
}
