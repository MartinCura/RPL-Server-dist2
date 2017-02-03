package com.rpl.runner;

import org.apache.commons.cli.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArgumentParser {

    private String[] args;
    private Options options;
    private boolean parseOk;

    private String solution;
    private String language;
    private String mode;
    private String modeData;
    private List<String[]> extraFiles = new ArrayList<>();

    public ArgumentParser(String[] args) {
        this.args = args;

        loadOptions();
        parse();
    }

    private void loadOptions(){
        options = new Options();

        Option optSolution = Option.builder("s").required(true).longOpt("solution").argName("source").hasArg()
                .desc("Content of the solution").build();

        Option optLanguage = Option.builder("l").required(true).longOpt("language").argName("language-name").hasArg()
                .desc("Programming language").build();

        Option optMode = Option.builder("m").required(true).longOpt("mode").argName("input|test").hasArg()
                .desc("Running mode: input or test").build();

        Option optModeData = Option.builder("d").required(true).longOpt("data").argName("additional-data").hasArg()
                .desc("Additional data for the running mode: input data for the input mode or tests for the test mode").build();

        Option optFiles = Option.builder("f").required(false).longOpt("file").argName("[name] [content]").numberOfArgs(2)
                .desc("Extra files for testing").build();

        options.addOption(optSolution);
        options.addOption(optLanguage);
        options.addOption(optMode);
        options.addOption(optModeData);
        options.addOption(optFiles);
    }

    private void parse() {
        CommandLineParser parser = new DefaultParser();
        parseOk = true;
        try {
            CommandLine line = parser.parse(options, args);

            solution = line.getOptionValue("s");
            language = line.getOptionValue("l");
            mode = line.getOptionValue("m");
            modeData = line.getOptionValue("d");

            String[] files = line.getOptionValues("f");
            if (files != null) {
                for (int i = 0; i < files.length; i += 2) {
                    extraFiles.add(new String[]{files[i], files[i + 1]});
                }
            }

        } catch(ParseException exp) {
            System.out.println("Error:" + exp.getMessage());
            printHelp();
            parseOk = false;
        }
    }

    private void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("runner", options);
    }

    public String getSolution() {
        return solution;
    }

    public String getLanguage() {
        return language;
    }

    public String getMode() {
        return mode;
    }

    public String getModeData() {
        return modeData;
    }

    public List<String[]> getExtraFiles() {
        return extraFiles;
    }

    public boolean parseOk() {
        return parseOk;
    }
}
