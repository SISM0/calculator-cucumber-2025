package visitor;

import calculator.Expression;
import calculator.MyNumber;
import calculator.Operation;

import java.util.List;

/**
 * Visitor qui évalue un AST et stocke le résultat en interne.
 */
public class Evaluator extends Visitor {
    private double result;

    public double getResult() {
        return result;
    }

    @Override
    public void visit(MyNumber n) {
        result = n.getValue();
    }

    @Override
    public void visit(Operation o) {
        List<Expression> args = o.getArgs();
        // on évalue récursivement le premier
        args.get(0).accept(this);
        double acc = result;
        // les suivants
        for (int i = 1; i < args.size(); i++) {
            args.get(i).accept(this);
            acc = o.op(acc, result);
        }
        // cas unaire (1 seul arg) : op(neutral, arg)
        if (args.size() == 1) {
            acc = o.op(o.getNeutral(), acc);
        }
        result = acc;
    }
}
