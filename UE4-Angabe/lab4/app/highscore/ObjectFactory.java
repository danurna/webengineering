
package highscore;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the highscore package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _HighScoreRequest_QNAME = new QName("http://big.tuwien.ac.at/we/highscore/data", "HighScoreRequest");
    private final static QName _Quiz_QNAME = new QName("", "quiz");
    private final static QName _HighScoreResponse_QNAME = new QName("http://big.tuwien.ac.at/we/highscore/data", "HighScoreResponse");
    private final static QName _UserKey_QNAME = new QName("http://big.tuwien.ac.at/we/highscore/data", "UserKey");
    private final static QName _Failure_QNAME = new QName("http://big.tuwien.ac.at/we/highscore", "Failure");
    private final static QName _OverviewB_QNAME = new QName("", "b");
    private final static QName _OverviewEm_QNAME = new QName("", "em");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: highscore
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link FailureType }
     * 
     */
    public FailureType createFailureType() {
        return new FailureType();
    }

    /**
     * Create an instance of {@link HighScoreRequestType }
     * 
     */
    public HighScoreRequestType createHighScoreRequestType() {
        return new HighScoreRequestType();
    }

    /**
     * Create an instance of {@link Quiz }
     * 
     */
    public Quiz createQuiz() {
        return new Quiz();
    }

    /**
     * Create an instance of {@link Gamer }
     * 
     */
    public Gamer createGamer() {
        return new Gamer();
    }

    /**
     * Create an instance of {@link Games }
     * 
     */
    public Games createGames() {
        return new Games();
    }

    /**
     * Create an instance of {@link Users }
     * 
     */
    public Users createUsers() {
        return new Users();
    }

    /**
     * Create an instance of {@link Player }
     * 
     */
    public Player createPlayer() {
        return new Player();
    }

    /**
     * Create an instance of {@link Choice }
     * 
     */
    public Choice createChoice() {
        return new Choice();
    }

    /**
     * Create an instance of {@link Answer }
     * 
     */
    public Answer createAnswer() {
        return new Answer();
    }

    /**
     * Create an instance of {@link Answers }
     * 
     */
    public Answers createAnswers() {
        return new Answers();
    }

    /**
     * Create an instance of {@link Questions }
     * 
     */
    public Questions createQuestions() {
        return new Questions();
    }

    /**
     * Create an instance of {@link Round }
     * 
     */
    public Round createRound() {
        return new Round();
    }

    /**
     * Create an instance of {@link Game }
     * 
     */
    public Game createGame() {
        return new Game();
    }

    /**
     * Create an instance of {@link Category }
     * 
     */
    public Category createCategory() {
        return new Category();
    }

    /**
     * Create an instance of {@link Overview }
     * 
     */
    public Overview createOverview() {
        return new Overview();
    }

    /**
     * Create an instance of {@link Categories }
     * 
     */
    public Categories createCategories() {
        return new Categories();
    }

    /**
     * Create an instance of {@link Question }
     * 
     */
    public Question createQuestion() {
        return new Question();
    }

    /**
     * Create an instance of {@link Group }
     * 
     */
    public Group createGroup() {
        return new Group();
    }

    /**
     * Create an instance of {@link User }
     * 
     */
    public User createUser() {
        return new User();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HighScoreRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://big.tuwien.ac.at/we/highscore/data", name = "HighScoreRequest")
    public JAXBElement<HighScoreRequestType> createHighScoreRequest(HighScoreRequestType value) {
        return new JAXBElement<HighScoreRequestType>(_HighScoreRequest_QNAME, HighScoreRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Quiz }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "quiz")
    public JAXBElement<Quiz> createQuiz(Quiz value) {
        return new JAXBElement<Quiz>(_Quiz_QNAME, Quiz.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://big.tuwien.ac.at/we/highscore/data", name = "HighScoreResponse")
    public JAXBElement<String> createHighScoreResponse(String value) {
        return new JAXBElement<String>(_HighScoreResponse_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://big.tuwien.ac.at/we/highscore/data", name = "UserKey")
    public JAXBElement<String> createUserKey(String value) {
        return new JAXBElement<String>(_UserKey_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FailureType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://big.tuwien.ac.at/we/highscore", name = "Failure")
    public JAXBElement<FailureType> createFailure(FailureType value) {
        return new JAXBElement<FailureType>(_Failure_QNAME, FailureType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Overview }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "b", scope = Overview.class)
    public JAXBElement<Overview> createOverviewB(Overview value) {
        return new JAXBElement<Overview>(_OverviewB_QNAME, Overview.class, Overview.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Overview }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "em", scope = Overview.class)
    public JAXBElement<Overview> createOverviewEm(Overview value) {
        return new JAXBElement<Overview>(_OverviewEm_QNAME, Overview.class, Overview.class, value);
    }

}
