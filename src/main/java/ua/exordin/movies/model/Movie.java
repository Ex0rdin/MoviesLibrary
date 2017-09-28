package ua.exordin.movies.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "Movies")
public class Movie {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String name;
    private String description;
    private Date premierDate;
    private int durationInMinutes;
    private int budgetInDollars;
    private float rating;

    public Movie(long id, String name, String description, Date premierDate, int durationInMinutes, int budgetInDollars, float rating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.premierDate = premierDate;
        this.durationInMinutes = durationInMinutes;
        this.budgetInDollars = budgetInDollars;
        this.rating = rating;
    }

    public Movie() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPremierDate() {
        return premierDate;
    }

    public void setPremierDate(Date premierDate) {
        this.premierDate = premierDate;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public int getBudgetInDollars() {
        return budgetInDollars;
    }

    public void setBudgetInDollars(int budgetInDollars) {
        this.budgetInDollars = budgetInDollars;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        if (id != movie.id) return false;
        if (durationInMinutes != movie.durationInMinutes) return false;
        if (budgetInDollars != movie.budgetInDollars) return false;
        if (Float.compare(movie.rating, rating) != 0) return false;
        if (name != null ? !name.equals(movie.name) : movie.name != null) return false;
        if (description != null ? !description.equals(movie.description) : movie.description != null) return false;
        return premierDate != null ? premierDate.equals(movie.premierDate) : movie.premierDate == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (premierDate != null ? premierDate.hashCode() : 0);
        result = 31 * result + durationInMinutes;
        result = 31 * result + budgetInDollars;
        result = 31 * result + (rating != +0.0f ? Float.floatToIntBits(rating) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", premierDate=" + premierDate +
                ", durationInMinutes=" + durationInMinutes +
                ", budgetInDollars=" + budgetInDollars +
                ", rating=" + rating +
                '}';
    }
}
