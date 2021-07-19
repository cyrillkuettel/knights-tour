package Genetic_Algorithm;

import Backtracking.Search;
import Backtracking.Square;
import Backtracking.ValidKnightMoves;


import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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

            The chromosome codes for this path. Each 8-bit number represents a direction from the previous square.
            Staring Square is given. The chromosome only codes *where* to move, not from where
     */

    private int[] chromosome;

    private int fitness;
    private int chromosomeLength;
    private Square startPosition;

    private static final ValidKnightMoves validKnightMoves = new ValidKnightMoves(World.BOARD_LEN);
    private static final Map<Integer, Square> directions = validKnightMoves.getDirections();
    private static final Map<Square, List<Square>> map = validKnightMoves.getMap();


    public Individual(int[] chromosome, final int x, final int y) {
        this.chromosomeLength = chromosome.length;
        this.chromosome = chromosome;
        this.startPosition = new Square(x, y);

    }

    public Individual() {
        this(init(), 3, 3);
    }

    private static int[] init() {
        final String chrom = "100000010100101101110111000010110001100010001110011010110001101000011001110010000001001001101111000010110011101010011000011100000100100101101111101000111101000111000111110101111000011010011111";
        final String[] chroms = chrom.split("");
        final int[] chroms2 = Stream.of(chroms).mapToInt(Integer::parseInt).toArray();
        return chroms2;
    }


    public Individual(final Square startPosition, final int len) {
        this.chromosomeLength = len;
        this.chromosome = initRandom();
        this.startPosition = startPosition;
    }


    public int[] initRandom() {
        final String chrom = generateInitialChromosome();
        final String[] chroms = chrom.split("");
        return Stream.of(chroms).mapToInt(Integer::parseInt).toArray();
    }

    public String generateInitialChromosome() {
        String chrom = "";
        while (chrom.length() < chromosomeLength) {
            if (flip(0.5)) { // flip a coin
                chrom += "1";
            } else {
                chrom += "0";
            }
        }
        return chrom;
    }

    /**
     * simply counts the number of legal move which the genotype represents. (Start Square is not part of the BitString)
     * Moves, which have been visited are illegal, as well as moves which move off the baord.
     * moves after an illegal move are not counted.
     *
     * @return number of legal moves the knight represents
     */
    // sieht kompliziert aus. Aber simpler Ablauf:
    // 1) Decipher Code. Zeigt (einer von maximal 8 möglichen) Richtungswechsel an
    // 2) Richtungswechsel zur alten Kooridnate addieren. mathematisch: x(t) + x(t+1) und y(t) + y(t+1),  t = time
    // 3) Kontrolle, ob überhaupt möglich. Wenn nicht, abbruch.
    // I'm almost certain, this could be improved performance wise
    public int FitnessFunction() {
        Stack<Square> walkedPath = new Stack<>();
        walkedPath.add(startPosition);
        boolean detectingValidMoves = true;
        int[] codes = parseChromosomeToDecimal();
        int count = 0;
        do {
            Square previousSquare = walkedPath.peek();
            List<Square> legalMoves = map.get(previousSquare);
            Square directionalChange = directions.get(codes[count]);
            int offsetX = previousSquare.getX() + directionalChange.getX();
            int offsetY = previousSquare.getY() + directionalChange.getY();
            Square nextSquare = new Square( // simple addition of the corresponding coordinates
                    offsetX, offsetY);
            if (offsetX < 0 || offsetY < 0) {
                detectingValidMoves = false;
            } else {
                if (legalMoves.contains(nextSquare) && !walkedPath.contains(nextSquare)) {
                    // if next square is legal and not yet visited, add it
                    walkedPath.add(nextSquare);
                    count++;
                } else {
                    detectingValidMoves = false;
                }
            }
        } while (detectingValidMoves);
        return count;
    }

    /**
     * True or false output based on a probability
     *
     * @param p Probabilty, range[0 ... 1]
     * @return boolean value based on p
     */
    public boolean flip(double p) {
        return ThreadLocalRandom.current().nextDouble() < p;
    }


    public int[] parseChromosomeToDecimal() {
        int[] chromosomesDecimal = new int[chromosome.length / 3];

        int count = 0;
        for (int i = 0; i < chromosome.length; i += 3) {
            String bytesAsString = "" + chromosome[i] + "" + chromosome[i + 1] + "" + chromosome[i + 2];
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

    public void setStartPosition(final int x, final int y) {
        this.startPosition = new Square(x, y);
    }

    public Map<Integer, Square> getDirections() {
        return directions;
    }

    /**
     * How many 1's there are in the chromosome.
     * @return relative Fitness
     */
    public double simpleFitnessFunction() {
        final double v = (double) IntStream.of(chromosome).sum() / chromosomeLength;
        return v;
    }

    @Override
    public String toString() {
       String str = Arrays.stream(chromosome).boxed().map(Object::toString)
                                                .collect(Collectors.joining(""));
        return "Individual{" +
                "chromosome=" + str +
                '}'+'\n';
    }
}
