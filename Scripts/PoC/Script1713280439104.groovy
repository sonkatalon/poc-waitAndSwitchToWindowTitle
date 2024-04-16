import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.driver.WebUIDriverType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.util.WebDriverPropertyUtil
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions


public static void openBrowserWithExtension() {
    ChromeOptions options = new ChromeOptions()
    String pathToExtension = "/absolute/path/to/katalon-g5/packages/recorder-webapp/build"
    options.addArguments("--load-extension=" + pathToExtension)
    System.setProperty("webdriver.chrome.driver", DriverFactory.getChromeDriverPath())
    WebDriver driver = new ChromeDriver(options)
    DriverFactory.changeWebDriver(driver)
}

public static void waitAndSwitchToWindowTitle(String title) {
    long millisSum = 0;
    while (DriverFactory.getWebDriver().getWindowHandles().size() == 1) {
        long millis = 1000 // sleep for 1s per cycle
        Thread.sleep(millis);
        millisSum += millis

		// wait for 30s at maximum
        if (millisSum > 30000) { break }
    }
    WebUI.switchToWindowTitle(title)
}

openBrowserWithExtension(); 

WebUI.navigateToUrl('https://testops.qa.katalon.com/')

// login
WebUI.click(findTestObject('Object Repository/Page_signIn/div_continueWithEmail'))
WebUI.setText(findTestObject('Object Repository/Page_signIn/input_username'), 'chi.nguyen+1@katalon.com')
WebUI.setEncryptedText(findTestObject('Object Repository/Page_signIn/input_password'), 'iqphkQK+i2rAlAEv1FWt9g==')
WebUI.click(findTestObject('Object Repository/Page_signIn/btn_login'))

// create a new test case
WebUI.click(findTestObject('Object Repository/Page_home/aHref_cloudStudio'))
WebUI.click(findTestObject('Object Repository/Page_cloudStudio/aHref_g5RecordTestCase'))
WebUI.click(findTestObject('Object Repository/Page_cloudStudio/div_locationSonDao'))
WebUI.click(findTestObject('Object Repository/Page_cloudStudio/aHref_g5SubmitRecordTestCase'))

// switch to AUT and perform a click
waitAndSwitchToWindowTitle("CURA Healthcare Service")
WebUI.click(findTestObject('Object Repository/Page_cura/aHref_makeAppointment'))

// switch back to editor to verify the captured test step
WebUI.switchToWindowIndex(0)
WebUI.click(findTestObject('Object Repository/Page_editor/button_stop'))
WebUI.verifyTextPresent("Click on 'a - Make Appointment'", false)

WebUI.closeBrowser(); 
