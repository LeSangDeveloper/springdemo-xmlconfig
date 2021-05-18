package com.company;

import org.springframework.beans.factory.DisposableBean;

public class TrackCoach implements Coach, DisposableBean {

	private FortuneService fortuneService;

	public TrackCoach(FortuneService fortuneService) {
		this.fortuneService = fortuneService;
	}

	@Override
	public String getDailyWorkout() {
		return "Practice 5k hard";
	}

	@Override
	public String getDailyFortune() {
		return fortuneService.getFortune();
	}

	public void testInit() {
		System.out.println("Bean just created");
	}

	public void testDestroy() {
		System.out.println("Bean destroyed");
	}

	@Override
	public void destroy() throws Exception {
		System.out.println("test Destroy");
	}
}
