package Backtracking;

import Backtracking.Search;
import Backtracking.Square;
import Backtracking.ValidKnightMoves;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static Backtracking.WalkedPathUtils.filterVisitedSquares;

public class SearchTestCustomBoardSize {


    @Test
    public void testAllPossibleMoves() {
        final int BOARD_LEN = 5;
        ValidKnightMoves validKnightMoves = new ValidKnightMoves(BOARD_LEN);
        Search search = new Search(1, 2, BOARD_LEN);

        List<Square> expected = new ArrayList<Square>();
        expected.add(new Square(0,0));
        expected.add(new Square(0,4));
        expected.add(new Square(2,4));
        expected.add(new Square(3,3));
        expected.add(new Square(2,0));
        expected.add(new Square(3,1));

        List<Square> outcome = validKnightMoves.allPossibleMoves(1, 2);

        // Lists should contain the same elements
        Assertions. assertTrue(expected.size() == outcome.size() &&
                                       expected.containsAll(outcome) && outcome.containsAll(expected));


    }

    @Test
    public void testFilterVisitedSquares2() {
        // [[0 0] , [2 1] , [4 2] , [2 3] , [0 2] , [1 4] ]

        // Important: this test is supposed to be only for a 5x5 board.

        final int BOARD_LEN = 5;
        ValidKnightMoves validKnightMoves = new ValidKnightMoves(BOARD_LEN);
        Search search = new Search(1, 2, BOARD_LEN);

        Stack<Square> walkedPath = new Stack<>();

        walkedPath.add(new Square(0, 0));
        walkedPath.add(new Square(2, 1));
        walkedPath.add(new Square(4, 2));
        walkedPath.add(new Square(2, 3));
        walkedPath.add(new Square(0, 2));

        Square currentPos = new Square(1, 4);
        walkedPath.add(currentPos);

        List<Square> possibleMoves = search.getMap().get(currentPos);
        List<Square> candidates = filterVisitedSquares(possibleMoves, walkedPath);

        // so there should be lots of candidates from this position
        Square onlyAlternative1 = new Square(2,2);
        Square onlyAlternative2 = new Square(3,3);

        Assertions.assertTrue(possibleMoves.contains(onlyAlternative1) && possibleMoves.contains(onlyAlternative2));
        /*
        Assertions.assertTrue(candidates.contains(onlyAlternative1) && candidates.contains(onlyAlternative2));
        Assertions.assertEquals(2, candidates.size());
        */

    }


}
