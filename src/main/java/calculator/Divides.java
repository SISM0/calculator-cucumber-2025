package calculator;

import java.util.List;

/**
 * Operation de division binaire.
 * Requiert exactement deux opérandes.
 */
public class Divides extends Operation {

    /**
     * Constructeur principal avec notation.
     * @param args liste de deux opérandes
     * @param notation mode d'affichage (INFIX/…)
     * @throws IllegalConstruction si args est null ou taille != 2
     */
    public Divides(List<Expression> args, Notation notation) throws IllegalConstruction {
        super(args, notation);
        if (args.size() != 2) {
            throw new IllegalConstruction("Divides requires exactly two operands", this);
        }
        this.symbol  = "/";
        this.neutral = 1.0;
    }

    /**
     * Constructeur historique par défaut en INFIX.
     */
    public Divides(List<Expression> args) throws IllegalConstruction {
        this(args, Notation.INFIX);
    }

    @Override
    public double op(double l, double r) {
        if (r == 0) throw new ArithmeticException("Division by zero");
        return l / r;
    }
}
