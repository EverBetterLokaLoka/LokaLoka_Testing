package com.kms.katalon.core.mobile.keyword.builtin

import java.text.MessageFormat

import org.openqa.selenium.InvalidElementStateException
import org.openqa.selenium.Keys
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.KeyInput
import org.openqa.selenium.interactions.Sequence;

import com.kms.katalon.core.annotation.internal.Action
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.exception.StepFailedException
import com.kms.katalon.core.helper.KeywordHelper
import com.kms.katalon.core.keyword.internal.SupportLevel
import com.kms.katalon.core.mobile.constants.StringConstants
import com.kms.katalon.core.mobile.keyword.internal.MobileAbstractKeyword
import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory
import com.kms.katalon.core.mobile.keyword.internal.MobileKeywordMain
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.selenium.util.SeleniumKeysUtil;

import groovy.transform.CompileStatic
import io.appium.java_client.AppiumDriver

@Action(value = "setText")
public class SetTextKeyword extends MobileAbstractKeyword {

	@CompileStatic
	@Override
	public SupportLevel getSupportLevel(Object ...params) {
		return super.getSupportLevel(params)
	}

	@CompileStatic
	@Override
	public Object execute(Object ...params) {
		TestObject to = getTestObject(params[0])
		String text = (String) params[1]
		int timeout = (int) params[2]
		FailureHandling flowControl = (FailureHandling)(params.length > 3 && params[3] instanceof FailureHandling ? params[3] : RunConfiguration.getDefaultFailureHandling())
		setText(to,text,timeout,flowControl)
	}

	@CompileStatic
	public void setText(TestObject to, String text, int timeout, FailureHandling flowControl) throws StepFailedException {
		MobileKeywordMain.runKeyword({
			KeywordHelper.checkTestObjectParameter(to)
			timeout = KeywordHelper.checkTimeout(timeout)
			WebElement element = findElement(to, timeout * 1000)
			if (element == null) {
				MobileKeywordMain.stepFailed(MessageFormat.format(StringConstants.KW_MSG_OBJ_NOT_FOUND, to.getObjectId()), flowControl, null, true)
				return
			}
			element.clear()
			try {
				element.sendKeys(text.toString())
			} catch (InvalidElementStateException e) {
				AppiumDriver driver = MobileDriverFactory.getDriver()
				element.click()
				KeyInput keyboard = new KeyInput("keyboard");
				Sequence sendKeys = new Sequence(keyboard, 0);

				for (int i = 0; i < text.length(); i++) {
					String c = text.charAt(i).toString()

					sendKeys.addAction(keyboard.createKeyDown(c.codePointAt(0)));
				}

				driver.perform(Arrays.asList(sendKeys));
			}
			String readableText = SeleniumKeysUtil.getReadableText(text)
			logger.logPassed(MessageFormat.format(StringConstants.KW_LOG_PASSED_TEXT_HAS_BEEN_SET_TO_ELEMENT, [
				readableText,
				to.getObjectId()
			] as Object[]))
		}, flowControl, true, to != null ? MessageFormat.format(StringConstants.KW_MSG_FAILED_TO_SET_ELEMENT_TEXT, to.getObjectId()) : StringConstants.KW_MSG_FAILED_TO_SET_ELEMENT_TEXT)
	}
}