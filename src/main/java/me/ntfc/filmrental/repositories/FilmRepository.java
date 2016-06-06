package me.ntfc.filmrental.repositories;


import me.ntfc.filmrental.entities.Film;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface FilmRepository extends PagingAndSortingRepository<Film, Long> {
}
