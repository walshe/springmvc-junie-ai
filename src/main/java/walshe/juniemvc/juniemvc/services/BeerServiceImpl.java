package walshe.juniemvc.juniemvc.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import walshe.juniemvc.juniemvc.entities.Beer;
import walshe.juniemvc.juniemvc.mappers.BeerMapper;
import walshe.juniemvc.juniemvc.models.BeerDto;
import walshe.juniemvc.juniemvc.repositories.BeerRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    @Transactional(readOnly = true)
    public List<BeerDto> listBeers() {
        return beerRepository.findAll()
                .stream()
                .map(beerMapper::beerToBeerDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BeerDto> getBeerById(Integer id) {
        return beerRepository.findById(id).map(beerMapper::beerToBeerDto);
    }

    @Override
    @Transactional
    public BeerDto saveNewBeer(BeerDto beerDto) {
        Beer toSave = beerMapper.beerDtoToBeer(beerDto);
        Beer saved = beerRepository.save(toSave);
        return beerMapper.beerToBeerDto(saved);
    }

    @Override
    @Transactional
    public void updateBeerById(Integer beerId, BeerDto beerDto) {
        beerRepository.findById(beerId).ifPresent(foundBeer -> {
            beerMapper.updateBeerFromDto(beerDto, foundBeer);
            beerRepository.save(foundBeer);
        });
    }

    @Override
    @Transactional
    public void deleteById(Integer beerId) {
        beerRepository.deleteById(beerId);
    }
}
