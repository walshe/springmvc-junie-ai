package walshe.juniemvc.juniemvc.services;

import walshe.juniemvc.juniemvc.entities.Beer;

import java.util.List;
import java.util.Optional;

public interface BeerService {
    List<Beer> listBeers();
    Optional<Beer> getBeerById(Integer id);
    Beer saveNewBeer(Beer beer);
    void updateBeerById(Integer beerId, Beer beer);
    void deleteById(Integer beerId);
}
