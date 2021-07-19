package Genetic_Algorithm;

import Backtracking.Square;

import java.util.concurrent.ThreadLocalRandom;
            /*
        1: generation = 0;
        2: population[generation] = initializePopulation(populationSize);
        3: evaluatePopulation(population[generation]);
        3: While isTerminationConditionMet() == false do
        4:
             parents = selectParents(population[generation]);
        5:   population[generation+1] = crossover(parents);
        6:   population[generation+1] = mutate(population[generation+1]);
        7:    evaluatePopulation(population[generation]);
        8:     generation++;
        9: End loop;
         */

public final class World {
    private static final int POPULATION_SIZE = 50;
    public static final int BOARD_LEN = 8;
    public static final int CHROM_LEN = 192;
    public static final double pMutation = 0.01;
    public static final double pCrossover = 0.8;

    private static final Square startSquare = new Square(0, 0);
    private Population[] population;

    public static void main(String[] args) {
        new World();
    }

    int generation;

    public World() {

        Individual[] parents;
        population = new Population[100];
        generation = 0;
        population[generation] = initializePopulation();

        evaluatePopulation(population[generation]);
        System.out.println(population[generation].toString());

        while (isTerminationConditionMet()) {
            parents = selectParents(population[generation]);
            /*
            population[generation + 1] = crossover(parents);
            population[generation + 1] = mutate(population[generation + 1]);
            evaluatePopulation(population[generation]);
            */

            generation++;
        }
    }

    public Individual[] crossOver(Individual[] parents) {
       /*
        if (flip(pCrossover)) {
            Individual[] children = parents;

        }
        }

        */
    return null;
    }

    private Individual[] selectParents(Population population) {
        return population.selectParents();
    }

    private Individual[] selectParentsRouletteWheel(Population population) {
        Individual[] individals = population.getIndividuals();
        // cummultative Probability.


    }

    private void evaluatePopulation(Population population) {
        population.sumOverallSimpleFitness();
    }

    public Population initializePopulation() {
        return new Population(POPULATION_SIZE, CHROM_LEN, startSquare);
    }

    public boolean isTerminationConditionMet() {
        // very simple, I will change this in the future.
        return generation < 100;
    }



    public boolean flip(double p) {
        return ThreadLocalRandom.current().nextDouble() < p;
    }


}
