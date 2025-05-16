package calculator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

public class TestSquare {

    private static final double DELTA = 1e-10;
    private Square op;
    private List<Expression> params;
    private final double v = 7.0;

    @BeforeEach
    void setUp() throws IllegalConstruction {
        params = List.of(new MyNumber(v));
        op = new Square(params, Notation.PREFIX);
    }

    @Test
    void testOpMethod() {
        assertEquals(v * v, ((Operation) op).op(1.0, v), DELTA);
    }

    @Test
    void testEval() throws IllegalConstruction {
        assertEquals(v * v, op.eval(), DELTA);
    }

    @Test
    void testToStringDefault() {
        assertEquals("( 7.0 ) x²", op.toString());
    }

    @Test
    void testToStringExplicitPrefix() {
        assertEquals("x² ( 7.0 )", op.toString(Notation.PREFIX));
    }

    @Test
    void testToStringExplicitPostfix() {
        assertEquals("( 7.0 ) x²", op.toString(Notation.POSTFIX));
    }

    @Test
    void testNoOperands() {
        assertThrows(IllegalConstruction.class,
                () -> new Square(List.of(), Notation.PREFIX));
    }

    @Test
    void testEqualsAndHashCode() throws IllegalConstruction {
        Square s1 = new Square(params, Notation.PREFIX);
        Square s2 = new Square(params, Notation.PREFIX);
        assertEquals(s1, s2);
        assertEquals(s1.hashCode(), s2.hashCode());
    }
}
