package app.lab03.process;

import app.lab03.data.Point;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.FacesException;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.ValidatorException;
import jakarta.faces.view.facelets.FaceletException;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Getter
@Named
@ApplicationScoped
public class ValidationService implements Serializable {
    private final List<Float> x = List.of(-2F,-1.5F, -1F, -0.5F, 0F, 0.5F, 1F, 1.5F);
    private final List<Integer> r = List.of(1, 2, 3, 4, 5);

    public Float validate(String x) {
        try{
            if (x == null){
                throw new NumberFormatException();
            }
            return Float.parseFloat(x);
        }
        catch (NumberFormatException e){
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "X is invalid", "X coordinate must be a number");
            throw new FacesException(message.toString());
        }
    }

//    public void validateR(FacesContext context, UIComponent component, Object value) throws ValidatorException {
//        FacesMessage msg;
//        System.out.println("--- МЕТОД VALIDATE R ВЫЗВАН ---"); // <-- ДОБАВИТЬ ЭТУ СТРОКУ
//
//        // 1. Проверка на null/невыбранное значение
//        if (value == null) {
//            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
//                    "Ошибка выбора",
//                    "R должно быть выбрано.");
//            throw new ValidatorException(msg);
//        }
//
//        // 2. Преобразование значения
//        Integer rValue;
//        try {
//            rValue = (Integer) value;
//        } catch (ClassCastException e) {
//            // Ловим на случай, если пришло что-то неожиданное, но это маловероятно
//            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
//                    "Ошибка типа",
//                    "Неверный формат данных для R.");
//            throw new ValidatorException(msg);
//        }
//
//        // 3. Логика проверки диапазона
//        // В вашем сообщении указан [1; 5], но в коде [4; 5].
//        // Используем [1; 5], так как это более типичный диапазон для R.
//        if (rValue < 4 || rValue > 5) {
//            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
//                    "Ошибка диапазона",
//                    "Параметр R должен быть в [4; 5].");
//            throw new ValidatorException(msg);
//        }
//    }

}
