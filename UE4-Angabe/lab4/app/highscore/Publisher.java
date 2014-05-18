package highscore;

import java.util.Date;

import models.QuizUser;
import play.Logger;
import twitter.TwitterClient;
import twitter.TwitterStatusMessage;

public class Publisher implements Runnable {

	QuizUser human;
	QuizUser computer;
	boolean humanIsWinner;

	public Publisher(QuizUser human, boolean humanIsWinner) {
		super();
		this.human = human;
		this.computer = new QuizUser();

		computer.setFirstName("Com");
		computer.setLastName("Puter");
		computer.setName("HaXxOr");
		computer.setUserName("HaXxOr01932");
		computer.setBirthDate(new Date(0));

		this.humanIsWinner = humanIsWinner;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Users users = new Users();
		users.getUser().add(new User(human, humanIsWinner));
		users.getUser().add(new User(computer, !humanIsWinner));

		String winnerName = humanIsWinner ? human.getUserName() : computer
				.getUserName();

		try {

			PublishHighScoreService service = new PublishHighScoreService();
			PublishHighScoreEndpoint endpoint = service
					.getPort(PublishHighScoreEndpoint.class);

			highscore.Quiz q = new highscore.Quiz();
			q.setUsers(users);

			HighScoreRequestType request = new HighScoreRequestType();
			request.setUserKey("rkf4394dwqp49x");
			request.setQuiz(q);

			String uuid = endpoint.publishHighScore(request);
			TwitterClient.share(new TwitterStatusMessage(winnerName, uuid,
					new Date()));

		} catch (Failure e) {
			Logger.debug("Something went wrong");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
