import java.io.*;
import java.util.*;

import org.json.simple.*;
import org.json.simple.parser.*;

public class Main {
    private static Long NUM_SAMPLES;

    private static Long[] preimage_bit_sizes;

    private static Long[] collision_bit_sizes;



    private static void collision(long num_bits) throws Exception {
        String filename = "collision_" + num_bits + "bits.txt";
        PrintStream ps = new PrintStream("./data_files/collision/" + filename);
        System.setOut(ps);

        System.out.println("Collision Attack. Number of Bits: " + num_bits);
        System.out.println("Number of Sample: " + NUM_SAMPLES);
        System.out.println("Raw Data\n");
        ArrayList<Long> counts = new ArrayList<>();

        for(int i = 1; i <= NUM_SAMPLES; ++i){
            HashMap<Long, String> hashMap = new HashMap<>(); // maps digest -> original message
            long count = 0;
            System.out.println("Sample #: " + i);
            while (true) {
                String msg = UUID.randomUUID().toString();
                long digest = new SHA1Wrapper().hash(msg.getBytes(), num_bits);
                ++count;
                if (hashMap.containsKey(digest)) {
                    System.out.println("Number of Guesses: " + count);
                    System.out.println("Original Message and Hash : " + hashMap.get(digest) + "->" + digest);
                    System.out.println("Collision Message and Hash: " + msg + "->" + digest);
                    System.out.println("-------------------------------------------------------------------------");
                    counts.add(count);
                    break;
                }

                hashMap.put(digest, msg);
            }
        }

        System.out.println("\n");
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("*************************************************************************");
        System.out.println("-------------------------------------------------------------------------\n");

        System.out.println("Aggregate Data");
        long sum = 0;
        for(long count: counts){
            sum += count;
        }
        long avg = sum / counts.size();
        System.out.println("Average Number of Guesses: " + avg);
        long avg_deviation = avg - (long)Math.pow(2, num_bits/2);
        double avg_deviation_percent = (double) avg_deviation / (double)avg;
        System.out.println("Average Deviation:         " + avg_deviation);
        System.out.println("Average Deviation Percent: " + avg_deviation_percent);

        ps.close();
    }

    private static void collision() throws Exception {
        for(long num_bits: collision_bit_sizes){
            collision(num_bits);
        }
    }

    private static void preimage(long num_bits) throws Exception {
        String filename = "preimage_" + num_bits + "bits.txt";
        PrintStream ps = new PrintStream("./data_files/preimage/" + filename);
        System.setOut(ps);

        System.out.println("Preimage Attack. Number of Bits: " + num_bits);
        System.out.println("Number of Samples: " + NUM_SAMPLES);
        System.out.println("Raw Data\n");

        ArrayList<Long> counts = new ArrayList<>();

        for (int i = 1; i <= NUM_SAMPLES; ++i) {
            String original_msg = UUID.randomUUID().toString();
            long original_digest = new SHA1Wrapper().hash(original_msg.getBytes(), num_bits);
            long count = 0;
            System.out.println("Sample #: " + i);
            while (true) {
                String msg = UUID.randomUUID().toString();
                long digest = new SHA1Wrapper().hash(msg.getBytes(), num_bits);
                ++count;
                if (original_digest == digest) {
                    System.out.println("Number of Guesses: " + count);
                    System.out.println("Original Message and Hash : " + original_msg + " -> " + original_digest);
                    System.out.println("Collision Message and Hash: " + msg + " -> " + digest);
                    System.out.println("-------------------------------------------------------------------------");
                    counts.add(count);
                    break;
                }
            }
        }
        System.out.println("\n");
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("*************************************************************************");
        System.out.println("-------------------------------------------------------------------------\n");

        System.out.println("Aggregate Data");
        long sum = 0;
        for(long count: counts){
            sum += count;
        }
        long avg = sum / counts.size();
        System.out.println("Average Number of Guesses: " + avg);
        long avg_deviation = avg - (long)Math.pow(2, num_bits);
        double avg_deviation_percent = (double) avg_deviation / (double)avg;
        System.out.println("Average Deviation:         " + avg_deviation);
        System.out.println("Average Deviation Percent: " + avg_deviation_percent);

        ps.close();
    }

    private static void preimage() throws Exception {
        for(long num_bits: preimage_bit_sizes){
            preimage(num_bits);
        }
    }

    private static void setup() throws Exception {

        JSONObject config = (JSONObject) (new JSONParser().parse(new FileReader("./config.json")));
        NUM_SAMPLES = (Long) config.get("num_samples");
        JSONArray arr = (JSONArray) config.get("preimage_bit_sizes");
        preimage_bit_sizes = (Long[]) arr.toArray(new Long[arr.size()]);
        arr = (JSONArray) config.get("collision_bit_sizes");
        collision_bit_sizes = (Long[]) arr.toArray(new Long[arr.size()]);

    }

    public static void main(String[] args) throws Exception {
        setup();
//        preimage();
        collision();

    }
}