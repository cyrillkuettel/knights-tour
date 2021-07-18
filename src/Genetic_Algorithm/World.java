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
    public static final int CHROM_LEN =  192;
    private static final Square startSquare = new Square(0,0);
    private Population[] population;

    public static void main(String[] args) {
        new World();
    }

    public World() {

        population = new Population[100];
        int generation = 0;
        population[generation] = initializePopulation();

        evaluatePopulation(population[generation]);
        System.out.println(population[generation].toString());
    /*
        while (isTerminationConditionMet(generation)) {

        }

     */
    }

    private void evaluatePopulation(Population population) {
        population.sumOverallFitness();
    }

    public Population initializePopulation() {
        return new Population(POPULATION_SIZE, CHROM_LEN, startSquare);
    }

    public boolean isTerminationConditionMet(int count) {
        if (count == population.length-1) {
            return true;
        }
        return false;
    }






    // lchrom: current Length of chromosome
    public String[] crossover(String parent1, String parent2, double pCrossover, int lchrom) {

        return null;
    }




    /**
     * function select implements roulette wheel selection
     *
     */
    // the question is now, should all the parameters be type Individual or something else.
    public void select() {

    }

}
