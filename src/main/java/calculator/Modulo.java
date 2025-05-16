// src/main/java/calculator/Modulo.java
package calculator;

import java.util.List;

/**
 * Operation de reste de division.
 * Requiert exactement deux opérandes.
 */
public class Modulo extends Operation {

    /**
     * Constructeur principal avec notation.
     * @param args liste de deux opérandes
     * @param notation mode d'affichage (INFIX/PREFIX/POSTFIX)
     * @throws IllegalConstruction si args est null ou taille != 2
     */
    public Modulo(List<Expression> args, Notation notation) throws IllegalConstruction {
        super(args, notation);
        if (args.size() != 2) {
            throw new IllegalConstruction("Modulo requires exactly two operands", this);
        }
        this.symbol  = "%";
        this.neutral = 0.0;
    }

    /**
     * Constructeur historique par défaut en INFIX.
     */
    public Modulo(List<Expression> args) throws IllegalConstruction {
        this(args, Notation.INFIX);
    }

    @Override
    public double op(double l, double r) {
        if (r == 0) throw new ArithmeticException("Modulo by zero");
        return l % r;
    }
}