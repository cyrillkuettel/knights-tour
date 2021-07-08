package Genetic_Algorithm;

import javax.imageio.metadata.IIOMetadataFormatImpl;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class testRouletteWheel {
    private static final int ITERATIONS = 1000;
    private static final int POPULATION_SIZE = 11;
    // simulate roulette wheel for these.
    double[] cumultativeProbability;
    List<String[]> allStructures = new ArrayList<>();
    List<Double[]> allProbabilities = new ArrayList<>();

    Double[] probability = new Double[]{0.1, 0.2, 0.05, 0.15, 0.0, 0.11, 0.07, 0.04, 0.00, 0.12, 0.16};
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

    Map<String, Double> probabilityMap;


    testRouletteWheel() {
        probabilityMap = new HashMap<>();
        // merge the two arrays so String is key of probability
        probabilityMap = IntStream.range(0, structure.length).boxed()
                .collect(Collectors.toMap(i -> structure[i], i -> probability[i]));
        cumultativeProbability = new double[probability.length];
        computeCumultativeProbability();
        // start Values
        allStructures.add(structure);
        allProbabilities.add(probability);

        for (int i = 0; i < ITERATIONS-1; i++) {
            String[] newStrings = new String[POPULATION_SIZE];
            Double[] newProbability = new Double[POPULATION_SIZE];

            for (int j = 0; j < POPULATION_SIZE; j++) {
                int Weightedindex = spinRouletteWheel();
                // System.out.format("Selected index is %d%n" , Weightedindex);
                newStrings[j] = structure[Weightedindex];
                newProbability[j] = probabilityMap.get(newStrings[j]);

            }
            allStructures.add(newStrings);
            allProbabilities.add(newProbability);
        }

        // allStructures.forEach(el -> System.out.println(Arrays.toString(el)));

        // I want to keep track of the number of selections per String
        int[] occurences = countOccurencesForAllStrings();
        double[] relativeOccurences = new double[POPULATION_SIZE];

        for (int i = 0; i < relativeOccurences.length; i++) {
            double divisor = (double) (POPULATION_SIZE * ITERATIONS);
            relativeOccurences[i] =  round(occurences[i] / divisor, 4);
        }

        System.out.println(Arrays.toString(occurences));
        System.out.println(Arrays.toString(probability));
        System.out.println(Arrays.toString(relativeOccurences));

    }
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    /**
     * this could be made way better time complexity than O(size^2*iterations)
     * but it's only a test so whatever
     * @return number of occurences for each string
     */
    public int[] countOccurencesForAllStrings() {

        int[] occurences = new int[POPULATION_SIZE];

        for (int i = 0; i < structure.length; i++) {
            int occured = 0;
            for (String[] element : allStructures) {
                occured += Collections.frequency(Arrays.stream(element).collect(Collectors.toList()), structure[i]);
            }
            occurences[i] = occured;
        }
        return occurences;
    }


    private int spinRouletteWheel() {
        /*
        loop throuhgh your array with the cumulative sum values and find the first value that is bigger than your random value.
        The ID is the id of your individual.
        For instance: If you draw 0.05, your ID would be 0 (the first individual).
         If you draw 0.8 your ID should be 3 (the last individual)
         */
        double randomValue = ThreadLocalRandom.current().nextDouble();
        int index = -1;
        for (int i = 0; i < cumultativeProbability.length; i++) {
            if (cumultativeProbability[i] > randomValue) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            throw new IllegalArgumentException("index in Roulette Wheel is -1. ");
        }
        return index;
    }

    private void computeCumultativeProbability() {
        double sum = 0;
        for (int i = 0; i < probability.length; i++) {
            sum += probability[i];
            cumultativeProbability[i] = sum;
        }
    }


    public static void main(String[] args) {
        new testRouletteWheel();
    }

}
