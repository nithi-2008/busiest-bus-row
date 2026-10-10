import unittest
from busiest_bus_row import busiest_row, format_output


class TestBusiestBusRow(unittest.TestCase):

    def test_standard_example(self):
        grid = [
            [2, 0, 1],
            [3, 3, 1],
            [1, 1, 1]
        ]
        row_idx, total = busiest_row(grid)
        self.assertEqual(row_idx, 1)
        self.assertEqual(total, 7)
        self.assertEqual(format_output(row_idx, total), "Row 1, Total 7")

    def test_tie_breaking_adjacent_rows(self):
        # Both row 1 and row 2 have sum 6. Row 1 must win.
        grid = [
            [1, 2],
            [3, 3],
            [3, 3]
        ]
        row_idx, total = busiest_row(grid)
        self.assertEqual(row_idx, 1)
        self.assertEqual(total, 6)

    def test_tie_breaking_non_adjacent_rows(self):
        # Row 0 and Row 2 have sum 5. Row 0 must win.
        grid = [
            [5, 0],
            [1, 1],
            [2, 3]
        ]
        row_idx, total = busiest_row(grid)
        self.assertEqual(row_idx, 0)
        self.assertEqual(total, 5)

    def test_single_element_grid(self):
        grid = [[2]]
        row_idx, total = busiest_row(grid)
        self.assertEqual(row_idx, 0)
        self.assertEqual(total, 2)

    def test_single_row_multi_cols(self):
        grid = [[1, 2, 3, 0, 1]]
        row_idx, total = busiest_row(grid)
        self.assertEqual(row_idx, 0)
        self.assertEqual(total, 7)

    def test_multi_rows_single_col(self):
        grid = [
            [0],
            [2],
            [3],
            [1]
        ]
        row_idx, total = busiest_row(grid)
        self.assertEqual(row_idx, 2)
        self.assertEqual(total, 3)

    def test_all_zeroes_grid(self):
        grid = [
            [0, 0],
            [0, 0],
            [0, 0]
        ]
        row_idx, total = busiest_row(grid)
        self.assertEqual(row_idx, 0)
        self.assertEqual(total, 0)

    def test_first_row_winner(self):
        grid = [
            [3, 3, 3],
            [1, 1, 1],
            [0, 1, 0]
        ]
        row_idx, total = busiest_row(grid)
        self.assertEqual(row_idx, 0)
        self.assertEqual(total, 9)

    def test_last_row_winner(self):
        grid = [
            [1, 0],
            [2, 1],
            [3, 3]
        ]
        row_idx, total = busiest_row(grid)
        self.assertEqual(row_idx, 2)
        self.assertEqual(total, 6)

    def test_max_constraints(self):
        grid = [[3] * 10 for _ in range(10)]
        row_idx, total = busiest_row(grid)
        self.assertEqual(row_idx, 0)
        self.assertEqual(total, 30)

        # Make row 7 strictly largest
        grid[7][0] = 4
        row_idx2, total2 = busiest_row(grid)
        self.assertEqual(row_idx2, 7)
        self.assertEqual(total2, 31)

    def test_empty_grid_raises(self):
        with self.assertRaises(ValueError):
            busiest_row([])


if __name__ == "__main__":
    unittest.main()
