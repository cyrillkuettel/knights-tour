package Genetic_Algorithm;

public final class Individual {
    private String structure;
    private int Fitness; // can be absolute or relative value



    public String getGenotype() {
        return structure;
    }

    public int getFitness() {
        return Fitness;
    }
}
