package calculator;

import java.util.List;

/**
 * Operation de racine carrée (unaire).
 * Requiert exactement un opérande >= 0.
 */
public class Sqrt extends Operation {

    /**
     * Constructeur principal avec notation.
     * @param args liste d’un seul opérande
     * @param notation mode d'affichage (PREFIX/INFIX/POSTFIX)
     * @throws IllegalConstruction si args est null ou taille != 1
     */
    public Sqrt(List<Expression> args, Notation notation) throws IllegalConstruction {
        super(args, notation);
        if (args.size() != 1) {
            throw new IllegalConstruction("Sqrt requires exactly one operand", this);
        }
        this.symbol  = "sqrt";
        this.neutral = 0.0;
    }

    /** Constructeur historique par défaut en PREFIX. */
    public Sqrt(List<Expression> args) throws IllegalConstruction {
        this(args, Notation.PREFIX);
    }

    @Override
    public double op(double l, double r) {
        if (r < 0) {
            throw new ArithmeticException("Square root of negative");
        }
        return Math.sqrt(r);
    }
    @Override
    public String toString() {
        return toString(Notation.POSTFIX);
    }
}
