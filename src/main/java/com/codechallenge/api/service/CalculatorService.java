package com.codechallenge.api.service;

import com.codechallenge.api.model.Request;
import com.codechallenge.api.model.Response;
import org.springframework.stereotype.Service;

import java.util.Stack;

@Service
public class CalculatorService {

    public Response<Double> calculate(Request<String> request) {
        Stack<Double> operandStack = new Stack<>();
        String[] tokens = request.data().split("\\s+");

        for (String token : tokens) {
            if (isOperator(token)) {
                if (operandStack.size() < 2) {
                    throw new IllegalArgumentException("Invalid expression: Not enough operands");
                }
                double b = operandStack.pop();
                double a = operandStack.pop();
                operandStack.push(performOperation(token, a, b));
            } else {
                try {
                    operandStack.push(Double.parseDouble(token));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid token: " + token);
                }
            }
        }

        if (operandStack.size() != 1) {
            throw new IllegalArgumentException("Invalid expression: Too many operands left");
        }

        return new Response<>(operandStack.pop());
    }

    private boolean isOperator(String token) {
        return "+-*/".contains(token);
    }

    private double performOperation(String operator, double a, double b) {
        return switch (operator) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "*" -> a * b;
            case "/" -> {
                if (b == 0) {
                    throw new IllegalArgumentException("Division by zero is not allowed");
                }
                yield a / b;
            }
            default -> throw new IllegalArgumentException("Invalid operator: " + operator);
        };
    }
}
