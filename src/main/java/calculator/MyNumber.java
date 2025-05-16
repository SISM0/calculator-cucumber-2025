package calculator;

import visitor.Visitor;

/**
 * Feuille du Composite, représente un littéral numérique.
 */
public class MyNumber implements Expression {
    private final double value;

    public MyNumber(double value) {
        this.value = value;
    }

    @Override
    public double eval() {
        return value;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    @Override
    public int countDepth() {
        return 1;
    }

    @Override
    public int countOps() {
        return 0;
    }

    @Override
    public int countNbs() {
        return 1;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MyNumber)) return false;
        MyNumber other = (MyNumber) o;
        return Double.compare(value, other.value) == 0;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(value);
    }
}
