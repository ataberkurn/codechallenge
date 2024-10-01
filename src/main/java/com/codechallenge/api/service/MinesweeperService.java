package com.codechallenge.api.service;

import com.codechallenge.api.model.Request;
import com.codechallenge.api.model.Response;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MinesweeperService {

    private static final char MINE = '*';
    private static final char EMPTY = '0';

    public Response<List<String>> showHints(Request<List<String>> request) {
        List<String> grid = request.data();

        if (grid == null || grid.isEmpty()) {
            throw new IllegalArgumentException("Input grid cannot be null or empty");
        }

        int rowCount = grid.size();
        int columnCount = grid.get(0).length();

        for (String row : grid) {
            if (row.length() != columnCount) {
                throw new IllegalArgumentException("All rows must have the same number of columns");
            }
        }

        char[][] hints = new char[rowCount][columnCount];

        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < columnCount; col++) {
                hints[row][col] = grid.get(row).charAt(col) == MINE ? MINE : calculateHint(grid, row, col);
            }
        }

        List<String> result = convertToStringList(hints);

        return new Response<>(result);
    }

    private char calculateHint(List<String> grid, int row, int col) {
        int adjacentMines = 0;
        int rowCount = grid.size();
        int columnCount = grid.get(0).length();

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;

                int newRow = row + i;
                int newCol = col + j;

                if (isInBounds(newRow, newCol, rowCount, columnCount) && grid.get(newRow).charAt(newCol) == MINE) {
                    adjacentMines++;
                }
            }
        }

        return adjacentMines > 0 ? (char) (adjacentMines + '0') : EMPTY;
    }

    private boolean isInBounds(int row, int col, int rowCount, int columnCount) {
        return row >= 0 && row < rowCount && col >= 0 && col < columnCount;
    }

    private List<String> convertToStringList(char[][] hints) {
        return Arrays.stream(hints)
                .map(String::new)
                .collect(Collectors.toList());
    }
}
