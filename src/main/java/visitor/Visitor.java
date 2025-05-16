package visitor;

import calculator.Expression;
import calculator.MyNumber;
import calculator.Operation;

/** Base pour un Visitor : ajoute ici d’autres visit(...) si besoin. */
public abstract class Visitor {
    public abstract void visit(MyNumber n);
    public abstract void visit(Operation o);
}
