package Genetic_Algorithm;

import javax.imageio.metadata.IIOMetadataFormatImpl;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class testRouletteWheel {
    private static final int ITERATIONS = 10000;
    private static final int POPULATION_SIZE = 11;
    // simulate roulette wheel for these.

    List<String[]> allStructures = new ArrayList<>();
    List<Double[]> allProbabilities = new ArrayList<>();
    Map<String, Double> probabilityMap;
    // some values for testing
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

    public static void main(String[] args) {
        new testRouletteWheel();
    }


    // my error:
        // I have wrongfully assumed, that probability can just be mapped in each successive Iteration.
        // This is implies that all Bitstrings are always selected, which is of course not the case.
        // After minimum 1 Iteration, some Bitstrings can occur more than once.
        // Therefore, I need to change the assignment of newProbability. for the duplicates,
        // the initial probability has to be multiplied, I think. The Map can't be used like this.


    testRouletteWheel() {
        probabilityMap = IntStream.range(0, structure.length).boxed() // Map String[] to Double[]
                .collect(Collectors.toMap(i -> structure[i], i -> probability[i]));

       double[] cumulativeProbability = computeCumultativeProbability(probability); // this changes for each Iteration

        allStructures.add(structure);
        allProbabilities.add(probability);

        for (int i = 0; i < ITERATIONS-1; i++) {
            String[] newStructure = new String[POPULATION_SIZE];
            Double[] newProbability = new Double[POPULATION_SIZE];
            for (int j = 0; j < POPULATION_SIZE; j++) { // spin the wheel for each Individual
                int Weightedindex = spinRouletteWheel(cumulativeProbability);
                newStructure[j] = structure[Weightedindex];

                // newProbability[j] = probabilityMap.get(newStructure[j]);
            }
            newProbability  = computeNewProbability(newStructure);
            if (!sumOfProbabilityIsOne(newProbability)) {
                System.out.printf("Probability not 1.0 in sum \n");
            }
            allStructures.add(newStructure);
            allProbabilities.add(newProbability);
           //  cumultativeProbability = computeCumultativeProbability(newProbability);
        }

        // allStructures.forEach(el -> System.out.println(Arrays.toString(el)));

        generateReport();

    }

    /**
     * used for Testing
     * @param test Indicates test
     */
    testRouletteWheel(String test) {
        probabilityMap = new HashMap<>();
        probabilityMap = IntStream.range(0, structure.length).boxed() // Map String[] to Double[]
                .collect(Collectors.toMap(i -> structure[i], i -> probability[i]));
    }




    public void generateReport() {
        // Count occurrences of strings.
        int[] occurrences = countOccurencesForAllStrings();
        double[] relativeOccurrences = new double[POPULATION_SIZE];

        // Finally, normalize values of frequency per String
        double divisor = (POPULATION_SIZE * ITERATIONS);
        for (int i = 0; i < relativeOccurrences.length; i++) {
            relativeOccurrences[i] =  round(occurrences[i] / divisor, 4);
        }

        // They should be roughly equal to the expected value
        System.out.println(Arrays.toString(occurrences));
        System.out.println(Arrays.toString(probability));
        System.out.println(Arrays.toString(relativeOccurrences));
    }

    /**
     * Simulates the biased roulette wheel. Spins exactly one time.
     * @param cumulativeProbability From the current population:
     * all relative probabilities sequentially summed up.
     * @return selected index of biased roulette wheel.
     */
    private int spinRouletteWheel(double[] cumulativeProbability) {
        /*
        loop throuhgh your array with the cumulative sum values and find the first value that is bigger than your random value.
        The ID is the id of your individual.
        For instance: If you draw 0.05, your ID would be 0 (the first individual).
         If you draw 0.8 your ID should be 3 (the last individual)
         */
        int index;

        OptionalDouble maxElement = DoubleStream.of(cumulativeProbability).max();
        double max = maxElement.isPresent() ? maxElement.getAsDouble() : -1.0;
        double randomValue;
        if (max == -1.0) {throw new IllegalArgumentException("function spinRouletteWheel: input array empty?"); }
        do {
             randomValue = ThreadLocalRandom.current().nextDouble();
             index = -1;
            for (int i = 0; i < cumulativeProbability.length; i++) {
                if (cumulativeProbability[i] >= randomValue) {
                    index = i;
                    break;
                }
            }
        } while (index == -1);

        return index;
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


    private double[] computeCumultativeProbability(Double[] probability) {
        double sum = 0;
        double[] cumultative = new double[probability.length];
        for (int i = 0; i < probability.length; i++) {
            sum += probability[i];
            cumultative[i] = sum;
        }
        return cumultative;
    }

    public boolean sumOfProbabilityIsOne(Double[] input) {
        double[] zwischenSpeicher = new double[input.length];
        for (int i = 0; i < input.length; i++) {
            zwischenSpeicher[i] = input[i];
        }
        return DoubleStream.of(zwischenSpeicher).sum() == 1.0;
    }


    // this is a bad Idea. This kind of strucure will note be used in a realistic setting.
    public Double[] computeNewProbability(String[] chromosomes) {
        /*
        List<String> list = Arrays.stream(chromosomes).collect(Collectors.toList());
        Map<String, Long> wordCountForEachItem =
                list.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        */
        // Methodology: Find duplicates and multiply their initial probability with their num_of_occurences.
        Double[] occurrences = new Double[chromosomes.length];
        for (int i = 0; i < chromosomes.length; i++) {
            String chrom = chromosomes[i];

            Double occured = 0.0;
            for (String chromosome : chromosomes) {
                if (chromosome.equals(chrom)) {
                    occured++;
                }
            }
            Double initialProbability = probabilityMap.get(chrom);
            occurrences[i] = initialProbability * occured;
        }
        return occurrences;
    }


}
