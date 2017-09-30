package ua.exordin.movies.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.exordin.movies.model.Request;
import ua.exordin.movies.repository.RequestRepository;
import ua.exordin.movies.util.UsingThisTo;
import javax.servlet.http.HttpServletRequest;
import java.sql.Date;

@Service
public class RequestsLogServiceImpl implements RequestsLogService {

    @Autowired
    RequestRepository requestRepository;

    @Override
    public void logFailure(HttpServletRequest hsr, Date date) {
        log(hsr, date, Statuses.FAILURE);
    }

    @Override
    public void logSuccess(HttpServletRequest hsr, Date date) {
        log(hsr, date, Statuses.SUCCESS);
    }

    @Override
    public void logReceived(HttpServletRequest hsr, Date date) {
        log(hsr, date, Statuses.RECEIVED);
    }

    private void log(HttpServletRequest hsr, Date date, Statuses status) {
        Request request = new Request();
        request.setIp(UsingThisTo.extractIpFromRequest(hsr));
        request.setDate(date);
        request.setRequestType(hsr.getMethod());
        request.setResult(status.toString());
        request.setAdditionalInfo(hsr.getRequestURI());

        requestRepository.save(request);
    }

    private enum Statuses {
        SUCCESS, FAILURE, RECEIVED
    }
}
