package br.com.letscode.stwars.dto;

import lombok.Data;

@Data
public class ReportRequestDto {

    private Long reportPersonId;

    private String description;
}
