package com.chakrar.chartapp;

import java.text.SimpleDateFormat;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleTask {
	@Autowired
    private SimpMessagingTemplate template;
	
	private static final Logger log = LoggerFactory.getLogger(ScheduleTask.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    // this will send a message to an endpoint on which a client can subscribe
    @Scheduled(fixedRate = 5000)
    public void execute() {
        // sends the message to /topic/message
    	//log.info(" RATNO Triggering WS message "+this.template.hashCode());
    	Integer value = getRandomNumberInRange(0, 200);
    	//log.info(" *** value *** = "+ value);
        //this.template.convertAndSend("/topic/message", "Date: " + dateFormat.format(new Date()));
        this.template.convertAndSend("/topic/message", ""+value );
        //this.template.convertAndSend("/topic/message", new Random().nextInt(100));    	
    }

	private Integer generate() {
		Random r = new Random();
		//Integer oldValue = series.getData()[0].intValue();
        Integer newValue = (int) ((r.nextDouble() - 0.5) * 20.0);
        if (newValue > 200) {
            newValue = 200;
        } else if (newValue < 0) {
            newValue = 0;
        }
		return newValue;
	}
	
	private int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
}
