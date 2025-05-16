package calculator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

public class TestPower {

    private static final double DELTA = 1e-10;
    private Power op;
    private List<Expression> params;
    private final double base = 2.0, exp = 5.0;

    @BeforeEach
    void setUp() throws IllegalConstruction {
        params = List.of(new MyNumber(base), new MyNumber(exp));
        op = new Power(params, Notation.INFIX);
    }

    @Test
    void testOpMethod() {
        assertEquals(Math.pow(base, exp), ((Operation) op).op(base, exp), DELTA);
    }

    @Test
    void testEval() throws IllegalConstruction {
        assertEquals(Math.pow(base, exp), op.eval(), DELTA);
    }

    @Test
    void testToStringDefault() {
        assertEquals("( 2.0 ^ 5.0 )", op.toString());
    }

    @Test
    void testToStringExplicitPrefix() {
        assertEquals("^ ( 2.0, 5.0 )", op.toString(Notation.PREFIX));
    }

    @Test
    void testToStringExplicitPostfix() {
        assertEquals("( 2.0, 5.0 ) ^", op.toString(Notation.POSTFIX));
    }

    @Test
    void testNoOperands() {
        assertThrows(IllegalConstruction.class,
                () -> new Power(List.of(), Notation.INFIX));
    }

    @Test
    void testWrongNumberOfOperands() {
        assertThrows(IllegalConstruction.class,
                () -> new Power(List.of(new MyNumber(1), new MyNumber(2), new MyNumber(3)), Notation.INFIX));
    }

    @Test
    void testEqualsAndHashCode() throws IllegalConstruction {
        Power p1 = new Power(params, Notation.INFIX);
        Power p2 = new Power(params, Notation.INFIX);
        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
    }
}
