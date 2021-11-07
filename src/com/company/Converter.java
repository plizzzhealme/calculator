package com.company;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Converter {

    public static final char PLUS = '+';
    public static final char MINUS = '-';
    public static final char MULTIPLY = '*';
    public static final char DIVIDE = '/';
    public static final char POW = '^';
    public static final char FAC = '!';
    public static final char LEFT_PARENTHESIS = '(';
    public static final char RIGHT_PARENTHESIS = ')';
    private static final Map<Character, Integer> OPERAND_PRIORITIES;

    static {
        OPERAND_PRIORITIES = new HashMap<>();
        OPERAND_PRIORITIES.put(LEFT_PARENTHESIS, 0);
        OPERAND_PRIORITIES.put(PLUS, 1);
        OPERAND_PRIORITIES.put(MINUS, 1);
        OPERAND_PRIORITIES.put(MULTIPLY, 2);
        OPERAND_PRIORITIES.put(DIVIDE, 2);
        OPERAND_PRIORITIES.put(POW, 3);
    }

    private Converter() {

    }

    public static String convertToPostfixNotation(String infixNotation) {

        StringBuilder outputString = new StringBuilder();
        Deque<Character> operandsStack = new LinkedList<>();

        for (int i = 0; i < infixNotation.length(); i++) {

            char c = infixNotation.charAt(i);

            switch (c) {
                case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', FAC -> addDigit(outputString, c);

                case LEFT_PARENTHESIS -> addLeftParenthesis(operandsStack);

                case RIGHT_PARENTHESIS -> addRightParenthesis(outputString, operandsStack);

                case POW, MULTIPLY, DIVIDE, PLUS, MINUS -> addOperand(outputString, operandsStack, c);

                default -> throw new UnsupportedOperationException("Unknown character '" + c + "'");
            }
        }

        while (!operandsStack.isEmpty()) {
            outputString.append(operandsStack.pop());
        }

        return outputString.toString();
    }

    private static void addDigit(StringBuilder outputString, char digit) {
        outputString.append(digit);
    }

    private static void addLeftParenthesis(Deque<Character> operandsStack) {
        operandsStack.push(LEFT_PARENTHESIS);
    }

    private static void addRightParenthesis(StringBuilder outputString, Deque<Character> operandsStack) {
        while (!operandsStack.isEmpty() && operandsStack.peek() != LEFT_PARENTHESIS) {
            outputString.append(operandsStack.pop());
        }
        operandsStack.pop();
    }

    private static void addOperand(StringBuilder outputString, Deque<Character> operandsStack, char operand) {
        while (hasLowerPriority(operandsStack, operand)) {
            outputString.append(operandsStack.pop());
        }
        operandsStack.push(operand);
    }

    private static boolean hasLowerPriority(Deque<Character> operandsStack, char operand) {
        if (operandsStack.isEmpty()) {
            return false;
        }

        return OPERAND_PRIORITIES.get(operandsStack.peek()) >= OPERAND_PRIORITIES.get(operand);
    }
}
