package com.outbrain.selenium.logging;

import junit.framework.Assert;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class OutbrainCopyrightYearTest {

  private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OutbrainCopyrightYearTest.class);
  private volatile WebDriver webdriver;
  private static int THIS_YEAR = new DateTime().getYear();

  @Before
  public void init() {
    webdriver = new FirefoxDriver();
    webdriver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);

    webdriver.findElement(By.tagName("html")).sendKeys(Keys.chord(Keys.F12));
    OutbrainSeleniumLogging.apply((JavascriptExecutor) webdriver);
  }

  @Test
  public void checkYerIsCurrentYear() {

    webdriver.get("http://www.outbrain.com");
    logger.info("page was loaded");


    List<WebElement> allPs = webdriver.findElements(By.tagName("p"));
    logger.info("got {} <P> elements in the page", allPs.size());
    logger.debug("about to iterate all all <P> elements");

    int i = 0;
    boolean found = false;
    for (WebElement p : allPs) {
      logger.info("checking P element {}", i++);
      if (p.getText().contains("Copyright © " + THIS_YEAR + " Outbrain Inc.")) {
        logger.info("found the copyright");
        found = true;
        break;
      }
    }

    Assert.assertTrue("copyright was not found for Year " + THIS_YEAR, found);
    logger.warn("test passed");
  }
}
