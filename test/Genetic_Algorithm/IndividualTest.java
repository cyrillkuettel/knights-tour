package Genetic_Algorithm;

import Backtracking.Square;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.*;
public class IndividualTest {
    Individual individual;

    @Before
    public void setup() {
        individual = new Individual();
    }
    public void testFitnessFunction() {

    }

    @Test
    public void testparseBitStringToDecimal() {
        final String chrom = "100000010100101101110111000010110001100010001110011010110001101000011001110010000001001001101111000010110011101010011000011100000100100101101111101000111101000111000111110101111000011010011111";
        individual.setChromosome(chrom);

        int[] decimalsFromChromosome = individual.parseBitStringToDecimal();
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
    public void mapTest() {
        final String chrom = "100000010100101101110111000010110001100010001110011010110001101000011001110010000001001001101111000010110011101010011000011100000100100101101111101000111101000111000111110101111000011010011111";
        individual.setChromosome(chrom);
        int[] decimalsFromChromosome = individual.parseBitStringToDecimal();
        int testElement = decimalsFromChromosome[1]; // expecting 0

        Square expectedDirection = new Square(1, -2);
        Square actualDirection = Individual.directions.get(testElement);
        assertEquals(expectedDirection, actualDirection);
    }
}