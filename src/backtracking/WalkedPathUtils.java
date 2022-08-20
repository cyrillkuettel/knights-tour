package backtracking;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * A collection of useful functions regarding the already visited squares.
 */
public final class WalkedPathUtils {
    //
    /**
     *
     * @param candidates all legal moves from a given Backtracking.Square, without limitations.
     * @param walkedPath history of moves, these moves should no longer be considered a valid option.
     *                   (Because that's the point of knight's tour)
     * @return candidates minus walkedPath
     */
    public static List<Square> filterVisitedSquares(List<Square> candidates, Stack<Square> walkedPath) {
        return candidates.stream().filter(el -> !walkedPath.contains(el)).collect(Collectors.toList());
    }

    public static boolean hasDuplicates(Stack<Square> stack) {
        Set<Square> set = new HashSet<>();
        for (Square each : stack) {
            if (!set.add(each)) {
                return true; // set.add returns false if the size of set did not change
            }
        }
        return false;
    }

    /**
     * returns duplicates in a stack, if present.
     * @param walkedPath Stack which supposedly contains duplicates
     */
    public static String getDuplicates(Stack<Square> walkedPath)
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

}
