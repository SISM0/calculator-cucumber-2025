package calculator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import visitor.Evaluator;  // Ajout de l'import du Visitor Evaluator

public class TestEvaluator {

    private Evaluator evalVisitor;

    @BeforeEach
    void setUp() {
        evalVisitor = new Evaluator();
    }

    @Test
    void testEvaluateNumber() {
        MyNumber n = new MyNumber(42.0);
        n.accept(evalVisitor);
        assertEquals(42.0, evalVisitor.getResult(), 1e-10);
    }

    @Test
    void testEvaluatePlus() throws IllegalConstruction {
        Expression plus = new Plus(
                List.of(new MyNumber(2.0), new MyNumber(3.0)),
                Notation.INFIX
        );
        plus.accept(evalVisitor);
        assertEquals(5.0, evalVisitor.getResult(), 1e-10);
    }

    @Test
    void testEvaluateNested() throws IllegalConstruction {
        // (2 + 3) * 4
        Expression times = new Times(
                List.of(
                        new Plus(List.of(new MyNumber(2.0), new MyNumber(3.0)), Notation.INFIX),
                        new MyNumber(4.0)
                ),
                Notation.INFIX
        );
        times.accept(evalVisitor);
        assertEquals(20.0, evalVisitor.getResult(), 1e-10);
    }

    @Test
    void testEvaluateDivides() throws IllegalConstruction {
        Expression div = new Divides(
                List.of(new MyNumber(7.0), new MyNumber(2.0)),
                Notation.INFIX
        );
        div.accept(evalVisitor);
        assertEquals(3.5, evalVisitor.getResult(), 1e-10);
    }

    @Test
    void testEvaluateAllOperators() throws IllegalConstruction {
        Expression[] ops = new Expression[] {
                new Plus(List.of(new MyNumber(1.0), new MyNumber(2.0)), Notation.INFIX),
                new Minus(List.of(new MyNumber(5.0), new MyNumber(3.0)), Notation.INFIX),
                new Times(List.of(new MyNumber(3.0), new MyNumber(4.0)), Notation.INFIX),
                new Divides(List.of(new MyNumber(8.0), new MyNumber(2.0)), Notation.INFIX),
                new Modulo(List.of(new MyNumber(7.0), new MyNumber(3.0)), Notation.INFIX),
                new Power(List.of(new MyNumber(2.0), new MyNumber(3.0)), Notation.INFIX),
                new Square(List.of(new MyNumber(6.0)), Notation.POSTFIX),
                new Sqrt(List.of(new MyNumber(9.0)), Notation.POSTFIX),
                new Factorial(List.of(new MyNumber(4.0)), Notation.POSTFIX)
        };
        double[] expected = new double[] {3.0, 2.0, 12.0, 4.0, 1.0, 8.0, 36.0, 3.0, 24.0};

        for (int i = 0; i < ops.length; i++) {
            evalVisitor = new Evaluator();
            ops[i].accept(evalVisitor);
            assertEquals(expected[i], evalVisitor.getResult(), 1e-10,
                    "Operator " + ops[i].getClass().getSimpleName());
        }
    }
}
