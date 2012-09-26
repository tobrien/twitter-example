twitter-example
=========

This project is a simple Twitter example project to be used by people interested in exploring the Geospatial capabilities of Akiban.   This example uses the Twitter Streaming API to parse and store Tweets in an Akiban database.  You can use this example to understand how you can integrate a Java application with Akiban and to experiment with geospatial queries.
 
Prerequisites?
---------

1. Go to Akiban.com and [download Akiban Developer Edition](http://www.akiban.com/products/akiban-developer/latest)
2. Install Akiban (there are several automated installers, it takes a few minutes)
3. Connect to Akiban with psql (standard postgresql client) - `psql -h localhost -p 15432 twitter`
4. Execute the following DDL statements:

	create table twitter_status
	(
	  tweet_id int not null,
      handle varchar(64),
      text varchar(150),
      lat decimal(11,7),
      lon decimal(11,7),
      primary key(tweet_id)
    );
    
    create index idx_geo on twitter_status(z_order_lat_lon(lat,lon));

5. Replace the twitter4j configuration with your own Twitter OAuth configuration
    * Go to [dev.twitter.com](https://dev.twitter.com/)
    * Login and create a new Application under [My Applications](https://dev.twitter.com/apps)
    * Copy your Consumer Key and Consumer Secret to `src/main/resources/twitter4j.properties`
    * Create an Access token and copy your Access Token and Access Token Secret to `src/main/resources/twitter4j.properties`
    
6. Run the application (For now, run this in Eclipse by running SimpleExample)
    

What does this example do?
======

This example is a very simple example that aims to demonstrate how to:

* Listen to the Twitter Streaming API (this example just listens to the public sample)
* Store all tweets with a GeoLocation in an Akiban Database

This example uses an annotation-based approach to Spring configuration and makes use of the JdbcTemplate to simplify interaction with the database.   For the purposes of this sample application we're simply using an Unpooled C3P0 datasource to connect to Akiban with the latest postgresql JDBC Type 4 driver.