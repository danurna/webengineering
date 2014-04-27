<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<jsp:useBean id="quiz" scope="session"
	class="at.ac.tuwien.big.we14.lab2.api.impl.SimpleQuiz" />
	<%@page import="at.ac.tuwien.big.we14.lab2.api.Player"%>
	<%@page import="at.ac.tuwien.big.we14.lab2.api.Quiz"%>
<%!

	public String getResultText(Player player){
    	if( player != null ){
    	    return player.getName() + " gewinnt!";
    	}
    	
    	return "Unentschieden!";
	}

%>
<%
	try{ // try/catch for null pointer exception if session is not set. Redirects to start.jsp
		Player winner;
	    winner = quiz.getQuizWinner();
%>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="de" lang="de">
    <head>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Business Informatics Group Quiz - Spielende</title>
        <link rel="stylesheet" type="text/css" href="style/screen.css" />
        <script src="js/jquery.js" type="text/javascript"></script>
        <script src="js/framework.js" type="text/javascript"></script>
    </head>
    <body id="winnerpage">
        <a class="accessibility" href="#roundwinner">Zur Spielwertung springen</a>
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
                <h2 id="roundwinnerheading" class="accessibility">Endstand</h2>
                <p class="roundwinnermessage"><%= getResultText(winner) %> </p>
            </section>
        
            <!-- round info -->    
            <section id="roundinfo" aria-labelledby="roundinfoheading">
                <h2 id="roundinfoheading" class="accessibility">Spielerinformationen</h2>
                <div id="player1info" class="playerinfo">
                    <span id="player1name" class="playername"><%= quiz.getPlayer(0).getName() %></span>
                    <p id="player1roundcounter" class="playerroundcounter">Gewonnene Runden: <span id="player1wonrounds" class="playerwonrounds"><%= quiz.getPlayer(0).getWonRounds() %></span></p>
                </div>
                <div id="player2info" class="playerinfo">
                    <span id="player2name" class="playername"><%= quiz.getPlayer(1).getName() %></span>
                    <p id="player2roundcounter" class="playerroundcounter">Gewonnene Runden: <span id="player2wonrounds" class="playerwonrounds"><%= quiz.getPlayer(1).getWonRounds() %></span></p>
                </div>
            	<form action="BigQuizServlet" method="POST">
                	<input type="hidden" name="action" value="start" />
                	<input type="submit" id="next" accesskey="n" value="Neues Spiel" />
            	</form>
            </section>
        </section>

        <!-- footer -->
        <footer role="contentinfo">© 2014 BIG Quiz</footer>
        <script type="text/javascript">
            //<![CDATA[
            
            $(document).ready(function() {
            	if(supportsLocalStorage()) {
            		localStorage.setItem("lastGame", new Date().getTime());
            	}
            });
            
            //]]>
        </script>
    </body>
</html>

<%  
	} catch(NullPointerException e){
	    response.sendRedirect("start.jsp"); 
	}
%>
