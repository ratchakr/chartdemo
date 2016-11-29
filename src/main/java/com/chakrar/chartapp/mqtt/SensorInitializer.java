/*
 * COPYRIGHT Ericsson 2013
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 */
package com.chakrar.chartapp.mqtt;

import javax.annotation.PreDestroy;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * The Class TcuInitializer.
 */
@Component
@Order(1)
public class SensorInitializer implements CommandLineRunner {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(SensorInitializer.class);

	/** The mqtt handler. */
	@Autowired
	private MQTTHandler mqttHandler;
	
/*	*//** The TcuDispatcher *//*
	@Autowired
	private TcuDispatcher tcuDispatcher;
*/	

	@Autowired
	private MessageDispatcher disp;
	
	/** Initializes the subscriber and connects to mosquitto broker 
	 * 
	 */
	public void initialize() {
		logger.info("Starting SensorInitializer...initialize called ::: "+disp.hashCode());
		//AsdpMessageDispatcher disp = new AsdpMessageDispatcher ();
		if(mqttHandler.connect(disp)) {
			logger.info("Subscription has been successfully done !!! ");
		}

		logger.info("initialize method executed Successfully");
	}
	
	
	/**
	 * Clean up.
	 */
	@PreDestroy
	public void cleanUp() {
		logger.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		logger.debug("Cleaning up on bean destroy...need to close the MQTT Connection");
		// close MQTT subscription
		
		try {
			mqttHandler.disconnect();
		} catch (MqttException e) {
			logger.error("Error in cleanUp method:  " + e.getMessage(), e);
		}
		
		logger.debug("Disconnected Successfully from MQTT Broker...CleanUp Done");
	}


	@Override
	public void run(String... arg0) throws Exception {
		logger.info("Inside run method of SensorInitializer..."+disp.hashCode());
		//AsdpMessageDispatcher disp = new AsdpMessageDispatcher ();
		if(mqttHandler.connect(disp)) {
			logger.info("Subscription has been successfully done !!! ");
		}

		logger.info("initialize method executed Successfully");
		
	}
}
