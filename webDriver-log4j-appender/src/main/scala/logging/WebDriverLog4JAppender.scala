package com.outbrain.selenium.logging

import org.apache.log4j.{Appender, Logger, AppenderSkeleton, Level}
import org.apache.log4j.spi.LoggingEvent
import org.openqa.selenium.{TimeoutException, JavascriptExecutor}
import java.util.Enumeration

object OutbrainSeleniumLogging {
  def apply(webDriver: JavascriptExecutor) = setWebDriver(webDriver)
  def quit() = setWebDriver(null)

  private def setWebDriver(webDriver: JavascriptExecutor) {
    val appenders: Enumeration[_] = Logger.getRootLogger.getAllAppenders
    while (appenders.hasMoreElements) {
      val appender: Appender = appenders.nextElement.asInstanceOf[Appender]
      if (appender.isInstanceOf[WebDriverLog4JAppender]) {
        (appender.asInstanceOf[WebDriverLog4JAppender]).setWebDriver(webDriver)
      }
    }
  }

}

class WebDriverLog4JAppender extends AppenderSkeleton {

  var webDriver: Option[JavascriptExecutor] = None

  def setWebDriver(webDriver: JavascriptExecutor) = {
    this.webDriver = Option(webDriver)
  }

  protected def append(event: LoggingEvent) {
    webDriver.foreach(driver => {
      try {
        driver.executeAsyncScript(generateLogScript(event))
      }
      catch {
        case ex: TimeoutException => {
          // failed to send loggingEvent to browser, nothing todo
        }
        case ex: Exception => {
          System.out.println("*** got exception while logging ***")
        }
      }
    }
    )
  }

  private def generateLogScript(event: LoggingEvent): String = {
    def consoleLevel(l: Level) = {
      l match {
        case Level.ALL | Level.TRACE | Level.DEBUG => "debug"
        case Level.INFO => "info"
        case Level.WARN => "warn"
        case Level.ERROR | Level.FATAL => "error"
        case _ => "log"
      }
    }

    val msg = event.getRenderedMessage.replace("'", "\\'")

    s"console.${consoleLevel(event.getLevel)}('$msg')"
  }

  def close(): Unit = {}

  def requiresLayout() = false

}

