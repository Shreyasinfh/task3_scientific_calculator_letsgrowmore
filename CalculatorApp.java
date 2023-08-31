import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Calculator {
    private JFrame frame;
    private JTextField displayField;
    private JToggleButton degreesRadiansToggle;

    private double currentValue = 0.0;
    private String currentOperator = "";
    private boolean isNewNumber = true;
    private boolean isRadians = false;

    Calculator() {
        frame = new JFrame("Scientific Calculator");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        displayField = new JTextField();
        displayField.setEditable(false);
        frame.add(displayField, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(6, 4));
        String[] buttonLabels = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+",
            "sin", "cos", "tan","C", "log"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.addActionListener(new ButtonClickListener());
            buttonPanel.add(button);
        }

        frame.add(buttonPanel, BorderLayout.CENTER);

        degreesRadiansToggle = new JToggleButton("Degrees");
        degreesRadiansToggle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                isRadians = !isRadians;
                degreesRadiansToggle.setText(isRadians ? "Radians" : "Degrees");
            }
        });

        JPanel togglePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        togglePanel.add(degreesRadiansToggle);
        frame.add(togglePanel, BorderLayout.SOUTH);

        frame.setSize(400, 400);
        frame.setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if ("0123456789.".contains(command)) {
                if (isNewNumber) {
                    displayField.setText(command);
                    isNewNumber = false;
                } else {
                    displayField.setText(displayField.getText() + command);
                }
            } else if ("+-*/".contains(command)) {
                if (!currentOperator.isEmpty()) {
                    calculate();
                }
                currentOperator = command;
                currentValue = Double.parseDouble(displayField.getText());
                isNewNumber = true;
                displayField.setText(displayField.getText() + " " + currentOperator + " ");
            } else if ("=".equals(command)) {
                calculate();
                currentOperator = "";
                isNewNumber = true;
            } else if ("sin".equals(command)) {
                double value = Double.parseDouble(displayField.getText());
                if (!isRadians) {
                    value = Math.toRadians(value);
                }
                currentValue = Math.sin(value);
                displayField.setText(String.valueOf(currentValue));
            } else if ("cos".equals(command)) {
                double value = Double.parseDouble(displayField.getText());
                if (!isRadians) {
                    value = Math.toRadians(value);
                }
                currentValue = Math.cos(value);
                displayField.setText(String.valueOf(currentValue));
            } else if ("tan".equals(command)) {
                double value = Double.parseDouble(displayField.getText());
                if (!isRadians) {
                    value = Math.toRadians(value);
                }
                currentValue = Math.tan(value);
                displayField.setText(String.valueOf(currentValue));
            } else if ("log".equals(command)) {
                double value = Double.parseDouble(displayField.getText());
                currentValue = Math.log10(value);
                displayField.setText(String.valueOf(currentValue));
            } else if ("C".equals(command)) {
                clear();
            }
        }

        private void calculate() {
            double secondValue = Double.parseDouble(displayField.getText().trim());

            switch (currentOperator) {
                case "+":
                    currentValue += secondValue;
                    break;
                case "-":
                    currentValue -= secondValue;
                    break;
                case "*":
                    currentValue *= secondValue;
                    break;
                case "/":
                    if (secondValue != 0) {
                        currentValue /= secondValue;
                    } else {
                        displayField.setText("Error");
                        return;
                    }
                    break;
            }

            displayField.setText(String.valueOf(currentValue));
        }

        private void clear() {
            currentValue = 0.0;
            currentOperator = "";
            isNewNumber = true;
            displayField.setText("");
        }
    }
}

public class CalculatorApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Calculator();
            }
        });
    }
}
