package models;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


public class QuizMapper {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/*
	private UserModel user;

	private QuizGame quiz;

	public Long getId() {
		return id;
	}

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	public QuizGame getQuiz() {
		return quiz;
	}

	public void setQuiz(QuizGame quiz) {
		this.quiz = quiz;
	}

	public static QuizGame findGameForUser(UserModel user) {
		
		QuizMapper mapper = JPA.em().find(QuizMapper.class, user.getId());
		
		return mapper.getQuiz();
	}
	*/

}