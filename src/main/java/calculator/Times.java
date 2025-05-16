package calculator;

import java.util.List;

/**
 * Operation de multiplication variadique.
 * Peut prendre une ou plusieurs opérandes.
 */
public class Times extends Operation {

    /**
     * Constructeur principal avec notation.
     * @param args liste des opérandes (>=1)
     * @param notation mode d'affichage (PREFIX/INFIX/POSTFIX)
     * @throws IllegalConstruction si args est null ou vide
     */
    public Times(List<Expression> args, Notation notation) throws IllegalConstruction {
        super(args, notation);
        if (args.size() < 1) {
            throw new IllegalConstruction("Times requires at least one operand", this);
        }
        this.symbol  = "*";
        this.neutral = 1.0;
    }

    /**
     * Constructeur historique par défaut en INFIX.
     */
    public Times(List<Expression> args) throws IllegalConstruction {
        this(args, Notation.INFIX);
    }

    @Override
    public double op(double l, double r) {
        return l * r;
    }
}
