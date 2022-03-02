package br.com.letscode.stwars.dto;

import br.com.letscode.stwars.enums.FactionEnum;
import br.com.letscode.stwars.enums.GenreEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PersonRequestDto {

    private String name;

    private String birth;

    private String genre;

    private String faction;

    //Locale
    private String latitude;
    private String longitude;
    private String base;

    //Invetory
    private Integer weapons;
    private Integer ammunitions;
    private Integer waters;
    private Integer foods;
}
