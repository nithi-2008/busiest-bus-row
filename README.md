# Problem 10: Busiest Bus Row

[![Java](https://img.shields.io/badge/Java-8%2B-ED8B00?logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Python](https://img.shields.io/badge/Python-3.8%2B-3776AB?logo=python&logoColor=white)](https://www.python.org/)
[![Status](https://img.shields.io/badge/Tests-15%20Passed-brightgreen)](tests/BusiestBusRowTest.java)

Complete solution and test suite for **Category C · Problem 10: Busiest Bus Row** (STEP SEM-3 · CodInClub | Powered by BridgeLabz).

---

## Repository Structure

```text
busiest-bus-row/
├── .gitignore                      # Git ignore rules for Java/Python build artifacts
├── README.md                       # Project overview & running instructions
├── SOLUTIONS.md                    # Complete QLA solutions, complexity analysis & trace
│
├── src/
│   └── BusiestBusRow.java          # Core Java implementation & interactive CLI
├── tests/
│   └── BusiestBusRowTest.java      # Comprehensive automated unit test suite (15 test cases)
│
└── python/
    ├── busiest_bus_row.py          # Python reference implementation & CLI
    └── test_busiest_bus_row.py     # Python automated test suite
```

---

## Problem Summary

A college bus records how many students sit in each seat as a 2D grid, where each row is one row of seats. The transport office wants to know which row carries the most students.

Implement a function `busiestRow(grid)` that returns the index of the row with the largest total and that total. If two rows tie, return the one with the smaller index.

### Constraints
- $1 \le \text{rows}, \text{columns} \le 10$
- $0 \le \text{grid}[i][j] \le 3$

### Example
```text
grid = [
  [2, 0, 1],
  [3, 3, 1],
  [1, 1, 1]
]

Expected Output: Row 1, Total 7
```

### Complexity
- **Time Complexity:** $\mathcal{O}(r \times c)$ where $r$ is the number of rows and $c$ is the number of columns.
- **Auxiliary Space:** $\mathcal{O}(1)$ constant extra space.

---

## How to Compile & Run

### 1. Java

#### Compile Everything
```bash
javac -d out src/BusiestBusRow.java tests/BusiestBusRowTest.java
```

#### Run Automated Test Suite
```bash
java -cp out tests.BusiestBusRowTest
```

#### Run the Program Interactively
```bash
java -cp out src.BusiestBusRow
```
*Provide grid dimensions followed by matrix entries, or formatted string:*
```text
3 3
2 0 1
3 3 1
1 1 1
```
*Output:*
```text
Row 1, Total 7
```

---

### 2. Python

#### Run Automated Tests
```bash
python -m unittest python/test_busiest_bus_row.py
# or using Python launcher:
py -m unittest python/test_busiest_bus_row.py
```

#### Run the Program
```bash
python python/busiest_bus_row.py
```

---

## Detailed QLA Solutions Document

For complete mathematical complexity proofs, step-by-step execution traces, tie-breaking invariant analysis, and conceptual Q&A on 2D arrays, refer to [SOLUTIONS.md](SOLUTIONS.md).
