"""
Problem 10: Busiest Bus Row

TASK:
A college bus records how many students sit in each seat as a 2D grid,
where each row is one row of seats. The transport office wants to know
which row carries the most students.

Implement a function busiestRow(grid) that returns the index of the row
with the largest total and that total. If two rows tie, return the one
with the smaller index.

CONSTRAINTS:
• 1 <= rows, columns <= 10
• 0 <= grid[i][j] <= 3

COMPLEXITY REQUIREMENTS:
• Time Complexity:  O(r * c) where r is rows and c is columns
• Auxiliary Space:  O(1)
"""

from typing import List, Tuple
import ast
import sys


def busiest_row(grid: List[List[int]]) -> Tuple[int, int]:
    """
    Finds the index of the row with the largest total students and the total.
    If two rows tie, returns the row with the smaller index.

    :param grid: 2D list of seat occupancy values
    :return: Tuple of (best_row_index, largest_total)
    """
    if not grid or len(grid) == 0:
        raise ValueError("Grid must not be null or empty.")

    best_row = 0
    max_total = -1

    # Outer loop for rows
    for r, row in enumerate(grid):
        # Inner loop to add up seats in the row
        row_total = sum(row)

        # Update best row only when total is strictly larger
        if row_total > max_total:
            max_total = row_total
            best_row = r

    return best_row, max(0, max_total)


def format_output(row_idx: int, total: int) -> str:
    """Formats output according to problem requirements: 'Row 1, Total 7'."""
    return f"Row {row_idx}, Total {total}"


def main():
    """
    CLI driver supporting bracket format or dimension-based input.
    """
    input_text = sys.stdin.read().strip()
    if not input_text:
        # Default sample example
        sample_grid = [
            [2, 0, 1],
            [3, 3, 1],
            [1, 1, 1]
        ]
        r, t = busiest_row(sample_grid)
        print(format_output(r, t))
        return

    # Check if input is Python/JSON-style list representation
    if input_text.startswith("["):
        grid = ast.literal_eval(input_text)
    else:
        tokens = input_text.split()
        rows = int(tokens[0])
        cols = int(tokens[1])
        vals = [int(x) for x in tokens[2:]]
        grid = []
        for i in range(rows):
            grid.append(vals[i * cols : (i + 1) * cols])

    best_row, max_total = busiest_row(grid)
    print(format_output(best_row, max_total))


if __name__ == "__main__":
    main()
