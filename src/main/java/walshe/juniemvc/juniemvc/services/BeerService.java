package walshe.juniemvc.juniemvc.services;

import walshe.juniemvc.juniemvc.models.BeerDto;

import java.util.List;
import java.util.Optional;

public interface BeerService {
    List<BeerDto> listBeers();
    Optional<BeerDto> getBeerById(Integer id);
    BeerDto saveNewBeer(BeerDto beerDto);
    void updateBeerById(Integer beerId, BeerDto beerDto);
    void deleteById(Integer beerId);
}
