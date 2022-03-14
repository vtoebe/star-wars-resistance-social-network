package br.com.letscode.stwars.controller;

import br.com.letscode.stwars.dto.MarketPlaceDto;
import br.com.letscode.stwars.dto.PersonIdDto;
import br.com.letscode.stwars.model.MarketPlaceEntity;
import br.com.letscode.stwars.service.MarketPlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/marketplace")
public class MarketPlaceController {

    private final MarketPlaceService service;

    @PostMapping(path = "/create-offer", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void insertOffer(@RequestBody MarketPlaceDto requestDto) {

        service.insertNewOffer(requestDto);
    }

    //todo by filters
    @GetMapping(path = "/offers", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MarketPlaceEntity> getListByMarketPlace() {
        return service.getListByMarketPlace();
    }

    @PatchMapping(path = "/offers/{offerId}/trade", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void tradeItems(@RequestBody PersonIdDto personId, @PathVariable("offerId") Long offerId){
        service.tradeItems(personId, offerId);
    }
}
