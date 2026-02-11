package walshe.juniemvc.juniemvc.mappers;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import walshe.juniemvc.juniemvc.entities.Beer;
import walshe.juniemvc.juniemvc.models.BeerDto;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-11T20:24:15+0000",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class BeerMapperImpl implements BeerMapper {

    @Override
    public BeerDto beerToBeerDto(Beer beer) {
        if ( beer == null ) {
            return null;
        }

        BeerDto.BeerDtoBuilder beerDto = BeerDto.builder();

        beerDto.id( beer.getId() );
        beerDto.version( beer.getVersion() );
        beerDto.beerName( beer.getBeerName() );
        beerDto.beerStyle( beer.getBeerStyle() );
        beerDto.upc( beer.getUpc() );
        beerDto.quantityOnHand( beer.getQuantityOnHand() );
        beerDto.price( beer.getPrice() );
        beerDto.createdDate( beer.getCreatedDate() );
        beerDto.updateDate( beer.getUpdateDate() );

        return beerDto.build();
    }

    @Override
    public Beer beerDtoToBeer(BeerDto dto) {
        if ( dto == null ) {
            return null;
        }

        Beer.BeerBuilder beer = Beer.builder();

        beer.version( dto.getVersion() );
        beer.beerName( dto.getBeerName() );
        beer.beerStyle( dto.getBeerStyle() );
        beer.upc( dto.getUpc() );
        beer.quantityOnHand( dto.getQuantityOnHand() );
        beer.price( dto.getPrice() );

        return beer.build();
    }

    @Override
    public void updateBeerFromDto(BeerDto dto, Beer beer) {
        if ( dto == null ) {
            return;
        }

        beer.setVersion( dto.getVersion() );
        beer.setBeerName( dto.getBeerName() );
        beer.setBeerStyle( dto.getBeerStyle() );
        beer.setUpc( dto.getUpc() );
        beer.setQuantityOnHand( dto.getQuantityOnHand() );
        beer.setPrice( dto.getPrice() );
    }
}
