package Genetic_Algorithm;

public class interfaceGeneticAlgorithm {


        private int populationSize;
        private double mutationRate;
        private double crossoverRate;
        private int elitismCount;

        /*
        public GeneticAlgorithm(int populationSize, double mutationRate, double crossoverRate, int elitismCount) { }
        public Population initPopulation(int chromosomeLength) { }
        public double calcFitness(Individual individual) { }
        public void evalPopulation(Population population) { }
        public boolean isTerminationConditionMet(Population population) { }
        public Individual selectParent(Population population) { }
        public Population crossoverPopulation(Population population) { }
        public Population mutatePopulation(Population population) { }
        */

}


        /*
        1: generation = 0;
        2: population[generation] = initializePopulation(populationSize);
        3: evaluatePopulation(population[generation]);
        3: While isTerminationConditionMet() == false do
        4:     parents = selectParents(population[generation]);
        5:    population[generation+1] = crossover(parents);
        6:   population[generation+1] = mutate(population[generation+1]);
        7:    evaluatePopulation(population[generation]);
        8:     generation++;
        9: End loop;
         */