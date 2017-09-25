package ua.exordin.movies.model;

public class Rate {

    private long id;
    private String sourceIp;
    private String browserFootprint;
    private int mark;
    private int movieId;

    public Rate(long id, String sourceIp, String browserFootprint, int mark, int movieId) {
        this.id = id;
        this.sourceIp = sourceIp;
        this.browserFootprint = browserFootprint;
        this.mark = mark;
        this.movieId = movieId;
    }

    public Rate() {
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

    public String getBrowserFootprint() {
        return browserFootprint;
    }

    public void setBrowserFootprint(String browserFootprint) {
        this.browserFootprint = browserFootprint;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
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
        return browserFootprint != null ? browserFootprint.equals(rate.browserFootprint) : rate.browserFootprint == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (sourceIp != null ? sourceIp.hashCode() : 0);
        result = 31 * result + (browserFootprint != null ? browserFootprint.hashCode() : 0);
        result = 31 * result + mark;
        result = 31 * result + movieId;
        return result;
    }

    @Override
    public String toString() {
        return "Rate{" +
                "id=" + id +
                ", sourceIp='" + sourceIp + '\'' +
                ", browserFootprint='" + browserFootprint + '\'' +
                ", mark=" + mark +
                ", movieId=" + movieId +
                '}';
    }
}
