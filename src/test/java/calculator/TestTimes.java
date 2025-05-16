package calculator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

public class TestTimes {

	private static final double DELTA = 1e-10;
	private Times op;
	private List<Expression> params;
	private final double v1 = 3.0, v2 = 4.0, v3 = 5.0;

	@BeforeEach
	void setUp() throws IllegalConstruction {
		params = List.of(new MyNumber(v1), new MyNumber(v2));
		op = new Times(params, Notation.INFIX);
	}

	@Test
	void testOpMethod() {
		assertEquals(v1 * v2, ((Operation) op).op(v1, v2), DELTA);
	}

	@Test
	void testEvalBinary() throws IllegalConstruction {
		assertEquals(v1 * v2, op.eval(), DELTA);
	}

	@Test
	void testEvalVariadic() throws IllegalConstruction {
		Times variadic = new Times(
				List.of(new MyNumber(v1), new MyNumber(v2), new MyNumber(v3)),
				Notation.POSTFIX
		);
		assertEquals(v1 * v2 * v3, variadic.eval(), DELTA);
	}

	@Test
	void testToStringDefault() {
		assertEquals("( 3.0 * 4.0 )", op.toString());
	}

	@Test
	void testToStringExplicitPrefix() {
		assertEquals("* ( 3.0, 4.0 )", op.toString(Notation.PREFIX));
	}

	@Test
	void testToStringExplicitPostfix() {
		assertEquals("( 3.0, 4.0 ) *", op.toString(Notation.POSTFIX));
	}

	@Test
	void testNoOperands() {
		assertThrows(IllegalConstruction.class,
				() -> new Times(List.of(), Notation.INFIX));
	}

	@Test
	void testEqualsAndHashCode() throws IllegalConstruction {
		Times t1 = new Times(params, Notation.INFIX);
		Times t2 = new Times(params, Notation.INFIX);
		assertEquals(t1, t2);
		assertEquals(t1.hashCode(), t2.hashCode());
	}
}
