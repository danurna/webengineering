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

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
        {
            if( request.getParameter("action").equals("start") ){
                response.setContentType("text/html;charset=UTF-8");
                PrintWriter out = response.getWriter();
                out.println("<html><body><h1>Start! Big Quiz Servlet here!</h1></body></html>");
            }
            
        }
    
}