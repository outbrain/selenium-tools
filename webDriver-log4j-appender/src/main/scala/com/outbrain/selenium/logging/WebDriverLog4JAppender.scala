package com.outbrain.selenium.logging

import org.apache.log4j.{AppenderSkeleton, Level}
import org.apache.log4j.spi.LoggingEvent
import org.openqa.selenium.{TimeoutException, JavascriptExecutor}
import OutbrainSeleniumLogging._

object OutbrainSeleniumLogging {
  var webDriver: Option[JavascriptExecutor] = None

  def apply(webDriver: JavascriptExecutor) = setWebDriver(webDriver)

  def quit() = setWebDriver(null)

  private def setWebDriver(webDriver: JavascriptExecutor) {
    OutbrainSeleniumLogging.webDriver = Option(webDriver);
  }
}

class WebDriverLog4JAppender extends AppenderSkeleton {

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

