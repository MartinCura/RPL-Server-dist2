## Build
After a general `mvn clean install`, a JAR with dependencies is generated in the *target/* folder.

## Commands

#### Help
```
usage: runner
 -d,--data <additional-data>     Additional data for the running mode:
                                 input data for the input mode or tests
                                 for the test mode
 -l,--language <language-name>   Programming language
 -m,--mode <input|test>          Running mode: input or test
 -s,--solution <source>          Content of the solution
```

#### Examples
`java -jar target/rpl-runner-0.0.1.jar -l "python" -s "print 'Hello'" -m "input" -d ""`

## Current Compatibility

| Language/Mode        | Input           | Test              |
| -------------------- |:---------------:| -----------------:|
| Python               | OK              |    OK (unittest)  |
| Java                 | OK              |    OK (JUNIT 4)   |
| C                    | OK              |    OK (Custom libcriterion)             |


## Docker

### Generate Docker image
`docker build -t rpl .`

### Run through Docker
`docker run --rm rpl java -l "python" -s "print 'Hello'" -m "input" -d ""`


