import org.junit.Test;
import org.junit.jupiter.api.Assertions;


import java.util.Arrays;
import java.util.List;


public class SearchTest {
    Search search;


    @Test
    public void testFilterVisitedSquares() {
        search = new Search();
        search.init();

        // Let's say, knight is currently on (1|2)
        Square currentPos = new Square(1, 2);
        search.walkedPath.add(currentPos);
        search.walkedPath.add(new Square(0, 0));
        search.walkedPath.add(new Square(0, 4));
        search.walkedPath.add(new Square(2, 4));
        search.walkedPath.add(new Square(2, 0));
        // so there should only be two possible moves from this Position.

        List<Square> possibleMoves = search.getMap().get(currentPos);
        List<Square> candidates = search.filterVisitedSquares(possibleMoves);

        Square onlyAlternative1 = new Square(3,1);
        Square onlyAlternative2 = new Square(3,3);
        Assertions.assertTrue(candidates.contains(onlyAlternative1) && candidates.contains(onlyAlternative2));
        Assertions.assertEquals(2, candidates.size());



    }

    @Test
    public void testfoundSolution() {
        search = new Search();
        int[][] board = search.getBoard();
        for (int[] arr : board) {
            Arrays.fill(arr, 1);
        }
        Assertions.assertTrue(search.foundSolution(board));

    }

}