package app.lab02.process;


import app.lab02.servlets.AreaCheckServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class ValidationService {
//    private List<Float> checkboxX = List.of(-2F, -1.5F, -1F, -0.5F, 0F, 0.5F, 1F, 1.5F, 2F);
    private final Logger logger = LoggerFactory.getLogger(ValidationService.class);

    public Map<String, ? extends Number> validate(Map<String, String> body) {
        return Map.of(
                "x", checkboxValidate("x", body.get("x")),
                "y", floatValidate("y", -10, 10, body.get("y")),
                "r", floatValidate("r", 2, 5, body.get("r")));
    }

    private float checkboxValidate(String x, String field){
        try{
            if (field == null || field.isBlank()){
                throw new IllegalArgumentException("Fields mustn't be empty");
            }
            //            Проверка на точное соответствие убрана, т.к. точки нормально не проверялись
//            if (!checkboxX.contains(value)){
//                throw new IllegalArgumentException("The parameter " + x + " must be one of the given options");
//            }
            return Float.parseFloat(field);
        }
        catch (NumberFormatException e){
            logger.error(e.getMessage());
            throw new IllegalArgumentException("Invalid type of argument " + x);
        }
    }

    private float floatValidate(String x, int min, int max, String field){
        try{
            if (field == null || field.isBlank()){
                throw new IllegalArgumentException("Fields mustn't be empty");
            }
            float value = Float.parseFloat(field);
            if (value < min || value > max){
                throw new IllegalArgumentException("The parameter " + x + " must be in [" + min + "; " + max + "]");
            }
            return value;
        }
        catch (NumberFormatException e){
            logger.error(e.getMessage());
            throw new IllegalArgumentException("Invalid type of argument " + x);
        }
    }
}
