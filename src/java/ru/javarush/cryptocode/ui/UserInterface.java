package ru.javarush.cryptocode.ui;

import ru.javarush.cryptocode.InputOutput.IOWorker;
import ru.javarush.cryptocode.cryptography.Code;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInterface {
    public static void start() {
        System.out.println("*****************\n" +
                           "*****Welcome*****\n" +
                           "**CRYPTANALYZER**\n" +
                           "*****************\n" +
                "And so on the menu:\n " +
                "1. Encryption by key. Enter number 1.\n " +
                "2. Decrypt by key. Enter number 2.\n " +
                "3. Cryptanalysis/Decryption by \"Brute-force\" method. Enter number 3.\n " +
                "4. Cryptanalysis/Decryption by statistical analysis method. Enter number 4.\n " +
                "Enter the required action number:");

        try {
            //created text files at the root of the folder
            String pathFileForText = "text.txt";
            String pathFileForCode = "code.txt";
            String pathFileForCompare = "anyText.txt";

            int paragraph = input();
            if (paragraph == 1) {
                actionCezarEncrypt(pathFileForText, pathFileForCode);
            } else if (paragraph == 2) {
                actionCezarDecrypt(pathFileForText, pathFileForCode);
            } else if (paragraph == 3) {
                actionBruteForce(pathFileForText, pathFileForCode);
            } else if (paragraph == 4) {
                actionStatisticalAnalysis(pathFileForText, pathFileForCode, pathFileForCompare);
            }
        } catch (IOException | InputMismatchException ex) {
            System.out.println("You’ve done something wrong. Read the instructions carefully.");
        }
    }

    public static int input() {
        Scanner input = new Scanner(System.in);
            int currentNumber = input.nextInt();
            return currentNumber;
    }

    public static void actionCezarEncrypt(String pathFileForText, String pathFileForCode) throws IOException {
        System.out.println("Copy the required text to the \"text.txt\" in the root of the code folder.");
        String textFromFile = IOWorker.readFile(pathFileForText);
        System.out.println("Enter the encryption key.");
        int keyCode = input();
        String codeLine = Code.cezarCoding(textFromFile, keyVerification(keyCode));
        IOWorker.writeFile(codeLine, pathFileForCode);
        System.out.println("Encryption by key was successful.");
        System.out.println("The encrypted text is in the file \"code.txt\" " +
                "in the root of the folder with the code.");
    }

    public static void actionCezarDecrypt(String pathFileForText, String pathFileForCode) throws IOException {
        System.out.println("Copy the required text to the \"code.txt\" in the root of the code folder.");
        String codeFromFile = IOWorker.readFile(pathFileForCode);
        System.out.println("Enter the decryption key.");
        int keyDecode = input();
        String decodeLine = Code.cezarDecoding(codeFromFile, keyVerification(keyDecode));
        IOWorker.writeFile(decodeLine, pathFileForText);
        System.out.println("Decryption by key was successful.");
        System.out.println("The decrypted text is in the file \"text.txt\" in the root of the code folder.");
    }

    public static void actionBruteForce(String pathFileForText, String pathFileForCode) throws IOException {
        System.out.println("Copy the required text to the \"code.txt\" in the root of the code folder.");
        System.out.println("Enter 0 to confirm.");
        input();
        String decodeBruteForce = Code.bruteForceDecoding(pathFileForCode);
        IOWorker.writeFile(decodeBruteForce, pathFileForText);
        System.out.println("Cryptanalysis/decryption by \"Brute-force\" was successful.");
        System.out.println("The decrypted text is in the file \"text.txt\" in the root of the code folder.");
    }

    public static void actionStatisticalAnalysis(String pathFileForText, String pathFileForCode,
                                                String pathFileForCompare) throws IOException {
        System.out.println("Copy the required text to the \"code.txt\" in the root of the code folder.");
        System.out.println("Enter 0 to confirm.");
        input();
        String decodeStatisticMethod = Code.statisticDecoding(IOWorker.readFile(pathFileForCode),
                IOWorker.readFile(pathFileForCompare));
        IOWorker.writeFile(decodeStatisticMethod, pathFileForText);
        System.out.println("Cryptanalysis/decryption by statistical analysis was successful.");
        System.out.println("The decrypted text is in the file \"text.txt\" in the root of the code folder.");
        System.out.println("Select a viable option.");
    }

    private static int keyVerification(int inputKey) {
        int worksKey = inputKey;
        if (inputKey > Code.SYMBOL_SEQUENCE.length) {
            worksKey = inputKey % Code.SYMBOL_SEQUENCE.length;
        } else if (inputKey < 0) {
            worksKey = Code.SYMBOL_SEQUENCE.length + inputKey % Code.SYMBOL_SEQUENCE.length;
        }
        return worksKey;
    }
}
