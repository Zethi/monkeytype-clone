package com.github.zethi.monkeytypebackendclone.testingUtils;

import java.util.Random;

public final class Randomizer {


    public static String[] generateRandomStringArray(int arrayLength, int stringLength) {
        String[] randomStrings = new String[arrayLength];

        for (int i = 0; i < arrayLength; i++) {
            randomStrings[i] = generateRandomString(stringLength);
        }

        return randomStrings;
    }

    public static String generateRandomString(int length) {
        String allowedCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder randomString = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = new Random().nextInt(allowedCharacters.length());
            char randomChar = allowedCharacters.charAt(randomIndex);
            randomString.append(randomChar);
        }

        return randomString.toString();
    }
}
