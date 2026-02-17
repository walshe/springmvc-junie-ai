package walshe.juniemvc.juniemvc.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import walshe.juniemvc.juniemvc.entities.Beer;
import walshe.juniemvc.juniemvc.mappers.BeerMapper;
import walshe.juniemvc.juniemvc.models.BeerDto;
import walshe.juniemvc.juniemvc.repositories.BeerRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_PAGE_SIZE = 25;

    @Override
    @Transactional(readOnly = true)
    public Page<BeerDto> listBeers(java.util.Optional<String> beerName, java.util.Optional<String> beerStyle, Integer pageNumber, Integer pageSize) {
        Pageable pageable = buildPageRequest(pageNumber, pageSize);

        String name = beerName.filter(StringUtils::hasText).orElse(null);
        String style = beerStyle.filter(StringUtils::hasText).orElse(null);

        Page<Beer> beerPage;

        if (name != null && style != null) {
            beerPage = listBeersByNameAndStyle(name, style, pageable);
        } else if (name != null) {
            beerPage = listBeersByName(name, pageable);
        } else if (style != null) {
            beerPage = listBeersByStyle(style, pageable);
        } else {
            beerPage = beerRepository.findAll(pageable);
        }

        return beerPage.map(beerMapper::beerToBeerDto);
    }

    private Page<Beer> listBeersByNameAndStyle(String beerName, String beerStyle, Pageable pageable) {
        return beerRepository.findAllByBeerNameIsLikeIgnoreCaseAndBeerStyleIsLikeIgnoreCase("%" + beerName + "%", "%" + beerStyle + "%", pageable);
    }

    private Page<Beer> listBeersByStyle(String beerStyle, Pageable pageable) {
        return beerRepository.findAllByBeerStyleIsLikeIgnoreCase("%" + beerStyle + "%", pageable);
    }

    private Page<Beer> listBeersByName(String beerName, Pageable pageable) {
        return beerRepository.findAllByBeerNameIsLikeIgnoreCase("%" + beerName + "%", pageable);
    }

    private Pageable buildPageRequest(Integer pageNumber, Integer pageSize) {
        int queryPageNumber;
        int queryPageSize;

        if (pageNumber != null && pageNumber > 0) {
            queryPageNumber = pageNumber - 1;
        } else {
            queryPageNumber = DEFAULT_PAGE;
        }

        if (pageSize == null) {
            queryPageSize = DEFAULT_PAGE_SIZE;
        } else {
            if (pageSize > 1000) {
                queryPageSize = 1000;
            } else {
                queryPageSize = pageSize;
            }
        }

        Sort sort = Sort.by(Sort.Order.asc("beerName"));

        return PageRequest.of(queryPageNumber, queryPageSize, sort);
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
