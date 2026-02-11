package walshe.juniemvc.juniemvc.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import walshe.juniemvc.juniemvc.entities.BeerOrder;
import walshe.juniemvc.juniemvc.entities.BeerOrderLine;
import walshe.juniemvc.juniemvc.models.BeerOrderDto;
import walshe.juniemvc.juniemvc.models.BeerOrderLineDto;

@Mapper(componentModel = "spring")
public interface BeerOrderMapper {

    BeerOrderDto beerOrderToBeerOrderDto(BeerOrder beerOrder);

    BeerOrder beerOrderDtoToBeerOrder(BeerOrderDto dto);

    @Mapping(target = "beerId", expression = "java(beerOrderLine.getBeer() != null ? beerOrderLine.getBeer().getId() : null)")
    BeerOrderLineDto beerOrderLineToBeerOrderLineDto(BeerOrderLine beerOrderLine);

    @Mapping(target = "beer", ignore = true)
    BeerOrderLine beerOrderLineDtoToBeerOrderLine(BeerOrderLineDto dto);
}
