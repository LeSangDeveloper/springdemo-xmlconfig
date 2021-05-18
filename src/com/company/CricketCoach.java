package com.company;

public class CricketCoach implements Coach {

    private FortuneService fortuneService;

    public CricketCoach() {

    }

    public void setFortuneService(FortuneService fortuneService) {
        this.fortuneService = fortuneService;
    }

    @Override
    public String getDailyWorkout() {
        return "Daily workout from Cricket Coach";
    }

    @Override
    public String getDailyFortune() {
        return fortuneService.getFortune();
    }
}
