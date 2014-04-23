package at.ac.tuwien.big.we14.lab2.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface BigQuizServletAction {
	
	void run(BigQuizServlet servlet, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
	
}
