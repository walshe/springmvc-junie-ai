package walshe.juniemvc.juniemvc.services;

import walshe.juniemvc.juniemvc.models.BeerOrderShipmentDto;
import walshe.juniemvc.juniemvc.models.CreateBeerOrderShipmentCommand;
import walshe.juniemvc.juniemvc.models.UpdateBeerOrderShipmentCommand;

import java.util.List;
import java.util.Optional;

public interface BeerOrderShipmentService {
    List<BeerOrderShipmentDto> listShipments();
    Optional<BeerOrderShipmentDto> getShipmentById(Integer id);
    BeerOrderShipmentDto createShipment(CreateBeerOrderShipmentCommand command);
    Optional<BeerOrderShipmentDto> updateShipment(Integer id, UpdateBeerOrderShipmentCommand command);
    boolean deleteShipment(Integer id);
}
