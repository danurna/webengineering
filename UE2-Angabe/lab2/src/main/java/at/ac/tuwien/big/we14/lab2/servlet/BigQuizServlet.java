package at.ac.tuwien.big.we14.lab2.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name ="BigQuiz", urlPatterns = { "/BigQuizServlet" })
public class BigQuizServlet extends HttpServlet{
    private static final long serialVersionUID = -2708561549069343716L;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException
        {
            resp.setContentType("text/html);charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.println("<html><body><h1>Hello "+ req.getParameter("userName") +"! Big Quiz Serlvet here!</h1></body></html>");
        }
    
}