package Backtracking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * This class generates all theoretical possible knight moves from a given coorindate (type Square).
 * The results of the computation is then stored in a Map, which other classes can use.
 */
public final class ValidKnightMoves {

    public final int BOARD_LEN;
    private final Map<Square, List<Square>> map = new HashMap<>(); // maps coordinate to List of possible moves
    private boolean isInitialized = false;

    public ValidKnightMoves(int boardLen) {
        this.BOARD_LEN = boardLen;
    }



    /**
     *  It just calculates all 'L-Shapes' from a Backtracking.Square.
     */
    public void initPossibleMoves() {
        isInitialized = true;
        for (int i = 0; i < BOARD_LEN; i++) {
            for (int j = 0; j < BOARD_LEN; j++) {
                allPossibleMoves(i, j);
            }
        }
    }

    /**
     * Calculates all Legal Knight moves from a given position
     * @param p 0-based X-coordinate
     * @param q 0-based Y-coordinate
     */
    public List<Square> allPossibleMoves(int p, int q) {
        List<Square> possibleFields = new ArrayList<>();

        int[] X = {2, 1, -1, -2, -2, -1, 1, 2};
        int[] Y = {1, 2, 2, 1, -1, -2, -2, -1};
        // Check if each possible move is valid or not
        for (int i = 0; i < X.length; i++) {
            // Position of knight after move
            int x = p + X[i];
            int y = q + Y[i];
            // count valid moves
            if (x >= 0 && y >= 0 && x < BOARD_LEN && y < BOARD_LEN) {
                possibleFields.add(new Square(x, y));
            }
        }
        map.put(new Square(p, q), possibleFields);
        return possibleFields;
    }

    public Map<Square, List<Square>> getMap() {
        if (!isInitialized) {
            throw new IllegalStateException("Map should first me initialiezd with initPossibleMoves()");
        }
        return map;
    }
}
