package at.ac.tuwien.big.we14.lab2.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import at.ac.tuwien.big.we14.lab2.api.Category;
import at.ac.tuwien.big.we14.lab2.api.QuestionDataProvider;
import at.ac.tuwien.big.we14.lab2.api.QuizFactory;
import at.ac.tuwien.big.we14.lab2.api.impl.ServletQuizFactory;


@WebServlet(name ="BigQuiz", urlPatterns = { "/BigQuizServlet" })
public class BigQuizServlet extends HttpServlet{
    private static final long serialVersionUID = -2708561549069343716L;
    final static int maxRounds = 5;
    final static int maxQuestions = 3; 
    
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
        {
            if( request.getParameter("action").equals("start") ){
                response.setContentType("text/html;charset=UTF-8");
                PrintWriter out = response.getWriter();
                out.println("<html><body><h1>Start! Big Quiz Servlet here!</h1></body></html>");
            
                ServletContext servletContext = getServletContext();
                QuizFactory factory = ServletQuizFactory.init(servletContext); 
                QuestionDataProvider provider = factory.createQuestionDataProvider();
                List<Category> categories = provider.loadCategoryData();
                for( Iterator<Category> it = categories.iterator(); it.hasNext(); ){
                    System.out.println(it.next());
                }
                
            }
            
        }
    
}