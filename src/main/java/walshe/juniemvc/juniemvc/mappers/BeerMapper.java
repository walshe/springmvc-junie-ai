package walshe.juniemvc.juniemvc.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import walshe.juniemvc.juniemvc.entities.Beer;
import walshe.juniemvc.juniemvc.models.BeerDto;

@Mapper(componentModel = "spring")
public interface BeerMapper {

    BeerDto beerToBeerDto(Beer beer);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    @Mapping(target = "beerOrderLines", ignore = true)
    Beer beerDtoToBeer(BeerDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    @Mapping(target = "beerOrderLines", ignore = true)
    void updateBeerFromDto(BeerDto dto, @MappingTarget Beer beer);
}
