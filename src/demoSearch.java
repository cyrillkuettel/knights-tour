public class demoSearch {
    public static void main(String[] args) throws Exception {

        Search search = new Search(0 , 0, 8);

        try {
            search.startSearch();
        } catch (FoundSolutionException ignored) {
            System.out.println("catched FoundSolutionException");
        }




    }
}
