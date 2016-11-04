package com.rpl.runner;

import com.rpl.runner.exception.RunnerException;
import com.rpl.runner.runner.Runner;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.util.HashMap;

public class Main {

    private static HashMap<String, String> map = new HashMap<String, String>();

    private static void loadRunners() {
        map.put("c", "com.rpl.runner.runner.CRunner");
        map.put("python", "com.rpl.runner.runner.PythonRunner");
        map.put("java", "com.rpl.runner.runner.JavaRunner");
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        loadRunners();

        ArgumentParser argumentParser = new ArgumentParser(args);

        if (argumentParser.parseOk()) {

            if (!map.containsKey(argumentParser.getLanguage())) {
                System.out.println("Invalid language: " + argumentParser.getLanguage());
                return;
            }

            Runner runner = null;
            try {
                runner = (Runner) Class.forName(map.get(argumentParser.getLanguage())).newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            boolean ok = true;
            try {
                runner.process(argumentParser.getSolution());
            } catch (RunnerException e) {
                System.out.println("Error type: \n" + e.getType());
                System.out.println("Error content: \n " + e.getMessageContent());
                ok = false;
            }

            if (ok) {
                System.out.println("Build and Run ok");
                System.out.println(runner.getStdout());
            }
        }

    }


}
