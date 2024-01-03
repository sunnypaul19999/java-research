package com.ra.javaresearch.oa.company.jpmc.oa2;

class Solution {
  final String colors;
  final int len;
  final char[] state;
  final int[][][] memo;

  private Solution(final String colors) {
    this.colors = colors;
    this.len = colors.length();
    state = colors.toCharArray();
    memo = new int[2][len][len];
  }

  private boolean isValidIndex(final int i, final int j) {
    return i >= 0 && j < len;
  }

  private boolean isValidPick(final int i, final char c) {
    boolean left = false, right = false;

    for (int k = i - 1; k >= 0; k--) {
      if (state[k] != 'x') {
        if (state[k] == c) {
          left = true;
        }
        break;
      }
    }

    for (int l = i + 1; l < len; l++) {
      if (state[l] != 'x') {
        if (state[l] == c) {
          right = true;
        }
        break;
      }
    }

    return left && right;
  }

  private int nextValidWhitePos(final int whitePos) {
    for (int i = whitePos + 1; i < len; i++) {
      if (state[i] == 'w' && isValidPick(i, 'w')) {
        return i;
      }
    }
    return -1;
  }

  private int nextValidBlackPos(final int blackPos) {
    for (int i = blackPos + 1; i < len; i++) {
      if (state[i] == 'b' && isValidPick(i, 'b')) {
        return i;
      }
    }
    return -1;
  }

  private int dp(final int turn, final int prevWhitePos, final int prevBlackPos) {
    if (turn == 0) {
      int takeNextWhitePos = 1, notTakeNextWhitePos = 1;
      if (prevWhitePos > -1) {
        final int nextWhitePos = nextValidWhitePos(-1);
        if (nextWhitePos > -1) {
          state[nextWhitePos] = 'x';
          takeNextWhitePos = dp(1, 0, prevBlackPos);
        }
        state[nextWhitePos] = 'w';
        notTakeNextWhitePos = dp(0, nextValidWhitePos(prevWhitePos), prevWhitePos);
      }
      return (takeNextWhitePos == 0 || notTakeNextWhitePos == 0) ? 0 : 1;
    } else {
      int takeNextBlackPos = 0, notTakeNextBlackPos = 0;
      if (prevBlackPos > -1) {
        final int nextWhitePos = nextValidBlackPos(-1);
        if (nextWhitePos > -1) {
          state[nextWhitePos] = 'x';
          takeNextBlackPos = dp(1, 0, prevBlackPos);
        }
        state[nextWhitePos] = 'b';
        notTakeNextBlackPos = dp(0, nextValidBlackPos(prevBlackPos), prevBlackPos);
      }
      return (takeNextBlackPos == 1 || notTakeNextBlackPos == 1) ? 1 : 0;
    }
  }

  public static String run(final String colors) {
    final Solution solution = new Solution(colors);
    return solution.dp(0, solution.nextValidWhitePos(-1), solution.nextValidBlackPos(-1)) == 0
        ? "wendy"
        : "bob";
  }
}

public class GameWinner {
  public static void main(String[] args) {
    System.out.println(Solution.run("ww"));
  }
}
