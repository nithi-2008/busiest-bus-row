package tests;

import src.BusiestBusRow;
import src.BusiestBusRow.RowResult;

/**
 * Automated Test Suite for Problem 10: Busiest Bus Row.
 * Validates correctness, edge cases, tie-breaking rules, and constraint limits.
 */
public class BusiestBusRowTest {

    private static int totalPassed = 0;
    private static int totalFailed = 0;

    private static void assertEquals(String testName, Object expected, Object actual) {
        if (expected == null && actual == null) {
            System.out.println("  [PASS] " + testName);
            totalPassed++;
        } else if (expected != null && expected.equals(actual)) {
            System.out.println("  [PASS] " + testName);
            totalPassed++;
        } else {
            System.err.println("  [FAIL] " + testName + " | Expected: " + expected + ", Got: " + actual);
            totalFailed++;
        }
    }

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println(" Running Test Suite: Busiest Bus Row (Problem 10)");
        System.out.println("=================================================");

        testStandardExample();
        testTieBreakingAdjacentRows();
        testTieBreakingNonAdjacentRows();
        testSingleElementGrid();
        testSingleRowMultiCols();
        testMultiRowsSingleCol();
        testAllZeroesGrid();
        testFirstRowWinner();
        testLastRowWinner();
        testMaxConstraintsGrid();
        testStringParser();
        testInvalidInputs();

        System.out.println("\n-------------------------------------------------");
        System.out.println("Test Results: " + totalPassed + " passed, " + totalFailed + " failed.");
        System.out.println("=================================================");

        if (totalFailed > 0) {
            System.exit(1);
        }
    }

    private static void testStandardExample() {
        System.out.println("\n--- Test 1: Standard Example from Assignment ---");
        int[][] grid = {
            {2, 0, 1},
            {3, 3, 1},
            {1, 1, 1}
        };
        RowResult result = BusiestBusRow.busiestRow(grid);
        assertEquals("Standard Example Result", new RowResult(1, 7), result);
        assertEquals("Formatted String Output", "Row 1, Total 7", result.toString());
    }

    private static void testTieBreakingAdjacentRows() {
        System.out.println("\n--- Test 2: Tie-Breaking Between Adjacent Rows ---");
        // Row 1 and Row 2 both sum to 6. Row 1 must win because 1 < 2.
        int[][] grid = {
            {1, 2},
            {3, 3},
            {3, 3}
        };
        RowResult result = BusiestBusRow.busiestRow(grid);
        assertEquals("Tie Break (Row 1 vs Row 2)", new RowResult(1, 6), result);
    }

    private static void testTieBreakingNonAdjacentRows() {
        System.out.println("\n--- Test 3: Tie-Breaking Between Non-Adjacent Rows ---");
        // Row 0 and Row 2 both sum to 5. Row 0 must win because 0 < 2.
        int[][] grid = {
            {5, 0},
            {1, 1},
            {2, 3}
        };
        RowResult result = BusiestBusRow.busiestRow(grid);
        assertEquals("Tie Break (Row 0 vs Row 2)", new RowResult(0, 5), result);
    }

    private static void testSingleElementGrid() {
        System.out.println("\n--- Test 4: Single 1x1 Seat Grid ---");
        int[][] grid = {{2}};
        RowResult result = BusiestBusRow.busiestRow(grid);
        assertEquals("1x1 Grid", new RowResult(0, 2), result);
    }

    private static void testSingleRowMultiCols() {
        System.out.println("\n--- Test 5: Single Row with Multiple Columns (1x5) ---");
        int[][] grid = {
            {1, 2, 3, 0, 1}
        };
        RowResult result = BusiestBusRow.busiestRow(grid);
        assertEquals("1x5 Grid", new RowResult(0, 7), result);
    }

    private static void testMultiRowsSingleCol() {
        System.out.println("\n--- Test 6: Multiple Rows with Single Column (4x1) ---");
        int[][] grid = {
            {0},
            {2},
            {3},
            {1}
        };
        RowResult result = BusiestBusRow.busiestRow(grid);
        assertEquals("4x1 Grid", new RowResult(2, 3), result);
    }

    private static void testAllZeroesGrid() {
        System.out.println("\n--- Test 7: All Zeroes Grid (All Rows Tie at 0) ---");
        // When all rows have 0 students, Row 0 has smaller index.
        int[][] grid = {
            {0, 0},
            {0, 0},
            {0, 0}
        };
        RowResult result = BusiestBusRow.busiestRow(grid);
        assertEquals("All Zeroes (Defaults to Row 0)", new RowResult(0, 0), result);
    }

    private static void testFirstRowWinner() {
        System.out.println("\n--- Test 8: First Row is Strictly Largest ---");
        int[][] grid = {
            {3, 3, 3},
            {1, 1, 1},
            {0, 1, 0}
        };
        RowResult result = BusiestBusRow.busiestRow(grid);
        assertEquals("Row 0 Winner", new RowResult(0, 9), result);
    }

    private static void testLastRowWinner() {
        System.out.println("\n--- Test 9: Last Row is Strictly Largest ---");
        int[][] grid = {
            {1, 0},
            {2, 1},
            {3, 3}
        };
        RowResult result = BusiestBusRow.busiestRow(grid);
        assertEquals("Row 2 Winner", new RowResult(2, 6), result);
    }

    private static void testMaxConstraintsGrid() {
        System.out.println("\n--- Test 10: Maximum Constraints (10x10 Grid with Max Seat Count 3) ---");
        int[][] grid = new int[10][10];
        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 10; c++) {
                grid[r][c] = 3;
            }
        }
        // All rows sum to 30; Row 0 must win due to tie break.
        RowResult result = BusiestBusRow.busiestRow(grid);
        assertEquals("10x10 Tie Break at Maximum Values", new RowResult(0, 30), result);

        // Make row 7 strictly highest
        grid[7][0] = 4; // row 7 now sums to 31
        RowResult result2 = BusiestBusRow.busiestRow(grid);
        assertEquals("10x10 Unique Maximum at Row 7", new RowResult(7, 31), result2);
    }

    private static void testStringParser() {
        System.out.println("\n--- Test 11: String Grid Parser ---");
        String input = "[[2, 0, 1], [3, 3, 1], [1, 1, 1]]";
        int[][] grid = BusiestBusRow.parseGridFromString(input);
        RowResult result = BusiestBusRow.busiestRow(grid);
        assertEquals("Parsed Grid Calculation", new RowResult(1, 7), result);
    }

    private static void testInvalidInputs() {
        System.out.println("\n--- Test 12: Invalid Input Handling ---");
        try {
            BusiestBusRow.busiestRow(null);
            System.err.println("  [FAIL] Expected exception on null grid");
            totalFailed++;
        } catch (IllegalArgumentException e) {
            System.out.println("  [PASS] Correctly rejected null grid");
            totalPassed++;
        }

        try {
            BusiestBusRow.busiestRow(new int[0][0]);
            System.err.println("  [FAIL] Expected exception on empty grid");
            totalFailed++;
        } catch (IllegalArgumentException e) {
            System.out.println("  [PASS] Correctly rejected empty grid");
            totalPassed++;
        }
    }
}
