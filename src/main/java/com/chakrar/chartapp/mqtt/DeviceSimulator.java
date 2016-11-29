package com.chakrar.chartapp.mqtt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeviceSimulator implements CommandLineRunner {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(DeviceSimulator.class);

    
    public void start() {
    	System.out.println("Starting Simulation");
    	//new PahoClient().start();
    }

	@Override
	public void run(String... arg0) throws Exception {
		logger.info("   ... Running Simulation ...   ");
    	new PahoClient().start();
		
	}
}
