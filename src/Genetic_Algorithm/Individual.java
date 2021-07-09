package Genetic_Algorithm;

import Backtracking.Square;

public final class Individual {
    /*
        Numerical representations of given knight moves from square X

        * 4 * 3 *
        5 * * * 2
        * * X * *
        6 * * * 1
        * 7 * 0 *

            0     1    2    3    4    5    6    7
            000, 001, 010, 011, 100, 101, 110, 111
     */
    private String chromosome; // The genotype for example 110110001010101
    private int fitness;
    private Square phenotype;

    /**
     * simply counts the number of legal move which the genotype represents.
     * moves after an illegal move are not counted.
     * @return number of legal moves the knight represents
     */
    public int FitnessFunction(){

        return 0;
    }


    public String getChromosome() {
        return chromosome;
    }

    public int getFitness() {
        return fitness;
    }
}
