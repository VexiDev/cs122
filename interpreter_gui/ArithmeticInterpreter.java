package interpreter_gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ArithmeticInterpreter {

    public static void main(String[] args) {
        // Schedule a job for the event-dispatching thread to create and show the application's GUI
        SwingUtilities.invokeLater(ArithmeticInterpreter::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        // Create the window
        JFrame frame = new JFrame("Simple Arithmetic Interpreter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));  // Adjusted to add gaps between components

        // Set the frame's preferred dimensions
        frame.setPreferredSize(new Dimension(400, 200));

        // Create a panel for the example label
        JPanel examplePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel exampleLabel = new JLabel("<html><i>Example: x * (3 + y) / 5</i></html>");
        exampleLabel.setForeground(Color.GRAY);
        examplePanel.add(exampleLabel);

        // Create a panel for the input field
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JTextField expressionField = new JTextField(20);
        inputPanel.add(expressionField);

        // Create a panel for the evaluate button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton evalButton = new JButton("Evaluate");
        buttonPanel.add(evalButton);

        // Create a panel for the result label
        JPanel resultPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel resultLabel = new JLabel("Result will be shown here");
        resultPanel.add(resultLabel);

        // Add the sub-panels to the frame
        frame.add(examplePanel);
        frame.add(inputPanel);
        frame.add(buttonPanel);
        frame.add(resultPanel);

        // Add border padding to the GUI
        int padding = 20;
        JComponent contentPane = (JComponent) frame.getContentPane();
        contentPane.setBorder(new EmptyBorder(padding, padding, padding, padding));

        // Display the window
        frame.pack();
        frame.setVisible(true);


        // Add action listener to the button
        evalButton.addActionListener(e -> {
            String expression = expressionField.getText();
            Set<Character> variables = findVariables(expression);

            if (!variables.isEmpty()) {
                Map<Character, JTextField> inputFields = new HashMap<>();
                JPanel panel = new JPanel(new GridLayout(0, 2));

                for (char variable : variables) {
                    panel.add(new JLabel("Value for '" + variable + "':"));
                    JTextField valueField = new JTextField(5);
                    panel.add(valueField);
                    inputFields.put(variable, valueField);
                }

                int result = JOptionPane.showConfirmDialog(frame, panel,
                        "Please input values for each of the variables below:",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    for (Map.Entry<Character, JTextField> entry : inputFields.entrySet()) {
                        expression = expression.replace(String.valueOf(entry.getKey()), entry.getValue().getText());
                    }
                    evaluateExpression(expression, resultLabel);
                }
            } else {
                evaluateExpression(expression, resultLabel);
            }
        });

        // Display the window
        frame.pack();
        frame.setVisible(true);
    }

    private static Set<Character> findVariables(String expression) {
        Set<Character> variables = new HashSet<>();
        for (char c : expression.toCharArray()) {
            if (Character.isLetter(c)) {
                variables.add(c);
            }
        }
        return variables;
    }

    private static void evaluateExpression(String expression, JLabel resultLabel) {
        try {
            double result = Expression.evaluate(expression.replaceAll("\\s+", ""));
            resultLabel.setText("Solution: " + result);
        } catch (Exception e) {
            System.out.println(e);
            resultLabel.setText("Check syntax and try again.");
        }
    }
}