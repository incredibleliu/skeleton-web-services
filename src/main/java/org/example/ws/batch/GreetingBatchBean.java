package org.example.ws.batch;

import java.util.Collection;

import org.example.ws.model.Greeting;
import org.example.ws.service.GreetingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Profile("batch")
@Component
public class GreetingBatchBean {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private GreetingService greetingService;
	
//	@Scheduled(
//			cron = "0,10,20,30 * * * * *")
	public void cronJob(){
		logger.info("> cronJob");
		//System.out.println("> cronJob");
		
		// Add scheduled logic here
		Collection<Greeting> greetings = greetingService.findAll();
		logger.info("There are {} greetings in the data store.",
				greetings.size());
		//System.out.println("There are " + greetings.size() + " greetings in the data store.");
		
		logger.info("< cronJob");
		//System.out.println("< cronJob");
	}
	
	
	/**
	 * If a fixed rate execution is desired, simply change the property name specified within the annotation. 
	 * The following would be executed every 15 seconds measured between the successive start times of each invocation.
	 */
	@Scheduled(
			initialDelay = 5000,
			fixedRate = 15000)
	public void fixedRateJobWithInitialDelay(){
		logger.info("> fixedRateJobWithInitialDelay");
		
		// Add scheduled logic here
		// Simulate job processing time
		long pause = 5000;
		long start = System.currentTimeMillis();
		do {
			if(start + pause < System.currentTimeMillis()){
				break;
			}
		} while (true);
		
		logger.info("Processing time was {} seconds.", pause/1000);
		
		logger.info("< fixedRateJobInitialDelay");
	}
	
	
	/**
	 * For example, the following method would be invoked every 15 seconds with a fixed delay, 
	 * meaning that the period will be measured from the completion time of each preceding invocation.
	 */
//	@Scheduled(
//			initialDelay = 5000,
//			fixedDelay = 15000)
	public void fixedDelayJobWithInitialDelay(){
		logger.info("> fixedDelayJobWithInitialDelay");
		
		// Add scheduled logic here
		// Simulate job processing time
		long pause = 5000;
		long start = System.currentTimeMillis();
		do {
			if(start + pause < System.currentTimeMillis()){
				break;
			}
		} while (true);
		
		logger.info("Processing time was {} seconds.", pause/1000);
		
		logger.info("< fixedDelayJobInitialDelay");
	}
	
	
	
	

}
