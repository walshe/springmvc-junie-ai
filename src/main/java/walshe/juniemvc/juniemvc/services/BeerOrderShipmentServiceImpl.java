package walshe.juniemvc.juniemvc.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import walshe.juniemvc.juniemvc.entities.BeerOrder;
import walshe.juniemvc.juniemvc.entities.BeerOrderShipment;
import walshe.juniemvc.juniemvc.mappers.BeerOrderShipmentMapper;
import walshe.juniemvc.juniemvc.models.BeerOrderShipmentDto;
import walshe.juniemvc.juniemvc.models.CreateBeerOrderShipmentCommand;
import walshe.juniemvc.juniemvc.models.UpdateBeerOrderShipmentCommand;
import walshe.juniemvc.juniemvc.repositories.BeerOrderRepository;
import walshe.juniemvc.juniemvc.repositories.BeerOrderShipmentRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class BeerOrderShipmentServiceImpl implements BeerOrderShipmentService {

    private final BeerOrderShipmentRepository beerOrderShipmentRepository;
    private final BeerOrderRepository beerOrderRepository;
    private final BeerOrderShipmentMapper beerOrderShipmentMapper;

    @Override
    @Transactional(readOnly = true)
    public List<BeerOrderShipmentDto> listShipments() {
        return beerOrderShipmentRepository.findAll().stream()
                .map(beerOrderShipmentMapper::beerOrderShipmentToBeerOrderShipmentDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BeerOrderShipmentDto> getShipmentById(Integer id) {
        return beerOrderShipmentRepository.findById(id)
                .map(beerOrderShipmentMapper::beerOrderShipmentToBeerOrderShipmentDto);
    }

    @Override
    @Transactional
    public BeerOrderShipmentDto createShipment(CreateBeerOrderShipmentCommand command) {
        BeerOrder beerOrder = beerOrderRepository.findById(command.beerOrderId())
                .orElseThrow(() -> new IllegalArgumentException("Beer Order not found"));

        BeerOrderShipment shipment = beerOrderShipmentMapper.createCommandToBeerOrderShipment(command);
        beerOrder.addShipment(shipment);

        return beerOrderShipmentMapper.beerOrderShipmentToBeerOrderShipmentDto(beerOrderShipmentRepository.save(shipment));
    }

    @Override
    @Transactional
    public Optional<BeerOrderShipmentDto> updateShipment(Integer id, UpdateBeerOrderShipmentCommand command) {
        return beerOrderShipmentRepository.findById(id)
                .map(found -> {
                    beerOrderShipmentMapper.updateBeerOrderShipmentFromCommand(command, found);
                    return beerOrderShipmentMapper.beerOrderShipmentToBeerOrderShipmentDto(beerOrderShipmentRepository.save(found));
                });
    }

    @Override
    @Transactional
    public boolean deleteShipment(Integer id) {
        return beerOrderShipmentRepository.findById(id)
                .map(found -> {
                    beerOrderShipmentRepository.delete(found);
                    return true;
                }).orElse(false);
    }
}
