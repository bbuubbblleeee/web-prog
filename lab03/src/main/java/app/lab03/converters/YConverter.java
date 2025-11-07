package app.lab03.converters;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FacesConverter("yConverter")
public class YConverter implements Converter<Float> {
    private Logger logger = LoggerFactory.getLogger(YConverter.class);
    @Override
    public Float getAsObject(FacesContext context, UIComponent component, String value) {
        logger.info("YConverter called");
        try{
            return Float.parseFloat(value.replace(',', '.'));
        }
        catch (NumberFormatException e){
            logger.info("Error from YConverter");
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Y coordinate must be a number.", "Y coordinate must be float.");
            throw new ConverterException(message);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Float value) {
        return value.toString();
    }
}