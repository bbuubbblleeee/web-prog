package app.lab02.servlets;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


@WebServlet("")
public class ControllerServlet extends HttpServlet {
    private Logger logger = LoggerFactory.getLogger(AreaCheckServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("get is called");
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("post is called");
        long startTime = System.nanoTime();
        request.setAttribute("startTime", startTime);

        String[] xValues = request.getParameterValues("x");
        String x = request.getParameter("x");
        String y = request.getParameter("y");
        String r = request.getParameter("r");
        if (xValues == null || y == null || y.isBlank() || r == null || r.isBlank()){
            logger.info("something is null + x = " + x + " y =" + y);
            request.setAttribute("error_message", "Fields mustn't be empty");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }
        request.setAttribute("accessToAreaCheck", true);
        request.getRequestDispatcher("/result").forward(request, response);
    }

}
