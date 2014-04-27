<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<jsp:useBean id="quiz" scope="session"
	class="at.ac.tuwien.big.we14.lab2.api.impl.SimpleQuiz" />
<%@page import="at.ac.tuwien.big.we14.lab2.api.Quiz"%>
<%@page import="at.ac.tuwien.big.we14.lab2.api.Round"%>
<%@page import="at.ac.tuwien.big.we14.lab2.api.Question"%>
<%@page import="at.ac.tuwien.big.we14.lab2.api.Choice"%>
<%@page import="at.ac.tuwien.big.we14.lab2.api.Player"%>
<%@page import="java.util.List"%>

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

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="de" lang="de">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Business Informatics Group Quiz - Zwischenstand</title>
<link rel="stylesheet" type="text/css" href="style/screen.css" />
<script src="js/jquery.js" type="text/javascript"></script>
<script src="js/framework.js" type="text/javascript"></script>
</head>
<body id="winnerpage">
	<a class="accessibility" href="#roundwinner">Zur Rundenwertung
		springen</a>
	<header role="banner" aria-labelledby="mainheading">
		<h1 id="mainheading">
			<span class="accessibility">Business Informatics Group</span> Quiz
		</h1>
	</header>
	<nav role="navigation" aria-labelledby="navheading">
		<h2 id="navheading" class="accessibility">Navigation</h2>
		<ul>
			<li><a id="logoutlink" title="Klicke hier um dich abzumelden"
				href="#" accesskey="l">Abmelden</a></li>
		</ul>
	</nav>

	<section role="main">
		<!-- winner message -->
		<section id="roundwinner" aria-labelledby="roundwinnerheading">
			<h2 id="roundwinnerheading" class="accessibility">Rundenzwischenstand</h2>
			<p class="roundwinnermessage">
				<%
				Player winner = quiz.getRoundWinner();
				if(winner == null) {
					out.println("Unentschieden!");
				} else {
					out.println(winner.getName() + " gewinnt Runde " + 
						(quiz.getCurrentRoundNumber()+1) + "!");
				}
				%>
			</p>
		</section>

		<!-- round info -->
		<section id="roundinfo" aria-labelledby="roundinfoheading">
			<h2 id="roundinfoheading" class="accessibility">Spielerinformationen</h2>
			<% for (int i = 0; i < 2; i++) { %>
                
                <div id="player<%= i+1 %>info">
                    <span id="player<%= i+1 %>name"><%= quiz.getPlayer(i).getName() %></span>
                    <ul class="playerroundsummary">
                    <% for (int j = 0; j < 3; j++) { %>
                        <li><span class="accessibility">Frage <%= j+1 %>:</span>
                        <span id="player<%= i+1 %>answer<%= j+1 %>" class="<%=  getAnswerString(quiz.getPlayer(i).getRoundAnswer(j), "class") %>">
                        <%=  getAnswerString(quiz.getPlayer(i).getRoundAnswer(j), "desc") %></span></li>
                  	<% } %>
                    </ul>
                </div>
                
                <% } %>
			
			 <form action="BigQuizServlet" method="POST">
                <input type="hidden" name="action" value="nextRound" />
                <input type="submit" id="next" value="Weiter" name="next" />
            </form>
		</section>
	</section>

	<!-- footer -->
	<footer role="contentinfo">© 2014 BIG Quiz</footer>
</body>
</html>
