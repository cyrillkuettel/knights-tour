package Backtracking;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import static java.lang.System.exit;

public final class Search {
    private  final int BOARD_LEN;
    private  final int startPosX;
    private  final int startPosY;



    private static Map<Square, List<Square>> map = new HashMap<>(); // maps coordinate to List of possible moves
    private final int[][] board;

    public Search(final int startPosX, final int startPosY, final int BOARDSIZE)  {
        this.startPosX = startPosX;
        this.startPosY = startPosY;
        this.BOARD_LEN = BOARDSIZE;
        this.board = new int[BOARD_LEN][BOARD_LEN];
        initPossibleMoves();
    }

    public Search(final int startPosX, final int startPosY)  {
        this(startPosX, startPosY, 8);
    }

    long start;
    public void startSearch() throws Exception {
        Square startSquare = new Square(startPosX, startPosY);
        Stack<Square> walkedPath = new Stack<>();
        walkedPath.add(startSquare);
        board[startSquare.getX()][startSquare.getY()] = walkedPath.indexOf(startSquare);
        start = System.currentTimeMillis();
        findTour(walkedPath);
    }

    /**
     *  Squares are ordered based on how many onward moves there are from a square. (The less, the better,)
     */
    public class SquareMovesComparator implements Comparator<Square> {
        @Override
        public int compare(Square o1, Square o2) {
            List<Square> square1_onward_moves = allPossibleMoves(o1.getX(), o1.getY());
            List<Square> square2_onward_moves = allPossibleMoves(o2.getX(), o2.getY());

            // don't count Backtracking.Square already visited

            square1_onward_moves = square1_onward_moves.stream().filter( element ->
                    (board[element.getX()][element.getY()] == 0)).collect(Collectors.toList());
            square2_onward_moves = square2_onward_moves.stream().filter( element ->
                    (board[element.getX()][element.getY()] == 0)).collect(Collectors.toList());

            int len1 = square1_onward_moves.size();
            int len2 = square2_onward_moves.size();
            return Integer.compare(len1, len2);
        }
    }


    /**
     *
     * This is the Backtracking algorithm. If there is a tour, it will find the tour.
     * It basically checks all moves, recursively starting a new search in every iteration.
     * If a move does not lead to a solution, it removes the square from the path (Stack).
     * @param theWalkedPath Behaves like a history. And as we all know, history is written by the winners.
     * @return Solution to the knight's tour.
     * @throws Exception Throws Exception if one square is present more than once.
     */

    // My suggestion is that PriorityQueue will improve performance, because you have very fast access to the top element

    public boolean findTour(Stack<Square> theWalkedPath) throws Exception {

        if (foundSolution(theWalkedPath)) {
            System.out.println("found Solution! " + '\n' + theWalkedPath);
            PrettyPrinter prettyPrinter = new PrettyPrinter(System.out);
            prettyPrinter.print(convertIntArrayToStringArray(board));
            long end = System.currentTimeMillis();
            System.out.format("Total time of computation: %d ms", (end - start));

            JFrame jf = new JFrame();
            jf.setSize(700, 700);
            jf.setTitle("Knight's Tour");
            jf.getContentPane().add(new ChessBoard(theWalkedPath, 8));
            jf.setLocationRelativeTo(null);
            jf.setBackground(Color.WHITE);
            jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            jf.setVisible(true);


           //  throw new Backtracking.FoundSolutionException(); // I know this is ugly, but it needs to be done.
            // Otherwise, it will search endlessly for solutions.
            exit(0);
            return true;


        } else {
           // System.out.println(theWalkedPath);
            Square nextSquare = theWalkedPath.peek(); // the latest move.
            if (hasDuplicates(theWalkedPath)) {
                throw new Exception("Fatal: spotted duplicates:\n" + getDuplicates(theWalkedPath));
            }

            List<Square> candidates = filterVisitedSquares(map.get(nextSquare), theWalkedPath);

            // Neat trick:
            // prioritize moves with the lowest number of onward moves.
            // After sorting, these moves are to be found at the beginning of the candidates List
            // astonishing: this line improves performance a hundred-fold, a thousand-fold!
            // this phenomenon is particularly observable for big boards
            candidates.sort(new SquareMovesComparator());

            candidates.forEach( possibleMove -> {

                if (possibleMove == null) {
                    System.out.println("got here");
                    return; // this, surprisingly and unexpectedly only skips ONE Iteration.
                }


                theWalkedPath.add(possibleMove);
                board[possibleMove.getX()][possibleMove.getY()] = theWalkedPath.indexOf(possibleMove);

                try {
                    findTour(theWalkedPath);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                board[possibleMove.getX()][possibleMove.getY()] = 0;
                theWalkedPath.remove(possibleMove);
                 //possibleMove = null;
            });
            return false; // if all the moves failed
        }

    }

    /**
     * returns duplicates in a stack, if present.
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

    /**
     *
     * @param candidates all legal moves from a given Backtracking.Square, without limitations.
     * @param walkedPath history of moves, these moves should no longer be considered a valid option. (Because that's the point of knight's tour)
     * @return candidates minus walkedPath
     */
    public List<Square> filterVisitedSquares(List<Square> candidates, Stack<Square> walkedPath) {
        return candidates.stream().filter(el -> !walkedPath.contains(el)).collect(Collectors.toList());
    }

    public boolean foundSolution(Stack<Square> theWalkedPath) {
       return theWalkedPath.size() == (BOARD_LEN*BOARD_LEN);
    }



    /**
     *  It just calculates all 'L-Shapes' from a Backtracking.Square.
     */
    public void initPossibleMoves() {
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

    public String[][] convertIntArrayToStringArray(int[][] input) {
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