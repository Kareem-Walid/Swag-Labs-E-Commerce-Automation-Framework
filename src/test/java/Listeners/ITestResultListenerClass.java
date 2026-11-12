package Listeners;

import Utilities.LogsUtils;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ITestResultListenerClass implements ITestListener {


    @Override
    public void onTestStart(ITestResult result) {
        LogsUtils.info("Test Case " + result.getName() + " STARTED");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LogsUtils.info("Test Case " + result.getName() + " PASSED");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LogsUtils.info("Test Case " + result.getName() + " failed");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LogsUtils.info("Test Case " + result.getName() + " SKIPPED");
    }

}
