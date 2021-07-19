package Genetic_Algorithm;

import Backtracking.Square;

import java.util.Arrays;
import java.util.Comparator;

public class Population {

    private  Individual[] population;
    private int populationFitness = -1;

    // for testing purposes
    private double summedSimpleFitness = 0;

    public Population(int populationSize)    {
        this.population = new Individual[populationSize];
    }


    public Population(int populationSize, int chromosomeLength, Square startSquare) {
        this.population = new Individual[populationSize];
        for (int i = 0; i < populationSize; i++) {
            population[i] = new Individual(startSquare, chromosomeLength); // Population randomly initialized
        }
    }

    public Individual getFittestIndividual(int offset) {
        Individual[] sortedIndividuals = Arrays.copyOf(population, population.length);

        Arrays.sort(sortedIndividuals, (o1, o2) -> {
            int f1 = o1.getFitness();
            int f2 = o2.getFitness();
            return Integer.compare(f1, f2);
        });
        return sortedIndividuals[sortedIndividuals.length-1-offset];
    }

     // very primitive. This simply selects half of the parents.
     // @return the 25 most fit parents

    public Individual[] selectParents() {
        Individual[] sortedIndividuals = Arrays.copyOf(population, population.length);

        Arrays.sort(sortedIndividuals, (o1, o2) -> {
            int f1 = o1.getFitness();
            int f2 = o2.getFitness();
            return Integer.compare(f1, f2);
        });
        int len = sortedIndividuals.length;
                                                // oldArray, startIndex, endIndex
        Individual[] parents = Arrays.copyOfRange(sortedIndividuals, len/2, len-1);
        return parents;
    }

    // get fittest
    // get Individual
    // getPopulationFitness


    /*
     I can write the system.
     I will write the repair function. This can improve performance.

     */

    public void setPopulation(Individual[] population) {
        this.population = population;
    }

    public Individual[] getIndividuals() {
        return population;
    }

    public void sumOverallSimpleFitness() {
        double count = 0;
        for (Individual ind: getIndividuals() ) {
            count += ind.simpleFitnessFunction();
        }
        this.summedSimpleFitness = count;
    }



    public void setPopulationFitness(int populationFitness) {
        this.populationFitness = populationFitness;
    }

    public int getPopulationFitness() {
        return populationFitness;
    }

    @Override
    public String toString() {
        return "Population {" + '\n'+
                "population=" + Arrays.toString(population) +
                "fittest Individual = " + getFittestIndividual(0).simpleFitnessFunction() + '}';
    }


}
