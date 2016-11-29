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

import java.util.Date;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * The Class MQTTHandler.
 */
@Configuration
public class MQTTHandler {

	/** The logger. */
	private Logger logger = LoggerFactory.getLogger(MQTTHandler.class);

	/** The Mqtt client. */
	private MqttClient mqttClient;
	
	private MqttAsyncClient maCl;
	
	public static String MQTT_URL = "tcp://192.168.0.4:1883";

	public MqttAsyncClient getMaCl() {
		return maCl;
	}

	public void setMaCl(MqttAsyncClient maCl) {
		this.maCl = maCl;
	}

	/** The its vin. */
	private String vin;

	/**
	 * Gets the vin.
	 *
	 * @return the vin
	 */
	public String getVin() {
		return vin;
	}

	/**
	 * Sets the vin.
	 *
	 * @param aVin
	 *            the new vin
	 */
	public void setVin(String aVin) {
		this.vin = aVin;
	}

	/**
	 * Instantiates a new mQTT handler.
	 */
	public MQTTHandler() {
		logger.info("Default Constructor of MQTTHandler called");
	}



	/**
	 * Subscribe.
	 *
	 * @param client the client
	 * @throws MqttException the mqtt exception
	 */
	public boolean subscribe(MqttAsyncClient client) throws MqttException {
		boolean subscriptionSuccess = true;
		try {
			String requestTopic = "airquality/#";//configManager.getStringParam(HwaConfigurationConstants.MQTT_PUBLISHER_TOPIC)+RemoteServicesConstant.MQTT_TOPIC_APPENDER;
			client.subscribe(requestTopic, 2);
			logger.info("================== subscription is done to topic...==========================  "+requestTopic);

		} catch (MqttException e) {
			subscriptionSuccess = false;
			throw e;
		}
		return subscriptionSuccess;
	}

	/**
	 * publishToTopic.
	 *
	 * @param aClient the a client
	 * @param randRequestId the rand request id
	 * @param aVin the a vin
	 * @param aServiceId the a service id
	 * @param diagnosticId the diagnostic id
	 * @param diagnosticPassword the diagnostic password
	 * @param userId the user id
	 * @return the i mqtt delivery token
	 * @throws RemoteServicesException the remote services exception
	 */
	public IMqttDeliveryToken publishToTopic(MqttAsyncClient aClient, String randRequestId, String aVin, int aServiceId, 
			String diagnosticId, String diagnosticPassword,String userId) throws Exception {
		IMqttDeliveryToken token = null;
		try {
			String topicName = null;//configManager.getStringParam(HwaConfigurationConstants.MQTT_PUBLISHER_TOPIC) + aVin;

			if (logger.isInfoEnabled()) {
				logger.info("TopicName :::: " + topicName);
			}
			MqttMessage message = new MqttMessage();
			//message.setPayload(createPayLoad(randRequestId, aVin, aServiceId, diagnosticId, diagnosticPassword,userId));
			message.setQos(2);
			//message.setQos(2);
			logger.info("message payload \" with QoS = " + message.getQos());
			token = aClient.publish(topicName, message);
			//token.waitForCompletion(configManager.getLongParam(HwaConfigurationConstants.MQTT_SLEEP_TIMEOUT));
		} catch (MqttException e) {
			logger.warn("MqttException: " + e.getMessage());

		}
		logger.info("Message published Successfully to Mqtt");
		return token;

	}



	/**
	 * Disconnect.
	 *
	 * @param subClient the sub client
	 * @throws MqttException the mqtt exception
	 */
	public void disconnect() throws MqttException {
		if (mqttClient != null) {
			if (mqttClient.isConnected()) {
				logger.debug("Disconnecting from the MQTT broker");
				try {
					mqttClient.unsubscribe("remote/#");
					mqttClient.disconnect(5000);
					mqttClient.close();
				} catch (MqttException e) {
					logger.error("Error: Unbale to disconnect ");
					throw e;
				}
			} else {
				logger.debug("MQTT Client is Not connected");
			}
		}
	}


	/**
	 * Disconnect client.
	 *
	 * @param aClient the a client
	 * @throws MqttException the mqtt exception
	 */
	public void disconnectClient(MqttAsyncClient aClient) throws MqttException {
		aClient.unsubscribe("remote/#");
		aClient.disconnect();
		logger.info("Client is disconnected from MQTT broker...");
	}

	/**
	 * Already connected.
	 *
	 * @param aClient the a client
	 * @return true, if successful
	 */
	public boolean alreadyConnected(MqttAsyncClient aClient) {
		return aClient.isConnected();
	}

	/**
	 * Generate client id.
	 *
	 * @param serviceIdentifier the service identifier
	 * @return the string
	 */
	private String generateClientId(String serviceIdentifier) {
		String mqttClientId = null;
		// generate a unique client ID - I'm basing this on a combination of
		// ServiceId and the current timestamp
		String timestamp = "" + (new Date()).getTime();
		mqttClientId = serviceIdentifier + timestamp ;
		// truncate - MQTT spec doesn't allow client ids longer than 23 chars
		logger.debug("mqttClientId ::: "+mqttClientId);
		if (mqttClientId.length() > 23) {
			mqttClientId = mqttClientId.substring(0, 23);
		}
		return mqttClientId;
	}

	/**
	 * Gets the tcp address.
	 *
	 * @return the tcp address
	 */
	public String getTcpAddress() {
		return MQTT_URL;//configManager.getStringParam(HwaConfigurationConstants.MQTT_TCP_HOST) + AdspServicesConstant.AUTH_SEPARATOR + configManager.getStringParam(HwaConfigurationConstants.MQTT_TCP_PORT);
	}


	/**
	 * Gets the async client.
	 *
	 * @param aClientId the a client id
	 * @return the async client
	 */
	public MqttAsyncClient getAsyncClient(String aClientId) {
		MqttAsyncClient asyncClient = null;
		try {
			String clientId = generateClientId(aClientId);
			logger.info("Client Id generated is === "+clientId);
			asyncClient = new MqttAsyncClient(getTcpAddress(), clientId, new MemoryPersistence());
		} catch (MqttException e) {
			logger.error("Exception ::: "+e.getMessage());
		}
		return asyncClient;
	}

	/**
	 * Connect to broker.
	 *
	 * @param client the client
	 * @return the i mqtt token
	 * @throws MqttException the mqtt exception
	 */
	public IMqttToken connectToBroker(MqttAsyncClient client) throws MqttException {
		MqttConnectOptions connOptions = new MqttConnectOptions();
		connOptions.setCleanSession(true);
		connOptions.setConnectionTimeout(5000);
		IMqttToken conToken = null;
		logger.info("client is connected ::: " + client.isConnected());
		if (!alreadyConnected(client)) {
			conToken = client.connect(connOptions);
		}
		int retry = 0;
		int maxRetry = 5;
		while (conToken != null && !conToken.isComplete() && retry < maxRetry) {
			try {
				Thread.sleep(1000);
				retry++;
			} catch (InterruptedException e) {
				logger.error("Thread InterruptedException");
			}
		}
		if ((conToken!= null) && (!conToken.isComplete())) {
			logger.error("MQTT  server is down. Please try after some time");
		}
		return conToken;
	}

	/** This method does the following
	 *  1. Creates MqttClient if not already present
	 *  2. Sets Callback
	 *  3. Connects to mosquitto broker
	 *  4. Subscribes to a topic
	 * 
	 * @param tcu
	 * @return
	 * @throws MqttException 
	 */
	public boolean connect(MessageDispatcher dispatcher) {
		boolean connSuccess = true;
		
		// get mqtt client		
		try {
			//mqttClient = getMqttClientObj();
			final String TCU_MQTT_SUBSCRIBER = "Sensor_Subscriber";//configManager.getStringParam(HwaConfigurationConstants.TCU_SUB_CLIENT_ID);
			maCl = getAsyncClient(TCU_MQTT_SUBSCRIBER);
		} catch (Exception e1) {
			//e1.printStackTrace();
			logger.error("Error getting MqttClient"+e1.getMessage());
			connSuccess = false;
		}
		
		IMqttToken tok = null;
		try {
			tok = connectToBroker(maCl);
		} catch (MqttException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// set callback
		maCl.setCallback(dispatcher);
		
		// do subscription
		if (tok.isComplete()) {
			logger.info("Client is connected...subscribing to topic ");
			try {
				subscribe(maCl);
			} catch (MqttException e) {
				logger.error("================= Error in Subscribe operation ========================"+e.getMessage());
				e.printStackTrace();
				connSuccess = false;
			}	
		} else {
			logger.info("Client is not connected...cannot subscribe ");
		}

		return connSuccess;
	}
	
	
}