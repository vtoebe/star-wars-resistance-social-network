package br.com.letscode.stwars.controller;

import br.com.letscode.stwars.dto.BaseDto;
import br.com.letscode.stwars.service.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/base")
public class BaseController {

    private final BaseService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BaseDto> getAllBase() {
        return service.getAllBases();
    }
}
