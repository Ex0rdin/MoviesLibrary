package ua.exordin.movies.model;

import java.sql.Date;

public class Request {

    private String requestMethod;
    private Date requestDate;
    private String sourceIp;

    public Request(String requestMethod, Date requestDate, String sourceIp) {
        this.requestMethod = requestMethod;
        this.requestDate = requestDate;
        this.sourceIp = sourceIp;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public String getSourceIp() {
        return sourceIp;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Request request = (Request) o;

        if (requestMethod != null ? !requestMethod.equals(request.requestMethod) : request.requestMethod != null)
            return false;
        if (requestDate != null ? !requestDate.equals(request.requestDate) : request.requestDate != null) return false;
        return sourceIp != null ? sourceIp.equals(request.sourceIp) : request.sourceIp == null;
    }

    @Override
    public int hashCode() {
        int result = requestMethod != null ? requestMethod.hashCode() : 0;
        result = 31 * result + (requestDate != null ? requestDate.hashCode() : 0);
        result = 31 * result + (sourceIp != null ? sourceIp.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestMethod='" + requestMethod + '\'' +
                ", requestDate=" + requestDate +
                ", sourceIp='" + sourceIp + '\'' +
                '}';
    }
}
