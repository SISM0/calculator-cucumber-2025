package calculator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestOperation {

	private Operation op1, op2;
	private List<Expression> params;

	@BeforeEach
	void setUp() throws IllegalConstruction {
		params = List.of(new MyNumber(4.0), new MyNumber(2.0));
		op1 = new Divides(params, Notation.INFIX);
		op2 = new Divides(params, Notation.INFIX);
	}

	@Test
	void testCountDepth() {
		// Divides de deux MyNumber doit avoir profondeur 2
		assertEquals(2, op1.countDepth());
	}

	@Test
	void testCountOps() {
		// 1 opération pour Divides
		assertEquals(1, op1.countOps());
	}

	@Test
	void testCountNbs() {
		// 2 littéraux MyNumber
		assertEquals(2, op1.countNbs());
	}

	@Test
	void testEquals() {
		// Deux Divides identiques doivent être égaux
		assertEquals(op1, op2);
		assertEquals(op1.hashCode(), op2.hashCode());
	}
}
