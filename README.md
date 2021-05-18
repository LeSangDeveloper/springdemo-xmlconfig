# Pure Spring 5 demo (config Bean by XML and without maven)
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

## Dependency Injection bean by constructor in XML

### Create Interface
create interface make helper for Object Factory inject through method declare in interface and implement in class which implement interface.
Example: we created interface named "FortuneService"
```
package com.company;

public interface FortuneService {
    String getFortune();
}
```

### Create class implement interface
We create a class implement interface created, class maybe called helper.
Example:
```
package com.company;

public class FortuneServiceImpl implements FortuneService {
    public String getFortune() {
        return "It's a happy day";
    }
}
```

### In XML configurable file

#### By constructor
We declare bean for interface named FortuneService we created and inject to param for constructor class which we inject FortuneService to.
Example:
```
<bean id="fortuneService" class="com.company.FortuneServiceImpl">
</bean>

<bean id="myCoach"
				class="com.company.TrackCoach">
	<constructor-arg ref="fortuneService">
	</constructor-arg>
</bean>
```

#### By getter method
Just like by constructor, but we won't inject Fortune through constructor, we will inject through setter method like
```
package com.company;

public class CricketCoach implements Coach {

    private FortuneService fortuneService;

    public CricketCoach() {

    }

    public void setFortuneService(FortuneService fortuneService) {
        this.fortuneService = fortuneService;
    }
...
```
and in XML file

```
...
	<bean id="myCricketCoach" class="com.company.CricketCoach">
		<property name="fortuneService" ref="fortuneService"></property>
	</bean>
...
```

## Injection Literals value from XML file
### Create field and setter, getter of that field
```
...
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    private String emailAddress;
    private String team;
...
```

### configure in XML file
```
...
	<bean id="myCricketCoach" class="com.company.CricketCoach">
		<property name="fortuneService" ref="fortuneService"></property>
		<property name="emailAddress" value="test@gmail.com"></property>
		<property name="team" value="Arsenal"></property>
	</bean>
...
```

## Inject value from a Properties file
### Create a properties file
In this example, we created properties file like sport.properties
```
foo.email=test@gmail.com
foo.team=arsenal
```

### Declare it in XML Config file
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
		xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		xmlns:context="http://www.springframework.org/schema/context"
		xsi:schemaLocation="http://www.springframework.org/schema/beans
		http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/context
		http://www.springframework.org/schema/context/spring-context.xsd">

	<context:property-placeholder location="classpath:sport.properties" />
...
```

### Using value of properties file
```
...
	<bean id="myCricketCoach" class="com.company.CricketCoach">
		<property name="fortuneService" ref="fortuneService"></property>
		<property name="emailAddress" value="${foo.email}"></property>
		<property name="team" value="${foo.team}"></property>
	</bean>
...
```

##BeanScope and lifecycle
### Singleton
Default Scope of bean which live all period application run.

### Prototype 
Create once when we use context.getBean(). Configure by:

```
...
    <bean id="myCoach"
          class="com.company.TrackCoach"
          scope="prototype">
        <constructor-arg ref="fortuneService">
        </constructor-arg>
    </bean>
...
```

## Add custom method in bean life cycle
### Create method in class of bean
The method can be named any name, return any type but should be void, and cannot have param.
```
...
	public void testInit() {
		System.out.println("Bean just created");
	}

	public void testDestroy() {
		System.out.println("Bean destroyed");
	}
...
```

### Add method to life cycle of bean
We add those methods in XMl config file following:
```
...
    <bean id="myCoach"
          class="com.company.TrackCoach"
          init-method="testInit"
          destroy-method="testDestroy">
        <constructor-arg ref="fortuneService">
        </constructor-arg>
    </bean>
...
```

### With Scope Prototype
Default, Spring don't destroy of bean scoped prototype, so we must create customBeanProcessor like:
```
package com.company;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class MyCustomBeanProcessor implements BeanPostProcessor, BeanFactoryAware, DisposableBean {

	private BeanFactory beanFactory;

	private final List<Object> prototypeBeans = new LinkedList<>();

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

		// after start up, keep track of the prototype scoped beans. 
		// we will need to know who they are for later destruction
		
		if (beanFactory.isPrototype(beanName)) {
			synchronized (prototypeBeans) {
				prototypeBeans.add(bean);
			}
		}

		return bean;
	}


	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}


	@Override
	public void destroy() throws Exception {

		// loop through the prototype beans and call the destroy() method on each one
		
        synchronized (prototypeBeans) {

        	for (Object bean : prototypeBeans) {

        		if (bean instanceof DisposableBean) {
                    DisposableBean disposable = (DisposableBean)bean;
                    try {
                        disposable.destroy();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        	prototypeBeans.clear();
        }
        
	}
}
```

then we add it to configure XML file.
```
...

    <bean id="myCoach"
          class="com.company.TrackCoach"
          scope="prototype"
          init-method="testInit"
          destroy-method="destroy">
        <constructor-arg ref="fortuneService">
        </constructor-arg>
    </bean>

    <!--  Bean custom processor to handle calling destroy methods on prototype scoped beans  -->
    <bean id="customProcessor" class="com.company.MyCustomBeanProcessor"> </bean>
...
```