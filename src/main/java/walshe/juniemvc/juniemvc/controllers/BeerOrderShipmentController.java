package walshe.juniemvc.juniemvc.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import walshe.juniemvc.juniemvc.models.BeerOrderShipmentDto;
import walshe.juniemvc.juniemvc.models.CreateBeerOrderShipmentCommand;
import walshe.juniemvc.juniemvc.models.UpdateBeerOrderShipmentCommand;
import walshe.juniemvc.juniemvc.services.BeerOrderShipmentService;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/shipments")
@Validated
class BeerOrderShipmentController {

    private final BeerOrderShipmentService beerOrderShipmentService;

    @GetMapping
    List<BeerOrderShipmentDto> listShipments() {
        return beerOrderShipmentService.listShipments();
    }

    @GetMapping("/{shipmentId}")
    ResponseEntity<BeerOrderShipmentDto> getShipmentById(@PathVariable("shipmentId") Integer shipmentId) {
        return beerOrderShipmentService.getShipmentById(shipmentId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    ResponseEntity<BeerOrderShipmentDto> createShipment(@RequestBody CreateBeerOrderShipmentCommand command) {
        BeerOrderShipmentDto created = beerOrderShipmentService.createShipment(command);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{shipmentId}")
    ResponseEntity<Void> updateShipment(@PathVariable("shipmentId") Integer shipmentId, @RequestBody UpdateBeerOrderShipmentCommand command) {
        return beerOrderShipmentService.updateShipment(shipmentId, command)
                .map(dto -> ResponseEntity.noContent().<Void>build())
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{shipmentId}")
    ResponseEntity<Void> deleteShipment(@PathVariable("shipmentId") Integer shipmentId) {
        if (beerOrderShipmentService.deleteShipment(shipmentId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
