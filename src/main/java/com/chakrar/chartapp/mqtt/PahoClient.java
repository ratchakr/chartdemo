package com.chakrar.chartapp.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class PahoClient {

    //Publish interval in seconds
    private final int DEFAULT_INTERVAL = 9;
    private final String DEVICE_STATUS_TOPIC = "eclipsecon/status";
    private final String DEVICE_INTERVAL_TOPIC = "eclipsecon/interval";
    
    private int interval = DEFAULT_INTERVAL;

    public void start() {
    	System.out.println(" ... Starting ... ");
        try {
            MqttClient client = new MqttClient(MQTTHandler.MQTT_URL, "simulator", new MemoryPersistence());

            final MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
            mqttConnectOptions.setWill(DEVICE_STATUS_TOPIC, "offline".getBytes(), 2, true);

            client.connect(mqttConnectOptions);
            //client.setCallback(new SubscribeCallback(this, DEVICE_INTERVAL_TOPIC));

            if (client.isConnected()) {

                client.publish(DEVICE_STATUS_TOPIC, "online".getBytes(), 2, true);
                client.subscribe(DEVICE_INTERVAL_TOPIC, 2);

                publishDataPeriodically(client);
            } else {
                System.out.println("Could not connect!");
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @param client
     */
    private void publishDataPeriodically(MqttClient client) {

        Publisher publisher = new Publisher(client);

        while (true) {
            try {
                publisher.publishData();
                Thread.sleep(interval * 1000);

            } catch (MqttException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void changeInterval(int newInterval) {
        interval = newInterval;
    }
}