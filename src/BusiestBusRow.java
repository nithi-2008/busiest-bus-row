package src;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Problem 10: Busiest Bus Row
 *
 * TASK:
 * A college bus records how many students sit in each seat as a 2D grid,
 * where each row is one row of seats. The transport office wants to know
 * which row carries the most students.
 *
 * Implement a function busiestRow(grid) that returns the index of the row
 * with the largest total and that total. If two rows tie, return the one
 * with the smaller index.
 *
 * CONSTRAINTS:
 * • 1 <= rows, columns <= 10
 * • 0 <= grid[i][j] <= 3
 *
 * COMPLEXITY:
 * • Time Complexity:  O(r * c) where r is rows and c is columns
 * • Auxiliary Space:  O(1)
 */
public class BusiestBusRow {

    /**
     * Data carrier for the result of the busiest row computation.
     */
    public static class RowResult {
        private final int rowIndex;
        private final int total;

        public RowResult(int rowIndex, int total) {
            this.rowIndex = rowIndex;
            this.total = total;
        }

        public int getRowIndex() {
            return rowIndex;
        }

        public int getTotal() {
            return total;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            RowResult other = (RowResult) obj;
            return this.rowIndex == other.rowIndex && this.total == other.total;
        }

        @Override
        public int hashCode() {
            return 31 * rowIndex + total;
        }

        @Override
        public String toString() {
            return "Row " + rowIndex + ", Total " + total;
        }
    }

    /**
     * Computes the index and student count of the row carrying the most students.
     * If two rows tie with the same total, returns the row with the smaller index.
     *
     * @param grid 2D array representing student counts in bus seats
     * @return RowResult containing the best row index and student count
     * @throws IllegalArgumentException if the grid is null or empty
     */
    public static RowResult busiestRow(int[][] grid) {
        if (grid == null || grid.length == 0) {
            throw new IllegalArgumentException("Grid must not be null or empty.");
        }

        int bestRow = 0;
        int maxTotal = -1;

        // Outer loop iterates through each row
        for (int r = 0; r < grid.length; r++) {
            int currentRowTotal = 0;

            if (grid[r] != null) {
                // Inner loop accumulates the sum of students in the current row
                for (int c = 0; c < grid[r].length; c++) {
                    currentRowTotal += grid[r][c];
                }
            }

            // Strictly greater condition ensures tie-breaking favors smaller row indices
            if (currentRowTotal > maxTotal) {
                maxTotal = currentRowTotal;
                bestRow = r;
            }
        }

        return new RowResult(bestRow, Math.max(0, maxTotal));
    }

    /**
     * Parses standard formatted string representation of a 2D grid,
     * e.g., "[[2, 0, 1], [3, 3, 1], [1, 1, 1]]".
     *
     * @param input raw string representation
     * @return 2D integer array
     */
    public static int[][] parseGridFromString(String input) {
        if (input == null || input.trim().isEmpty()) {
            return new int[0][0];
        }

        String cleaned = input.trim();
        if (cleaned.startsWith("[") && cleaned.endsWith("]")) {
            cleaned = cleaned.substring(1, cleaned.length() - 1).trim();
        }

        String[] rowTokens = cleaned.split("\\]\\s*,\\s*\\[");
        int[][] grid = new int[rowTokens.length][];

        for (int i = 0; i < rowTokens.length; i++) {
            String rowStr = rowTokens[i].replace("[", "").replace("]", "").trim();
            if (rowStr.isEmpty()) {
                grid[i] = new int[0];
                continue;
            }
            String[] colTokens = rowStr.split("[,\\s]+");
            grid[i] = new int[colTokens.length];
            for (int j = 0; j < colTokens.length; j++) {
                grid[i][j] = Integer.parseInt(colTokens[j].trim());
            }
        }

        return grid;
    }

    /**
     * Main CLI entry point.
     * Supports two input modes:
     * 1. Dimensions followed by matrix elements (e.g., "3 3 \n 2 0 1 \n 3 3 1 \n 1 1 1")
     * 2. Bracketed format string (e.g., "[[2, 0, 1], [3, 3, 1], [1, 1, 1]]")
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        if (!scanner.hasNext()) {
            System.out.println("No input provided. Running default example:");
            int[][] sample = {
                {2, 0, 1},
                {3, 3, 1},
                {1, 1, 1}
            };
            System.out.println(busiestRow(sample));
            scanner.close();
            return;
        }

        String firstToken = scanner.next();

        try {
            // Check if first token is an integer representing number of rows
            int rows = Integer.parseInt(firstToken);
            int cols = scanner.nextInt();
            int[][] grid = new int[rows][cols];
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    grid[r][c] = scanner.nextInt();
                }
            }
            RowResult result = busiestRow(grid);
            System.out.println(result);
        } catch (NumberFormatException e) {
            // Parse remainder of input as bracketed text
            StringBuilder sb = new StringBuilder(firstToken);
            while (scanner.hasNextLine()) {
                sb.append(" ").append(scanner.nextLine());
            }
            int[][] grid = parseGridFromString(sb.toString());
            RowResult result = busiestRow(grid);
            System.out.println(result);
        } finally {
            scanner.close();
        }
    }
}
