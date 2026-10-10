# Problem 10: Busiest Bus Row — Complete Solutions (QLA)

**Category:** Category C · Coding Assignment  
**Program:** STEP SEM-3 · CodInClub | Powered by BridgeLabz  
**Topic:** 2D Arrays, Matrix Traversal, Aggregation & Tie-Breaking Algorithms  

---

## Table of Contents
1. [Part A — Problem Statement & Specifications](#part-a--problem-statement--specifications)
2. [Part B — Algorithmic Design & Implementation](#part-b--algorithmic-design--implementation)
   - [Core Algorithm](#core-algorithm)
   - [Tie-Breaking Invariant](#tie-breaking-invariant)
   - [Java Implementation](#java-implementation)
   - [Python Implementation](#python-implementation)
3. [Part C — Complexity Analysis](#part-c--complexity-analysis)
   - [Time Complexity](#time-complexity)
   - [Space Complexity](#space-complexity)
4. [Part D — Step-by-Step Execution Trace](#part-d--step-by-step-execution-trace)
5. [Part E — Edge Cases & Validation Matrix](#part-e--edge-cases--validation-matrix)
6. [Part F — Conceptual Q&A on 2D Arrays & Traversal](#part-f--conceptual-qa-on-2d-arrays--traversal)

---

# Part A — Problem Statement & Specifications

### Problem Description
A college bus records how many students sit in each seat as a 2-dimensional grid ($r$ rows $\times$ $c$ columns), where each row corresponds to one physical row of seats in the bus. The college transport office needs to know which row carries the highest total number of students.

### Requirements
Implement a function `busiestRow(grid)` that determines:
1. The **index of the row** (0-indexed) with the largest total students.
2. The **largest total** number of students found in that row.
3. If two or more rows tie with the identical largest total, return the row with the **smaller row index**.

### Constraints
- $1 \le \text{rows}, \text{columns} \le 10$
- $0 \le \text{grid}[i][j] \le 3$

### Example
**Input Grid:**
```text
grid = [
  [2, 0, 1],
  [3, 3, 1],
  [1, 1, 1]
]
```

**Row Computations:**
- Row 0: $2 + 0 + 1 = 3$
- Row 1: $3 + 3 + 1 = 7$
- Row 2: $1 + 1 + 1 = 3$

**Expected Output:**
```text
Row 1, Total 7
```

---

# Part B — Algorithmic Design & Implementation

### Core Algorithm
To find the busiest row, we iterate across the 2D grid using a nested loop structure:
1. Maintain two tracking variables:
   - `bestRow`: The index of the row with the current maximum student count (initialized to `0`).
   - `maxTotal`: The maximum student count encountered so far (initialized to `-1` or first row's total).
2. **Outer Loop (Row Traversal):** Iterate index $r$ from $0$ to $\text{grid.length} - 1$.
3. **Inner Loop (Column Accumulation):** Iterate index $c$ from $0$ to $\text{grid}[r].\text{length} - 1$, accumulating the row sum:
   $$\text{currentRowTotal} = \sum_{c=0}^{\text{cols}-1} \text{grid}[r][c]$$
4. **Conditional Update (Strict Inequality):**
   ```text
   if (currentRowTotal > maxTotal) {
       maxTotal = currentRowTotal;
       bestRow = r;
   }
   ```
5. Return `(bestRow, maxTotal)`.

### Tie-Breaking Invariant
The problem requires: *"If two rows tie, return the one with the smaller index."*

By using a **strictly greater comparison** (`currentRowTotal > maxTotal`) and scanning from row $0$ upwards ($0, 1, \dots, r-1$):
- When a new row achieves a total that matches `maxTotal`, the condition `currentRowTotal > maxTotal` evaluates to `false`.
- Therefore, `bestRow` is **not overwritten**, preserving the earlier (smaller) row index.
- If all rows have the same sum (e.g., all zeros), row 0 is evaluated first, sets `maxTotal = 0`, and no subsequent row overwrites it.

---

### Java Implementation

```java
package src;

import java.util.Scanner;

public class BusiestBusRow {

    public static class RowResult {
        private final int rowIndex;
        private final int total;

        public RowResult(int rowIndex, int total) {
            this.rowIndex = rowIndex;
            this.total = total;
        }

        public int getRowIndex() { return rowIndex; }
        public int getTotal() { return total; }

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
     * Finds the busiest row in O(r * c) time and O(1) auxiliary space.
     * Guarantees tie-breaking returns the smaller row index.
     */
    public static RowResult busiestRow(int[][] grid) {
        if (grid == null || grid.length == 0) {
            throw new IllegalArgumentException("Grid must not be null or empty.");
        }

        int bestRow = 0;
        int maxTotal = -1;

        for (int r = 0; r < grid.length; r++) {
            int currentRowTotal = 0;
            if (grid[r] != null) {
                for (int c = 0; c < grid[r].length; c++) {
                    currentRowTotal += grid[r][c];
                }
            }

            if (currentRowTotal > maxTotal) {
                maxTotal = currentRowTotal;
                bestRow = r;
            }
        }

        return new RowResult(bestRow, Math.max(0, maxTotal));
    }
}
```

---

### Python Implementation

```python
from typing import List, Tuple

def busiest_row(grid: List[List[int]]) -> Tuple[int, int]:
    """
    Finds the index of the row with the largest total students and the total.
    Ties broken in favor of the smaller index.
    
    Time Complexity:  O(r * c)
    Auxiliary Space:  O(1)
    """
    if not grid:
        raise ValueError("Grid must not be null or empty.")

    best_row = 0
    max_total = -1

    for r, row in enumerate(grid):
        row_total = sum(row)
        if row_total > max_total:
            max_total = row_total
            best_row = r

    return best_row, max(0, max_total)
```

---

# Part C — Complexity Analysis

### Time Complexity
- Let $r$ be the number of rows in the grid ($1 \le r \le 10$).
- Let $c$ be the number of columns in the grid ($1 \le c \le 10$).

1. The outer loop executes exactly $r$ times.
2. For each iteration of the outer loop, the inner loop iterates across all $c$ columns of that row, performing a single addition operation.
3. The comparison `currentRowTotal > maxTotal` and variable assignments are $O(1)$ operations performed once per row.
4. Total elementary operations:
   $$T(r, c) = \sum_{i=0}^{r-1} \sum_{j=0}^{c-1} \Theta(1) = r \times c \times \Theta(1) = \mathbf{O(r \times c)}$$
5. Given the constraint $r, c \le 10$, the maximum number of operations is $10 \times 10 = 100$, executing essentially instantaneously ($< 1\,\text{ms}$).

### Additional (Auxiliary) Space Complexity
- The algorithm does not allocate any dynamic data structures, additional arrays, lists, or recursion stacks.
- Only a fixed number of scalar variables are maintained during runtime:
  - `bestRow` (integer primitive)
  - `maxTotal` (integer primitive)
  - `currentRowTotal` (integer primitive)
  - Loop counters $r$ and $c$
- Therefore, the auxiliary space used is:
  $$\mathbf{O(1)} \quad (\text{constant auxiliary space})$$

---

# Part D — Step-by-Step Execution Trace

### Example Input
$$\text{grid} = \begin{bmatrix} 2 & 0 & 1 \\ 3 & 3 & 1 \\ 1 & 1 & 1 \end{bmatrix}, \quad r = 3, \; c = 3$$

| Step | Row ($r$) | Elements | Inner Loop Sum | `currentRowTotal` | Comparison (`total > maxTotal`) | Updated `bestRow` | Updated `maxTotal` | Explanation |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :--- |
| **Init** | - | - | - | - | - | `0` | `-1` | Baseline initialization |
| **1** | `0` | `[2, 0, 1]` | $2 + 0 + 1$ | **3** | $3 > -1 \implies \text{True}$ | `0` | `3` | Row 0 establishes initial maximum |
| **2** | `1` | `[3, 3, 1]` | $3 + 3 + 1$ | **7** | $7 > 3 \implies \text{True}$ | `1` | `7` | Row 1 is strictly greater; updates best |
| **3** | `2` | `[1, 1, 1]` | $1 + 1 + 1$ | **3** | $3 > 7 \implies \text{False}$ | `1` | `7` | Row 2 is not greater; retain Row 1 |

**Final Result:** `Row 1, Total 7`

---

# Part E — Edge Cases & Validation Matrix

| Test Case | Grid Input | Expected Result | Reason / Invariant Validated |
| :--- | :--- | :--- | :--- |
| **Standard Example** | `[[2, 0, 1], [3, 3, 1], [1, 1, 1]]` | `Row 1, Total 7` | Core functionality matching assignment specification |
| **Tie (Adjacent Rows)** | `[[1, 2], [3, 3], [3, 3]]` | `Row 1, Total 6` | Row 1 and Row 2 tie at 6; Row 1 chosen because $1 < 2$ |
| **Tie (Non-Adjacent)** | `[[5, 0], [1, 1], [2, 3]]` | `Row 0, Total 5` | Row 0 and Row 2 tie at 5; Row 0 chosen because $0 < 2$ |
| **Single Seat ($1 \times 1$)** | `[[2]]` | `Row 0, Total 2` | Minimum grid dimension boundary |
| **Single Row ($1 \times 5$)** | `[[1, 2, 3, 0, 1]]` | `Row 0, Total 7` | Row count $r = 1$ |
| **Single Col ($4 \times 1$)** | `[[0], [2], [3], [1]]` | `Row 2, Total 3` | Column count $c = 1$ |
| **All Zeroes Grid** | `[[0, 0], [0, 0], [0, 0]]` | `Row 0, Total 0` | All rows tie at 0; row 0 selected |
| **Maximum Bounds ($10 \times 10$)**| $10 \times 10$ with elements all $3$ | `Row 0, Total 30` | Upper constraint bound ($r=10, c=10, \text{val}=3$) |

---

# Part F — Conceptual Q&A on 2D Arrays & Traversal

### Q1: Why is row-major traversal more cache-friendly than column-major traversal in languages like Java and C?
**Answer:**  
In Java, a 2D array (`int[][]`) is implemented as an "array of arrays", where each row is a contiguous block of memory referenced by a pointer in the top-level array. In C/C++, a 2D array is stored in contiguous memory in row-major order.
When iterating row by row (`grid[r][0], grid[r][1], grid[r][2], ...`), elements that are adjacent in memory are accessed sequentially. The CPU cache line loads contiguous memory blocks, resulting in **spatial locality** and near-zero cache misses. In contrast, column-major traversal (`grid[0][c], grid[1][c], ...`) strides across different heap objects or distant memory locations, causing frequent CPU cache misses.

### Q2: How does the choice of comparison operator (`>` vs `>=`) impact the stability of tie-breaking?
**Answer:**  
When searching for an extremum (maximum or minimum) in an indexed collection:
- Using **strictly greater** (`>`) updates the result *only* when an element strictly exceeds the current maximum. This preserves the **first occurrence** (smallest index) when multiple elements have identical maximum values.
- Using **greater than or equal to** (`>=`) overwrites the tracker whenever a subsequent element matches the current maximum, resulting in the **last occurrence** (largest index) winning.
Because the assignment demands the smaller index on ties, strict inequality (`>`) is mandatory.

### Q3: What is the difference between Time Complexity and Auxiliary Space Complexity?
**Answer:**  
- **Time Complexity** measures the asymptotic growth of the number of basic execution steps (comparisons, additions, memory reads) as input dimensions $r$ and $c$ scale.
- **Auxiliary Space Complexity** measures only the *additional* memory allocated by the algorithm to perform its computation, excluding the memory already used by the input itself. In our solution, auxiliary space is $O(1)$ because no auxiliary arrays or structures proportional to $r$ or $c$ are created.
