package com.discursive.twitter;

import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import twitter4j.Status;

import com.mchange.v2.c3p0.DataSources;

@Component
public class AkibanManager {

	final static Logger log = LoggerFactory.getLogger(AkibanManager.class);
	
	private DataSource ds;
	private JdbcTemplate jdbcTemplate;
	
	private String insert_status_sql = 
			"insert into twitter_status (tweet_id, handle, text, lat, lon) values (?, ?, ?, ?, ?)";
	
	@PostConstruct
	public void init() {
		log.info( "Initializing Akiban Manager" );
	
		DataSource ds_unpooled;
		try {
			ds = DataSources.unpooledDataSource("jdbc:postgresql://localhost:15432/twitter", 
			        "dummyuser", 
			        "nopassword");
		
			jdbcTemplate = new JdbcTemplate( ds );
		} catch (SQLException e) {
			log.error( "Error connecting to the Akiban Database, exiting",  e );
			System.exit(1);
		}	
	}
	
	@PreDestroy
	public void cleanup() {
		log.info( "Closing Akiban Manager" ); 
		
		try {
			DataSources.destroy( ds );
		} catch (SQLException e) {
			log.error( "Error destroying a datasource: " + e );
		}
	}
	
	public void storeTweet( Status status ) {
		jdbcTemplate.update( insert_status_sql, 
				new Object[] { status.getId(),
				status.getUser().getScreenName(),
				status.getText(),
				status.getGeoLocation().getLatitude(),
				status.getGeoLocation().getLongitude()				
		} );	
	}
	
}
