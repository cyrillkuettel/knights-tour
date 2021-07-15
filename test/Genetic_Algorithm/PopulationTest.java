package Genetic_Algorithm;

import static org.junit.jupiter.api.Assertions.*;
import Backtracking.Square;
import org.junit.Before;
import org.junit.Test;
import java.util.*;


import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

/*
        * 4 * 3 *
        5 * * * 2
        * * X * *
        6 * * * 1
        * 7 * 0 *
 */

public class PopulationTest {

    @Test
    public void testGetFittestIndividual() {

        // testing three Individuals:
        // 1.) Has fitness of 0, the 2.) has fitness of 4. 3rd has fitness of 63

        final int popSize = 3;
        Population pop = new Population(popSize);
        Individual[] tpopulation = new Individual[popSize];

        tpopulation[0] = new Individual(); // lowest fitness
        tpopulation[0].setChromosome("000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
        tpopulation[0].setStartPosition(7,7);

        tpopulation[1] = new Individual();
        String firstfourShouldBeValid = "1010001110000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";
        tpopulation[1].setChromosome(firstfourShouldBeValid);
        tpopulation[1].setStartPosition(2,1);

        String validSequence = "011001010001111000111101110101011000110011100011010001010111000111110011001110101110100011100001000011000010100101110101000011001010000110000000101110101100001011000101011000011000101111100";
        validSequence += "000"; // to fill up the last by with an arbitrary number, I always ignore the last
        tpopulation[2] = new Individual();
        tpopulation[2].setChromosome(validSequence);
        tpopulation[2].setStartPosition(0,2);
        pop.setPopulation(tpopulation);
       assertEquals(tpopulation[2], pop.getFittestIndividual(0));
       assertEquals(tpopulation[1], pop.getFittestIndividual(1));
       assertEquals(tpopulation[0], pop.getFittestIndividual(2));



    }
}