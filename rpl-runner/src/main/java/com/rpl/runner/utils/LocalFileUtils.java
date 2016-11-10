package com.rpl.runner.utils;

import com.rpl.runner.Settings;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class LocalFileUtils {
    public static void write(String path, String content) {
        // Save solution
        PrintWriter out = null;
        try {
            out = new PrintWriter(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        out.print(content);
        out.close();
    }

    public static String fileToString(File file) {
        if (fileIsEmpty(file))
            return "";

        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
