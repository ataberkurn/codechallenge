package com.codechallenge.api.controller;

import com.codechallenge.api.model.Request;
import com.codechallenge.api.model.Response;
import com.codechallenge.api.service.MinesweeperService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/minesweeper")
@RequiredArgsConstructor
public class MinesweeperController {

    private final MinesweeperService minesweeperService;

    @PostMapping("/show-hints")
    public Response<List<String>> showHints(@RequestBody Request<List<String>> request) {
        return minesweeperService.showHints(request);
    }
}
