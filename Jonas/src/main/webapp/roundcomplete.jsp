<%@page import="at.ac.tuwien.big.we14.lab2.api.Choice"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %>
 <jsp:useBean id="game" scope="session" class="at.ac.tuwien.big.we14.lab2.model.Game" />
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="de" lang="de">
    <head>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Business Informatics Group Quiz - Zwischenstand</title>
        <link rel="stylesheet" type="text/css" href="style/screen.css" />
        <script src="js/jquery.js" type="text/javascript"></script>
        <script src="js/framework.js" type="text/javascript"></script>
    </head>
    <body id="winnerpage">
        <a class="accessibility" href="#roundwinner">Zur Rundenwertung springen</a>
        <header role="banner" aria-labelledby="mainheading"><h1 id="mainheading"><span class="accessibility">Business Informatics Group</span> Quiz</h1></header>
        <nav role="navigation" aria-labelledby="navheading">
            <h2 id="navheading" class="accessibility">Navigation</h2>
            <ul>
                <li><a id="logoutlink" title="Klicke hier um dich abzumelden" href="#" accesskey="l">Abmelden</a></li>
            </ul>
        </nav>
        
        <section role="main">
            <!-- winner message -->
            <section id="roundwinner" aria-labelledby="roundwinnerheading">
                <h2 id="roundwinnerheading" class="accessibility">Rundenzwischenstand</h2>
                <% if (game.getCurrentRound().getWinner()==0) { %>
                <p class="roundwinnermessage">Unentschieden in Runde <%=game.getCurrentRoundIndex()+1 %>!</p>
                <% } else if (game.getCurrentRound().getWinner()==1) { %>
				<p class="roundwinnermessage">Spieler 1 gewinnt Runde <%=game.getCurrentRoundIndex()+1 %>!</p>
                <% } else if (game.getCurrentRound().getWinner()==2) { %>
                <p class="roundwinnermessage">Spieler 2 gewinnt Runde <%=game.getCurrentRoundIndex()+1 %>!</p>				
				<% } %>
            </section>
        
            <!-- round info -->   
			
            <section id="roundinfo" aria-labelledby="roundinfoheading">
                <h2 id="roundinfoheading" class="accessibility">Spielerinformationen</h2>
                <% HashMap<Integer,Integer> player1Score = game.getCurrentRound().getScorePlayer1();%>
			<div id="player1info">
				<span id="player1name"><%=game.getPlayer1().getName()%></span>
				<ul class="playerroundsummary">
				<% for (int i = 0; i < 3; i++) { %>
				<% 		if (player1Score.containsKey(i) && player1Score.get(i).equals(1)) {%>
					<li><span class="accessibility">Frage 1:</span><span id="player1answer1" class="correct">Richtig</span></li>
				<%		} else if (player1Score.containsKey(i) && player1Score.get(i).equals(0)) {%>
					<li><span class="accessibility">Frage 2:</span><span id="player1answer2" class="incorrect">Falsch</span></li>
				<%		} else {%>
					<li><span class="accessibility">Frage 3:</span><span id="player1answer3" class="unknown">Unbekannt</span></li>
				<%		}		
				 } %>
				</ul>
			</div>
			<div id="player2info">
				<span id="player2name"><%=game.getPlayer2().getName()%></span>
				<ul class="playerroundsummary">
					<% HashMap<Integer,Integer> player2Score = game.getCurrentRound().getScorePlayer2();%>
					<% for (int i = 0; i < 3; i++) { %>
					<% 		if (player2Score.containsKey(i) && player2Score.get(i).equals(1)) {%>
					 <li><span class="accessibility">Frage 1:</span><span id="player2answer1" class="correct">Richtig</span></li>
					<%		} else if (player2Score.containsKey(i) && player2Score.get(i).equals(0)) {%>
					 <li><span class="accessibility">Frage 2:</span><span id="player2answer1" class="incorrect">Falsch</span></li>
					<%		} else {%>
					<li><span class="accessibility">Frage 3:</span><span id="player2answer3" class="unknown">Unbekannt</span></li>
					<%		}		
					 } %>
				</ul>
			</div> 
                <!-- <a id="next" href="question.html">Weiter</a> -->
                <form action="BigQuizServlet" method="get">
					<input type="hidden" name="action" value="nextRound" />
					<input type="submit" value="Weiter" id="next" />
				</form>
            </section>
        </section>

        <!-- footer -->
        <footer role="contentinfo">© 2014 BIG Quiz</footer>
    </body>
</html>
