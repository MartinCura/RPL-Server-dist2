package com.rpl.runner;

import com.rpl.model.runner.Result;
import com.rpl.model.runner.ResultStatus;
import com.rpl.model.runner.Tests;
import com.rpl.runner.exception.RunnerException;
import com.rpl.runner.runner.Runner;
import com.rpl.runner.utils.DirectoryCleaner;
import com.rpl.runner.utils.JsonUtils;

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
        DirectoryCleaner.clean();
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
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            Runner.TestMode mode = argumentParser.getMode().equals("test") ? Runner.TestMode.TEST : Runner.TestMode.INPUT;
            runner.setMode(mode);
            runner.setModeData(argumentParser.getModeData());
            runner.setSolution(argumentParser.getSolution());
            runner.setExtraFiles(argumentParser.getExtraFiles());

            Result result = new Result();
            ResultStatus status = new ResultStatus();

            boolean ok = true;
            try {
                runner.process();
            } catch (RunnerException e) {
                status.setResult(ResultStatus.STATUS_ERROR);
                status.setType(e.getType());
                status.setStage(e.getStage());
                status.setStderr(e.getMessageContent());
                ok = false;
            }

            if (ok) {
                status.setResult(ResultStatus.STATUS_OK);

                if (mode.equals(Runner.TestMode.TEST)) {
                    System.out.println(runner.getStdout());//
                    Tests tests = JsonUtils.stringToObject(runner.getStdout());
                    result.setTests(tests);
                } else if (mode.equals(Runner.TestMode.INPUT)) {
                    result.setStdout(runner.getStdout());
                }
            }

            result.setStatus(status);
            System.out.println(JsonUtils.objectToJson(result));

        }

    }


}
