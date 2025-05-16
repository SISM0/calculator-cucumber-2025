package calculator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestCounting {

    @Test
    void testLeafCounts() {
        MyNumber n = new MyNumber(3.0);
        assertEquals(1, n.countDepth(), "Depth of leaf should be 1");
        assertEquals(0, n.countOps(),   "Ops of leaf should be 0");
        assertEquals(1, n.countNbs(),   "Nbs of leaf should be 1");
    }

    @Test
    void testUnaryCounts() throws IllegalConstruction {
        Minus m = new Minus(List.of(new MyNumber(7.0)), Notation.PREFIX);
        assertEquals(2, m.countDepth(), "Depth of unary should be 2");
        assertEquals(1, m.countOps(),   "Ops of unary should be 1");
        assertEquals(1, m.countNbs(),   "Nbs of unary should be 1");
    }

    @Test
    void testBinaryCounts() throws IllegalConstruction {
        Times t = new Times(List.of(new MyNumber(2.0), new MyNumber(3.0)), Notation.INFIX);
        assertEquals(2, t.countDepth(), "Depth of binary should be 2");
        assertEquals(1, t.countOps(),   "Ops of binary should be 1");
        assertEquals(2, t.countNbs(),   "Nbs of binary should be 2");
    }

    @Test
    void testNestedCounts() throws IllegalConstruction {
        Divides d = new Divides(
                List.of(
                        new MyNumber(5.0),
                        new Times(List.of(new MyNumber(2.0), new MyNumber(3.0)), Notation.INFIX)
                ), Notation.POSTFIX
        );
        assertEquals(3, d.countDepth(), "Depth of nested should be 3");
        assertEquals(2, d.countOps(),   "Ops of nested should be 2");
        assertEquals(3, d.countNbs(),   "Nbs of nested should be 3");
    }

    @Test
    void testComplexExpressionCounting() throws IllegalConstruction {
        // (2 + 3) * (4 - 1) / 5
        Expression e = new Divides(
                List.of(
                        new Times(
                                List.of(
                                        new Plus(List.of(new MyNumber(2.0), new MyNumber(3.0)), Notation.INFIX),
                                        new Minus(List.of(new MyNumber(4.0), new MyNumber(1.0)), Notation.INFIX)
                                ), Notation.INFIX
                        ),
                        new MyNumber(5.0)
                ), Notation.INFIX
        );
        assertEquals(4, e.countOps(), "Ops of complex should be 4");
        assertEquals(4, e.countDepth(), "Depth of complex should be 4");
        assertEquals(5, e.countNbs(),   "Nbs of complex should be 5");
    }
}
