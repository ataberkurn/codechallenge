package com.codechallenge.api.service;

import com.codechallenge.api.model.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalculatorServiceTest {

    private CalculatorService calculatorService;

    @BeforeEach
    public void setUp() {
        calculatorService = new CalculatorService();
    }

    @Test
    public void testCalculate_SimpleExpression() {
        Request<String> request = new Request<>("15 5 /");

        double result = calculatorService.calculate(request).result();

        assertEquals(3.0, result);
    }

    @Test
    public void testCalculate_ComplexExpression() {
        Request<String> request = new Request<>("2 1 5 * 5 - *");

        double result = calculatorService.calculate(request).result();

        assertEquals(0.0, result);
    }

    @Test
    public void testCalculate_InvalidExpression_UnknownToken() {
        Request<String> request = new Request<>("5 3 .");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> calculatorService.calculate(request));

        assertEquals("Invalid token: .", exception.getMessage());
    }

    @Test
    public void testCalculate_InvalidExpression_TooFewOperands() {
        Request<String> request = new Request<>("5 +");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> calculatorService.calculate(request));

        assertEquals("Invalid expression: Not enough operands", exception.getMessage());
    }

    @Test
    public void testCalculate_InvalidExpression_ExtraOperands() {
        Request<String> request = new Request<>("3 4 + 2");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> calculatorService.calculate(request));

        assertEquals("Invalid expression: Too many operands left", exception.getMessage());
    }

    @Test
    public void testCalculate_DivisionByZero() {
        Request<String> request = new Request<>("9 0 /");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> calculatorService.calculate(request));

        assertEquals("Division by zero is not allowed", exception.getMessage());
    }
}
