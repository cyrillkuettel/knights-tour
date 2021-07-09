package Genetic_Algorithm;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class testRouletteWheelTest {

    @Test
    public void testsumOfProbabilityIsOne() {
        Double[] probability = new Double[]{0.1,0.2, 0.05, 0.15, 0.0, 0.11, 0.07, 0.04, 0.00, 0.12, 0.16};
        testRouletteWheel test = new testRouletteWheel("test");
        assertTrue(test.sumOfProbabilityIsOne(probability));
    }

    @Test
    public void testsumOfProbabilityIsOneShouldFail() {
        Double[] probability = new Double[]{0.1,0.1, 0.05, 0.15, 0.0, 0.11, 0.07, 0.04, 0.00, 0.12, 0.16};
        testRouletteWheel test = new testRouletteWheel("test");
        assertFalse(test.sumOfProbabilityIsOne(probability));
    }

    @Test
    public void testNewProbability() {
        testRouletteWheel test = new testRouletteWheel("test");

        String[] structure = new String[]{"00001100",
                "11100111",
                "11101100",
                "01010010",
                "01100100",
                "01100011",
                "10010001",
                "11011001",
                "00001011",
                "01001101",
                "01001001"};

        Double[] result = test.computeNewProbability(structure);
        System.out.println(Arrays.toString(result));
        assertTrue(test.sumOfProbabilityIsOne(test.computeNewProbability(structure)));

        structure[0] = structure[1];

        Double[] resultAfterAddingDuplicates = test.computeNewProbability(structure);
        System.out.println(Arrays.toString(resultAfterAddingDuplicates));
        assertTrue(test.sumOfProbabilityIsOne(test.computeNewProbability(structure)));

    }


}