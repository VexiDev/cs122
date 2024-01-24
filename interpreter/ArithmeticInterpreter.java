package interpreter;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;


public class ArithmeticInterpreter {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter an arithmetic expression (include all operators): ");
        String expression = scanner.nextLine();
        arithmeticInterpreter(expression, scanner);
    }

    public static void arithmeticInterpreter(String expression, Scanner scanner) {
        try {
            Set<Character> variables = new HashSet<>();
            for (char c : expression.toCharArray()) {
                if (Character.isLetter(c)) {
                    variables.add(c);
                }
            }

            for (char variable : variables) {
                System.out.print("Enter a value for " + variable + ": ");
                String value = scanner.nextLine();
                expression = expression.replace(String.valueOf(variable), value);
            }

            double result = Expression.evaluate(expression.replaceAll(" ", ""));
            System.out.printf("The solution for your expression is %.2f\n", result);

        } catch (Exception e) {
            System.out.println("\u001B[31m" + "There was an error while evaluating your expression, please try again." + "\u001B[0m\n");
        }
    }
}
