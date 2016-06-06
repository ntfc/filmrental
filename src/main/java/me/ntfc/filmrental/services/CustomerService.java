package me.ntfc.filmrental.services;

import me.ntfc.filmrental.entities.Customer;
import me.ntfc.filmrental.entities.Film;
import me.ntfc.filmrental.entities.Rental;
import me.ntfc.filmrental.entities.RentedFilm;
import me.ntfc.filmrental.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer updateCustomerPoints(Customer customer, Rental rental) {
        int rentalPoints = 0;
        for (RentedFilm rentedFilm : rental.getFilms()) {
            if (rentedFilm.getFilm().getType() == Film.Type.NEW) {
                rentalPoints += 2;
            }
            else {
                rentalPoints++;
            }
        }
        customer.incrementPoints(rentalPoints);
        return customerRepository.save(customer);

    }
}
