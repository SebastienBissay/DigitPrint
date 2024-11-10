package mycellium;

import processing.core.PApplet;

import java.util.ArrayList;

import static parameters.Parameters.*;
import static processing.core.PApplet.*;

public class Cell {
    private static float[][] grid;
    private static PApplet pApplet;

    private final int x;
    private final int y;
    private final ArrayList<Cell> children;
    private int nextX;
    private int nextY;
    private float bestField;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        children = new ArrayList<>();
    }


    public static void initGrid(PApplet pApplet) {
        Cell.pApplet = pApplet;
        grid = new float[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                Cell.getGrid()[x][y] = -1 * pApplet.noise(x * NOISE_SCALE, y * NOISE_SCALE);
                Cell.getGrid()[x][y] += sqrt(sq(WIDTH / 2f - x) + sq(HEIGHT / 2f - y)) / (WIDTH / 4f);
            }
        }
    }

    public static float[][] getGrid() {
        return grid;
    }

    void generateField() {
        for (int i = max(x - 1, 0); i < min(x + 2, WIDTH); i++) {
            for (int j = max(y - 1, 0); j < min(y + 2, HEIGHT); j++) {
                grid[i][j] += (i == x && j == y) ? FIELD_POINT_VALUE : FIELD_NEIGHBOUR_VALUE;
            }
        }
    }

    void senseField() {
        bestField = Float.MAX_VALUE;
        for (int i = max(x - 1, 0); i < min(x + 2, WIDTH); i++) {
            for (int j = max(y - 1, 0); j < min(y + 2, HEIGHT); j++) {
                if ((i != x || j != y)
                        && ((grid[i][j] < bestField)
                        || ((grid[i][j] == bestField && pApplet.random(1) < 0.5)))) {
                    bestField = grid[i][j];
                    nextX = i;
                    nextY = j;
                }

            }
        }
    }

    public void drawLine(Cell cell) {
        pApplet.line(x, y, cell.x, cell.y);
    }

    Cell mitosis() {
        Cell c = new Cell(nextX, nextY);
        children.add(c);
        return c;
    }

    public ArrayList<Cell> getChildren() {
        return children;
    }

    public float getBestField() {
        return bestField;
    }
}
