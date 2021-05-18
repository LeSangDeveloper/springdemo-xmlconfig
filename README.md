# pure Spring 5 demo (config Bean by XML and without maven)
Demo a Spring 5 without maven and annotation

## Setup
Because this example don't have maven so we need add all jar file in /libs for running program correctly. 

## IoC (inversion of Control)
It simply have a something like Object Factory when application (client) need a method from specified object through interface which implemented by object.

### Setup class and interface
#### Interface
Interfcaes in IoC which define methods and it will be managed and injected by Object factory. Ex: Interface Coach.java:
```
package com.company;

public interface Coach {
    String getDailyWorkout();
}

```

#### Class
Classes in IoC which implement methods in interface and implementation will inject to client by Object Factory. In this example, we have 2 classes BaseballCoach and TrackCoach and both of them implement Coach interface:

```
package com.company;

public class BaseballCoach implements Coach {

	@Override
	public String getDailyWorkout() {
		return "Spend 30 minutes on batting practice";
	}
	
}
```
### XML configuration
In this example, we use .xml file to config bean for Object Fatory inject to client.

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
		xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		xmlns:context="http://www.springframework.org/schema/context"
		xsi:schemaLocation="http://www.springframework.org/schema/beans
		http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/context
		http://www.springframework.org/schema/context/spring-context.xsd">
		
	<bean id="myCoach"
				class="com.company.TrackCoach">
	</bean>

	<bean id="myLoggerConfig" class="com.company.MyLoggerConfig" init-method="initLogger">
		<property name="rootLoggerLevel" value="FINE" />
		<property name="printedLoggerLevel" value="FINE"/>
	</bean>

</beans>
```
Id in <bean> tag using indentify Bean managed by Object Factory and class property mark class will inject when Client request that bean's id.
Bean have name myLoggerConfig using set logging's configuration. See more: https://www.tutorialspoint.com/log4j/log4j_logging_levels.htm.
