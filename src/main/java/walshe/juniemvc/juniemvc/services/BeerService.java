package walshe.juniemvc.juniemvc.services;

import org.springframework.data.domain.Page;
import walshe.juniemvc.juniemvc.models.BeerDto;

import java.util.Optional;

public interface BeerService {
    Page<BeerDto> listBeers(String beerName, Integer pageNumber, Integer pageSize);
    Optional<BeerDto> getBeerById(Integer id);
    BeerDto saveNewBeer(BeerDto beerDto);
    void updateBeerById(Integer beerId, BeerDto beerDto);
    void deleteById(Integer beerId);
}
