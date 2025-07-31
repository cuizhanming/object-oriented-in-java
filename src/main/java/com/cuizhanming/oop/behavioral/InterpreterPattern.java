package com.cuizhanming.oop.behavioral;

import java.util.Stack;
import java.util.HashMap;
import java.util.Map;

/**
 * Interpreter Pattern - Defines grammar for a language and interprets sentences
 * 解释器模式 - 为语言定义语法并解释句子
 */
public class InterpreterPattern {

    // Abstract expression
    public interface Expression {
        int interpret(Context context);
    }

    // Context class
    public static class Context {
        private final Map<String, Integer> variables = new HashMap<>();

        public void setVariable(String name, int value) {
            variables.put(name, value);
        }

        public int getVariable(String name) {
            return variables.getOrDefault(name, 0);
        }

        public void showVariables() {
            System.out.println("Variables: " + variables);
        }
    }

    // Terminal expressions
    public static class NumberExpression implements Expression {
        private final int number;

        public NumberExpression(int number) {
            this.number = number;
        }

        @Override
        public int interpret(Context context) {
            return number;
        }

        @Override
        public String toString() {
            return String.valueOf(number);
        }
    }

    public static class VariableExpression implements Expression {
        private final String variable;

        public VariableExpression(String variable) {
            this.variable = variable;
        }

        @Override
        public int interpret(Context context) {
            return context.getVariable(variable);
        }

        @Override
        public String toString() {
            return variable;
        }
    }

    // Non-terminal expressions
    public static class AddExpression implements Expression {
        private final Expression left;
        private final Expression right;

        public AddExpression(Expression left, Expression right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public int interpret(Context context) {
            return left.interpret(context) + right.interpret(context);
        }

        @Override
        public String toString() {
            return "(" + left + " + " + right + ")";
        }
    }

    public static class SubtractExpression implements Expression {
        private final Expression left;
        private final Expression right;

        public SubtractExpression(Expression left, Expression right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public int interpret(Context context) {
            return left.interpret(context) - right.interpret(context);
        }

        @Override
        public String toString() {
            return "(" + left + " - " + right + ")";
        }
    }

    public static class MultiplyExpression implements Expression {
        private final Expression left;
        private final Expression right;

        public MultiplyExpression(Expression left, Expression right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public int interpret(Context context) {
            return left.interpret(context) * right.interpret(context);
        }

        @Override
        public String toString() {
            return "(" + left + " * " + right + ")";
        }
    }

    // Expression parser
    public static class ExpressionParser {

        public static Expression parse(String expression) {
            Stack<Expression> stack = new Stack<>();
            String[] tokens = expression.split("\\s+");

            for (String token : tokens) {
                switch (token) {
                    case "+" -> {
                        Expression right = stack.pop();
                        Expression left = stack.pop();
                        stack.push(new AddExpression(left, right));
                    }
                    case "-" -> {
                        Expression right = stack.pop();
                        Expression left = stack.pop();
                        stack.push(new SubtractExpression(left, right));
                    }
                    case "*" -> {
                        Expression right = stack.pop();
                        Expression left = stack.pop();
                        stack.push(new MultiplyExpression(left, right));
                    }
                    default -> {
                        if (token.matches("\\d+")) {
                            stack.push(new NumberExpression(Integer.parseInt(token)));
                        } else {
                            stack.push(new VariableExpression(token));
                        }
                    }
                }
            }

            return stack.pop();
        }
    }

    // Calculator using interpreter
    public static class Calculator {
        private final Context context = new Context();

        public void setVariable(String name, int value) {
            context.setVariable(name, value);
            System.out.println("Set " + name + " = " + value);
        }

        public int evaluate(String expression) {
            System.out.println("Evaluating: " + expression);
            Expression expr = ExpressionParser.parse(expression);
            System.out.println("Parsed as: " + expr);
            int result = expr.interpret(context);
            System.out.println("Result: " + result);
            return result;
        }

        public void showVariables() {
            context.showVariables();
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Interpreter Pattern Demo ===");
        System.out.println("Using Reverse Polish Notation (RPN)");
        System.out.println("Examples: '3 4 +' = 3 + 4, '5 2 * 3 +' = (5 * 2) + 3");

        Calculator calculator = new Calculator();

        // Set variables
        System.out.println("\n--- Setting Variables ---");
        calculator.setVariable("x", 10);
        calculator.setVariable("y", 5);
        calculator.setVariable("z", 2);
        calculator.showVariables();

        // Evaluate expressions with numbers
        System.out.println("\n--- Numeric Expressions ---");
        calculator.evaluate("3 4 +");           // 3 + 4 = 7
        calculator.evaluate("10 5 -");          // 10 - 5 = 5
        calculator.evaluate("6 7 *");           // 6 * 7 = 42
        calculator.evaluate("15 3 - 4 *");      // (15 - 3) * 4 = 48

        // Evaluate expressions with variables
        System.out.println("\n--- Variable Expressions ---");
        calculator.evaluate("x y +");           // x + y = 10 + 5 = 15
        calculator.evaluate("x y -");           // x - y = 10 - 5 = 5
        calculator.evaluate("x z *");           // x * z = 10 * 2 = 20
        calculator.evaluate("x y + z *");       // (x + y) * z = (10 + 5) * 2 = 30

        // Mixed expressions
        System.out.println("\n--- Mixed Expressions ---");
        calculator.evaluate("x 5 + y *");       // (x + 5) * y = (10 + 5) * 5 = 75
        calculator.evaluate("100 x y + -");     // 100 - (x + y) = 100 - (10 + 5) = 85
    }
}
