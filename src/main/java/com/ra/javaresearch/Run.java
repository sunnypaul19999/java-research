package com.ra.javaresearch;

class Solution {
  private int[][] grid;
  private int m, n;
  private boolean[][] seenDfs0;

  private boolean inRange(int i, int j) {
    return (i >= 0 && i < m) && (j >= 0 && j < n);
  }

  private void bfsAdd(int i, int j, int k, int[] curr, int[] max, int[] next) {
    if ((j < 0 || j >= n) || seenDfs0[i][j]) {
      return;
    }
    next[j] = Math.max(next[j], curr[j] + max[k]);
  }

  private int[] bfs1(int i, int[] max) {
    final int[] curr = grid[i];
    final int[] next = new int[n];
    for (int j = 0; j < n; j++) {
      bfsAdd(i, j - 1, j, curr, max, next);
      bfsAdd(i, j, j, curr, max, next);
      bfsAdd(i, j + 1, j, curr, max, next);
    }
    return next;
  }

  private int bfs0() {
    int[] max = new int[n];
    for (int i = m - 1; i >= 0; i--) {
      max = bfs1(i, max);
    }
    return max[n - 1];
  }

  private int dfs0(int i, int j) {
    if (!inRange(i, j)) {
      return bfs0();
    }
    seenDfs0[i][j] = true;
    int max = 0;
    max = Math.max(max, dfs0(i + 1, j - 1));
    max = Math.max(max, dfs0(i + 1, j));
    max = Math.max(max, dfs0(i + 1, j + 1));
    max += grid[i][j];
    seenDfs0[i][j] = false;
    return max;
  }

  public int cherryPickup(int[][] grid) {
    this.grid = grid;
    m = grid.length;
    n = grid[0].length;
    seenDfs0 = new boolean[m][n];
    return dfs0(0, 0);
  }
}

public class Run {
  public static void main(String[] args) {
    int[][] grid = {
      {16, 1, 9, 16, 5, 16, 3, 16, 6, 3, 1, 7},
      {10, 0, 0, 12, 9, 12, 19, 3, 9, 3, 18, 9},
      {18, 6, 6, 13, 2, 1, 9, 8, 12, 2, 10, 6},
      {16, 7, 6, 10, 5, 17, 16, 0, 12, 7, 5, 15},
      {20, 11, 17, 15, 2, 8, 12, 2, 17, 13, 12, 0},
      {11, 1, 10, 11, 13, 9, 16, 7, 1, 12, 13, 8},
      {12, 19, 19, 3, 13, 0, 7, 1, 1, 3, 1, 16},
      {4, 9, 1, 4, 16, 19, 11, 11, 3, 9, 2, 7},
      {10, 13, 1, 4, 3, 7, 14, 3, 20, 7, 6, 11},
      {8, 17, 14, 13, 2, 5, 9, 11, 11, 18, 19, 15},
      {16, 4, 3, 13, 18, 17, 14, 16, 15, 12, 20, 13},
      {20, 0, 19, 16, 0, 3, 16, 16, 1, 15, 2, 20},
      {20, 18, 8, 11, 0, 13, 8, 7, 13, 6, 18, 19},
      {9, 2, 9, 18, 10, 16, 0, 5, 9, 11, 4, 14},
      {11, 4, 18, 8, 18, 18, 10, 12, 11, 0, 10, 13},
      {0, 7, 9, 2, 1, 17, 4, 1, 6, 9, 7, 9}
    };
    //    grid = new int[][] {{3, 1, 1}, {2, 5, 1}, {1, 5, 5}, {2, 1, 1}};

    Solution solution = new Solution();
    System.out.println(solution.cherryPickup(grid));
  }
}
