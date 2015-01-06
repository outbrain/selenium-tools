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

## How to use it
in your code you simply write log messages before / after critical points as
you probably do anyway


```java


Logger log = LoggerFactory.getLogger(MyTest)
....


log.info("waiting for spinner to show");
...
log.info("waiting for spinner to hide");
...
log.info("waiting for button to be enabled");
...
log.info("clicking on the button");
...

```

## Demo - How it works
[![See how it works](http://img.youtube.com/vi/G1yvX64Rke0/0.jpg)](http://www.youtube.com/watch?v=G1yvX64Rke0)

now image that all these message would appear in the browser console and will
be part of the video.

## Install

### maven
#### add bintray to your maven's repositories list just put it in your pom.xml

```xml
  <repositories>
    <repository>
      <id>central</id>
      <name>bintray</name>
      <url>http://dl.bintray.com/yonatanm/maven</url>
    </repository>
  </repositories>
```
#### add the webDriver Log4J appender artifact as your dependency
```xml
	<dependencies>
		...
		<dependency>
			<groupId>com.outbrain.selenium-tools</groupId>
			<artifactId>webDriver-log4j-appender</artifactId>
			<version>1.0</version>
		</dependency>
		...
	</dependencies>
```

