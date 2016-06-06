package me.ntfc.filmrental.controllers;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import me.ntfc.filmrental.entities.Customer;
import me.ntfc.filmrental.entities.Rental;
import me.ntfc.filmrental.repositories.CustomerRepository;
import me.ntfc.filmrental.repositories.RentalRepository;
import me.ntfc.filmrental.services.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RepositoryRestController
@RequestMapping("/rentals")
public class RentalRestController {
    public static class RentalBody {
        private Long customerId;
        private List<DaysPerFilm> films = Lists.newArrayList();

        public Long getCustomerId() {
            return customerId;
        }

        public void setCustomerId(Long customerId) {
            this.customerId = customerId;
        }

        public List<DaysPerFilm> getFilms() {
            return films;
        }

        public void setFilms(List<DaysPerFilm> filmIdDays) {
            this.films = filmIdDays;
        }
    }

    public static class DaysPerFilm {
        private long filmId;
        private long days;

        public long getFilmId() {
            return filmId;
        }

        public void setFilmId(long filmId) {
            this.filmId = filmId;
        }

        public long getDays() {
            return days;
        }

        public void setDays(long days) {
            this.days = days;
        }
    }


    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RentalService rentalService;

    @RequestMapping
    @ResponseBody
    public ResponseEntity<Iterable<Rental>> getAllRentals() {
        return new ResponseEntity<>(rentalRepository.findAll(), HttpStatus.OK);
    }
    @RequestMapping(value = "/{rentalId}")
    @ResponseBody
    public ResponseEntity<Rental> getRental(@PathVariable Long rentalId) {
        return new ResponseEntity<>(rentalService.getRental(rentalId), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Rental> rentFilmsToCustomer(@RequestBody RentalBody rentalBody) {
        Preconditions.checkState(! rentalBody.getFilms().isEmpty(), "Need at least one film");

        Customer customer = customerRepository.findOne(rentalBody.getCustomerId());
        return new ResponseEntity<>(rentalService.doRent(customer, rentalBody.getFilms()), HttpStatus.OK);
    }

    @RequestMapping(value = "/{rentalId}/return/{movieIds}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Rental> returnRental(@PathVariable Long rentalId, @PathVariable List<Long> movieIds) {
        return new ResponseEntity<>(rentalService.doReturn(rentalId, movieIds), HttpStatus.OK);
    }

    @RequestMapping(value = "/{rentalId}/return/all", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Rental> returnRental(@PathVariable Long rentalId) {
        return new ResponseEntity<>(rentalService.doReturn(rentalId), HttpStatus.OK);
    }
}
