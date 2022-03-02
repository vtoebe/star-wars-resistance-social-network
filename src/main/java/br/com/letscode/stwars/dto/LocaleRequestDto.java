package br.com.letscode.stwars.dto;

import lombok.Data;

@Data
public class LocaleRequestDto {

    //Locale
    private String latitude;

    private String longitude;

    private String base;
}
