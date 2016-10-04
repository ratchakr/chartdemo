package com.chakrar.chartapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ChartdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChartdemoApplication.class, args);
	}
	
/*	@Bean
	public CommandLineRunner subscribe(final ScheduleTask st) {
		return new CommandLineRunner() {
			
			@Override
			public void run(String... arg0) throws Exception {
				
				st.execute();
			}
		};
	}*/	
}
