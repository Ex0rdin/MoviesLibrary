package ua.exordin.movies.service;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;

public interface RequestsLogService {

    void logFailure(HttpServletRequest hsr, Date date);

    void logSuccess(HttpServletRequest hsr, Date date);

    void logReceived(HttpServletRequest hsr, Date date);

}
