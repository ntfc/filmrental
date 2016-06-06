# Decisions

I decided to use Spring Boot and Spring Boot Starters to quickly get a REST application up and running.
It has a simple H2 database, which uses liquibase insert test data and to manage the database schema.

The basic and premium prices are hard coded on the Price enum, but in the future they could be sabed on the DB.

My focus was mainly on the rent and return actions since they are the main functions of a simple film rental store.
No validations are performed for now.

The application has 3 main entities:
* Customer: stores customer info and his bonus points
* Film: stores the film info and type
* Rental: associates one or many films with a customer. This association is describede by the RentedFilm entity

The total cost of a single Rental is the sum of all RentedFilm's cost.
That is, each RentedFilm has an upfront price and a surcharge price which are calculated when a film is rented or returned, respectively.
The rental contains helper methods to get the upfront and surcharge price.

The basic and premium prices are hard coded. They could be stored on the DB in the future.

# Usage

This is a Spring Boot application, so you can run it with:

```
mvn spring-boot:run
```

The application will be available at `http://localhost:8080`.

You can use the HAL browser at `http://localhost:8080/api` to perform simple CRUD operations on the entities.

The following endpoints are available:

* GET /customers: return all customer profiles, along with his bonus points
* GET /customers/{id}: obtain the customer profile, along with his bonus points
* GET /customers/{id}/rentals: obtain the customer rentals'
* GET /films: obtain all films
* GET /films/{id}: obtain a particular film
* GET /rentals: obtain all rental details
* GET /rentals/{id}: obtain details from a single rental
* POST /rentals: rent one or many films. The body is a JSON like:
```
{
  "films": [
    { "filmId": 1, "days": 5 },
    { "filmId": 2, "days": 2 },
  ],
  "customerId": 10
}
```
* POST /rentals/{id}/return/[films]: return one or many films from a given rental
* POST /rentals/{id}/return/all: return all remaining unreturned films
