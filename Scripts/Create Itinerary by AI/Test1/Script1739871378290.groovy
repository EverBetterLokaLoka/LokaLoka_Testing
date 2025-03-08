import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.By
import org.testng.Assert
import mypackage.MobileSupport
import io.appium.java_client.MobileElement
import io.appium.java_client.android.AndroidDriver
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import io.appium.java_client.remote.MobileCapabilityType
import io.github.ashwith.flutter.FlutterFinder
import io.github.ashwith.flutter.FlutterElement
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.URL
import com.kms.katalon.core.util.KeywordUtil

// Khai báo capabilities để chạy Appium với Flutter
DesiredCapabilities desCaps = new DesiredCapabilities();
desCaps.setCapability("appium:automationName", "Flutter");
desCaps.setCapability("appium:deviceName", "emulator-5554");
desCaps.setCapability("platformName", "Android");
desCaps.setCapability("appium:app", "C:\\Users\\Admin\\AndroidStudioProjects\\Mobile\\build\\app\\outputs\\flutter-apk\\app-debug.apk");

// Khởi tạo AndroidDriver với Flutter Finder
AndroidDriver driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), desCaps);
// Lấy danh sách context hiện có
Set<String> contextHandles = driver.getContextHandles();
for (String contextName : contextHandles) {
	System.out.println("Available context: " + contextName);
}

if (driver == null) {
	KeywordUtil.markFailed("❌ Driver chưa được khởi tạo hoặc kết nối Appium bị lỗi")
}

// Chuyển sang context FLUTTER nếu tồn tại
if (contextHandles.contains("FLUTTER")) {
    driver.context("FLUTTER");
    System.out.println("✅ Đã chuyển sang context FLUTTER");
} else {
    KeywordUtil.markFailed("❌ Không tìm thấy context FLUTTER, kiểm tra lại app")
}
// Sử dụng FlutterFinder để tìm button "Get Started"

FlutterFinder finder = new FlutterFinder(driver)

KeywordUtil.logInfo("⏳ Chờ 3 giây trước khi thao tác...")
Thread.sleep(3000) // Chờ 3 giây

FlutterElement getStartedButton = finder.byValueKey("get_started_button")

if (getStartedButton != null) {
    getStartedButton.click()
} else {
    KeywordUtil.markFailed("❌ Không tìm thấy button 'Get Started'")
}


FlutterElement nameField = finder.byValueKey("full_name")
nameField.sendKeys("honglam")

FlutterElement emailField = finder.byValueKey("email")
emailField.sendKeys("user@gmail.com")

FlutterElement passwordField = finder.byValueKey("password")
passwordField.sendKeys("12345678")

FlutterElement confirmPasswordField = finder.byValueKey("confirm_password")
    confirmPasswordField.sendKeys("12345678")

// Đóng driver sau khi chạy xong
//driver.quit()