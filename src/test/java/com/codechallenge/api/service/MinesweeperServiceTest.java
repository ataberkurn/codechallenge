package com.codechallenge.api.service;

import com.codechallenge.api.model.Request;
import com.codechallenge.api.model.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MinesweeperServiceTest {

    private MinesweeperService minesweeperService;

    @BeforeEach
    public void setUp() {
        minesweeperService = new MinesweeperService();
    }

    @Test
    public void testShowHints_ValidInput() {
        Request<List<String>> request = new Request<>(Arrays.asList(
                "*...",
                "....",
                ".*..",
                "...."
        ));

        Response<List<String>> response = minesweeperService.showHints(request);

        List<String> expectedHints = Arrays.asList(
                "*100",
                "2210",
                "1*10",
                "1110"
        );

        assertEquals(expectedHints, response.result());
    }

    @Test
    public void testShowHints_EmptyGrid() {
        Request<List<String>> request = new Request<>(List.of());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> minesweeperService.showHints(request));

        assertEquals("Input grid cannot be null or empty", exception.getMessage());
    }

    @Test
    public void testShowHints_NullGrid() {
        Request<List<String>> request = new Request<>(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> minesweeperService.showHints(request));

        assertEquals("Input grid cannot be null or empty", exception.getMessage());
    }

    @Test
    public void testShowHints_VariableRowLengths() {
        Request<List<String>> request = new Request<>(Arrays.asList(
                "**..",
                "....",
                ".*.",
                "..."
        ));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> minesweeperService.showHints(request));

        assertEquals("All rows must have the same number of columns", exception.getMessage());
    }

    @Test
    public void testShowHints_RowWithDifferentLengths() {
        Request<List<String>> request = new Request<>(Arrays.asList(
                "**..",
                "....",
                ".*",
                "..."
        ));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> minesweeperService.showHints(request));

        assertEquals("All rows must have the same number of columns", exception.getMessage());
    }
}
