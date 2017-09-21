package ua.exordin.movies.model;

import java.util.Date;

public class Movie {

    private String name;
    private String description;
    private Date premierDate;
    private int durationInMinutes;
    private int budgetInDollars;
    private float rating;

    public Movie(String name, String description, Date premierDate, int durationInMinutes, int budgetInDollars, float rating) {
        this.name = name;
        this.description = description;
        this.premierDate = premierDate;
        this.durationInMinutes = durationInMinutes;
        this.budgetInDollars = budgetInDollars;
        this.rating = rating;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPremierDate(Date premierDate) {
        this.premierDate = premierDate;
    }

    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public void setBudgetInDollars(int budgetInDollars) {
        this.budgetInDollars = budgetInDollars;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getPremierDate() {
        return premierDate;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public int getBudgetInDollars() {
        return budgetInDollars;
    }

    public float getRating() {
        return rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie that = (Movie) o;

        if (durationInMinutes != that.durationInMinutes) return false;
        if (budgetInDollars != that.budgetInDollars) return false;
        if (Float.compare(that.rating, rating) != 0) return false;
        if (!name.equals(that.name)) return false;
        if (!description.equals(that.description)) return false;
        return premierDate.equals(that.premierDate);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + premierDate.hashCode();
        result = 31 * result + durationInMinutes;
        result = 31 * result + budgetInDollars;
        result = 31 * result + (rating != +0.0f ? Float.floatToIntBits(rating) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", premierDate=" + premierDate +
                ", durationInMinutes=" + durationInMinutes +
                ", budgetInDollars=" + budgetInDollars +
                ", rating=" + rating +
                '}';
    }
}
