package com.chakrar.chartapp.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Configuration
public class MessageDispatcher implements MqttCallback {
	
	@Autowired
    private SimpMessagingTemplate template;
	
	/** The Constant logger. */
	private Logger logger = LoggerFactory.getLogger(MessageDispatcher.class);

	
	public MessageDispatcher() {
		//super();
		logger.debug("INSIDE AsdpMessageDispatcher Constr"+this.hashCode());
		System.out.println("INSIDE AsdpMessageDispatcher Constr"+this.hashCode());
	}

	@Override
	public void connectionLost(Throwable cause) {
		logger.error("OHHHHHHHHHHHHHHHHHHH     NOooooooooooooooooooooooooooooooooo Connection to MQTT is lost......."+cause.getMessage());
		logger.debug("=========     hashcode     ======== "+this.hashCode()+"");
		cause.printStackTrace();
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		logger.info("Delivery completed for client= " + token.getClient().getClientId());
		
	}

	@Override
	public void messageArrived(String topicName, MqttMessage message) throws Exception {
		System.out.println(" messageArrived called for AsdpMessageDispatcher "+message.getPayload());
		String m = new String(message.getPayload());
		
		System.out.println("The message payload is = "+ m + " and the topic is = "+ topicName);
		logger.debug(" messageArrived called for AsdpMessageDispatcher ");
		if (topicName.contains("windspeed")) {
			System.out.println(" Calling gaugechart function to redraw ");
			this.template.convertAndSend("/topic/airquality/windspeed", ""+Integer.parseInt(m) );
		} else if (topicName.contains("temperature")) {
			System.out.println(" Calling linechart function to redraw ");
			this.template.convertAndSend("/topic/airquality/temperature", ""+Integer.parseInt(m) );
		}
		
	}
	
	

}
