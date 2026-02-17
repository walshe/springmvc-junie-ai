package walshe.juniemvc.juniemvc.mappers;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import walshe.juniemvc.juniemvc.entities.BeerOrder;
import walshe.juniemvc.juniemvc.entities.BeerOrderLine;
import walshe.juniemvc.juniemvc.models.BeerOrderDto;
import walshe.juniemvc.juniemvc.models.BeerOrderLineDto;
import walshe.juniemvc.juniemvc.models.PatchBeerOrderCommand;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-17T19:14:25+0000",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.2 (Amazon.com Inc.)"
)
@Component
public class BeerOrderMapperImpl implements BeerOrderMapper {

    @Override
    public BeerOrderDto beerOrderToBeerOrderDto(BeerOrder beerOrder) {
        if ( beerOrder == null ) {
            return null;
        }

        BeerOrderDto.BeerOrderDtoBuilder beerOrderDto = BeerOrderDto.builder();

        beerOrderDto.id( beerOrder.getId() );
        beerOrderDto.version( beerOrder.getVersion() );
        beerOrderDto.customerRef( beerOrder.getCustomerRef() );
        beerOrderDto.paymentAmount( beerOrder.getPaymentAmount() );
        beerOrderDto.status( beerOrder.getStatus() );
        beerOrderDto.notes( beerOrder.getNotes() );
        beerOrderDto.beerOrderLines( beerOrderLineSetToBeerOrderLineDtoList( beerOrder.getBeerOrderLines() ) );
        beerOrderDto.createdDate( beerOrder.getCreatedDate() );
        beerOrderDto.updateDate( beerOrder.getUpdateDate() );

        return beerOrderDto.build();
    }

    @Override
    public BeerOrder beerOrderDtoToBeerOrder(BeerOrderDto dto) {
        if ( dto == null ) {
            return null;
        }

        BeerOrder.BeerOrderBuilder beerOrder = BeerOrder.builder();

        beerOrder.customerRef( dto.getCustomerRef() );
        beerOrder.paymentAmount( dto.getPaymentAmount() );
        beerOrder.status( dto.getStatus() );
        beerOrder.notes( dto.getNotes() );
        beerOrder.beerOrderLines( beerOrderLineDtoListToBeerOrderLineSet( dto.getBeerOrderLines() ) );

        return beerOrder.build();
    }

    @Override
    public BeerOrderLineDto beerOrderLineToBeerOrderLineDto(BeerOrderLine beerOrderLine) {
        if ( beerOrderLine == null ) {
            return null;
        }

        BeerOrderLineDto.BeerOrderLineDtoBuilder beerOrderLineDto = BeerOrderLineDto.builder();

        beerOrderLineDto.id( beerOrderLine.getId() );
        beerOrderLineDto.version( beerOrderLine.getVersion() );
        beerOrderLineDto.orderQuantity( beerOrderLine.getOrderQuantity() );
        beerOrderLineDto.quantityAllocated( beerOrderLine.getQuantityAllocated() );
        beerOrderLineDto.status( beerOrderLine.getStatus() );
        beerOrderLineDto.createdDate( beerOrderLine.getCreatedDate() );
        beerOrderLineDto.updateDate( beerOrderLine.getUpdateDate() );

        beerOrderLineDto.beerId( beerOrderLine.getBeer() != null ? beerOrderLine.getBeer().getId() : null );

        return beerOrderLineDto.build();
    }

    @Override
    public BeerOrderLine beerOrderLineDtoToBeerOrderLine(BeerOrderLineDto dto) {
        if ( dto == null ) {
            return null;
        }

        BeerOrderLine.BeerOrderLineBuilder beerOrderLine = BeerOrderLine.builder();

        beerOrderLine.orderQuantity( dto.getOrderQuantity() );
        beerOrderLine.quantityAllocated( dto.getQuantityAllocated() );
        beerOrderLine.status( dto.getStatus() );

        return beerOrderLine.build();
    }

    @Override
    public void updateBeerOrderFromPatch(PatchBeerOrderCommand command, BeerOrder entity) {
        if ( command == null ) {
            return;
        }

        if ( command.customerRef() != null ) {
            entity.setCustomerRef( command.customerRef() );
        }
        if ( command.paymentAmount() != null ) {
            entity.setPaymentAmount( command.paymentAmount() );
        }
        if ( command.status() != null ) {
            entity.setStatus( command.status() );
        }
        if ( command.notes() != null ) {
            entity.setNotes( command.notes() );
        }
    }

    protected List<BeerOrderLineDto> beerOrderLineSetToBeerOrderLineDtoList(Set<BeerOrderLine> set) {
        if ( set == null ) {
            return null;
        }

        List<BeerOrderLineDto> list = new ArrayList<BeerOrderLineDto>( set.size() );
        for ( BeerOrderLine beerOrderLine : set ) {
            list.add( beerOrderLineToBeerOrderLineDto( beerOrderLine ) );
        }

        return list;
    }

    protected Set<BeerOrderLine> beerOrderLineDtoListToBeerOrderLineSet(List<BeerOrderLineDto> list) {
        if ( list == null ) {
            return null;
        }

        Set<BeerOrderLine> set = LinkedHashSet.newLinkedHashSet( list.size() );
        for ( BeerOrderLineDto beerOrderLineDto : list ) {
            set.add( beerOrderLineDtoToBeerOrderLine( beerOrderLineDto ) );
        }

        return set;
    }
}
