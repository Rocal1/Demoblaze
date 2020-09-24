package reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentReportsListeners implements ITestListener {


	ExtentReports extent = ExtentReport.getReporterObject();
	ExtentTest test;

	@Override
	public synchronized void onStart(ITestContext context) {

	}

	@Override
	public synchronized void onFinish(ITestContext context) {
		extent.flush();
	}

	@Override
	public synchronized void onTestStart(ITestResult result) {
		test= extent.createTest(result.getMethod().getMethodName());
	}

	@Override
	public synchronized void onTestSuccess(ITestResult result) {
		test.log(Status.PASS, "Test PASS");
	}

	@Override
	public synchronized void onTestFailure(ITestResult result) {
		test.fail(result.getThrowable());
	}

	@Override
	public synchronized void onTestSkipped(ITestResult result) {

	}

	@Override
	public synchronized void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	}

}