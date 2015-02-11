package twitter;

import java.util.Date;

import play.Logger;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterClient implements ITwitterClient{

	public static void share(TwitterStatusMessage message){
		if( message == null ){
			return;
		}
		
		try {
			new TwitterClient().publishUuid(message);
		} catch (Exception e) {
			Logger.info("Tweeting went wrong :/");
		}
	}
	
	@Override
	public void publishUuid(TwitterStatusMessage message) throws Exception {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey("")
		  .setOAuthConsumerSecret("")
		  .setOAuthAccessToken("")
		  .setOAuthAccessTokenSecret("");
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
	    Status status = twitter.updateStatus(message.getTwitterPublicationString());
	    Logger.info("Successfully updated the status to [" + status.getText() + "].");
	}

}
