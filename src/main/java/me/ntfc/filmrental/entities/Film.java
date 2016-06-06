package me.ntfc.filmrental.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.domain.Persistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "film")
public class Film implements Persistable<Long> {

    public enum Type {
        NEW,
        REGULAR,
        OLD
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "release_type", nullable = false)
    private Type type;

    public Film() {
    }

    public Film(String title, Type type) {
        this.title = title;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @JsonIgnore
    @Override
    public boolean isNew() {
        return id == null;
    }

    public Double calcPrice(long nDaysRented) {
        if (nDaysRented < 0) {
            return 0.0;
        }
        double price = 0.0;
        double BASIC_PRICE = Price.BASIC.getValue();
        double PREMIUM_PRICE = Price.PREMIUM.getValue();

        switch (type) {
           case NEW:
                price = PREMIUM_PRICE * nDaysRented;
                break;
            case REGULAR:
                price = BASIC_PRICE;
                if (nDaysRented > 3) {
                    price += BASIC_PRICE * (nDaysRented - 3);
                }
                break;
            case OLD:
                price = BASIC_PRICE;
                if (nDaysRented > 5) {
                    price += BASIC_PRICE * (nDaysRented - 5);
                }
                break;
        }

        return price;
    }
}
