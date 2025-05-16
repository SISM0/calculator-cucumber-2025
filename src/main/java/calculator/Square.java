package calculator;

import java.util.List;

/**
 * Operation de mise au carré (unaire).
 * Requiert exactement un opérande.
 */
public class Square extends Operation {

    /**
     * Constructeur principal avec notation.
     * @param args liste d'un seul opérande
     * @param notation mode d'affichage (PREFIX/INFIX/POSTFIX)
     * @throws IllegalConstruction si args est null ou taille != 1
     */
    public Square(List<Expression> args, Notation notation) throws IllegalConstruction {
        super(args, notation);
        if (args.size() != 1) {
            throw new IllegalConstruction("Square requires exactly one operand", this);
        }
        this.symbol = "x²";
        this.neutral = 1.0;
    }

    /**
     * Constructeur historique par défaut en PREFIX.
     */
    public Square(List<Expression> args) throws IllegalConstruction {
        this(args, Notation.PREFIX);
    }

    @Override
    public double op(double l, double r) {
        return r * r;
    }

    /**
     * Affichage par défaut en POSTFIX pour correspondre aux tests.
     */
    @Override
    public String toString() {
        return toString(Notation.POSTFIX);
    }
}
