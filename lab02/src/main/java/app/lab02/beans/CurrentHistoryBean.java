package app.lab02.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named("currentHistory")
@RequestScoped
public class CurrentHistoryBean extends HistoryBean{
}
