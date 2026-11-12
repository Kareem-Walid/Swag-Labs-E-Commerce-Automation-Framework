package Listeners;

import DriverFactory.DriverFactory;
import Utilities.LogsUtils;
import Utilities.Utility;
import io.qameta.allure.Allure;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class IInvokeMethodListenerClass implements IInvokedMethodListener {


    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {

        if (!method.isTestMethod()) {
            return;
        }

        File logFile = Utility.getLatestFile(LogsUtils.LOGS_PATH);
        if (logFile != null) {
            try {
                Allure.attachment("logs.log", Files.readString(Path.of(logFile.getPath())));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (testResult.getStatus() == ITestResult.FAILURE) {
            LogsUtils.info("Test case " + testResult.getName() + " Failed");
            Utility.takeScreenshot(DriverFactory.getDriver(), testResult.getName());
        }
    }
}