import subprocess

runExample = "c"


def openFiles(solutionPath, testPath):
	solutionFile = open(solutionPath, 'r')
	solution = solutionFile.read()

	dataFile = open(testPath, 'r')
	test = dataFile.read()
	
	return [solution, test]
	
if (runExample == "python"):
	[solution, test] = openFiles('extras/code-samples/python/test/solution.py', 'extras/code-samples/python/test/test.py')
	subprocess.call(["java", "-jar", "target/rpl-runner-0.0.1.jar", "-l", "python", "-m", "test", "-s", solution, "-d", test])
	
elif (runExample == "java"):
	[solution, test] = openFiles('extras/code-samples/java/test/Solution.java', 'extras/code-samples/java/test/TestSolution.java')
	subprocess.call(["java", "-jar", "target/rpl-runner-0.0.1.jar", "-l", "java", "-m", "test", "-s", solution, "-d", test])

else:
	[solution, test] = openFiles('extras/code-samples/c/test/solution.c', 'extras/code-samples/c/test/test.c')
	subprocess.call(["java", "-jar", "target/rpl-runner-0.0.1.jar", "-l", "c", "-m", "test", "-s", solution, "-d", test])
	
	#subprocess.call(["docker", "run", "--rm", "rpl", "java", "-l", "c", "-m", "test", "-s", solution, "-d", test])

	
