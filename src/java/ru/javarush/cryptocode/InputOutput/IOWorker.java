package ru.javarush.cryptocode.InputOutput;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class IOWorker {

    public static String readFile(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    public static void writeFile(String codeLine, String filePath) throws IOException {
        Files.writeString(Paths.get(filePath), codeLine);
    }
}
