package com.chakrar.chartapp.mqtt;

import java.util.Random;

public class RandomGenerator {


/*    private final int Min = -20;
    private final int Max = 60;
    private boolean trend = true;
    private Integer lastValue;
    
    private final int Wind_Speed_Min = 5;*/


    /**
     * Random temperature generator
     *
     * @return a increasing/decreasing series of numbers from -20 and 60 and backwards
     */
    public int getRandomTemp() {

/*        int variation = 0;
        if (lastValue == null) {
            lastValue = 0;
        } else {
            if (trend) {
                variation = getNumberBetween(0, 3);
            } else {
                variation = getNumberBetween(-3, 0);
            }

        }
            lastValue = lastValue + variation;
        if(lastValue>Max) {
            lastValue = Max;
            trend = false;
        }
        else if(lastValue<Min) {
            lastValue = Min;
            trend = true;
        }

        return lastValue;*/
    	
    	return getRandomNumberInRange(0, 20);

    }

/*    *//**
     * Random Weather Forecast
     *
     * @return string which describes the weather situation
     *//*
    public String getWeather(int temp) {
        String weather = null;
        if (temp < 0) {
            weather = "SNOW";
        }
        else if ((temp >= 0) && (temp <= 35)) {
            final int number = getNumberBetween(1, 4);
            switch (number) {
                case 1:
                    weather = "CLOUD";
                    break;
                case 2:
                    weather = "RAIN";
                    break;
                case 3:
                    weather = "SUN";
                    break;
                case 4:
                    weather = "STORM";
                    break;
            }
        } else if ((temp > 35) && (temp <= 50)){
            final int number = getNumberBetween(1, 2);
            switch (number) {
                case 1:
                    weather = "SUN";
                    break;
                case 2:
                    weather = "STORM";
                    break;
            }
        }
        else {
            weather = "SUN";
        }

        return weather;

    }


    public String isStreetIcy(int temp)
    {
        String glazeWarning;
        if (temp > 4) {
            glazeWarning = "false";

        } else {
            glazeWarning = "true";
        }
        return glazeWarning;
    }*/

    private int getNumberBetween(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

	public Integer getRandomWindSpeed(int min, int max) {
/*	       int variation = 0;
	        if (lastValue == null) {
	            lastValue = 0;
	        } else {
	            if (trend) {
	                variation = getNumberBetween(0, 3);
	            } else {
	                variation = getNumberBetween(-3, 0);
	            }

	        }
	            lastValue = lastValue + variation;
	        if(lastValue>Max) {
	            lastValue = Max;
	            trend = false;
	        }
	        else if(lastValue< Wind_Speed_Min) {
	            lastValue = Wind_Speed_Min;
	            trend = true;
	        }

	        return lastValue;*/
		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;		
	}

	private int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

}
