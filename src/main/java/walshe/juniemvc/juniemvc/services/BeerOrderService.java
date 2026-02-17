package walshe.juniemvc.juniemvc.services;

import walshe.juniemvc.juniemvc.models.BeerOrderDto;
import walshe.juniemvc.juniemvc.models.CreateBeerOrderCommand;
import walshe.juniemvc.juniemvc.models.PatchBeerOrderCommand;

import java.util.List;
import java.util.Optional;

public interface BeerOrderService {
    List<BeerOrderDto> listOrders();
    Optional<BeerOrderDto> getOrderById(Integer id);
    BeerOrderDto createOrder(CreateBeerOrderCommand command);
    void updateOrder(Integer id, BeerOrderDto dto);
    void patchBeerOrder(Integer id, PatchBeerOrderCommand command);
    void deleteOrder(Integer id);
}
