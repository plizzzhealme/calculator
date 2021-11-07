package com.company;

public class Main {

    public static void main(String[] args) {
        String infixNotation = "3+4*2/(1-5)^2";
        System.out.println(Converter.convertToPostfixNotation(infixNotation));
    }
}
