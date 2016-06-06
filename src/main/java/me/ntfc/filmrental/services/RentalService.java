package me.ntfc.filmrental.services;

import me.ntfc.filmrental.controllers.RentalRestController;
import me.ntfc.filmrental.entities.Customer;
import me.ntfc.filmrental.entities.Film;
import me.ntfc.filmrental.entities.Rental;
import me.ntfc.filmrental.repositories.FilmRepository;
import me.ntfc.filmrental.repositories.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RentalService {
    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private CustomerService customerService;

    public Rental getRental(Long rentalId) {
        return rentalRepository.findOne(rentalId);
    }

    public Rental doRent(Customer customer, List<RentalRestController.DaysPerFilm> daysPerFilm) {
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setStartDate(LocalDateTime.now());
        daysPerFilm.stream()
            .forEach(daysPerFilmId -> {
                Film film = filmRepository.findOne(daysPerFilmId.getFilmId());
                rental.addFilm(film, daysPerFilmId.getDays());
            });

        customerService.updateCustomerPoints(customer, rental);

        return rentalRepository.save(rental);
    }

    public Rental doReturn(Long rentalId, List<Long> filmIds) {
        LocalDateTime currentTime = LocalDateTime.now();
        Rental rental = rentalRepository.findOne(rentalId);
        rental.getFilms().stream()
            .filter(rentedFilm -> rentedFilm.getReturnDate() == null)
            .filter(rentedFilm ->
            filmIds.contains(rentedFilm.getFilm().getId()))
                .forEach(rentedFilm -> {
                    rentedFilm.setReturnDate(currentTime);
                    rentedFilm.setSurchargePrice(rentedFilm.calcSurchargePrice());
                }
        );

        rentalRepository.save(rental);
        return rental;
    }


    public Rental doReturn(Long rentalId) {
        LocalDateTime currentTime = LocalDateTime.now();
        Rental rental = rentalRepository.findOne(rentalId);
        rental.getFilms().stream()
            .filter(rentedFilm -> rentedFilm.getReturnDate() == null)
            .forEach(rentedFilm -> {
                rentedFilm.setReturnDate(currentTime);
                rentedFilm.setSurchargePrice(rentedFilm.calcSurchargePrice());
            }
        );

        rentalRepository.save(rental);
        return rental;
    }
}
