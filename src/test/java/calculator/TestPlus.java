package calculator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

public class TestPlus {

	private static final double DELTA = 1e-10;
	private Plus op;
	private List<Expression> params;
	private final double v1 = 3.0, v2 = 4.0, v3 = 5.0;

	@BeforeEach
	void setUp() throws IllegalConstruction {
		params = List.of(new MyNumber(v1), new MyNumber(v2), new MyNumber(v3));
		op = new Plus(params, Notation.INFIX);
	}

	@Test
	void testOpMethod() {
		// Vérifie la méthode op
		assertEquals(v1 + v2, ((Operation) op).op(v1, v2), DELTA);
	}

	@Test
	void testEval() throws IllegalConstruction {
		// Vérifie l'évaluation complète
		assertEquals(v1 + v2 + v3, op.eval(), DELTA);
	}

	@Test
	void testToStringDefault() {
		// INFIX avec parenthèses et décimales
		assertEquals("( 3.0 + 4.0 + 5.0 )", op.toString());
	}

	@Test
	void testToStringExplicitPrefix() {
		// PREFIX
		assertEquals("+ ( 3.0, 4.0, 5.0 )", op.toString(Notation.PREFIX));
	}

	@Test
	void testToStringExplicitPostfix() {
		// POSTFIX
		assertEquals("( 3.0, 4.0, 5.0 ) +", op.toString(Notation.POSTFIX));
	}

	@Test
	void testNoOperands() {
		// Erreur si 0 opérandes
		assertThrows(IllegalConstruction.class,
				() -> new Plus(List.of(), Notation.INFIX));
	}

	@Test
	void testEqualsAndHashCode() throws IllegalConstruction {
		// Vérification equals & hashCode
		Plus p1 = new Plus(params, Notation.INFIX);
		Plus p2 = new Plus(params, Notation.INFIX);
		assertEquals(p1, p2);
		assertEquals(p1.hashCode(), p2.hashCode());
	}
}
