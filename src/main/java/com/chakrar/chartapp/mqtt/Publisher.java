package com.chakrar.chartapp.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

public class Publisher {

	public static final String WINDSPEED_TOPIC = "airquality/windspeed";
    //private static final String WEATHER_TOPIC = "eclipsecon/weather";
    private static final String TEMPERATURE_TOPIC = "airquality/temperature";
    //private static final String GLAZE_WARNING_TOPIC = "eclipsecon/glaze";

    private final MqttClient client;
    private final RandomGenerator randomGenerator;

    public Publisher(MqttClient client) {
        this.client = client;
        this.randomGenerator = new RandomGenerator();
    }

    public void publishData() throws MqttException {
    	
    	final Integer windSpeed = publishWindSpeed();
    	
        final Integer temperature = publishTemperature();
        
        /*
        final String weather = publishWeather(temperature);
        final String glazeWarning = publishGlazeWarning(temperature);
        System.out.println("Weather:\t"+weather+" \t Temperature: \t"+temperature+"\t Glaze Warning: \t"+glazeWarning + "\t Wind Speed: \t" + windSpeed);
        */        
        System.out.println("Wind Speed: \t" + windSpeed + " \t Temperature: \t"+temperature);
    }

    private Integer publishWindSpeed() throws MqttException {
    	System.out.println("... Inside publishWindSpeed method ...");
        Integer temperature = randomGenerator.getRandomWindSpeed(1, 200);
        client.publish(WINDSPEED_TOPIC, temperature.toString().getBytes(), 2, false);
        return temperature;
	}

/*	private String publishGlazeWarning(int temp) throws MqttException {

        final String isStreetIcy = randomGenerator.isStreetIcy(temp);
        client.publish(GLAZE_WARNING_TOPIC, isStreetIcy.getBytes(), 2, false);
        return isStreetIcy;
    }

    private String publishWeather(int temp) throws MqttException {
        final String weather = randomGenerator.getWeather(temp);
        client.publish(WEATHER_TOPIC, weather.getBytes(), 2, false);
        return weather;
    }*/

    private Integer publishTemperature() throws MqttException {
    	System.out.println("... Inside publishTemperature method ...");
        Integer temperature = randomGenerator.getRandomTemp();
        client.publish(TEMPERATURE_TOPIC, temperature.toString().getBytes(), 2, false);
        return temperature;
    }

}
