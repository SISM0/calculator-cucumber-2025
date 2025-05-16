// src/main/java/calculator/Calculator.java
package calculator;

import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import visitor.Evaluator;

public class Calculator {

    public Calculator() {}

    public void print(Expression e) {
        System.out.println("The result of evaluating expression " + e);
        System.out.println("is: " + eval(e) + ".");
        System.out.println();
    }

    public void printExpressionDetails(Expression e) {
        print(e);
        System.out.print("It contains " + e.countDepth() + " levels of nested expressions, ");
        System.out.print(e.countOps() + " operations");
        System.out.println(" and " + e.countNbs() + " numbers.");
        System.out.println();
    }

    /**
     * Évalue un AST construit (pour vos pas Cucumber).
     */
    public double evaluate(Operation op) throws IllegalConstruction {
        return op.eval();
    }

    /**
     * Crée dynamiquement une Operation à partir d'une liste de doubles,
     * du symbole et de la notation souhaitée (pour vos pas Cucumber).
     */
    public Operation create(List<Double> nums, String symbol, Notation notation) throws IllegalConstruction {
        // Convertit les doubles en Expression
        List<Expression> args = new ArrayList<>(nums.size());
        for (Double d : nums) {
            args.add(new MyNumber(d));
        }
        // Sélection de l'opération
        return switch (symbol) {
            case "+"            -> new Plus(args, notation);
            case "-"            -> new Minus(args, notation);
            case "*"            -> new Times(args, notation);
            case "/"            -> new Divides(args, notation);
            case "%"            -> new Modulo(args, notation);
            case "^"            -> new Power(args, notation);
            case "square"       -> new Square(args, notation);
            case "√", "sqrt"    -> new Sqrt(args, notation);
            case "!"            -> new Factorial(args, notation);
            case "fibonacci", "fib" -> new Fibonacci(args, notation);
            default -> throw new IllegalArgumentException("Symbole inconnu : " + symbol);
        };
    }

    /**
     * Somme des nombres fournis (pour vos pas Cucumber).
     */
    public double sum(List<Double> nums) throws IllegalConstruction {
        return evaluate(create(nums, "+", Notation.INFIX));
    }

    /**
     * Produit des nombres fournis (pour vos pas Cucumber).
     */
    public double product(List<Double> nums) throws IllegalConstruction {
        return evaluate(create(nums, "*", Notation.INFIX));
    }

    /**
     * Différence (nums[0] - nums[1] - ...) (pour Cucumber).
     */
    public double difference(List<Double> nums) throws IllegalConstruction {
        return evaluate(create(nums, "-", Notation.INFIX));
    }

    /**
     * Quotient (nums[0] / nums[1] / ...) (pour Cucumber).
     */
    public double quotient(List<Double> nums) throws IllegalConstruction {
        return evaluate(create(nums, "/", Notation.INFIX));
    }

    // -----------------------------------------------------
    // Tout ce qui suit est votre code existant pour read()
    // -----------------------------------------------------

    public double eval(Expression e) {
        Evaluator v = new Evaluator();
        e.accept(v);
        return v.getResult();
    }

    public Expression read(String s) throws IllegalConstruction {
        // 1) Remove whitespace
        String input = s.replaceAll("\\s+", "");

        // 2) Tokenize raw input
        List<String> raw = new ArrayList<>();
        Matcher m = Pattern.compile("(\\d+(?:\\.\\d+)?|sqrt|fib|x²|!|[+\\-*/%^()])").matcher(input);
        while (m.find()) raw.add(m.group());

        // 3) Handle unary + and -
        List<String> tokens = new ArrayList<>();
        for (int i = 0; i < raw.size(); i++) {
            String t = raw.get(i);
            boolean unaryContext = (i == 0)
                    || raw.get(i - 1).equals("(")
                    || raw.get(i - 1).matches("[+\\-*/%^]");
            // Combine --5 or -5 into a single negative literal
            if (t.equals("-") && unaryContext && i + 1 < raw.size() && raw.get(i+1).matches("\\d+(?:\\.\\d+)?")) {
                tokens.add("-" + raw.get(i + 1));
                i++;
                continue;
            }
            // Regular unary minus: insert 0 before -
            if (t.equals("-") && unaryContext) {
                tokens.add("0");
                tokens.add("-");
                continue;
            }
            // Unary plus: skip
            if (t.equals("+") && unaryContext) {
                continue;
            }
            tokens.add(t);
        }

        // 4) Precedence map
        Map<String,Integer> prec = new HashMap<>();
        prec.put("(", 0); prec.put(")", 0);
        prec.put("+", 1); prec.put("-", 1);
        prec.put("*", 2); prec.put("/", 2); prec.put("%", 2);
        prec.put("^", 3);
        prec.put("x²", 4); prec.put("sqrt", 4);
        prec.put("fib", 4); prec.put("!", 4);

        // 5) Shunting-Yard to RPN
        Deque<String> output = new ArrayDeque<>();
        Deque<String> ops = new ArrayDeque<>();
        for (String tok : tokens) {
            if (tok.matches("\\d+(?:\\.\\d+)?")) {
                output.addLast(tok);
            } else if (tok.equals("sqrt") || tok.equals("fib") || tok.equals("x²") || tok.equals("!")) {
                ops.push(tok);
            } else if (tok.matches("[+\\-*/%^]")) {
                while (!ops.isEmpty() && prec.get(ops.peek()) >= prec.get(tok)) {
                    output.addLast(ops.pop());
                }
                ops.push(tok);
            } else if (tok.equals("(")) {
                ops.push(tok);
            } else if (tok.equals(")")) {
                while (!ops.isEmpty() && !ops.peek().equals("(")) {
                    output.addLast(ops.pop());
                }
                if (ops.isEmpty()) throw new IllegalConstruction("Mismatched parentheses");
                ops.pop();
            } else {
                throw new IllegalConstruction("Unknown token: " + tok);
            }
        }
        while (!ops.isEmpty()) {
            String op = ops.pop();
            if (op.equals("(") || op.equals(")")) throw new IllegalConstruction("Mismatched parentheses");
            output.addLast(op);
        }

        // 6) RPN to AST
        Deque<Expression> stack = new ArrayDeque<>();
        for (String tok : output) {
            if (tok.matches("\\d+(?:\\.\\d+)?")) {
                stack.push(new MyNumber(Double.parseDouble(tok)));
            } else if (tok.equals("!")) {
                Expression e1 = stack.pop();
                stack.push(new Factorial(List.of(e1)));
            } else if (tok.equals("sqrt")) {
                Expression e1 = stack.pop();
                stack.push(new Sqrt(List.of(e1)));
            } else if (tok.equals("x²")) {
                Expression e1 = stack.pop();
                stack.push(new Square(List.of(e1)));
            } else if (tok.equals("fib")) {
                Expression e1 = stack.pop();
                stack.push(new Fibonacci(List.of(e1)));
            } else {
                Expression b = stack.pop();
                Expression a = stack.pop();
                switch (tok) {
                    case "+": stack.push(new Plus(List.of(a, b))); break;
                    case "-": stack.push(new Minus(List.of(a, b))); break;
                    case "*": stack.push(new Times(List.of(a, b))); break;
                    case "/": stack.push(new Divides(List.of(a, b))); break;
                    case "%": stack.push(new Modulo(List.of(a, b))); break;
                    case "^": stack.push(new Power(List.of(a, b))); break;
                    default: throw new IllegalConstruction("Unknown RPN token: " + tok);
                }
            }
        }
        if (stack.size() != 1) throw new IllegalConstruction("Invalid expression");
        return stack.pop();
    }
}
