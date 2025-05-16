package calculator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

public class TestFibonacci {

    private Fibonacci op;
    private List<Expression> params;
    private final double v = 5.0;

    @BeforeEach
    void setUp() throws IllegalConstruction {
        params = List.of(new MyNumber(v));
        op = new Fibonacci(params, Notation.PREFIX);
    }

    @Test
    void testOpMethod() {
        // op(neutral, v) doit donner fib(v)
        assertEquals(5.0, ((Operation) op).op(op.getNeutral(), v), 1e-10);
    }

    @Test
    void testEval() throws IllegalConstruction {
        assertEquals(5.0, op.eval(), 1e-10);
    }

    @Test
    void testToStringDefault() {
        // Préfixe par défaut
        assertEquals("fib ( 5.0 )", op.toString());
    }

    @Test
    void testToStringExplicitPrefix() {
        assertEquals("fib ( 5.0 )", op.toString(Notation.PREFIX));
    }

    @Test
    void testToStringExplicitPostfix() {
        // En notation POSTFIX, Fibonacci s'affiche comme postfixe par défaut
        assertEquals("( 5.0 ) fib", op.toString(Notation.POSTFIX));
    }

    @Test
    void testNoOperands() {
        assertThrows(IllegalConstruction.class,
                () -> new Fibonacci(List.of(), Notation.PREFIX));
    }

    @Test
    void testEqualsAndHashCode() throws IllegalConstruction {
        Fibonacci f1 = new Fibonacci(params, Notation.PREFIX);
        Fibonacci f2 = new Fibonacci(params, Notation.PREFIX);
        assertEquals(f1, f2);
        assertEquals(f1.hashCode(), f2.hashCode());
    }
}
