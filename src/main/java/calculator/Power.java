package calculator;

import java.util.List;

/**
 * Operation de puissance.
 * Requiert exactement deux opérandes : base et exposant.
 */
public class Power extends Operation {

    /**
     * Constructeur principal avec notation.
     * @param args liste des deux opérandes
     * @param notation mode d'affichage (INFIX/PREFIX/POSTFIX)
     * @throws IllegalConstruction si args est null ou taille != 2
     */
    public Power(List<Expression> args, Notation notation) throws IllegalConstruction {
        super(args, notation);
        if (args.size() != 2) {
            throw new IllegalConstruction("Power requires exactly two operands", this);
        }
        this.symbol  = "^";
        this.neutral = 1.0;
    }

    /**
     * Constructeur historique par défaut en INFIX.
     */
    public Power(List<Expression> args) throws IllegalConstruction {
        this(args, Notation.INFIX);
    }

    @Override
    public double op(double l, double r) {
        return Math.pow(l, r);
    }
}
