<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<jsp:useBean id="quiz" scope="session"
	class="at.ac.tuwien.big.we14.lab2.api.impl.SimpleQuiz" />
<%@page import="at.ac.tuwien.big.we14.lab2.api.Quiz"%>
<%@page import="at.ac.tuwien.big.we14.lab2.api.Round"%>
<%@page import="at.ac.tuwien.big.we14.lab2.api.Question"%>
<%@page import="at.ac.tuwien.big.we14.lab2.api.Choice"%>
<%@page import="java.util.List"%>

<?xml version="1.0" encoding="UTF-8"?>
<%!

	public String getAnswerString(Boolean answer, String type){
		if( answer == null ) {
			return (type.equals("class")) ? "unknown" : "Unbekannt";
		}
		
		if( answer ) {
			return (type.equals("class")) ? "correct" : "Richtig";
		}else {
			return (type.equals("class")) ? "incorrect" : "Falsch";
		}
		
	}
%>
<%
	Round currentRound = quiz.getRounds().get( quiz.getCurrentRoundNumber() );
	Question currentQuestion = currentRound.getQuestions().get( currentRound.getCurrentQuestionNumber() );
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="de" lang="de">
    <head>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Business Informatics Group Quiz</title>
        <link rel="stylesheet" type="text/css" href="style/screen.css" />
        <script src="js/jquery.js" type="text/javascript"></script>
        <script src="js/framework.js" type="text/javascript"></script>
    </head>
    <body id="questionpage">
        <a class="accessibility" href="#question">Zur Frage springen</a>
        <header role="banner" aria-labelledby="mainheading"><h1 id="mainheading"><span class="accessibility">Business Informatics Group</span> Quiz</h1></header>
        <nav role="navigation" aria-labelledby="navheading">
            <h2 id="navheading" class="accessibility">Navigation</h2>
            <ul>
                <li><a id="logoutlink" title="Klicke hier um dich abzumelden" href="#" accesskey="l">Abmelden</a></li>
            </ul>
        </nav>
        
        <!-- round info -->
        <section role="main">
            <section id="roundinfo" aria-labelledby="roundinfoheading">
                <h2 id="roundinfoheading" class="accessibility">Spielerinformationen</h2>
                <% for (int i = 0; i < 2; i++) { %>
                
                <div id="player<%= i+1 %>info">
                    <span id="player<%= i+1 %>name"><%= quiz.getPlayer(i).getName() %></span>
                    <ul class="playerroundsummary">
                    <% for (int j = 0; j < 3; j++) { %>
                        <li><span class="accessibility">Frage <%= j+1 %>:</span><span id="player<%= i+1 %>answer<%= j+1 %>" class="<%=  getAnswerString(quiz.getPlayer(i).getRoundAnswer(j), "class") %>"><%=  getAnswerString(quiz.getPlayer(i).getRoundAnswer(j), "desc") %></span></li>
                  	<% } %>
                    </ul>
                </div>
                
                <% } %>
                
                <div id="currentcategory"><span class="accessibility">Kategorie:</span><%= currentRound.getCategory().getName() %></div>
            </section>
            
            <!-- Question -->
            <section id="question" aria-labelledby="questionheading">
                <form id="questionform" action="BigQuizServlet" method="POST">
                    <h2 id="questionheading" class="accessibility">Frage</h2>
                    <p id="questiontext"><%= currentQuestion.getText() %></p>
                    <ul id="answers">
                    <% List<Choice> choices = currentQuestion.getAllChoices();
                       for (Choice choice : choices ){ %>
                    	<li>
                    		<input id="option<%= choices.indexOf(choice)+1 %>" name="option<%= choices.indexOf(choice)+1 %>" type="checkbox"/><label for="option<%= choices.indexOf(choice)+1 %>"><%= choice.getText() %></label>
                    	    <input type="hidden" name="option<%= choices.indexOf(choice)+1 %>id"" value="<%= choice.getId() %>" />
                    	</li>
                    <% }  %>
                    </ul>
                    <input type="hidden" name="action" value="nextQuestion" />
                    <input id="timeleftvalue" name="timeleftvalue" type="hidden" value="100"/>
                    <input id="next" type="submit" value="weiter" accesskey="s"/>
                </form>
            </section>
            
            <section id="timer" aria-labelledby="timerheading">
                <h2 id="timerheading" class="accessibility">Timer</h2>
                <p><span id="timeleftlabel">Verbleibende Zeit:</span> <time id="timeleft">undefined</time></p>
                <meter id="timermeter" min="0" low="20" value="100" max="100"/>
            </section>

		<section id="lastgame">
			<p>
				Letztes Spiel: <span id="lastGameDate">Nie</span>
			</p>
		</section>
	</section>

	<!-- footer -->
	<footer role="contentinfo">© 2014 BIG Quiz</footer>

	<script type="text/javascript">
            //<![CDATA[
            
            // initialize time
            $(document).ready(function() {
                var maxtime = <%= currentQuestion.getMaxTime() %>;
                var hiddenInput = $("#timeleftvalue");
                var meter = $("#timer meter");
                var timeleft = $("#timeleft");
                var lastGameDate = $("#lastGameDate");
                
                hiddenInput.val(maxtime);
                meter.val(maxtime);
                meter.attr('max', maxtime);
                meter.attr('low', maxtime/100*20);
                timeleft.text(secToMMSS(maxtime));
                
                if(supportsLocalStorage()) {
                	var storedValue = localStorage.getItem("lastGame");
                	if(storedValue != null) {
	                	var lastGame = new Date(Number(storedValue));
	                	lastGameDate.text(lastGame.toLocaleDateString() + ", " +
	                			lastGame.toLocaleTimeString().substr(0,5));
                	}
                } else {
                	lastGameDate.text("Nie");
                }
            });
            
            // update time
            function timeStep() {
                var hiddenInput = $("#timeleftvalue");
                var meter = $("#timer meter");
                var timeleft = $("#timeleft");
                
                var value = $("#timeleftvalue").val();
                if(value > 0) {
                    value = value - 1;   
                }
                
                hiddenInput.val(value);
                meter.val(value);
                timeleft.text(secToMMSS(value));
                
                if(value <= 0) {
                    $('#questionform').submit();
                }
            }
            
            window.setInterval(timeStep, 1000);
            
            //]]>
        </script>
</body>
</html>
