package walshe.juniemvc.juniemvc.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import walshe.juniemvc.juniemvc.entities.BeerOrderShipment;
import walshe.juniemvc.juniemvc.models.BeerOrderShipmentDto;
import walshe.juniemvc.juniemvc.models.CreateBeerOrderShipmentCommand;
import walshe.juniemvc.juniemvc.models.UpdateBeerOrderShipmentCommand;

@Mapper(componentModel = "spring")
public interface BeerOrderShipmentMapper {

    @Mapping(target = "beerOrderId", source = "beerOrder.id")
    BeerOrderShipmentDto beerOrderShipmentToBeerOrderShipmentDto(BeerOrderShipment beerOrderShipment);

    @Mapping(target = "beerOrder", ignore = true)
    BeerOrderShipment beerOrderShipmentDtoToBeerOrderShipment(BeerOrderShipmentDto dto);

    @Mapping(target = "beerOrder", ignore = true)
    BeerOrderShipment createCommandToBeerOrderShipment(CreateBeerOrderShipmentCommand command);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    @Mapping(target = "beerOrder", ignore = true)
    void updateBeerOrderShipmentFromCommand(UpdateBeerOrderShipmentCommand command, @MappingTarget BeerOrderShipment beerOrderShipment);
}
