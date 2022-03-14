package br.com.letscode.stwars.controller;

import br.com.letscode.stwars.model.ResistanceReportEntity;
import br.com.letscode.stwars.service.ResistanceReportsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/resistance-reports")
public class ResistanceReportController {

    private final ResistanceReportsService resistanceReportsService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResistanceReportEntity getResistanceReport(){
        return resistanceReportsService.getResistanceReport();
    }
}
