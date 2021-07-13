package Genetic_Algorithm;

import Backtracking.Square;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Stream;

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

    private int[] chromosome;
    private int fitness;
    private final Square startPosition = new Square(0,0);
    private Square phenotype;

    public Individual(int[] chromosome) {
        this.chromosome = chromosome;
    }

    public Individual() {
        this(init());
    }

    public static int[] init() {
        final String chrom = "100000010100101101110111000010110001100010001110011010110001101000011001110010000001001001101111000010110011101010011000011100000100100101101111101000111101000111000111110101111000011010011111";
        final String[] chroms = chrom.split("");
        final int[] chroms2 = Stream.of(chroms).mapToInt(Integer::parseInt).toArray();
        return chroms2;
    }

    /**
     * simply counts the number of legal move which the genotype represents.
     * moves after an illegal move are not counted.
     * @return number of legal moves the knight represents
     */
    public int FitnessFunction(){



        // it needs to be
        // decoded                                                      [ ]
        // translated into coorindates ( Squares)                       [ ]
        // the translation is dependent on the starting position and int[] chromosome.
        // from there, first I need to count the number of valid moves. [ ]

        return 0;
    }

    public int[] parseBitStringToDecimal() {
        int[] chromosomesDecimal = new int[chromosome.length /3];

        int count = 0;
        for (int i = 0; i < chromosome.length; i +=3) {
            String bytesAsString = "" + chromosome[i] + "" + chromosome[i+1] + "" + chromosome[i+2];
            int decimalsFromString = Integer.parseInt(bytesAsString, 2);
            chromosomesDecimal[count] = decimalsFromString;
            count++;
        }
        return chromosomesDecimal;
    }



    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public int[] getChromosome() {
        return chromosome;
    }
    public void setChromosome(String input) {
        final String[] chroms = input.split("");
        this.chromosome = Stream.of(chroms).mapToInt(Integer::parseInt).toArray();
    }

    public static void main(String[] args) {
        Individual i = new Individual();
        i.FitnessFunction();

    }


}
