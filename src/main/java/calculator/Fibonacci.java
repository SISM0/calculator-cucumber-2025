// src/main/java/calculator/Fibonacci.java
package calculator;

import java.util.List;

/**
 * Operation de calcul du n-ième Fibonacci (préfixe).
 * Requiert exactement un opérande entier >= 0.
 */
public class Fibonacci extends Operation {

    /**
     * Constructeur principal avec notation.
     * @param args liste d’un seul opérande
     * @param notation mode d'affichage (PREFIX, INFIX, POSTFIX)
     * @throws IllegalConstruction si args est null ou taille != 1
     */
    public Fibonacci(List<Expression> args, Notation notation) throws IllegalConstruction {
        super(args, notation);
        if (args.size() != 1) {
            throw new IllegalConstruction("Fibonacci requires exactly one operand", this);
        }
        this.symbol  = "fib";
        this.neutral = 0.0;
    }

    /** Constructeur historique par défaut en PREFIX. */
    public Fibonacci(List<Expression> args) throws IllegalConstruction {
        this(args, Notation.PREFIX);
    }

    private double fib(int n) {
        if (n < 0) throw new ArithmeticException("Invalid Fibonacci");
        if (n < 2) return n;
        double a = 0, b = 1;
        for (int i = 2; i <= n; i++) {
            double tmp = a + b;
            a = b; b = tmp;
        }
        return b;
    }

    @Override
    public double op(double l, double r) {
        return fib((int) r);
    }

    // **Suppression de tout override de toString()** :
    // on garde le comportement par défaut de Operation pour INFIX, PREFIX et POSTFIX.
}
