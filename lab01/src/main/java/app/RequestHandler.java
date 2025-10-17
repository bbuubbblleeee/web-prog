package app;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;

public class RequestHandler {
    BufferedReader bufferedReader;
    PrintStream printStream;
    float x;
    int y;
    float r;
    boolean result;

    public RequestHandler() {
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        printStream = System.out;

    }

    private String getRequest(){
        String contentLength = System.getProperty("CONTENT_LENGTH");
        try {
            if (contentLength == null || contentLength.isEmpty()){
                throw new IllegalArgumentException("Not enough parameters");
            }
            int length = Integer.parseInt(contentLength);
            char[] params = new char[length];
            int quantity = bufferedReader.read(params, 0, length);
            return new String(params, 0, quantity);
        }
        catch (NumberFormatException | IOException e){
            throw new IllegalArgumentException("Not enough parameters");
        }
    }

    public void handle() {
        long startTime = System.nanoTime();
        try {
            String paramsStr = getRequest();
            ParseValidationService parseValidationService = new ParseValidationService();
            Map<String, String> body = parseValidationService.parse(paramsStr);
            parseValidationService.validate(body);
            x = parseValidationService.x;
            y = parseValidationService.y;
            r = parseValidationService.r;
            result = ifHits(x, y, r);
            sendResponse(x, y, r, result, startTime);
        }
        catch (Exception e){
            sendError(e.getMessage());
        }
    }

    private boolean ifHits(float x, int y, float r){
        if (x >= 0 && y >= 0){
            return 2 * x + y <= r;
        }
        else if (x < 0 && y >= 0) {
            return x >= -r / 2 && y <= r;
        }
        else if (x < 0){
            return x * x + y * y <= r * r / 4;
        }
        return false;
    }


    private void sendResponse(float x, int y, float r, boolean result, long startTime) {
        ZonedDateTime moscowTime = ZonedDateTime.now(ZoneId.of("Europe/Moscow"));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss", Locale.forLanguageTag("ru"));
        String currentTime = dateTimeFormatter.format(moscowTime);
        long execTime = System.nanoTime() - startTime;
        System.out.println("Content-Type: application/json\n");

        String response = jsonResponse(result, currentTime, execTime);
//        String response = String.format(Locale.US, "{\"x\": %f, \"y\": %d, \"r\": %f, \"result\": %b, \"currentTime\": \"%s\",\"execTime\": \"%s\"}", x, y, r, result, currentTime, execTime + " ns");
        System.out.println(response);
        System.out.flush();
    }

    public void sendError(String error) {
        String response = jsonError(error);
//        String response = String.format("{\"errorMessage\": \"%s\"}", error);
        System.out.println("Status: 400 Bad Request");
        System.out.println("Content-Type: application/json\n");
        System.out.println(response);
        System.out.flush();
    }

    public String jsonResponse(boolean result, String currentTime, long execTime){
        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("x", x);
        jsonObject.addProperty("y", y);
        jsonObject.addProperty("r", r);
        jsonObject.addProperty("result", result);
        jsonObject.addProperty("currentTime", currentTime);
        jsonObject.addProperty("execTime", execTime + " ns");
        return gson.toJson(jsonObject);
    }

    public String jsonError(String error){
        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("errorMessage", error);
        return gson.toJson(jsonObject);
    }
}
