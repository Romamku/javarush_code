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

    public static String cezarCoding(String text, int key) {
        StringBuilder resultString = new StringBuilder(text.length());
        char symbol;
        int index;
        for (int i = 0; i < text.length(); i++) {
            symbol = text.toLowerCase(Locale.ROOT).charAt(i);
            for (int j = 0; j < SYMBOL_SEQUENCE.length; j++) {
                if (symbol == SYMBOL_SEQUENCE[j]) {
                    index = j + key;
                    if (index >= 0 && index <= 40) {
                        resultString.append(SYMBOL_SEQUENCE[index]);
                    } else if (index > 40) {
                        resultString.append(SYMBOL_SEQUENCE[index % 41]);
                    }
                }
            }
        }
        return resultString.toString();
    }

    public static String cezarDecoding(String text, int key) {
        StringBuilder resultString = new StringBuilder(text.length());
        char symbol;
        int index;
        for (int i = 0; i < text.length(); i++) {
            symbol = text.charAt(i);
            for (int j = 0; j < SYMBOL_SEQUENCE.length; j++) {
                if (symbol == SYMBOL_SEQUENCE[j]) {
                    index = j - key;
                    if (index >= 0 && index <= 40) {
                        resultString.append(SYMBOL_SEQUENCE[index]);
                    } else if (index < 0 && index > -40) {
                        resultString.append(SYMBOL_SEQUENCE[41 + index]);
                    } else if (index < -40) {
                        resultString.append(SYMBOL_SEQUENCE[(index % 41) * (-1)]);
                    }
                }
            }
        }
        return resultString.toString();
    }

    public static String bruteForceDecoding(String pathFileForCode) throws IOException {
        String result = null;
        String[] variantText = new String[SYMBOL_SEQUENCE.length];
        for (int i = 0; i < SYMBOL_SEQUENCE.length; i++) {
            variantText[i] = cezarDecoding(IOWorker.readFile(pathFileForCode), i);
        }

        for (int i = 0; i < variantText.length; i++) {
            if (variantText[i].contains(", ") && variantText[i].contains(". ")) {
                result = variantText[i];
            }
        }
        return result;
    }

    public static String statisticDecoding(String textForStatisticDecoding, String textForCompare) {
        Character[] symbolsForStatisticDecoding = searchThreeRepeatableSymbol(textForStatisticDecoding);
        Character[] symbolsTextForCompare = searchThreeRepeatableSymbol(textForCompare);

        StringBuffer resultStb = new StringBuffer();

        for (int i = 0; i < 3; i++) {
            int numberCode = 0;
            int numberCompare = 0;
            for (int j = 0; j < SYMBOL_SEQUENCE.length; j++) {
                if (SYMBOL_SEQUENCE[j] == symbolsForStatisticDecoding[i]) {
                    numberCode = j;
                }
                if (SYMBOL_SEQUENCE[j] == symbolsTextForCompare[i]) {
                    numberCompare = j;
                }
            }
            int key = Math.abs(numberCode - numberCompare);
            resultStb.append(cezarDecoding(textForStatisticDecoding, key) + "\n");
        }
        return resultStb.toString();
    }

    private static Character[] searchThreeRepeatableSymbol(String textForCompare) {
        Character[] mass = new Character[3];
        Map<Character, Integer> symbolsCard = searchAmountSymbols(textForCompare);
        char oftRepeatableSymbol = 0;
        for (int i = 0; i < mass.length; i++) {
            int maxValue = 0;
            for (Map.Entry<Character, Integer> entry : symbolsCard.entrySet()) {
                int value = entry.getValue();
                if (value > maxValue) {
                    maxValue = value;
                    oftRepeatableSymbol = entry.getKey();
                }
            }
            mass[i] = oftRepeatableSymbol;
            symbolsCard.remove(mass[i]);
        }
        return mass;
    }

    private static Map<Character, Integer> searchAmountSymbols(String text) {
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < SYMBOL_SEQUENCE.length; i++) {
            char symbol = SYMBOL_SEQUENCE[i];
            int count = 0;
            for (int j = 0; j < text.length(); j++) {
                if (symbol == text.charAt(j)) {
                    count++;
                }
            }
            map.put(SYMBOL_SEQUENCE[i], count);
        }
        return map;
    }
}
