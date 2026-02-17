package walshe.juniemvc.juniemvc.mappers;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import walshe.juniemvc.juniemvc.entities.BeerOrder;
import walshe.juniemvc.juniemvc.entities.BeerOrderShipment;
import walshe.juniemvc.juniemvc.models.BeerOrderShipmentDto;
import walshe.juniemvc.juniemvc.models.CreateBeerOrderShipmentCommand;
import walshe.juniemvc.juniemvc.models.UpdateBeerOrderShipmentCommand;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-17T19:14:25+0000",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.2 (Amazon.com Inc.)"
)
@Component
public class BeerOrderShipmentMapperImpl implements BeerOrderShipmentMapper {

    @Override
    public BeerOrderShipmentDto beerOrderShipmentToBeerOrderShipmentDto(BeerOrderShipment beerOrderShipment) {
        if ( beerOrderShipment == null ) {
            return null;
        }

        BeerOrderShipmentDto.BeerOrderShipmentDtoBuilder beerOrderShipmentDto = BeerOrderShipmentDto.builder();

        beerOrderShipmentDto.beerOrderId( beerOrderShipmentBeerOrderId( beerOrderShipment ) );
        beerOrderShipmentDto.id( beerOrderShipment.getId() );
        beerOrderShipmentDto.version( beerOrderShipment.getVersion() );
        beerOrderShipmentDto.shipmentDate( beerOrderShipment.getShipmentDate() );
        beerOrderShipmentDto.carrier( beerOrderShipment.getCarrier() );
        beerOrderShipmentDto.trackingNumber( beerOrderShipment.getTrackingNumber() );
        beerOrderShipmentDto.createdDate( beerOrderShipment.getCreatedDate() );
        beerOrderShipmentDto.updateDate( beerOrderShipment.getUpdateDate() );

        return beerOrderShipmentDto.build();
    }

    @Override
    public BeerOrderShipment beerOrderShipmentDtoToBeerOrderShipment(BeerOrderShipmentDto dto) {
        if ( dto == null ) {
            return null;
        }

        BeerOrderShipment.BeerOrderShipmentBuilder beerOrderShipment = BeerOrderShipment.builder();

        beerOrderShipment.shipmentDate( dto.getShipmentDate() );
        beerOrderShipment.carrier( dto.getCarrier() );
        beerOrderShipment.trackingNumber( dto.getTrackingNumber() );

        return beerOrderShipment.build();
    }

    @Override
    public BeerOrderShipment createCommandToBeerOrderShipment(CreateBeerOrderShipmentCommand command) {
        if ( command == null ) {
            return null;
        }

        BeerOrderShipment.BeerOrderShipmentBuilder beerOrderShipment = BeerOrderShipment.builder();

        beerOrderShipment.shipmentDate( command.shipmentDate() );
        beerOrderShipment.carrier( command.carrier() );
        beerOrderShipment.trackingNumber( command.trackingNumber() );

        return beerOrderShipment.build();
    }

    @Override
    public void updateBeerOrderShipmentFromCommand(UpdateBeerOrderShipmentCommand command, BeerOrderShipment beerOrderShipment) {
        if ( command == null ) {
            return;
        }

        beerOrderShipment.setShipmentDate( command.shipmentDate() );
        beerOrderShipment.setCarrier( command.carrier() );
        beerOrderShipment.setTrackingNumber( command.trackingNumber() );
    }

    private Integer beerOrderShipmentBeerOrderId(BeerOrderShipment beerOrderShipment) {
        BeerOrder beerOrder = beerOrderShipment.getBeerOrder();
        if ( beerOrder == null ) {
            return null;
        }
        return beerOrder.getId();
    }
}
