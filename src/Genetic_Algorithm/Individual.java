package Genetic_Algorithm;

import Backtracking.Square;
import Backtracking.ValidKnightMoves;


import java.util.*;
import java.util.stream.Stream;

import static Backtracking.WalkedPathUtils.hasDuplicates;

public final class Individual {
    /*
        Numerical representations of given knight moves from square X (all in Map directions)

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
    // start Position has to be hard-coded it seems. The bitstring only codes *where* to move, not from where
    private final Square startPosition = new Square(0,0);
    public static final Map<Integer, Square> directions = new HashMap<>();
    private final ValidKnightMoves validKnightMoves = new ValidKnightMoves(World.BOARD_LEN);
    private Map<Square, List<Square>> map;



    public Individual(int[] chromosome) {
        this.chromosome = chromosome;
        directions.put(0, new Square(1,-2));
        directions.put(1, new Square(2,1));
        directions.put(2, new Square(2,-1));
        directions.put(3, new Square(1,-2));
        directions.put(4, new Square(-1,-2));
        directions.put(5, new Square(-2,-1));
        directions.put(6, new Square(-2,1));
        directions.put(7, new Square(-1,2));
        validKnightMoves.initPossibleMoves();
        this.map = validKnightMoves.getMap();

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
        Stack<Square> walkedPath = new Stack<>();
        walkedPath.add(startPosition);

        boolean validSequence = true;
        int[] codes = parseBitStringToDecimal();

        int count = 0;
        do  {
            Square previousSquare = walkedPath.peek();
            List<Square> legalMoves = map.get(previousSquare);

            Square nextSquare = directions.get(codes[count]);
            if (legalMoves.contains(nextSquare) && !walkedPath.contains(nextSquare)) {
                // if next square is legal and not yet visited, add it
                walkedPath.add(directions.get(codes[count]));
                count++;
            } else {
                validSequence = false;
            }


        } while (validSequence);

        Square square = directions.get(codes[0]);
       // Square jumpSquare =

        // the translation is dependent on the starting position and current int[] chromosome.
        // then count the number of valid moves from the resulting List<Square> [ ]

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
