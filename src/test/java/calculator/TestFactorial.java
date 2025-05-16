// src/test/java/calculator/TestFactorial.java
package calculator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

public class TestFactorial {

    private static final double DELTA = 1e-10;
    private Factorial op;
    private List<Expression> params;
    private final double v = 4.0;

    @BeforeEach
    void setUp() throws IllegalConstruction {
        params = List.of(new MyNumber(v));
        op = new Factorial(params, Notation.POSTFIX);
    }

    @Test
    void testOpMethod() {
        // op(neutral, v) doit donner v!
        assertEquals(24.0, ((Operation) op).op(op.getNeutral(), v), DELTA);
    }

    @Test
    void testEval() throws IllegalConstruction {
        assertEquals(24.0, op.eval(), DELTA);
    }

    @Test
    void testToStringDefault() {
        assertEquals("( 4.0 ) !", op.toString());
    }

    @Test
    void testToStringExplicitPostfix() {
        assertEquals("( 4.0 ) !", op.toString(Notation.POSTFIX));
    }

    @Test
    void testNoOperands() {
        assertThrows(IllegalConstruction.class,
                () -> new Factorial(List.of(), Notation.POSTFIX));
    }

    @Test
    void testEqualsAndHashCode() throws IllegalConstruction {
        Factorial f1 = new Factorial(params, Notation.POSTFIX);
        Factorial f2 = new Factorial(params, Notation.POSTFIX);
        assertEquals(f1, f2);
        assertEquals(f1.hashCode(), f2.hashCode());
    }
}
