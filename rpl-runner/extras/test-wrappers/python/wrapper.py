import unittest
import test
import sys
import os
import json


class RplTestResult(unittest.TestResult):
    # def startTest(self, test):
    #    super(RplTestResult, self).startTest(test)

    # def stopTest(self, test):
    #    super(RplTestResult, self).stopTest(test)

    def addSuccess(self, test):
        self.testResults.append({"name": test._testMethodName, "result": "passed"})
        super(RplTestResult, self).addSuccess(test)

    def addError(self, test, err):
        self.testResults.append({"name": test._testMethodName, "result": "error"})
        super(RplTestResult, self).addError(test, err)

    def addFailure(self, test, err):
        self.testResults.append({"name": test._testMethodName, "result": "failure"})
        super(RplTestResult, self).addFailure(test, err)

    def addSkip(self, test, reason):
        self.testResults.append({"name": test._testMethodName, "result": "skip"})
        super(RplTestResult, self).addSkip(test, reason)

    def addExpectedFailure(self, test, err):
        self.testResults.append({"name": test._testMethodName, "result": "error"})
        super(RplTestResult, self).addExpectedFailure(test, err)

    def addUnexpectedSucces(self, test):
        self.testResults.append({"name": test._testMethodName, "result": "unexpectedSuccess"})
        super(RplTestResult, self).addUnexpectedSucces(test)

    def startTestRun(self):
        self.testResults = []
        super(RplTestResult, self).startTestRun()

    def stopTestRun(self):
        result = {}
        result['result'] = super(RplTestResult, self).wasSuccessful()
        result['tests'] = self.testResults

        print json.dumps(result)

        super(RplTestResult, self).stopTestRun()


if __name__ == '__main__':
    nullf = open(os.devnull, 'w')

    testToRun = unittest.TextTestRunner(stream=nullf, descriptions=True, verbosity=2, failfast=False, buffer=False,
                                        resultclass=RplTestResult)
    suite = unittest.TestLoader().loadTestsFromTestCase(test.TestMethods)
    testToRun.run(suite)
