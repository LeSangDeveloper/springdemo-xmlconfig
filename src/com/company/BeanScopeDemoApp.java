package com.company;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanScopeDemoApp {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beanScope-applicationContext.xml");

        TrackCoach theCoach = context.getBean("myCoach", TrackCoach.class);

        context.close();
    }
}
