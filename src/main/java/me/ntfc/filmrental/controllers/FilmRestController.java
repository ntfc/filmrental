package me.ntfc.filmrental.controllers;

import me.ntfc.filmrental.entities.Film;
import me.ntfc.filmrental.repositories.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/films")
public class FilmRestController {

    @Autowired
    private FilmRepository filmRepository;

    @RequestMapping
    @ResponseBody
    public ResponseEntity<Iterable<Film>> getAllFilms() {
        return new ResponseEntity<>(filmRepository.findAll(), HttpStatus.OK);
    }
}
