package ua.exordin.movies.model;

import javax.persistence.*;

@Entity
@Table(name = "Ratings")
public class Rate {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String sourceIp;
    private String browserFingerprint;
    private int mark;
    private long movieId;

    public Rate(long id, String sourceIp, String browserFingerprint, int mark, long movieId) {
        this.id = id;
        this.sourceIp = sourceIp;
        this.browserFingerprint = browserFingerprint;
        this.mark = mark;
        this.movieId = movieId;
    }

    public Rate() {}

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

    public String getBrowserFingerprint() {
        return browserFingerprint;
    }

    public void setBrowserFingerprint(String browserFingerprint) {
        this.browserFingerprint = browserFingerprint;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rate rate = (Rate) o;

        if (id != rate.id) return false;
        if (mark != rate.mark) return false;
        if (movieId != rate.movieId) return false;
        if (sourceIp != null ? !sourceIp.equals(rate.sourceIp) : rate.sourceIp != null) return false;
        return browserFingerprint != null ? browserFingerprint.equals(rate.browserFingerprint) : rate.browserFingerprint == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (sourceIp != null ? sourceIp.hashCode() : 0);
        result = 31 * result + (browserFingerprint != null ? browserFingerprint.hashCode() : 0);
        result = 31 * result + mark;
        result = 31 * result + (int) (movieId ^ (movieId >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Rate{" +
                "id=" + id +
                ", sourceIp='" + sourceIp + '\'' +
                ", browserFingerprint='" + browserFingerprint + '\'' +
                ", mark=" + mark +
                ", movieId=" + movieId +
                '}';
    }
}
