package br.com.letscode.stwars.service;

import br.com.letscode.stwars.dto.LocaleRequestDto;
import br.com.letscode.stwars.dto.PersonRequestDto;
import br.com.letscode.stwars.dto.ReportRequestDto;
import br.com.letscode.stwars.enums.FactionEnum;
import br.com.letscode.stwars.mapper.PersonMapper;
import br.com.letscode.stwars.model.*;
import br.com.letscode.stwars.repository.BaseRepository;
import br.com.letscode.stwars.repository.PersonRepository;
import br.com.letscode.stwars.repository.RebellionRepository;
import br.com.letscode.stwars.utils.EntityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;
    private final LocaleService localeService;

    public void insertPerson(PersonRequestDto request) {
        PersonEntity person = personMapper.toEntity(request);

        // Config de Relecionamento
        InventoryEntity inventory = EntityUtils.getInventory(person);
        inventory.setPerson(person);

        localeService.saveLocale(request, person);
        personRepository.save(person);
    }

    public void updateLocale(LocaleRequestDto request, Long personId) {
        PersonEntity person = personRepository.findById(personId).get(); //todo -> orElseThrow( EntityNotFound )

        localeService.updateLocale(request, person);
        personRepository.save(person);
    }

    public List<PersonEntity> getListByPerson(){
        return personRepository.findAll();
    }

    public PersonEntity getPersonById(Long id) {
        return personRepository.findById(id).get();
    }
}