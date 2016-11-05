package com.rpl.runner.tests;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class FileWriter {
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
}
