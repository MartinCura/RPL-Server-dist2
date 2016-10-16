package com.rpl.runner;

public class Settings {
    // Max allowed time in seconds to execute the program
    public static final int EXECUTION_TIMEOUT = 10000;

    // Execute path
    public static final String EXECUTION_PATH = "extras/code";

    // Temporary output files
    public static final String ERROR_FILE = "extras/code/tmp/stderr-";
    public static final String OUTPUT_FILE = "extras/code/tmp/stdout-";
}
