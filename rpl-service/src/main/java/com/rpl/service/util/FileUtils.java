package com.rpl.service.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.rpl.exception.RplException;
import com.rpl.model.MessageCodes;

public class FileUtils {

    private static final Integer FILE_KB_LIMIT = 100;
    private static final Integer KB = 1024;

    public static String fileToString(File file) {
        Scanner scanner;
        try {
            scanner = new Scanner(file);
            String result = scanner.useDelimiter("\\A").next();
            scanner.close();
            return result;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void validateFile(byte[] byteArray) throws RplException{
        if (toKB(byteArray.length) > FILE_KB_LIMIT){
            throw RplException.of(MessageCodes.ERROR_FILE_TOO_HEAVY, "");
        }
    }

    private static Integer toKB(int length) {
        return length / KB;
    }

    public static String getFileName(String[] contentDisposition) {

        for (String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {

                String[] name = filename.split("=");

                return name[1].trim().replaceAll("\"", "");
            }
        }
        return null;
    }

}
