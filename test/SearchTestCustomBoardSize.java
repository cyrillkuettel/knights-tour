import Backtracking.Search;
import Backtracking.Square;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

public class SearchTestCustomBoardSize {

    @Test
    public void testAllPossibleMoves() {
        Search search;
        search = new Search(1, 2, 5);

        List<Square> expected = new ArrayList<Square>();
        expected.add(new Square(0,0));
        expected.add(new Square(0,4));
        expected.add(new Square(2,4));
        expected.add(new Square(3,3));
        expected.add(new Square(2,0));
        expected.add(new Square(3,1));

        List<Square> outcome = search.allPossibleMoves(1, 2);

        // Lists should contain the same elements
        Assertions. assertTrue(expected.size() == outcome.size() &&
                                       expected.containsAll(outcome) && outcome.containsAll(expected));


    }
}
