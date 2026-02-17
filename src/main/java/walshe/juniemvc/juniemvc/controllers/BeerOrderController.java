package walshe.juniemvc.juniemvc.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import walshe.juniemvc.juniemvc.models.BeerOrderDto;
import walshe.juniemvc.juniemvc.models.CreateBeerOrderCommand;
import walshe.juniemvc.juniemvc.models.PatchBeerOrderCommand;
import walshe.juniemvc.juniemvc.services.BeerOrderService;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/beerOrder")
@Validated
class BeerOrderController {

    private final BeerOrderService beerOrderService;

    @GetMapping
    List<BeerOrderDto> listOrders() {
        return beerOrderService.listOrders();
    }

    @GetMapping("/{orderId}")
    BeerOrderDto getOrderById(@PathVariable("orderId") Integer orderId) {
        return beerOrderService.getOrderById(orderId).orElseThrow(RuntimeException::new);
    }

    @PostMapping
    ResponseEntity<BeerOrderDto> createOrder(@RequestBody CreateBeerOrderCommand command) {
        BeerOrderDto created = beerOrderService.createOrder(command);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PatchMapping("/{orderId}")
    ResponseEntity<Void> patchOrder(@PathVariable("orderId") Integer orderId, @RequestBody PatchBeerOrderCommand command) {
        beerOrderService.patchBeerOrder(orderId, command);
        return ResponseEntity.noContent().build();
    }
}
