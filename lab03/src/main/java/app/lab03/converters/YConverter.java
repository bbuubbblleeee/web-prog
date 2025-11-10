package app.lab03.converters;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.ConverterException;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;

@Slf4j
public class YConverter implements Converter<Float> {
    @Override
    public Float getAsObject(FacesContext context, UIComponent component, String value) {
        log.info("YConverter called");
        try{
            if (value == null){

                throw new NumberFormatException();
            }
            return Float.parseFloat(value.replace(',', '.'));
        }
        catch (NumberFormatException e){
            log.info("Error from YConverter");
            log.error(e.getMessage());
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Y coordinate must be a number.", "Y coordinate must be float.");
            throw new ConverterException(message);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Float value) {
        return value.toString();
    }
}