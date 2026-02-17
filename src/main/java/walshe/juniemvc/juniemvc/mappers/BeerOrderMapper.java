package walshe.juniemvc.juniemvc.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import walshe.juniemvc.juniemvc.entities.BeerOrder;
import walshe.juniemvc.juniemvc.entities.BeerOrderLine;
import walshe.juniemvc.juniemvc.models.BeerOrderDto;
import walshe.juniemvc.juniemvc.models.BeerOrderLineDto;
import walshe.juniemvc.juniemvc.models.PatchBeerOrderCommand;

@Mapper(componentModel = "spring")
public interface BeerOrderMapper {

    BeerOrderDto beerOrderToBeerOrderDto(BeerOrder beerOrder);

    BeerOrder beerOrderDtoToBeerOrder(BeerOrderDto dto);

    @Mapping(target = "beerId", expression = "java(beerOrderLine.getBeer() != null ? beerOrderLine.getBeer().getId() : null)")
    BeerOrderLineDto beerOrderLineToBeerOrderLineDto(BeerOrderLine beerOrderLine);

    @Mapping(target = "beer", ignore = true)
    BeerOrderLine beerOrderLineDtoToBeerOrderLine(BeerOrderLineDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBeerOrderFromPatch(PatchBeerOrderCommand command, @MappingTarget BeerOrder entity);
}
