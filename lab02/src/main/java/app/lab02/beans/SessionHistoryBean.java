package app.lab02.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;


@Named("sessionHistory")
@SessionScoped
public class SessionHistoryBean extends HistoryBean{
}
