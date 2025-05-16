package calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;

/**
 * Interface graphique Swing pour le calculateur.
 */
public class CalculatorGUI extends JFrame {
    private final JTextField display;
    private StringBuilder currentExpression = new StringBuilder();

    public CalculatorGUI() {
        super("Calculator GUI");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(360, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(5, 5));

        // Display
        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.PLAIN, 28));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setPreferredSize(new Dimension(0, 60));
        add(display, BorderLayout.NORTH);

        // Buttons panel: 7 rows x 4 cols
        JPanel buttonsPanel = new JPanel(new GridLayout(7, 4, 5, 5));
        String[] labels = {
                "C", "%", "fib", "sqrt",
                "x²", "!", "^", "",
                "(", ")", "", "",
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+"
        };
        Arrays.stream(labels).forEach(label -> {
            if (label.isEmpty()) {
                buttonsPanel.add(new JLabel());  // placeholder
            } else {
                JButton btn = new JButton(label);
                btn.setFont(new Font("Arial", Font.BOLD, 20));
                btn.addActionListener(this::onButtonClick);
                buttonsPanel.add(btn);
            }
        });
        add(buttonsPanel, BorderLayout.CENTER);

        setResizable(false);
        setVisible(true);
    }

    private void onButtonClick(ActionEvent e) {
        String cmd = ((JButton) e.getSource()).getText();
        switch (cmd) {
            case "C":
                currentExpression.setLength(0);
                display.setText("");
                break;
            case "=":
                evaluateExpression();
                break;
            default:
                // tous les autres boutons (chiffres, opérateurs, fonctions)
                currentExpression.append(cmd);
                display.setText(currentExpression.toString());
        }
    }

    private void evaluateExpression() {
        try {
            Calculator calc = new Calculator();
            String exprStr = currentExpression.toString();
            Expression expr = calc.read(exprStr);
            // on passe maintenant à double
            double result = calc.eval(expr);
            // évite les ".0" inutiles si c'est un entier
            String resultStr = (result == (long) result)
                    ? String.valueOf((long) result)
                    : String.valueOf(result);
            display.setText(resultStr);
            currentExpression = new StringBuilder(resultStr);
        } catch (Exception ex) {
            display.setText("Error");
            currentExpression.setLength(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CalculatorGUI::new);
    }
}
