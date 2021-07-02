import java.util.*;
import java.util.stream.IntStream;

public final class Search {
    private static final int BOARD_LEN = 8;
    private static final int startPosX = 2;
    private static final int startPosY = 2;


    // 00 -> possibleFields
    // important: Map does not consider visited squares
    private final Map<Integer[], List<List<Integer>>> map = new HashMap<>(); // maps coordinate to List of possible moves

    // I want to keep this to pretty print the Board
    private final int[][] board = new int[BOARD_LEN][BOARD_LEN];



    /*
        Here I'm going to put all the fields the knight has visited.
     */
    private Queue<Integer[]> walkedPath = new LinkedList<>();

    public Search() {
        init();
        findTour();
    }
    public boolean findTour() {
        if (foundSolution()) {
            PrettyPrinter prettyPrinter = new PrettyPrinter(System.out);
            prettyPrinter.print(convertIntToStringArray(board));


        } else {

        }
        walkedPath.add(new Integer[]{3,2});


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
    }

    /**
     * Calculcates all Legal Knight moves from a given position
     * @param p 0-based X-coordinate
     * @param q 0-based Y-coordinate
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
        Integer[] key = {p,q};

        map.put(key, possibleFields);
    }

    public String[][] convertIntToStringArray(int[][] input) {
        String boardString[][] = new String[BOARD_LEN][BOARD_LEN];

        for (int i = 0; i < input.length; i++) {
            for ( int j = 0; j < input[i].length; i++) {
                boardString[i][j] = String.valueOf(input[i][j]);
            }

        }
        return boardString;
    }
}