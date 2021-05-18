package com.company;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        Coach theCoach = context.getBean("myCoach", Coach.class);

        System.out.println(theCoach.getDailyWorkout());

        System.out.println(theCoach.getDailyFortune());

        CricketCoach theCricketCoach = context.getBean("myCricketCoach", CricketCoach.class);

        System.out.println(theCricketCoach.getDailyWorkout());

        System.out.println(theCricketCoach.getDailyFortune());

        System.out.println(theCricketCoach.getEmailAddress());

        System.out.println(theCricketCoach.getTeam());

        context.close();
    }
}
