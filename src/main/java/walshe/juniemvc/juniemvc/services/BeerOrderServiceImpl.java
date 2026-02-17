package walshe.juniemvc.juniemvc.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import walshe.juniemvc.juniemvc.entities.Beer;
import walshe.juniemvc.juniemvc.entities.BeerOrder;
import walshe.juniemvc.juniemvc.entities.BeerOrderLine;
import walshe.juniemvc.juniemvc.mappers.BeerOrderMapper;
import walshe.juniemvc.juniemvc.models.BeerOrderDto;
import walshe.juniemvc.juniemvc.models.CreateBeerOrderCommand;
import walshe.juniemvc.juniemvc.models.PatchBeerOrderCommand;
import walshe.juniemvc.juniemvc.repositories.BeerOrderRepository;
import walshe.juniemvc.juniemvc.repositories.BeerRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class BeerOrderServiceImpl implements BeerOrderService {

    private final BeerOrderRepository beerOrderRepository;
    private final BeerRepository beerRepository;
    private final BeerOrderMapper beerOrderMapper;

    @Override
    @Transactional(readOnly = true)
    public List<BeerOrderDto> listOrders() {
        return beerOrderRepository.findAll()
                .stream()
                .map(beerOrderMapper::beerOrderToBeerOrderDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BeerOrderDto> getOrderById(Integer id) {
        return beerOrderRepository.findWithLinesById(id)
                .map(beerOrderMapper::beerOrderToBeerOrderDto);
    }

    @Override
    @Transactional
    public BeerOrderDto createOrder(CreateBeerOrderCommand command) {
        BeerOrder order = BeerOrder.builder()
                .customerRef(command.customerRef())
                .notes(command.notes())
                .build();

        if (command.beerOrderLines() != null) {
            for (CreateBeerOrderCommand.CreateBeerOrderLineCommand lineCmd : command.beerOrderLines()) {
                Beer beer = beerRepository.findById(lineCmd.beerId())
                        .orElseThrow(() -> new IllegalArgumentException("Beer not found: " + lineCmd.beerId()));
                BeerOrderLine line = BeerOrderLine.builder()
                        .beer(beer)
                        .orderQuantity(lineCmd.orderQuantity())
                        .build();
                order.addLine(line);
            }
        }

        BeerOrder saved = beerOrderRepository.save(order);
        return beerOrderMapper.beerOrderToBeerOrderDto(saved);
    }

    @Override
    @Transactional
    public void updateOrder(Integer id, BeerOrderDto dto) {
        beerOrderRepository.findById(id).ifPresent(found -> {
            found.setCustomerRef(dto.getCustomerRef());
            found.setPaymentAmount(dto.getPaymentAmount());
            found.setStatus(dto.getStatus());
            found.setNotes(dto.getNotes());
            beerOrderRepository.save(found);
        });
    }

    @Override
    @Transactional
    public void patchBeerOrder(Integer id, PatchBeerOrderCommand command) {
        beerOrderRepository.findById(id).ifPresent(found -> {
            beerOrderMapper.updateBeerOrderFromPatch(command, found);
            beerOrderRepository.save(found);
        });
    }

    @Override
    @Transactional
    public void deleteOrder(Integer id) {
        beerOrderRepository.deleteById(id);
    }
}
