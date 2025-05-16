package calculator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

public class TestSqrt {

    private static final double DELTA = 1e-10;
    private Sqrt op;
    private List<Expression> params;
    private final double v = 16.0;

    @BeforeEach
    void setUp() throws IllegalConstruction {
        params = List.of(new MyNumber(v));
        op = new Sqrt(params, Notation.PREFIX);
    }

    @Test
    void testOpMethod() {
        // op(neutral, v) doit renvoyer sqrt(v)
        assertEquals(Math.sqrt(v), ((Operation) op).op(op.getNeutral(), v), DELTA);
    }

    @Test
    void testEval() throws IllegalConstruction {
        assertEquals(Math.sqrt(v), op.eval(), DELTA);
    }

    @Test
    void testToStringDefault() {
        // Par défaut, postfixe ou infix donnera "( 16.0 ) sqrt"
        assertEquals("( 16.0 ) sqrt", op.toString());
    }

    @Test
    void testToStringExplicitPrefix() {
        assertEquals("sqrt ( 16.0 )", op.toString(Notation.PREFIX));
    }

    @Test
    void testToStringExplicitPostfix() {
        assertEquals("( 16.0 ) sqrt", op.toString(Notation.POSTFIX));
    }

    @Test
    void testNoOperands() {
        assertThrows(IllegalConstruction.class,
                () -> new Sqrt(List.of(), Notation.PREFIX));
    }

    @Test
    void testEqualsAndHashCode() throws IllegalConstruction {
        Sqrt s1 = new Sqrt(params, Notation.PREFIX);
        Sqrt s2 = new Sqrt(params, Notation.PREFIX);
        assertEquals(s1, s2);
        assertEquals(s1.hashCode(), s2.hashCode());
    }
}
