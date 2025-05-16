package calculator;

import java.util.List;

/**
 * Operation de soustraction (binaire ou unaire).
 * - Binaire : deux opérandes ou plus
 * - Una ire : un opérande (renvoie -arg)
 */
public class Minus extends Operation {

    /**
     * Constructeur principal avec notation.
     * @param args liste des opérandes (>=1)
     * @param notation mode d'affichage (PREFIX/INFIX/POSTFIX)
     * @throws IllegalConstruction si args est null ou vide
     */
    public Minus(List<Expression> args, Notation notation) throws IllegalConstruction {
        super(args, notation);
        if (args.size() < 1) {
            throw new IllegalConstruction("Minus requires at least one operand", this);
        }
        this.symbol  = "-";
        this.neutral = 0.0;
    }

    /**
     * Constructeur historique par défaut en INFIX.
     */
    public Minus(List<Expression> args) throws IllegalConstruction {
        this(args, Notation.INFIX);
    }

    @Override
    public double op(double l, double r) {
        return l - r;
    }
}