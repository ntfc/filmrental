package me.ntfc.filmrental.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "rental_films")
public class RentedFilm {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    @ManyToOne
    @JoinColumn(name = "film_id", nullable = false, updatable = false)
    private Film film;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "rental_id", nullable = false, updatable = false)
    private Rental rental;

    @Column(name = "return_date")
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime returnDate;

    @Column(name = "days_rented_for", nullable = false)
    private Long daysRentedFor;

    @Column(name = "upfront_price", nullable = false)
    private Double upfrontPrice = 0.0;

    @Column(name = "surcharge_price")
    private Double surchargePrice = 0.0;


    public RentedFilm() {
    }

    public RentedFilm(Rental rental, Film film, Long daysRentedFor) {
        this.rental = rental;
        this.film = film;
        this.daysRentedFor = daysRentedFor;
    }

    public Long getId() {
        return id;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public Long getDaysRentedFor() {
        return daysRentedFor;
    }

    public void setDaysRentedFor(Long daysRentedFor) {
        this.daysRentedFor = daysRentedFor;
    }

    public Double getSurchargePrice() {
        return surchargePrice;
    }

    public void setSurchargePrice(Double surchargePrice) {
        this.surchargePrice = surchargePrice;
    }

    public Double getUpfrontPrice() {
        return upfrontPrice;
    }

    public void setUpfrontPrice(Double upfrontPrice) {
        this.upfrontPrice = upfrontPrice;
    }

    public Rental getRental() {
        return rental;
    }

    public void setRental(Rental rental) {
        this.rental = rental;
    }

    public Double calcUpfrontPrice() {
        return film.calcPrice(daysRentedFor);
    }

    public Double calcSurchargePrice() {
        long extraDays;
        if (returnDate == null) {
            extraDays = rental.getStartDate().until(LocalDateTime.now(), ChronoUnit.DAYS) - daysRentedFor;
        } else {
            extraDays = rental.getStartDate().until(returnDate, ChronoUnit.DAYS) - daysRentedFor;
        }
        return film.calcPrice(extraDays);
    }
}
