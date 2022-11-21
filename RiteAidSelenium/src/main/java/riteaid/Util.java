package riteaid;

import java.util.ArrayList;
import java.util.Random;

public final class Util {
    public static long GeneratePhoneNumber(long seed, long p) {
        //int p = 9999991;
        int offset = 69420;
        long a = ((offset + seed) % (p - 1)) + 1;

        ArrayList<Long> integers = new ArrayList<Long>();
        integers.add(p);
        integers.add(a);

        for (int counter = 0; integers.get(counter + 1) != 1; ++counter) {
            integers.add(integers.get(counter) % integers.get(counter + 1));
        }

        long i = 1;
        long j = -(integers.get(integers.size() - 3)/integers.get(integers.size() - 2));
        long tmp;

        for (int counter = integers.size() - 4; counter >= 0; --counter) {
            tmp = j;
            j = i + j*-(integers.get(counter)/integers.get(counter + 1));
            i = tmp;
        }

        return (j + p) % p;

    }

    public static String GenerateEmailAddress(String ending, int seed) {
            String[] adj = {"red", "orange", "yellow", "green", "blue", "purple", "black", "white", "happy", "sad", "the"};

            String[] noun = {"panda", "bird", "dog", "cat", "fish", "cow", "bear", "shirt", "pants", "house", "people", "chicken", "ant", "thing", "coffee", "rock"};

            assert seed < adj.length * noun.length;

            return adj[seed % adj.length] + noun[seed / adj.length] + ending;
    }
}
