package walshe.juniemvc.juniemvc.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import walshe.juniemvc.juniemvc.entities.Beer;
import walshe.juniemvc.juniemvc.repositories.BeerRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;

    @Override
    public List<Beer> listBeers() {
        return beerRepository.findAll();
    }

    @Override
    public Optional<Beer> getBeerById(Integer id) {
        return beerRepository.findById(id);
    }

    @Override
    public Beer saveNewBeer(Beer beer) {
        return beerRepository.save(beer);
    }

    @Override
    public void updateBeerById(Integer beerId, Beer beer) {
        beerRepository.findById(beerId).ifPresent(foundBeer -> {
            foundBeer.setBeerName(beer.getBeerName());
            foundBeer.setBeerStyle(beer.getBeerStyle());
            foundBeer.setPrice(beer.getPrice());
            foundBeer.setUpc(beer.getUpc());
            foundBeer.setQuantityOnHand(beer.getQuantityOnHand());
            beerRepository.save(foundBeer);
        });
    }

    @Override
    public void deleteById(Integer beerId) {
        beerRepository.deleteById(beerId);
    }
}
