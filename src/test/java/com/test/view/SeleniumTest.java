package com.test.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SeleniumTest {

	private WebDriver driver;
	private String baseUrl;
	private String thisWindow;
	
	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.gecko.driver", "C:/springsource/geckodriver-v0.14.0-win64/geckodriver.exe");
		driver = new FirefoxDriver();
		baseUrl = "http://www.google.com";		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	@Test
	public void 구글_검색_테스트()  throws Exception {
		driver.get(baseUrl);
		thisWindow = driver.getWindowHandle();
		driver.findElement(By.name("q")).click();
		driver.findElement(By.name("q")).sendKeys("펭귄너구리");
		driver.findElement(By.name("btnK")).click();
		
		String bodyText = driver.findElement(By.tagName("body")).getText();
		System.out.println(bodyText);
		assertEquals("Google", driver.getTitle());
		assertTrue("원하는 결과값을 찾지 못하였습니다.", bodyText.contains("한국"));
	}
	
	@After
	public void tearDown() throws Exception {
		driver.switchTo().window(thisWindow);
		driver.close();
		driver.quit();
	}
}
