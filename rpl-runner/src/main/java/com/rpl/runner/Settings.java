package com.rpl.runner;

public class Settings {
    // Max allowed time in seconds to execute the program
    public static int EXECUTION_TIMEOUT = 10000;

    // Execute path
    public static final String EXECUTION_PATH = "output/";

    // Temporary output files
    public static final String ERROR_FILE = "output/stderr-";
    public static final String OUTPUT_FILE = "output/stdout-";
}
