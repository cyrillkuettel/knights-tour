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

    public void testGetFittestIndividual() {
        final int popSize = 3;
        Population pop = new Population(popSize);
        Individual[] population = new Individual[popSize];

        population[0] = new Individual(); //
        population[0].setChromosome("000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
        population[0].setStartPosition(7,7);

        population[1] = new Individual(); //5 0 7
        population[1].setChromosome("000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
        population[1].setStartPosition(2,1);



    }
}