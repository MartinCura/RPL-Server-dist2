package com.rpl.service.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileUtils {

	public static String fileToString(File file) {
		Scanner scanner = null;
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
}
