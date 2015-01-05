selenium-tools
==============

Selenium Tools we use in Outbrain

Includes:

# WebDriver Log4J Appender
in Outbrain we record the video of each selenium test (we do delete the tests
that passed, and keep only the failed ones. The videos are published as
artifacts of the Jenkins build.

This is very helpful, but sometime it is not enough, we found ourselves   watching the video and trying to understand why it failed, or more precisely
what was the test tried to do just before it failed. In most cases the answers
are in the test's log, which bring us back to Jenkins. 
In order to make this process more
efficient we have built the WebDriver log4j Appener.

## How does it work?
in your code you simply write log messages before / after critical points as
you probably do anyway

```java


Logger log = LoggerFactory.getLogger(MyClass)
....

log.info("waiting for spinner to show");
log.info("waiting for spinner to hide");
log.info("waiting for button to be enabled");
log.info("clicking onm the button");
```

now image that all these message would appear in the browser console and will
be part of the video.

