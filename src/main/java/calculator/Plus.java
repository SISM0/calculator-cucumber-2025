package calculator;

import java.util.List;

/**
 * Operation de somme variadique.
 * On peut passer un ou plusieurs opérandes.
 */
public class Plus extends Operation {

    /**
     * Constructeur principal permettant de préciser la notation.
     * @param args liste des opérandes (>=1)
     * @param notation mode d'affichage (INFIX pour l'addition)
     * @throws IllegalConstruction si args est null ou vide
     */
    public Plus(List<Expression> args, Notation notation) throws IllegalConstruction {
        super(args, notation);
        if (args.size() < 1) {
            throw new IllegalConstruction("Plus requires at least one operand", this);
        }
        this.symbol  = "+";
        this.neutral = 0.0;
    }

    /**
     * Constructeur historique par défaut en INFIX.
     * @param args liste des opérandes (>=1)
     * @throws IllegalConstruction si args est null ou vide
     */
    public Plus(List<Expression> args) throws IllegalConstruction {
        this(args, Notation.INFIX);
    }

    @Override
    public double op(double l, double r) {
        return l + r;
    }
}
