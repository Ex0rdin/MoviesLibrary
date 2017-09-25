package ua.exordin.movies.model;

import java.sql.Date;

public class Request {

    private long id;
    private String sourceIp;
    private Date requestDate;
    private String requestType;
    private String result;
    private String additionalInfo;

    public Request(long id, String sourceIp, Date requestDate, String requestType, String result, String additionalInfo) {
        this.id = id;
        this.sourceIp = sourceIp;
        this.requestDate = requestDate;
        this.requestType = requestType;
        this.result = result;
        this.additionalInfo = additionalInfo;
    }

    public Request() {
        id = 0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSourceIp() {
        return sourceIp;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Request request = (Request) o;

        if (id != request.id) return false;
        if (sourceIp != null ? !sourceIp.equals(request.sourceIp) : request.sourceIp != null) return false;
        if (requestDate != null ? !requestDate.equals(request.requestDate) : request.requestDate != null) return false;
        if (requestType != null ? !requestType.equals(request.requestType) : request.requestType != null) return false;
        if (result != null ? !result.equals(request.result) : request.result != null) return false;
        return additionalInfo != null ? additionalInfo.equals(request.additionalInfo) : request.additionalInfo == null;
    }

    @Override
    public int hashCode() {
        int result1 = (int) (id ^ (id >>> 32));
        result1 = 31 * result1 + (sourceIp != null ? sourceIp.hashCode() : 0);
        result1 = 31 * result1 + (requestDate != null ? requestDate.hashCode() : 0);
        result1 = 31 * result1 + (requestType != null ? requestType.hashCode() : 0);
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        result1 = 31 * result1 + (additionalInfo != null ? additionalInfo.hashCode() : 0);
        return result1;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", sourceIp='" + sourceIp + '\'' +
                ", requestDate=" + requestDate +
                ", requestType='" + requestType + '\'' +
                ", result='" + result + '\'' +
                ", additionalInfo='" + additionalInfo + '\'' +
                '}';
    }
}
