package com.discursive.twitter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

@Component
public class TwitterStatusListener implements StatusListener {

	final static Logger log = LoggerFactory.getLogger(TwitterStatusListener.class);
		
	@Autowired
	private AkibanManager akibanManager;
	
	@Override
	public void onException(Exception e) {
		log.error( "onException", e);
	}

	@Override
	public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
		
	}

	@Override
	public void onScrubGeo(long userId, long upToStatusId) {

	}

	@Override
	public void onStatus(Status status) {
		if( status.getGeoLocation() != null ) {
			log.info( "Tweet contains geo information: " + status.getGeoLocation().getLatitude() + " " + status.getGeoLocation().getLongitude()  );
			akibanManager.storeTweet( status );
		}
	}

	@Override
	public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
		
	}

}
