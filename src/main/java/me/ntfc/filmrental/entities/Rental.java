package me.ntfc.filmrental.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.google.common.collect.Sets;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "rental")
public class Rental implements Persistable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id", nullable = false, updatable = false)
    private Customer customer;

    @OneToMany(mappedBy = "rental", cascade = CascadeType.ALL)
    private Set<RentedFilm> films = Sets.newHashSet();

    @Column(name = "start_date", nullable = false)
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime startDate;

    public Rental() {
    }

    @Override
    public Long getId() {
        return id;
    }

    public Set<RentedFilm> getFilms() {
        return films;
    }

    public void setFilms(Set<RentedFilm> films) {
        this.films = films;
    }

    public void addFilm(Film film, Long days) {
        RentedFilm rentedFilm = new RentedFilm(this, film, days);
        rentedFilm.setUpfrontPrice(rentedFilm.calcUpfrontPrice());
        this.films.add(rentedFilm);
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public Double getUpfrontPrice() {
        Double total = 0.0;
        for (RentedFilm rentedFilm : films) {
            total += rentedFilm.getUpfrontPrice();
        }
        return total;
    }

    public Double getSurchargePrice() {
        Double total = 0.0;
        for (RentedFilm rentedFilm : films) {
            total += rentedFilm.getSurchargePrice();
        }
        return total;
    }

    @JsonIgnore
    @Override
    public boolean isNew() {
        return id == null;
    }

    public boolean hasUnreturnedFilmes() {
        for (RentedFilm rentedFilm : films) {
            if (rentedFilm.getReturnDate() == null) {
                return true;
            }
        }
        return false;
    }

    public Double calcUpFrontPrice() {
        Double total = 0.0;
        for (RentedFilm rentedFilm : films) {
            total += rentedFilm.calcUpfrontPrice();
        }
        return total;
    }

    public Double calcSurchargePrice() {
        Double total = 0.0;
        for (RentedFilm rentedFilm : films) {
            total += rentedFilm.calcSurchargePrice();
        }
        return total;
    }
}
