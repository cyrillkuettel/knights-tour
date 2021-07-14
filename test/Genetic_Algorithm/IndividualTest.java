package Genetic_Algorithm;

import Backtracking.Square;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class IndividualTest {
    Individual individual;

    @Before
    public void setup() {
        individual = new Individual();
    }
    public void testFitnessFunction() {

    }

    @Test
    public void testParseChromosomesToDeicmal() {
        final String chrom = "100000010100101101110111000010110001100010001110011010110001101000011001110010000001001001101111000010110011101010011000011100000100100101101111101000111101000111000111110101111000011010011111";
        individual.setChromosome(chrom);

        int[] decimalsFromChromosome = individual.parseChromosomeToDecimal();
        int firstElement = decimalsFromChromosome[0]; // 100 in decimal is 4
        int lastElement = decimalsFromChromosome[decimalsFromChromosome.length-1];

        assertEquals(4, firstElement);
        assertEquals(7, lastElement);

        int decimalsFromChromosomeLength = decimalsFromChromosome.length;
        int shouldBeAThirdOfChromosomeLength = individual.getChromosome().length / 3;

        System.out.println(Arrays.toString(decimalsFromChromosome));
        assertEquals(shouldBeAThirdOfChromosomeLength, decimalsFromChromosomeLength );
    }

    @Test
    public void testParseChromosomesToDeicmalLongSequence() {

        /*
            0     1    2    3    4    5    6    7
            000, 001, 010, 011, 100, 101, 110, 111
         */
        // test Sequence 0->7->5->5->4->3->2 which is the first 21 genes:
        //                    000111101101100011010
        final String chrom = "000111101101100011010100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100"; //
        final int[] expected = new int[]{0,7,5,5,4,3,2};

        individual.setChromosome(chrom);
        individual.setStartPosition(new Square(5,3));    // starting a tour from Square (5|3)



        int[] result = individual.parseChromosomeToDecimal();
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], result[i]);
        }

    }

    @Test
    public void mapTest() {
        final String chrom = "100000010100101101110111000010110001100010001110011010110001101000011001110010000001001001101111000010110011101010011000011100000100100101101111101000111101000111000111110101111000011010011111";
        individual.setChromosome(chrom);
        int[] decimalsFromChromosome = individual.parseChromosomeToDecimal();
        int testElement = decimalsFromChromosome[1]; // expecting 0

        Square expectedDirection = new Square(1, -2);
        Square actualDirection = individual.directions.get(testElement);
        assertEquals(expectedDirection, actualDirection);

    }

    @Test
    public void FitnessFunctionTest() {
        // test Sequence     0->7->5->5->4->3->2 which is the first 21 genes:
        //                    000111101101100011010
        final String chrom = "000111101101100011010100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100";
        individual.setChromosome(chrom);
        individual.setStartPosition(new Square(5,3));    // starting a test from Square (5|3)

        int fitness = individual.FitnessFunction();
        assertEquals(7, fitness);    // should yield 7 because each move is possible

    }

    @Test
    public void FitnessFunctionTest2() {
        // start at (7|7)
        // test Sequence     4->5->4->5 which is the first 12 genes:
        //                    100101100101
        final String chrom = "100101100101100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100100";
        individual.setChromosome(chrom);
        individual.setStartPosition(new Square(7,7));    // starting a test from Square (5|3)

        int fitness = individual.FitnessFunction();
        assertEquals(4, fitness);    // should yield 7 because each move is possible

    }

}