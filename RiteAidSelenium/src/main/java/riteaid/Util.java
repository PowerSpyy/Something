package riteaid;

import java.util.ArrayList;
import java.util.Random;

public final class Util {
    @Todo
    public static int GeneratePhoneNumber(int seed, int p) {
        //int p = 9999991;
        int offset = 69420;
        int a = (offset + seed) % p;

        assert seed != p-offset;
        assert a != 0;

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

        return (j + p) % p;

    }

    @Todo
    public static String GenerateEmailAddress(String ending) {
        return ending;
    }
}
