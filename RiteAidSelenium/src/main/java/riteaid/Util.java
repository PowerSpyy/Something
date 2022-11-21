package riteaid;

import java.util.Random;

public final class Util {
    @Todo
    public static String GeneratePhoneNumber() {
        Random random = new Random();
        String endString = String.valueOf(random.nextInt(790) + 201)
                         + String.valueOf(random.nextInt(900) + 100)
                         + String.valueOf(random.nextInt(900) + 100);
        return endString;
    }

    @Todo
    public static String GenerateEmailAddress(String ending) {
        return ending;
    }
}
