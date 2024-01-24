package interpreter;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class Expression {

    public static double evaluate(String expression) {
        List<String> postfix = infixToPostfix(expression);
        return evaluatePostfix(postfix);
    }

    private static List<String> infixToPostfix(String expression) {
        Stack<Character> stack = new Stack<>();
        List<String> output = new ArrayList<>();

        for (char c : expression.toCharArray()) {
            if (Character.isDigit(c)) {
                output.add(Character.toString(c));
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    output.add(Character.toString(stack.pop()));
                }
                stack.pop();
            } else if (isOperator(c)) {
                while (!stack.isEmpty() && precedence(stack.peek()) >= precedence(c)) {
                    output.add(Character.toString(stack.pop()));
                }
                stack.push(c);
            }
        }

        while (!stack.isEmpty()) {
            output.add(Character.toString(stack.pop()));
        }

        return output;
    }

    private static double evaluatePostfix(List<String> postfix) {
        Stack<Double> stack = new Stack<>();

        for (String token : postfix) {
            if (Character.isDigit(token.charAt(0))) {
                stack.push(Double.valueOf(token));
            } else {
                double b = stack.pop();
                double a = stack.pop();

                switch (token.charAt(0)) {
                    case '+':
                        stack.push(a + b);
                        break;
                    case '-':
                        stack.push(a - b);
                        break;
                    case '*':
                        stack.push(a * b);
                        break;
                    case '/':
                        if (b == 0) throw new UnsupportedOperationException("Cannot divide by zero");
                        stack.push(a / b);
                        break;
                }
            }
        }

        return stack.pop();
    }

    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private static int precedence(char c) {
        switch (c) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
        }
        return -1;
    }

    public static void main(String[] args) {
        // Test case
        String expression = "3 + (2 * 4) - 7";
        double result = evaluate(expression.replaceAll(" ", ""));
        System.out.println("Result: " + result);
    }
}
