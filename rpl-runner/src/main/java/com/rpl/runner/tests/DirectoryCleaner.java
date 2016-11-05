package com.rpl.runner.tests;

import com.rpl.runner.Settings;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class DirectoryCleaner {
    public static void clean() {
        File outputDir = new File(Settings.EXECUTION_PATH);

        if (outputDir.exists() && outputDir.isDirectory()) {
            try {
                FileUtils.deleteDirectory(outputDir);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        outputDir.mkdir();
    }
}
