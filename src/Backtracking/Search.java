package Backtracking;

import org.junit.jupiter.api.Assertions;

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


    private final Map<Square, List<Square>> map; // maps coordinate to List of possible moves
    private final int[][] board;

    public Search(final int startPosX, final int startPosY, final int BOARDSIZE)  {
        this.startPosX = startPosX;
        this.startPosY = startPosY;
        this.BOARD_LEN = BOARDSIZE;
        this.board = new int[BOARD_LEN][BOARD_LEN];
        ValidKnightMoves validknightMoves = new ValidKnightMoves(this.BOARD_LEN);
        this.map = validknightMoves.getMap();
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
            ValidKnightMoves validKnightMoves = new ValidKnightMoves(BOARD_LEN);

            List<Square> square1_onward_moves = validKnightMoves.allPossibleMoves(o1.getX(), o1.getY());
            List<Square> square2_onward_moves = validKnightMoves.allPossibleMoves(o2.getX(), o2.getY());

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


           //  throw new Backtracking.FoundSolutionException(); // I know this is ugly.
            // Otherwise, it will search endlessly for solutions, and possibly crash your pc.
            exit(0);
            return true;


        } else {
           // System.out.println(theWalkedPath);
            Square nextSquare = theWalkedPath.peek(); // the latest move.
            if (WalkedPathUtils.hasDuplicates(theWalkedPath)) {
                throw new Exception("Fatal: spotted duplicates:\n" + WalkedPathUtils.getDuplicates(theWalkedPath));
            }

            List<Square> candidates = WalkedPathUtils.filterVisitedSquares(map.get(nextSquare), theWalkedPath);

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

    public boolean foundSolution(Stack<Square> theWalkedPath) {
        return theWalkedPath.size() == (BOARD_LEN*BOARD_LEN);
    }


    /**
     * helper function which provides valid chromosomes ( sequence of moves coded as BitStrings)
     * Used for testing in Genetic Algorithm
     * @return
     */
    public String codeWalkedPathToBitString(Stack<Square> theWalkedPath) {
        // calculate differences between
        Iterator<Square> it = theWalkedPath.iterator();


/*
        Assertions.assertEquals(previous.getX(), startPosX);
        Assertions.assertEquals(previous.getY(), startPosY);
*/
        while (it.hasNext()) {
            Square current = it.next();
            int x = current.getX();
            int y = current.getY();

        }

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