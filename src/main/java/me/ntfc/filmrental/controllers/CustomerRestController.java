package me.ntfc.filmrental.controllers;

import me.ntfc.filmrental.entities.Customer;
import me.ntfc.filmrental.entities.Rental;
import me.ntfc.filmrental.repositories.CustomerRepository;
import me.ntfc.filmrental.repositories.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RepositoryRestController
@RequestMapping("/customers")
public class CustomerRestController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RentalRepository rentalRepository;

    @RequestMapping
    @ResponseBody
    public ResponseEntity<Iterable<Customer>> getAllCustomers() {
        return new ResponseEntity<>(customerRepository.findAll(), HttpStatus.OK);
    }
    @RequestMapping(value = "/{customerId}")
    @ResponseBody
    public ResponseEntity<Customer> getCustomer(@PathVariable Long customerId) {
        return new ResponseEntity<>(customerRepository.findOne(customerId), HttpStatus.OK);
    }

    @RequestMapping(value = "/{customerId}/rentals")
    @ResponseBody
    public ResponseEntity<Iterable<Rental>> getCustomerRentals(@PathVariable Long customerId) {
        return new ResponseEntity<>(rentalRepository.findRentalsByCustomerId(customerId), HttpStatus.OK);
    }

}
