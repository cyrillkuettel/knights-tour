package Genetic_Algorithm;

import Backtracking.Square;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
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
        population[generation] = initializePopulation(); // randomly assigns variables

        evaluatePopulation(population[generation]);
        System.out.println(population[generation].toString());

        while (isTerminationConditionMet()) {
            parents = selectBetterHalfOfParents(population[generation]);
            /*
            population[generation + 1] = crossover(parents);
            population[generation + 1] = mutate(population[generation + 1]);
            evaluatePopulation(population[generation]);
            */

            generation++;
        }
    }

    // crossover based on single random point
    public Individual[] crossOver(Individual[] parents) {
        Individual[] children = new Individual[parents.length];

        Stack<Individual> stackToBeMated = new Stack<>();
        Arrays.stream(parents).parallel().forEach(stackToBeMated::push);
        // change this so that parents are randomly selected
        if (stackToBeMated.size() % 2  != 0) {
            throw new IllegalArgumentException("Indiviual[] parents has to be of even length");
        }
        Individual parent1 =
        while (!stackToBeMated.isEmpty()) {
            Individual p1 = stackToBeMated.pop();
            Individual p2 = stackToBeMated.pop();
            int len = p1.getChromosome().length;
            int selectedCrossoverPoint = ThreadLocalRandom.current().nextInt(0,len );
            // simple: swapping substrings

        }


        // loop through parents:
        // select two random parents
        // crossover, add the new Individual to return arrray
        // repeat until all parents are mated.



       /*
        if (flip(pCrossover)) {
            Individual[] children = parents;

        }
        }

        */
    return null;
    }

    private Individual[] selectBetterHalfOfParents(Population population) {
        return population.selectParents();
    }


    // TODO: write the roulette function.
    private Individual[] selectParentsRouletteWheel(Population population) {
        Individual[] individals = population.getIndividuals();
        // cumulative Probability.
        return null;


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
