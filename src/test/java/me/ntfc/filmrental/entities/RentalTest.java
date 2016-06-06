package me.ntfc.filmrental.entities;

import com.google.common.collect.Lists;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;


public class RentalTest {

    @Test
    public void testUpfrontPrices() throws Exception {
        Rental rental = new Rental();
        List<RentedFilm> rentedFilms = Lists.newArrayList(
            new RentedFilm(rental, new Film("Matrix 11", Film.Type.NEW), 1L),
            new RentedFilm(rental, new Film("Spider Man", Film.Type.REGULAR), 5L),
            new RentedFilm(rental, new Film("Spider Man 2", Film.Type.REGULAR), 2L),
            new RentedFilm(rental, new Film("Out of Africe", Film.Type.OLD), 7L)
        );
        Assertions.assertThat(rentedFilms.get(0).getFilm().calcPrice(1)).isEqualTo(40.0);
        Assertions.assertThat(rentedFilms.get(1).getFilm().calcPrice(5)).isEqualTo(90.0);
        Assertions.assertThat(rentedFilms.get(2).getFilm().calcPrice(2)).isEqualTo(30.0);
        Assertions.assertThat(rentedFilms.get(3).getFilm().calcPrice(7)).isEqualTo(90.0);
    }

    @Test
    public void testSurchargePrices() throws Exception {
        Rental rental = new Rental();
        List<RentedFilm> rentedFilms = Lists.newArrayList(
            new RentedFilm(rental, new Film("Matrix 11", Film.Type.NEW), 1L),
            new RentedFilm(rental, new Film("Spider Man", Film.Type.REGULAR), 5L),
            new RentedFilm(rental, new Film("Spider Man 2", Film.Type.REGULAR), 2L),
            new RentedFilm(rental, new Film("Out of Africe", Film.Type.OLD), 7L)
        );
        Assertions.assertThat(rentedFilms.get(0).getFilm().calcPrice(2)).isEqualTo(80.0);
        Assertions.assertThat(rentedFilms.get(1).getFilm().calcPrice(1)).isEqualTo(30.0);
    }
}
