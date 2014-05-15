package twitter;

import java.util.Date;

import play.Logger;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterClient implements ITwitterClient{

	public static void share(){
		TwitterClient client = new TwitterClient();
		
		try {
			client.publishUuid(new TwitterStatusMessage("me", "oanszwoa", new Date()));
		} catch (IllegalArgumentException e){
			Logger.info("Provide proper values for status message.");
		} catch (Exception e) {
			Logger.info("Tweeting went wrong :/");
		}
	}
	
	@Override
	public void publishUuid(TwitterStatusMessage message) throws Exception {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey("GZ6tiy1XyB9W0P4xEJudQ")
		  .setOAuthConsumerSecret("gaJDlW0vf7en46JwHAOkZsTHvtAiZ3QUd2mD1x26J9w")
		  .setOAuthAccessToken("1366513208-MutXEbBMAVOwrbFmZtj1r4Ih2vcoHGHE2207002")
		  .setOAuthAccessTokenSecret("RMPWOePlus3xtURWRVnv1TgrjTyK7Zk33evp4KKyA");
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
	    Status status = twitter.updateStatus(message.getTwitterPublicationString());
	    Logger.info("Successfully updated the status to [" + status.getText() + "].");
	}

}
