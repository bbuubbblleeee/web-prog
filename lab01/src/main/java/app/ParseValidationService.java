package app;


import java.util.HashMap;
import java.util.Map;

public class ParseValidationService {
    float x;
    int y;
    float r;
    public Map<String, String> parse(String params){
        Map<String, String> body = new HashMap<>();
        for (var i : params.split("&")){
            String[] obj = i.split("=");
            if (obj.length == 2) {
                body.put(obj[0], obj[1]);
            }
        }
        return body;
    }

    public void validate(Map<String, String> body) {
        Map<String, Number> result = new HashMap<>();
        if (!body.containsKey("x") || !body.containsKey("y") || !body.containsKey("r")){
            throw new IllegalArgumentException("Fields mustn't be empty");
        }
        x = floatValidate("x", -5, 5, body);
        y = intValidate("y", -5, 3, body);
        r = floatValidate("r", 2, 5, body);
    }

    private float floatValidate(String x, int min, int max, Map<String, String> body){
        try{
            if (x.isBlank()){
                throw new IllegalArgumentException("Fields mustn't be empty");
            }
            float value = Float.parseFloat(body.get(x));
            if (value < min || value > max){
                throw new IllegalArgumentException("The parameter " + x + " must be in [" + min + "; " + max + "]");
            }
            return value;
        }
        catch (NumberFormatException e){
            throw new IllegalArgumentException("Invalid type of argument " + x);
        }
    }

    private int intValidate(String x, int min, int max, Map<String, String> body){
        try{
            if (x.isBlank()){
                throw new IllegalArgumentException("Fields mustn't be empty");
            }
            int value = Integer.parseInt(body.get("y"));
            if (value < min || value > max){
                throw new IllegalArgumentException("The parameter " + x + " must be integer in [" + min + "; " + max + "]");
            }
            return value;
        }
        catch (NumberFormatException e){
            throw new IllegalArgumentException("Invalid type of argument y");
        }
    }
}
