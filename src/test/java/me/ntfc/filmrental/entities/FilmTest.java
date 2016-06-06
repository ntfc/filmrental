package me.ntfc.filmrental.entities;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class FilmTest {
    @Test
    public void testNewFilmPrice() throws Exception {
        Film newFilm = new Film("New Film", Film.Type.NEW);
        Assertions.assertThat(newFilm.calcPrice(1)).isEqualTo(40.0);
        Assertions.assertThat(newFilm.calcPrice(2)).isEqualTo(80.0);
    }

    @Test
    public void testOldFilmPrice() throws Exception {
        Film newFilm = new Film("Old Film", Film.Type.OLD);
        Assertions.assertThat(newFilm.calcPrice(1)).isEqualTo(30.0);
        Assertions.assertThat(newFilm.calcPrice(5)).isEqualTo(30.0);
        Assertions.assertThat(newFilm.calcPrice(6)).isEqualTo(60.0);
        Assertions.assertThat(newFilm.calcPrice(10)).isEqualTo(180.0);
        Assertions.assertThat(newFilm.calcPrice(15)).isEqualTo(330.0);
    }

    @Test
    public void testRegularFilmPrice() throws Exception {
        Film newFilm = new Film("Regular Film", Film.Type.REGULAR);
        Assertions.assertThat(newFilm.calcPrice(1)).isEqualTo(30.0);
        Assertions.assertThat(newFilm.calcPrice(3)).isEqualTo(30.0);
        Assertions.assertThat(newFilm.calcPrice(4)).isEqualTo(60.0);
        Assertions.assertThat(newFilm.calcPrice(5)).isEqualTo(90.0);
    }
}
