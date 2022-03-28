package ru.javarush.cryptocode.cryptography;

import ru.javarush.cryptocode.InputOutput.IOWorker;
import java.io.IOException;
import java.util.*;

public class Code {
    public static final char[] SYMBOL_SEQUENCE = {'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и',
                                                  'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т',
                                                  'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь',
                                                  'э', 'ю', 'я', '.', ',', '"', ':', '-', '!', '?',
                                                  ' '};

    public static final int ALPHABET_LENGTH = SYMBOL_SEQUENCE.length;

    public static String cezarCoding(String originalText, int key) {
        return process(originalText, key);
    }

    public static String cezarDecoding(String originalText, int key) {
        return process(originalText, -key);
    }

    public static String bruteForceDecoding(String pathFileForCode) throws IOException {
        String result = null;
        String[] optionText = new String[ALPHABET_LENGTH];
        for (int i = 0; i < ALPHABET_LENGTH; i++) {
            optionText[i] = cezarDecoding(IOWorker.readFile(pathFileForCode), i);
        }

        for (int i = 0; i < optionText.length; i++) {
            if (optionText[i].contains(", ") && optionText[i].contains(". ")) {
                result = optionText[i];
            }
        }

        return result;
    }

    public static String statisticDecoding(String originalText, String textForCompare) {
        Character[] symbolsForStatisticDecoding = searchThreeRepeatableSymbols(originalText);
        Character[] symbolsTextForCompare = searchThreeRepeatableSymbols(textForCompare);

        StringBuffer resultStb = new StringBuffer();

        for (int i = 0; i < 3; i++) {
            int originalCharIndex = 0;
            int compareCharIndex = 0;
            for (int alphabetCharIndex = 0; alphabetCharIndex < ALPHABET_LENGTH; alphabetCharIndex++) {
                if (SYMBOL_SEQUENCE[alphabetCharIndex] == symbolsForStatisticDecoding[i]) {
                    originalCharIndex = alphabetCharIndex;
                }
                if (SYMBOL_SEQUENCE[alphabetCharIndex] == symbolsTextForCompare[i]) {
                    compareCharIndex = alphabetCharIndex;
                }
            }
            int key = Math.abs(originalCharIndex - compareCharIndex);
            resultStb.append(cezarDecoding(originalText, key) + "\n");
        }

        return resultStb.toString();
    }

    private static String process(String originalText, int key) {
        StringBuilder resultStb = new StringBuilder(originalText.length());

        int originalCharIndex;
        int newCharIndex;
        for (int i = 0; i < originalText.length(); i++) {
            char originalChar = originalText.toLowerCase(Locale.ROOT).charAt(i);
            for (int j = 0; j < ALPHABET_LENGTH; j++) {
                if (originalChar == SYMBOL_SEQUENCE[j]) {
                    originalCharIndex = j;
                    newCharIndex = (ALPHABET_LENGTH + (originalCharIndex + key)) % ALPHABET_LENGTH;
                    resultStb.append(SYMBOL_SEQUENCE[newCharIndex]);
                }
            }
        }

        return resultStb.toString();
    }

    private static Character[] searchThreeRepeatableSymbols(String text) {
        Character[] threeRepeatableSymbols = new Character[3];
        Map<Character, Integer> symbolsCard = searchAmountSymbols(text);
        char oftRepeatableSymbol = 0;
        for (int i = 0; i < threeRepeatableSymbols.length; i++) {
            int maxValue = 0;
            for (Map.Entry<Character, Integer> entry : symbolsCard.entrySet()) {
                int value = entry.getValue();
                if (value > maxValue) {
                    maxValue = value;
                    oftRepeatableSymbol = entry.getKey();
                }
            }
            threeRepeatableSymbols[i] = oftRepeatableSymbol;
            symbolsCard.remove(threeRepeatableSymbols[i]);
        }

        return threeRepeatableSymbols;
    }

    private static Map<Character, Integer> searchAmountSymbols(String text) {
        Map<Character, Integer> mapSymbols = new HashMap<>();
        for (int i = 0; i < ALPHABET_LENGTH; i++) {
            char symbol = SYMBOL_SEQUENCE[i];
            int count = 0;
            for (int j = 0; j < text.length(); j++) {
                if (symbol == text.charAt(j)) {
                    count++;
                }
            }
            mapSymbols.put(SYMBOL_SEQUENCE[i], count);
        }

        return mapSymbols;
    }
}
