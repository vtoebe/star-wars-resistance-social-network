package br.com.letscode.stwars.controller;

import br.com.letscode.stwars.dto.MarketPlaceDto;
import br.com.letscode.stwars.service.MarketPlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/marketplace")
public class MarketPlaceController {

    private MarketPlaceService service;

    @PostMapping
    public void insertOffer(@RequestBody MarketPlaceDto requestDto) {
        service.insertNewOffer(requestDto);
    }

}
