package br.com.letscode.stwars.dto;

import lombok.Data;

@Data
public class MarketPlaceDto {

    private Long idPerson;

    private String base;

    //Invetory
    private ItemsDto offer;

    private ItemsDto receive;
}
