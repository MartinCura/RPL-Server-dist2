package com.rpl.runner.utils;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.Scanner;

public class LocalFileUtils {
    public static void write(String path, String content) {
        // Save solution
        PrintWriter out;
        try {
            out = new PrintWriter(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        out.print(content);
        out.close();
    }

    public static void write(String path, byte[] content) {
        File file = new File(path);
        BufferedOutputStream writer;
        try {
            writer = new BufferedOutputStream(new FileOutputStream(file));
            writer.write(content);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String fileToString(File file) {
        if (fileIsEmpty(file))
            return "";

        Scanner scanner;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        String text = scanner.useDelimiter("\\A").next();
        scanner.close();

        return text;
    }

    public static boolean fileIsEmpty(File file) {
        return (file.length() == 0);
    }

    public static void copyFile(String f1, String f2) {
        try {
            FileUtils.copyFile(new File(f1), new File(f2));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
