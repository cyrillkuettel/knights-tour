import org.junit.Test;
import org.junit.jupiter.api.Assertions;


import java.util.Arrays;
import java.util.List;
import java.util.Stack;


public class SearchTest {
    Search search;


    @Test
    public void testFilterVisitedSquares() {
        search = new Search(1, 2);
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
        List<Square> candidates = search.filterVisitedSquares(possibleMoves, walkedPath);

        Square onlyAlternative1 = new Square(3,1);
        Square onlyAlternative2 = new Square(3,3);
        Assertions.assertTrue(candidates.contains(onlyAlternative1) && candidates.contains(onlyAlternative2));
        Assertions.assertEquals(2, candidates.size());

    }

    @Test
    public void testFilterVisitedSquares2() {
        // [[0 0] , [2 1] , [4 2] , [2 3] , [0 2] , [1 4] ]

        search = new Search(0,0,5);
        Stack<Square> walkedPath = new Stack<>();

        walkedPath.add(new Square(0, 0));
        walkedPath.add(new Square(2, 1));
        walkedPath.add(new Square(4, 2));
        walkedPath.add(new Square(2, 3));
        walkedPath.add(new Square(0, 2));

        Square currentPos = new Square(1, 4);
        walkedPath.add(currentPos);

        List<Square> possibleMoves = search.getMap().get(currentPos);
        List<Square> candidates = search.filterVisitedSquares(possibleMoves, walkedPath);

        // so there should be lots of candidates from this position
        Square onlyAlternative1 = new Square(2,2);
        Square onlyAlternative2 = new Square(3,3);
        System.out.println(possibleMoves);
        Assertions.assertTrue(possibleMoves.contains(onlyAlternative1) && possibleMoves.contains(onlyAlternative2));
        /*
        Assertions.assertTrue(candidates.contains(onlyAlternative1) && candidates.contains(onlyAlternative2));
        Assertions.assertEquals(2, candidates.size());
        */

    }

    @Test
    public void testMap() {
        search = new Search(0,0,5);
        Stack<Square> walkedPath = new Stack<>();
        Square currentPos = new Square(1, 4);
        walkedPath.add(currentPos);

        List<Square> possibleMoves = search.getMap().get(new Square(1,4));


        Square onlyAlternative1 = new Square(2,2);
        Square onlyAlternative2 = new Square(3,3);
        Square onlyAlternative3 = new Square(0,2);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.println(search.getMap().get(new Square(i, j)));
            }

        }


        Assertions.assertTrue(possibleMoves.contains(onlyAlternative1) && possibleMoves.contains(onlyAlternative2) && possibleMoves.contains(onlyAlternative3));
        /*
        Assertions.assertTrue(candidates.contains(onlyAlternative1) && candidates.contains(onlyAlternative2));
        Assertions.assertEquals(2, candidates.size());
        */

    }
    @Test
    public void testfoundSolution() {
        search = new Search(0, 0);
        int[][] board = search.getBoard();
        for (int[] arr : board) {
            Arrays.fill(arr, 1);
        }
        Assertions.assertTrue(search.foundSolution(board));

    }

    @Test
    public void testHasDuplicates() {
        search = new Search(0, 0);
        Stack<Square> stack = new Stack<>();
        stack.add(new Square(1,1));
        stack.add(new Square(1,1));
        Assertions.assertTrue(search.hasDuplicates(stack));
    }

    @Test
    public void testHasDuplicatesIfNotDuplicates() {
        search = new Search(0, 0);
        Stack<Square> stack = new Stack<>();
        stack.add(new Square(1,1));
        stack.add(new Square(1,2));
        Assertions.assertFalse(search.hasDuplicates(stack));
    }

}