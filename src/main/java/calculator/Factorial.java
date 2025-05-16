package calculator;

import java.util.List;

/**
 * Operation de factorielle (postfixe).
 * Requiert exactement un opérande entier >= 0.
 */
public class Factorial extends Operation {

    /**
     * Constructeur principal avec notation.
     * @param args liste d’un seul opérande
     * @param notation mode d'affichage (PREFIX/INFIX/POSTFIX)
     * @throws IllegalConstruction si args est null ou taille != 1
     */
    public Factorial(List<Expression> args, Notation notation) throws IllegalConstruction {
        super(args, notation);
        if (args.size() != 1) {
            throw new IllegalConstruction("Factorial requires exactly one operand", this);
        }
        this.symbol  = "!";
        this.neutral = 1.0;
    }

    /** Constructeur historique par défaut en POSTFIX. */
    public Factorial(List<Expression> args) throws IllegalConstruction {
        this(args, Notation.POSTFIX);
    }

    private double fact(double n) {
        if (n < 0 || n != Math.floor(n)) {
            throw new ArithmeticException("Invalid factorial");
        }
        double result = 1;
        for (int i = 2; i <= (int)n; i++) {
            result *= i;
        }
        return result;
    }

    @Override
    public double op(double l, double r) {
        return fact(r);
    }
}


