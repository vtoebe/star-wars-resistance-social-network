package br.com.letscode.stwars.controller;

import br.com.letscode.stwars.dto.AcceptOfferDto;
import br.com.letscode.stwars.dto.MarketPlaceDto;
import br.com.letscode.stwars.model.MarketPlaceEntity;
import br.com.letscode.stwars.service.MarketPlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/marketplace")
public class MarketPlaceController {

    private final MarketPlaceService service;

    @PostMapping
    public void insertOffer(@RequestBody MarketPlaceDto requestDto) {
        service.insertNewOffer(requestDto);
    }

    //todo by filters
    @GetMapping()
    public List<MarketPlaceEntity> getListByMarketPlace() {
        return service.getListByMarketPlace();
    }

    @PostMapping(value = "{id}")
    public void acceptOffer(@PathVariable("id") Long id, @RequestBody AcceptOfferDto request){
        service.acceptOffer(request, id);
    }
}
