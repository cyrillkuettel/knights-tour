package Genetic_Algorithm;

public class Population {

    private Individual population[];
    private double populationFitness = -1;

    public Population(int populationSize)    {
        this.population = new Individual[populationSize];
    }


    public Population(int populationSize,  int chromosomeLength) {
        this.population = new Individual[populationSize];


    }

    // get fittest individual.
    // get indivitual.
    /*
     I can write the system.
     I will write the repair function. The only thing that's still not clear for me at the moment, is how long is the
     initial size of the Bitstring? I think size is always constant in the paper. Repair is only happening on
     the first string

     */
}
