package Genetic_Algorithm;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class testRouletteWheel {
    private static final int ITERATIONS= 1000;
    private static final int POPULATION_SIZE = 11;
    // simulate roulette wheel for these.
    double[] cumultativeProbability;
    List<String[]> allStructures = new ArrayList<>();
    double[] relativeProbabilitySelection = new double[]{0.1, 0.2, 0.05, 0.15, 0.0, 0.11, 0.07, 0.04, 0.00, 0.12, 0.16};
    String[] strings = new String[]{"00001100",
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

    Map<String, Double> probabilityMap;


    testRouletteWheel() {
        probabilityMap = new HashMap<>();
        // merge the two arrays so String is key of probability
        probabilityMap = IntStream.range(0, strings.length).boxed()
                .collect(Collectors.toMap(i -> strings[i], i -> relativeProbabilitySelection[i]));

        for (int i = 0; i < 2; i++) {
            cumultativeProbability = new double[relativeProbabilitySelection.length];
            computeCumultativeProbability();


            String[] newStrings = new String[POPULATION_SIZE];
            for (int j = 0; j < POPULATION_SIZE; j++) {
                int Weightedindex = spinRouletteWheel();
                System.out.format("Selected index is %d%n" , Weightedindex);
                newStrings[j] = strings[Weightedindex];
            }
            allStructures.add(newStrings);
        }



    }


    private int spinRouletteWheel() {
        double rand = ThreadLocalRandom.current().nextDouble();
        // System.out.println("random double is + " +rand);
        /*
        loop throuhgh your array with the cumulative sum values and find the first value that is bigger than your random value.
        The ID is the id of your individual.
        For instance: If you draw 0.05, your ID would be 0 (the first individual). If you draw 0.8 your ID should be 3 (the last individual)
         */
        int index = -1;
        for (int i = 0; i < cumultativeProbability.length; i++) {
            if (cumultativeProbability[i] >= rand) {
                index = i;
                break;
            }
        }
        return index;
    }

    private void computeCumultativeProbability() {
        double sum = 0;
        for (int i = 0; i < relativeProbabilitySelection.length; i++) {
            sum += relativeProbabilitySelection[i];
            cumultativeProbability[i] = sum;
        }
    }


    public static void main(String[] args) {
        new testRouletteWheel();
    }

}
