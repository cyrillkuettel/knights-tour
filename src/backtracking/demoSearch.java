package backtracking;

public class demoSearch {
    public static void main(String[] args) throws Exception {

        Search search = new Search(2 , 1, 8);

        try {
            search.startSearch();
        } catch (FoundSolutionException ignored) {
            System.out.println("catched Backtracking.FoundSolutionException");
        }




    }
}
