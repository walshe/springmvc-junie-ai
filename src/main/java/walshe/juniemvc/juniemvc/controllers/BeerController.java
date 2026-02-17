package walshe.juniemvc.juniemvc.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import walshe.juniemvc.juniemvc.models.BeerDto;
import walshe.juniemvc.juniemvc.services.BeerService;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/beer")
@Validated
class BeerController {

    private final BeerService beerService;

    @GetMapping
    Page<BeerDto> listBeers(@RequestParam(name = "beerName", required = false) String beerName,
                            @RequestParam(name = "page") Integer page,
                            @RequestParam(name = "size") Integer size) {
        return beerService.listBeers(beerName, page, size);
    }

    @GetMapping("/{beerId}")
    BeerDto getBeerById(@PathVariable("beerId") Integer beerId) {
        return beerService.getBeerById(beerId).orElseThrow(RuntimeException::new);
    }

    @PostMapping
    ResponseEntity<BeerDto> handlePost(@Valid @RequestBody BeerDto beerDto) {
        BeerDto savedBeer = beerService.saveNewBeer(beerDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedBeer.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedBeer);
    }

    @PutMapping("/{beerId}")
    ResponseEntity<Void> updateById(@PathVariable("beerId") Integer beerId, @Valid @RequestBody BeerDto beerDto) {
        beerService.updateBeerById(beerId, beerDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{beerId}")
    ResponseEntity<Void> deleteById(@PathVariable("beerId") Integer beerId) {
        beerService.deleteById(beerId);
        return ResponseEntity.noContent().build();
    }
}
