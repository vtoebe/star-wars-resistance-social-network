package br.com.letscode.stwars.controller;

import br.com.letscode.stwars.dto.LocaleRequestDto;
import br.com.letscode.stwars.dto.PersonRequestDto;
import br.com.letscode.stwars.model.PersonEntity;
import br.com.letscode.stwars.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;

    @GetMapping(value = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public PersonEntity getPersonById(@PathVariable(value = "id") Long id){
        return personService.getPersonById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PersonEntity> getListByPerson(){ return personService.getListByPerson(); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201 -> Objeto criado com sucesso.
    public void insertPerson(@RequestBody PersonRequestDto request) {
        personService.insertPerson(request);
    }

    @PutMapping("/{personId}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204 -> Sucesso, sem body
    public void updateLocale(@RequestBody LocaleRequestDto request, @PathVariable("personId") Long personId) {
        personService.updateLocale(request, personId);
    }

}
