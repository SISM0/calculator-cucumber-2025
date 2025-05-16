// src/test/java/calculator/TestModulo.java
package calculator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

public class TestModulo {

    private static final double DELTA = 1e-10;
    private Modulo op;
    private List<Expression> params;
    private final double v1 = 8.0, v2 = 6.0;

    @BeforeEach
    void setUp() throws IllegalConstruction {
        params = List.of(new MyNumber(v1), new MyNumber(v2));
        op = new Modulo(params, Notation.INFIX);
    }

    @Test
    void testOpMethod() {
        assertEquals(v1 % v2, ((Operation) op).op(v1, v2), DELTA);
    }

    @Test
    void testEval() throws IllegalConstruction {
        assertEquals(v1 % v2, op.eval(), DELTA);
    }

    @Test
    void testToStringDefault() {
        assertEquals("( 8.0 % 6.0 )", op.toString());
    }

    @Test
    void testToStringExplicitPrefix() {
        assertEquals("% ( 8.0, 6.0 )", op.toString(Notation.PREFIX));
    }

    @Test
    void testToStringExplicitPostfix() {
        assertEquals("( 8.0, 6.0 ) %", op.toString(Notation.POSTFIX));
    }

    @Test
    void testNoOperands() {
        assertThrows(IllegalConstruction.class,
                () -> new Modulo(List.of(), Notation.INFIX));
    }

    @Test
    void testWrongNumberOfOperands() {
        assertThrows(IllegalConstruction.class,
                () -> new Modulo(List.of(new MyNumber(1), new MyNumber(2), new MyNumber(3)), Notation.INFIX));
    }

    @Test
    void testEqualsAndHashCode() throws IllegalConstruction {
        Modulo m1 = new Modulo(params, Notation.INFIX);
        Modulo m2 = new Modulo(params, Notation.INFIX);
        assertEquals(m1, m2);
        assertEquals(m1.hashCode(), m2.hashCode());
    }
}
