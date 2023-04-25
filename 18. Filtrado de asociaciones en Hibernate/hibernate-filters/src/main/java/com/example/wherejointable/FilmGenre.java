package com.example.wherejointable;

import jakarta.persistence.*;

@Entity(name = "film_genres")
public class FilmGenre {

    @Id
    @Column(name = "film_id", insertable = false, updatable = false)
    private Long filmId;

    @Id
    @Column(name = "genre_id", insertable = false, updatable = false)
    private Long genreId;

    private Integer minAge;

    private String classification;

    public FilmGenre() {
    }

    public FilmGenre(Long filmId, Long genreId, Integer minAge, String classification) {
        this.filmId = filmId;
        this.genreId = genreId;
        this.minAge = minAge;
        this.classification = classification;
    }

    public Long getFilmId() {
        return filmId;
    }

    public void setFilmId(Long filmId) {
        this.filmId = filmId;
    }

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    @Override
    public String toString() {
        return "FilmGenre{" +
                "filmId=" + filmId +
                ", genreId=" + genreId +
                ", minAge=" + minAge +
                ", classification='" + classification + '\'' +
                '}';
    }
}
