package riteaid;

import java.util.ArrayList;

public final class Util {
    public static String GeneratePhoneNumber(int seed, int AreaCode) {
        int p = 9999991;
        int offset = 69420;
        int a = ((offset + seed) % (p - 1)) + 1;

        ArrayList<Integer> integers = new ArrayList<Integer>();
        integers.add(p);
        integers.add(a);

        for (int counter = 0; integers.get(counter + 1) != 1; ++counter) {
            integers.add(integers.get(counter) % integers.get(counter + 1));
        }

        int i = 1;
        int j = -(integers.get(integers.size() - 3)/integers.get(integers.size() - 2));
        int tmp;

        for (int counter = integers.size() - 4; counter >= 0; --counter) {
            tmp = j;
            j = i + j*-(integers.get(counter)/integers.get(counter + 1));
            i = tmp;
        }

        Integer thing = (j + p) % p;

        return "" + AreaCode + thing.toString();
    }

    public static String GenerateEmailAddress(String ending, int seed) {
            final String[] adj = {"red", "orange", "yellow", "green", "blue", "purple", "black", "white", "happy", "sad", "the"};

            final String[] noun = {"panda", "bird", "dog", "cat", "fish", "cow", "bear", "shirt", "pants", "house", "people", "chicken", "ant", "thing", "coffee", "rock"};

            assert seed < adj.length * noun.length;

            return adj[seed % adj.length] + noun[seed / adj.length] + ending;
    }

    public static void LogMessage(String message) {
        System.out.print("\033[42m" + " CLIENT  " + "\033[0m" + " ");
        System.out.println(message);
    }

    public static void LogMessageAsError(String message) {
        System.out.print("\033[41m" + " ERROR!  " + "\033[0m" + " ");
        System.out.println("\033[0;91m" + message + "\033[0m");
    }

    public static void LogMessageAsInfo(String message) {
        System.out.print("\033[46m" + " INFO    " + "\033[0m" + " ");
        System.out.println(message);
    }
}
