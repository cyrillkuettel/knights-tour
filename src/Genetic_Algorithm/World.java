package Genetic_Algorithm;

import java.util.*;


public final class World {
    private static final int populationCount = 50;
    private final List<Individual> population = new ArrayList<>();


    private void spawn() {
        for (int i = 0; i < populationCount; i++) {
            population.add(new Individual());
        }
    }

}
