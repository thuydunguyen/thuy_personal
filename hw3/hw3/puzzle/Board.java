package hw3.puzzle;

import java.util.ArrayList;

import edu.princeton.cs.algs4.Queue;

public class Board implements WorldState {

    /**
     * Returns the string representation of the board.
     * Uncomment this method.
     */


    private int[][] tiles;
    private int size;
    private int[][] goal;

    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

    public Board(int[][] tiles) {
        size = tiles.length;
        goal = new int[size][size];
        int n = 1;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                goal[y][x] = n;
                n++;
            }
        }
        goal[size - 1][size - 1] = 0;
        size = tiles.length;
        this.tiles = tiles;
    }

    public int tileAt(int i, int j) {
        return tiles[i][j];
    }

    public int size() {
        return size;
    }

    @Override
    //Used the staff solution for neighbors
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == 0) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ntile = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ntile[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ntile[bug][zug] = ntile[l11il][lil1il1];
                    ntile[l11il][lil1il1] = 0;
                    Board neighbor = new Board(ntile);
                    neighbors.enqueue(neighbor);
                    ntile[l11il][lil1il1] = ntile[bug][zug];
                    ntile[bug][zug] = 0;
                }
            }
        }
        return neighbors;
    }

    public int hamming() {
        int n = 0;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (tiles[y][x] == goal[y][x] || tiles[y][x] == 0) {
                    n += 0;
                } else {
                    n += 1;
                }
            }
        }
        return n;
    }

    public int manhattan() {
        int n = 0;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                int[] origin = int2coords(tiles[y][x]);
                int orow = origin[0];
                int ocol = origin[1];
                int away = (Math.abs(y - orow) + Math.abs(x - ocol));
                n += away;
            }
        }
        return n;
    }

    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    public boolean isGoal() {
        return hamming() == 0;
    }

    public boolean equals(Object y) {
        int[][] board = (int[][]) y;
        int n = 0;
        for (int x = 0; x < size; x++) {
            for (int z = 0; z < size; z++) {
                if (tiles[z][x] == board[z][x] || tiles[z][x] == 0) {
                    n += 0;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    private int[] int2coords(int x) {
        int row = x / size;
        int col = (x % size) - 1;
        return new int[]{row, col};
    }


}
