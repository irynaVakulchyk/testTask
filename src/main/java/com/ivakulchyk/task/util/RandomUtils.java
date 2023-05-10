package com.ivakulchyk.task.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.concurrent.ThreadLocalRandom;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RandomUtils {

    public static int randomize(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    public static int randomize(int min, int max, int... integers) {
        int result = randomize(min, max);
        return !checkRandom(integers, result) ? randomize(min, max, integers) : result;
    }

    private static boolean checkRandom(int[] integers, int random) {
        for (Integer integer : integers) {
            if (integer == random) {
                return false;
            }
        }
        return true;
    }
}
