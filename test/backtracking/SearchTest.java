package backtracking;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;


import java.util.*;

import static backtracking.WalkedPathUtils.filterVisitedSquares;
import static backtracking.WalkedPathUtils.hasDuplicates;


public class SearchTest {
    Search search;
    public static final int BOARD_LEN = 8;
    ValidKnightMoves validKnightMoves = new ValidKnightMoves(BOARD_LEN);



    @Test
    public void testFilterVisitedSquares() {

        // this test should work on any size Board >=5
        // it Works on 8x8, but not on other board sizes. This is very suspicious.

        search = new Search(1, 2, BOARD_LEN);
        Stack<Square> walkedPath = new Stack<>();

        // path the knight took
        walkedPath.add(new Square(0, 0));
        walkedPath.add(new Square(0, 4));
        walkedPath.add(new Square(2, 4));
        walkedPath.add(new Square(2, 0));

        // Let's say, knight is currently on (1|2)
        Square currentPos = new Square(1, 2);

        walkedPath.add(currentPos);
        // so there should only be two possible moves from this Position.

        List<Square> possibleMoves = search.getMap().get(currentPos);

        List<Square> candidates = filterVisitedSquares(possibleMoves, walkedPath);


        Square onlyAlternative1 = new Square(3,1);
        Square onlyAlternative2 = new Square(3,3);
        Assertions.assertTrue(candidates.contains(onlyAlternative1) && candidates.contains(onlyAlternative2));
        Assertions.assertEquals(2, candidates.size());

    }

    @Test
    public void testAllPossibleMoves() {
        search = new Search(1, 2, BOARD_LEN);

        List<Square> expected = new ArrayList<>();
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
    public void testComparator() {
        search = new Search(0,0,BOARD_LEN);


        List<Square> candidates = new ArrayList<>();

        Square aLotOfMoves = new Square(3,3); // in the middle, there should be a lot of moves.
        Square lessMoves = new Square(0,0);
        candidates.add(aLotOfMoves);
        candidates.add(lessMoves);

        candidates.sort((o1, o2) -> {
            List<Square> square1_onward_moves = validKnightMoves.allPossibleMoves(o1.getX(), o1.getY());
            List<Square> square2_onward_moves = validKnightMoves.allPossibleMoves(o2.getX(), o2.getY());


            int len1 = square1_onward_moves.size();
            int len2 = square2_onward_moves.size();
            return Integer.compare(len1, len2);

        });

        System.out.println(candidates.get(0));
        Assertions.assertEquals(lessMoves, candidates.get(0));

    }

    @Test
    public void testComparatorWithMoreSquares   () {
        search = new Search(0,0,BOARD_LEN);


        List<Square> candidates = new ArrayList<>();

        Square aLotOfMoves = new Square(3,3); // in the middle, there should be a lot of moves.
        Square someMoves = new Square(2,2); // in the middle, there should be a lot of moves.
        Square someMoves2 = new Square(2,3); // in the middle, there should be a lot of moves.
        Square lessMoves = new Square(0,0);
        Square AlsolessMoves = new Square(6,7);

        candidates.add(aLotOfMoves);
        candidates.add(lessMoves);
        candidates.add(AlsolessMoves);
        candidates.add(someMoves);
        candidates.add(someMoves2);


        candidates.sort((o1, o2) -> {

            List<Square> square1_onward_moves = validKnightMoves.allPossibleMoves(o1.getX(), o1.getY());
            List<Square> square2_onward_moves = validKnightMoves.allPossibleMoves(o2.getX(), o2.getY());


            int len1 = square1_onward_moves.size();
            int len2 = square2_onward_moves.size();
            return Integer.compare(len1, len2);

        });


        Assertions.assertEquals(lessMoves, candidates.get(0));
        candidates.remove(candidates.get(0));

        Assertions.assertEquals(AlsolessMoves, candidates.get(0));

    }

    @Test
    public void testMap() {
        search = new Search(0,0,BOARD_LEN);
        Stack<Square> walkedPath = new Stack<>();
        Square currentPos = new Square(1, 4);
        walkedPath.add(currentPos);

        List<Square> possibleMoves = search.getMap().get(new Square(1,4));


        Square onlyAlternative1 = new Square(2,2);
        Square onlyAlternative2 = new Square(3,3);
        Square onlyAlternative3 = new Square(0,2);

        Assertions.assertTrue(possibleMoves.contains(onlyAlternative1) && possibleMoves.contains(onlyAlternative2)
                                      && possibleMoves.contains(onlyAlternative3));
    }


    @Test
    public void testHasDuplicates() {
        search = new Search(0, 0, BOARD_LEN);
        Stack<Square> stack = new Stack<>();
        stack.add(new Square(1,1));
        stack.add(new Square(1,1));
        Assertions.assertTrue(hasDuplicates(stack));
    }

    @Test
    public void testHasDuplicatesIfNotDuplicates() {
        search = new Search(0, 0, BOARD_LEN);
        Stack<Square> stack = new Stack<>();
        stack.add(new Square(1,1));
        stack.add(new Square(1,2));
        Assertions.assertFalse(hasDuplicates(stack));
    }


}