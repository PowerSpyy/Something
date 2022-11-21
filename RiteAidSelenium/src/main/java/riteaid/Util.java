package riteaid;

import java.util.Random;

public final class Util {
    @Todo
    public static String GeneratePhoneNumber() {
        Random random = new Random();
        return random.nextInt(790) + 201
                         + String.valueOf(random.nextInt(900) + 100)
                         + (random.nextInt(900) + 100);
    }

    @Todo
    public static String GenerateEmailAddress(String ending) {
        return ending;
    }
}
