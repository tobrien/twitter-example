package com.discursive.twitter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.support.GenericApplicationContext;

import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

public class SimpleExample {

	final static Logger log = LoggerFactory.getLogger(SimpleExample.class);

	public static void main(String[] args) {
		new SimpleExample().run();
	}

	private GenericApplicationContext context;

	public void run() {

		configureSpring();

		
		TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
		TwitterStatusListener listener = (TwitterStatusListener) context.getBean("twitterStatusListener");
		twitterStream.addListener(listener);
        twitterStream.sample();

		log.info("Closing Spring Context...");
		context.close();

	}

	private void runComponent(String component) {
		log.info("Running Component: " + component);

		Runnable runnable = (Runnable) context.getBean(component);

		Thread t = new Thread(runnable);
		t.run();

		try {
			t.join();
		} catch (InterruptedException e) {
			log.error("Process Interrupted", e);
		}
	}

	public void configureSpring() {
		// Configure Spring Framework
		log.info("Configuring Spring Framework...");
		GenericApplicationContext ctx = new GenericApplicationContext();
		ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner( ctx );
		scanner.scan( "com.discursive.twitter" );
		ctx.refresh();
		context = ctx;
	}


}
