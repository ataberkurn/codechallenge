package com.codechallenge.api.controller;

import com.codechallenge.api.model.Request;
import com.codechallenge.api.model.Response;
import com.codechallenge.api.service.CalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/calculators")
@RequiredArgsConstructor
public class CalculatorController {

    private final CalculatorService calculatorService;

    @PostMapping("/calculate")
    public Response<Double> calculate(@RequestBody Request<String> request) {
        return calculatorService.calculate(request);
    }
}
