package me.ntfc.filmrental.repositories;

import me.ntfc.filmrental.entities.Rental;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface RentalRepository extends PagingAndSortingRepository<Rental, Long> {

    Iterable<Rental> findRentalsByCustomerId(@Param("customerId") Long costumerId);

}
