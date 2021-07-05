import java.util.*;
import java.util.stream.Collectors;
import static java.lang.System.exit;

public final class Search {
    private  final int BOARD_LEN;
    private  final int startPosX;
    private  final int startPosY;

    public class SquareMovesComparator implements Comparator<Square> {


        @Override
        public int compare(Square o1, Square o2) {
            List<Square> square1_onward_moves = allPossibleMoves(o1.getX(), o1.getY());
            List<Square> square2_onward_moves = allPossibleMoves(o2.getX(), o2.getY());


            int len1 = square1_onward_moves.size();
            int len2 = square2_onward_moves.size();
            return Integer.compare(len1, len2);

        }

    }



    /**
     * Squares are ordered based on how many onward moves there are from a square. (The less, the better, the higher)
     */
    // PriorityQueue<Square> pqueue = new PriorityQueue<>(8, new SquareMovesComparator().reversed());
    private static Map<Square, List<Square>> map = new HashMap<>(); // maps coordinate to List of possible moves

    // I want to keep this to pretty print the Board
    private final int[][] board;

    /*
        Here I'm going to put all the fields the knight has visited.
     */
    public Search(final int startPosX, final int startPosY, final int BOARDSIZE)  {
        this.startPosX = startPosX;
        this.startPosY = startPosY;
        this.BOARD_LEN = BOARDSIZE;
        this.board = new int[BOARD_LEN][BOARD_LEN];
        initPossibleMoves();
    }

    public Search(final int startPosX, final int startPosY)  {
        this(startPosX, startPosY, 5);
    }

    long start;
    public void startSearch() throws Exception {
        Square startSquare = new Square(startPosX, startPosY);
        Stack<Square> walkedPath = new Stack<>();
        walkedPath.add(startSquare);
        board[startSquare.getX()][startSquare.getY()] = 1;
        start = System.currentTimeMillis();
        findTour(walkedPath);
    }




    // Quite possibly, it's necessary to pass walkedPath as an Argument, when using recursion
    // could use a Priority Queue or something like that
    // for example filter Squares based on the criteria, how many onward moves are possible
    boolean set = false;
    public boolean findTour(Stack<Square> theWalkedPath) throws Exception {

        if (theWalkedPath.size() == (BOARD_LEN*BOARD_LEN)) {
            System.out.println("found Solution! " + '\n' + theWalkedPath.toString());
            PrettyPrinter prettyPrinter = new PrettyPrinter(System.out);
            prettyPrinter.print(convertIntToStringArray(board));
            long end = System.currentTimeMillis();
            System.out.format("Total time of computation: %d ms", (end - start));

            //exit(0); // only search one Solution
            return true;
        } else {
           //System.out.println(theWalkedPath);
            Square nextSquare = theWalkedPath.peek(); // the latest move.
            if (hasDuplicates(theWalkedPath)) {

                throw new Exception("spotted duplicates:\n " + getDuplicates(theWalkedPath));
            }
           // PriorityQueue<Square> candidates = new PriorityQueue<>(8, new SquareMovesComparator().reversed());
            List<Square> candidates;

            if (theWalkedPath.size() == 1) {
                candidates = map.get(nextSquare) ; // the first time, everything is possible.

            } else {
                candidates = filterVisitedSquares(map.get(nextSquare), theWalkedPath);
            }

            // Squares with less possible onward squares are at the beginning of the List
            candidates.sort(new SquareMovesComparator());

            candidates.forEach( possibleMove -> {

                theWalkedPath.add(possibleMove);
                board[theWalkedPath.peek().getX()][theWalkedPath.peek().getY()] = 1;

                try {
                    findTour(theWalkedPath);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                board[theWalkedPath.peek().getX()][theWalkedPath.peek().getY()] = 0;
                // theWalkedPath().peek() == possibleMove;
                // this actually works, I'm amazed.
                theWalkedPath.remove(theWalkedPath.peek());
                candidates.remove(theWalkedPath.peek());

            });


            return false; // only way to get here is if all the moves failed

        }


    }



    /**
     * returns duplicates in a stack
     * @param walkedPath Stack which supposedly contains duplicates
     */
    private String getDuplicates(Stack<Square> walkedPath)
    {
        final Set<Square> setToReturn = new HashSet<>();
        final Set<Square> set1 = new HashSet<>();

        for (Square yourInt : walkedPath)
        {
            if (!set1.add(yourInt))
            {
                setToReturn.add(yourInt);
            }
        }
        return setToReturn.toString();
    }

    public boolean hasDuplicates(Stack<Square> stack) {
        Set<Square> set = new HashSet<>();
        for (Square each : stack) {
            if (!set.add(each)) {
                return true; // set.add returns false if the size of set did not change
            }
        }
        return false;
    }


    public List<Square> filterVisitedSquares(List<Square> candidates, Stack<Square> walkedPath) {

        return candidates.stream().filter(el -> !walkedPath.contains(el)).collect(Collectors.toList());

    }

    public boolean foundSolution(final int[][] board) {
        return Arrays.stream(board).flatMapToInt(Arrays::stream).allMatch(n -> (n == 1));
    }



    /**
     * Initialize the map. This is always the same
     */
    public void initPossibleMoves() {
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
    public  List<Square> allPossibleMoves(int p, int q) {
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

    public String[][] convertIntToStringArray(int[][] input) {
        String[][] boardString = new String[BOARD_LEN][BOARD_LEN];

        for (int i = 0; i < input.length; i++) {
            for ( int j = 0; j < input[i].length; j++) {
                boardString[i][j] = " " + String.valueOf(input[i][j]) + " ";
            }

        }
        return boardString;
    }

    // used for Tests

    public int[][] getBoard() {
        return board;
    }

    public Map<Square, List<Square>> getMap() {
        return map;
    }


}