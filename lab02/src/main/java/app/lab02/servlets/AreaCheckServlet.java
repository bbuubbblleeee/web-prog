package app.lab02.servlets;


import app.lab02.beans.CurrentHistoryBean;
import app.lab02.beans.RequestBean;
import app.lab02.beans.SessionHistoryBean;
import app.lab02.process.MathematicalCalculations;
import app.lab02.process.ValidationService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;

@WebServlet("/result")
public class AreaCheckServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(ValidationService.class);

    @Inject
    CurrentHistoryBean currentHistory;

    @Inject
    SessionHistoryBean sessionHistory;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response){
        try{
            checkAccess(request, response);
        }
        catch (Exception e){
            logger.error(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Logger logger = LoggerFactory.getLogger(AreaCheckServlet.class);

        checkAccess(request, response);

        String[] xValues = request.getParameterValues("x");
        String y = request.getParameter("y");
        String r = request.getParameter("r");

        try {
            ValidationService validationService = new ValidationService();
            for (String string : xValues) {
                Map<String, ? extends Number> map = validationService.validate(Map.of(
                        "x", string,
                        "y", y,
                        "r", r
                ));
                saveCurrentHistory(map, request);
            }
            request.setAttribute("currentHistory", currentHistory);
            saveSessionHistory(request);

            String source = request.getParameter("source");
            if (source != null && source.equals("graph")){
                sendJsonResponse(response, currentHistory.getAllRequests().get(0).getResult(),
                        currentHistory.getAllRequests().get(0).getCurrentTime(),
                        currentHistory.getAllRequests().get(0).getExecutionTime());
                return;
            }
            request.getRequestDispatcher("/result.jsp").forward(request, response);

        }
        catch (Exception e){
            logger.error(e.getMessage());
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }




    }

    private void saveCurrentHistory(Map<String, ? extends Number> map, HttpServletRequest request){
        RequestBean requestBean = new RequestBean();
        requestBean.setX((float) map.get("x"));
        requestBean.setY((float) map.get("y"));
        requestBean.setR((float) map.get("r"));

        boolean result = new MathematicalCalculations().ifHits(requestBean.getX(),
                requestBean.getY(), requestBean.getR());
        requestBean.setResult(result);

        ZonedDateTime moscowTime = ZonedDateTime.now(ZoneId.of("Europe/Moscow"));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss", Locale.forLanguageTag("ru"));
        String currentTime = dateTimeFormatter.format(moscowTime);

        requestBean.setCurrentTime(currentTime);

        long executionTime = System.nanoTime() - (Long) request.getAttribute("startTime");

        requestBean.setExecutionTime(executionTime);
        currentHistory.add(requestBean);
    }

    private void saveSessionHistory(HttpServletRequest request){
        for (RequestBean requestBean : currentHistory.getAllRequests()){
            sessionHistory.add(requestBean);
        }
        request.getSession().setAttribute("sessionHistory", sessionHistory);
    }



    private void checkAccess(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getAttribute("accessToAreaCheck") == null){
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    private void sendJsonResponse(HttpServletResponse response, boolean result, String currentTime, long executionTime) throws IOException {
        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();
        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("result", result);
        jsonObject.addProperty("currentTime", currentTime);
        jsonObject.addProperty("executionTime", executionTime + " ns");

        printWriter.println(gson.toJson(jsonObject));
    }
}


