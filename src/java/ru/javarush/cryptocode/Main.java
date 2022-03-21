package ru.javarush.cryptocode;

import ru.javarush.cryptocode.InputOutput.IOWorker;
import ru.javarush.cryptocode.cryptography.Code;

import java.io.IOException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to the best Caesar cipher application!\n " +
                           "And so on the menu:\n " +
                           "1. Encryption by key. Enter number 1.\n " +
                           "2. Decrypt by key. Enter number 2.\n " +
                           "3. Cryptanalysis/Decryption by \"Brute-force\" method. Enter number 3.\n " +
                           "4. Cryptanalysis/Decryption by statistical analysis method. Enter number 4.\n " +
                           "Enter the required action number:");

        try (Scanner input = new Scanner(System.in);) {
            String pathFileForText = "text.txt";
            String pathFileForCode = "code.txt";

            int paragraph = input.nextInt();
            if (paragraph == 1) {
                System.out.println("Copy the required text to the \"text.txt\" in the root of the code folder.");
                String textFromFile = IOWorker.readFile(pathFileForText);
                System.out.println("Enter the encryption key.");
                int keyCode = input.nextInt();
                String codeLine = Code.cezarCoding(textFromFile, keyVerification(keyCode));
                IOWorker.writeFile(codeLine, pathFileForCode);
                System.out.println("Key encryption was successful.");
                System.out.println("The encrypted text is in the file \"code.txt\" " +
                                   "in the root of the folder with the code.");
            } else if (paragraph == 2) {
                System.out.println("Copy the required text to the \"code.txt\" in the root of the code folder.");
                String codeFromFile = IOWorker.readFile(pathFileForCode);
                System.out.println("Enter the decryption key.");
                int keyDecode = input.nextInt();
                String decodeLine = Code.cezarDecoding(codeFromFile, keyVerification(keyDecode));
                IOWorker.writeFile(decodeLine, pathFileForText);
                System.out.println("Decryption by key was successful.");
                System.out.println("The decrypted text is in the file \"text.txt\" in the root of the code folder.");
            } else if (paragraph == 3) {
                System.out.println("Copy the required text to the \"code.txt\" in the root of the code folder.");
                System.out.println("Enter 0 to confirm.");
                input.next();
                String decodeBruteForce = Code.bruteForceDecoding("code.txt");
                IOWorker.writeFile(decodeBruteForce, pathFileForText);
                System.out.println("Cryptanalysis/decryption by \"Brute-force\" was successful.");
                System.out.println("The decrypted text is in the file \"text.txt\" in the root of the code folder.");
            } else if (paragraph == 4) {
                System.out.println("Copy the required text to the \"code.txt\" in the root of the code folder.");
                System.out.println("Enter 0 to confirm.");
                input.next();
                String decodeStatisticMethod = Code.statisticDecoding(IOWorker.readFile(pathFileForCode),
                        IOWorker.readFile("text_brute.txt"));
                IOWorker.writeFile(decodeStatisticMethod, pathFileForText);
                System.out.println("Cryptanalysis/decryption by statistical analysis was successful.");
                System.out.println("The decrypted text is in the file \"text.txt\" in the root of the code folder.");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
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


