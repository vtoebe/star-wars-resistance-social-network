package br.com.letscode.stwars.controller;

import br.com.letscode.stwars.dto.ReportRequestDto;
import br.com.letscode.stwars.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/{personId}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204 -> Sucesso, sem body
    public void reportPerson(@RequestBody ReportRequestDto request, @PathVariable("personId") Long personId) {
        reportService.reportPerson(request, personId);
    }
}