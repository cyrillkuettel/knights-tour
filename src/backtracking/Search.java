package backtracking;


import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.System.exit;

public final class Search {
    private final int BOARD_LEN;
    private final int startPosX;
    private final int startPosY;

    private final Map<Square, List<Square>> map; // maps coordinate to List of possible moves
    private final int[][] board;

    public Search(final int startPosX, final int startPosY, final int BOARDSIZE) {
        this.startPosX = startPosX;
        this.startPosY = startPosY;
        this.BOARD_LEN = BOARDSIZE;
        this.board = new int[BOARD_LEN][BOARD_LEN];
        ValidKnightMoves validknightMoves = new ValidKnightMoves(this.BOARD_LEN);
        this.map = validknightMoves.getMap();
    }

    public Search(final int startPosX, final int startPosY) {
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
     * This is the Backtracking algorithm. If there is a tour, it will find the tour.
     * It basically checks all moves, recursively starting a new search in every iteration.
     * If a move does not lead to a solution, it removes the square from the path (Stack). Then it
     * proceeds with the next candidate move.
     *
     * @param theWalkedPath Behaves like a history. And as we all know, history is written by the winners.
     * @throws Exception Throws Exception if one square is present more than once.
     */

    // My suggestion is that PriorityQueue will improve performance, because you have very fast access to the top element
    public void findTour(Stack<Square> theWalkedPath) throws Exception {

        if (foundSolution(theWalkedPath)) {
            System.out.println("found Solution! " + '\n' + theWalkedPath);
            PrettyPrinter prettyPrinter = new PrettyPrinter(System.out);
            prettyPrinter.print(convertIntArrayToStringArray(board));
            long end = System.currentTimeMillis();
            System.out.format("Total time of computation: %d ms", (end - start));

            setupJFrameBoilderplate(theWalkedPath);

            //  throw new Backtracking.FoundSolutionException(); // I know this is ugly.
            // However, I've not found a good alternative to breaking out of the recursion.
            exit(0);

        } else {
            Square nextSquare = theWalkedPath.peek(); // the latest move.
            if (WalkedPathUtils.hasDuplicates(theWalkedPath)) {
                throw new Exception("Fatal: spotted duplicates:\n"
                        + WalkedPathUtils.getDuplicates(theWalkedPath));
            }

            List<Square> candidates = WalkedPathUtils.filterVisitedSquares(
                    map.get(nextSquare), theWalkedPath);

            // Neat trick:
            // prioritize moves with the lowest number of onward moves.
            // After sorting, these moves are to be found at the beginning of the candidates List
            // astonishing: this line improves performance a hundred-fold, a thousand-fold!
            // this phenomenon is particularly observable for big boards
            candidates.sort(new SquareMovesComparator());

            candidates.forEach(possibleMove -> {

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
            });
        }

    }

    private void setupJFrameBoilderplate(Stack<Square> theWalkedPath) {
        JFrame jf = new JFrame();
        jf.setSize(700, 700);
        jf.setTitle("Knight's Tour");
        jf.getContentPane().add(new ChessBoard(theWalkedPath, 8));
        jf.setLocationRelativeTo(null);
        jf.setBackground(Color.WHITE);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
    }

    public boolean foundSolution(Stack<Square> theWalkedPath) {
        return theWalkedPath.size() == (BOARD_LEN * BOARD_LEN);
    }

    /**
     * Squares are ordered based on how many onward moves there are from a square. (The less, the better,)
     */
    public class SquareMovesComparator implements Comparator<Square> {
        @Override
        public int compare(Square o1, Square o2) {
            ValidKnightMoves validKnightMoves = new ValidKnightMoves(BOARD_LEN);

            List<Square> square1_onward_moves = validKnightMoves.allPossibleMoves(o1.getX(), o1.getY());
            List<Square> square2_onward_moves = validKnightMoves.allPossibleMoves(o2.getX(), o2.getY());

            // don't count Backtracking.Square already visited

            square1_onward_moves = square1_onward_moves.stream().filter(element ->
                    (board[element.getX()][element.getY()] == 0)).collect(Collectors.toList());
            square2_onward_moves = square2_onward_moves.stream().filter(element ->
                    (board[element.getX()][element.getY()] == 0)).collect(Collectors.toList());

            int len1 = square1_onward_moves.size();
            int len2 = square2_onward_moves.size();
            return Integer.compare(len1, len2);
        }
    }


    /**
     * Very simple function which just translates a WalkedPath<Square> to BitString (sequence
     * of moves coded as BitStrings)
     * Only used for testing purposes in Genetic Algorithm tests
     *
     * @returns BitString with 63 Valid moves
     */

    public String codeWalkedPathToBitString(Stack<Square> theWalkedPath) {

        final Stack<Square> cleanedWalkedPath = new Stack<>();
        Square first = theWalkedPath.firstElement();
        System.out.println();
        System.out.println("starting Square: " + first);
        for (Square el : theWalkedPath) {
            if (el != first) {
                cleanedWalkedPath.add(el);
            }
        }

        System.out.println();
        Map<Integer, Square> directions;
        ValidKnightMoves validKnightMoves = new ValidKnightMoves(BOARD_LEN);
        directions = validKnightMoves.getDirections();
        Map<Square, Integer> directionsInversed;
        directionsInversed =
                directions.entrySet()
                        .stream()
                        .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));

        Square previousElement = first;
        Square currentElement;
        StringBuilder bitStringBuilder = new StringBuilder();

        int count = 0;
        for (Square el : cleanedWalkedPath) {
            currentElement = el;
            int x0 = previousElement.getX();
            int y0 = previousElement.getY();
            int x1 = currentElement.getX();
            int y1 = currentElement.getY();
            int diffX = x1 - x0;
            int diffY = y1 - y0;
            Square differenceSquare = new Square(diffX, diffY);
                /*
                System.out.println(previousElement);
                System.out.println(currentElement);
                System.out.println(differenceSquare);
                System.out.println();
         */
            StringBuilder binString = new StringBuilder(Integer.toBinaryString(directionsInversed.get(differenceSquare)));
            while (binString.length() < 3) {
                binString.insert(0, "0"); // insert at the beginning
            }
            bitStringBuilder.append(binString);
            previousElement = currentElement;
            count++;
        }
        System.out.printf("count = %d%n", count);
        System.out.printf("BitString Length = %s", bitStringBuilder.toString().length());
        System.out.println();
        System.out.format("Starting Square: %d | %d ", startPosX, startPosY);
        return bitStringBuilder.toString();
    }


    public String[][] convertIntArrayToStringArray(int[][] input) {
        String[][] boardString = new String[BOARD_LEN][BOARD_LEN];

        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
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