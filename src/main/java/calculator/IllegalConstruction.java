// src/main/java/calculator/IllegalConstruction.java
package calculator;

public class IllegalConstruction extends Exception {
    public IllegalConstruction(String msg) {
        super(msg);
    }

    /**
     * Nouveau constructeur pour pouvoir faire
     *   throw new IllegalConstruction("…", this);
     */
    public IllegalConstruction(String msg, Expression source) {
        super(msg);
    }
}
