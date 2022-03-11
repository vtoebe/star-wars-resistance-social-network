package br.com.letscode.stwars.controller;

import br.com.letscode.stwars.dto.MarketPlaceDto;
import br.com.letscode.stwars.dto.PersonIdDto;
import br.com.letscode.stwars.model.MarketPlaceEntity;
import br.com.letscode.stwars.service.MarketPlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/marketplace")
public class MarketPlaceController {

    private final MarketPlaceService marketplaceService;

    @PostMapping("/create-offer")
    public void insertOffer(@RequestBody MarketPlaceDto requestDto) {

        marketplaceService.insertNewOffer(requestDto);
    }

    //todo by filters
    @GetMapping("/offers")
    public List<MarketPlaceEntity> getListMarketPlace() {

        return marketplaceService.getListByMarketPlace();
    }

    @PatchMapping("/offers/{offerId}/trade")
    public void tradeItems(@RequestBody PersonIdDto personId, @PathVariable("offerId") Long offerId){
        marketplaceService.tradeItems(personId, offerId);
    }
}
