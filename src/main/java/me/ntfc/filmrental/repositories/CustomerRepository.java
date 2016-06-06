package me.ntfc.filmrental.repositories;

import me.ntfc.filmrental.entities.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {
}
