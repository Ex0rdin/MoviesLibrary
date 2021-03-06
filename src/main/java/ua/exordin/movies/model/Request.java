package ua.exordin.movies.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "requests_log")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String ip;
    private Date date;
    private String requestType;
    private String result;
    private String additionalInfo;

    public Request(long id, String ip, Date date, String requestType, String result, String additionalInfo) {
        this.id = id;
        this.ip = ip;
        this.date = date;
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
        if (ip != null ? !ip.equals(request.ip) : request.ip != null) return false;
        if (date != null ? !date.equals(request.date) : request.date != null) return false;
        if (requestType != null ? !requestType.equals(request.requestType) : request.requestType != null) return false;
        if (result != null ? !result.equals(request.result) : request.result != null) return false;
        return additionalInfo != null ? additionalInfo.equals(request.additionalInfo) : request.additionalInfo == null;
    }

    @Override
    public int hashCode() {

        /*DISCLAIMER*/
        /*DO NOT INCLUDE ID IN HASHCODE CALCULATION WHEN USING WITH JPA*/

        int result1 = ip != null ? ip.hashCode() : 0;
        result1 = 31 * result1 + (date != null ? date.hashCode() : 0);
        result1 = 31 * result1 + (requestType != null ? requestType.hashCode() : 0);
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        result1 = 31 * result1 + (additionalInfo != null ? additionalInfo.hashCode() : 0);
        return result1;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", ip='" + ip + '\'' +
                ", date=" + date +
                ", requestType='" + requestType + '\'' +
                ", result='" + result + '\'' +
                ", additionalInfo='" + additionalInfo + '\'' +
                '}';
    }
}
