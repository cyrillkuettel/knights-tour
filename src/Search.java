import java.util.*;
public final class Search {
    private static final int BOARD_LEN = 8;

    // 00 -> possibleFields
    private final Map<Integer, List<List<Integer>>> map = new HashMap<>(); // maps coordinate to List of possible moves
    private int[][] board = new int[BOARD_LEN][BOARD_LEN];



    public Search() {
        Arrays.fill(board, 0);
        init();
    }


    public boolean findTour() {
        if (foundSolution()) {

        }

        /*
        If all squares are visited
    print the solution
Else
   a) Add one of the next moves to solution vector and recursively
   check if this move leads to a solution. (A Knight can make maximum
   eight moves. We choose one of the 8 moves in this step).

   b) If the move chosen in the above step doesn't lead to a solution
   then remove this move from the solution vector and try other
   alternative moves.

   c) If none of the alternatives work then return false (Returning false
   will remove the previously added item in recursion and if false is
   returned by the initial call of recursion then "no solution exists" )

         */
        return false;
    }

    public boolean foundSolution() {
        return Arrays.stream(board).flatMapToInt(Arrays::stream).allMatch(n -> (n == 1));
    }

    public static void main(String[] args) {
        new Search();
    }

    public void init() {
        for (int i = 0; i < BOARD_LEN; i++) {
            for (int j = 0; j < BOARD_LEN; j++) {
                allPossibleMoves(i, j);
            }
        }
        System.out.println(new PrettyPrintingMap<Integer, List<List<Integer>>>(map));
    }

    /**
     * Calculcates all Legal Knight moves from a given position
     * @param p
     * @param q
     */
    public void allPossibleMoves(int p, int q) {
        List<List<Integer>> possibleFields = new ArrayList<>();
        int[] X = {2, 1, -1, -2, -2, -1, 1, 2};
        int[] Y = {1, 2, 2, 1, -1, -2, -2, -1};
        // Check if each possible move is valid or not
        for (int i = 0; i < BOARD_LEN; i++) {
            // Position of knight after move
            int x = p + X[i];
            int y = q + Y[i];
            // count valid moves
            if (x >= 0 && y >= 0 && x < BOARD_LEN && y < BOARD_LEN) {
                List<Integer> list = new ArrayList<>();
                list.add(x);
                list.add(y);
                possibleFields.add(list);
            }
        }
        int key = Integer.valueOf(String.valueOf(p) + String.valueOf(q));
        map.put(key, possibleFields);
    }
}