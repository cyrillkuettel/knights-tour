package Genetic_Algorithm;
import static org.junit.jupiter.api.Assertions.*;
import Backtracking.Square;
import org.junit.Before;
import org.junit.Test;
import java.util.*;


import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class testChromosomeParsing {

    @Test
    public void testChromosmeParsing() {
        String validSequence = "011001010001111000111101110101011000110011100011010001010111000111110011001110101110100011100001000011000010100101110101000011001010000110000000101110101100001011000101011000011000101111100";
        validSequence += "000"; // to fill up the last by with an arbitrary number, I always ignore the last
        assertEquals(192, validSequence.length());

        Individual individual = new Individual();
        individual.setChromosome(validSequence);      // there should be 63 valid moves.
        individual.setStartPosition(0,2);  // starting Square is (0|2)
        int fitness = individual.FitnessFunction();
        assertEquals(63, fitness);
    }
}
