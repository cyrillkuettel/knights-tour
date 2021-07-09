package Genetic_Algorithm;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public final class World {
    private static final int populationCount = 50;
    private final List<Individual> population = new ArrayList<>();


    private void spawn() {
        for (int i = 0; i < populationCount; i++) {
            population.add(new Individual());
        }
    }

    // lchrom: current Length of chromosome
    public String[] crossover(String parent1, String parent2, double pCrossover, int lchrom) {
        if (flip(pCrossover)) {

        }
        return null;
    }


    /**
     * True or false output based on a probability
     * @param p Probabilty
     * @return boolean value based on p
     */
    public boolean flip(double p) {
      return ThreadLocalRandom.current().nextDouble() < p;

    }

    /**
     * function select implements roulette wheel selection
     *
     */
    // the question is now, should all the parameters be type Individual or something else.
    public void select() {

    }

}
