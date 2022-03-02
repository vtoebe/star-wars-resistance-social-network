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

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final RebellionRepository rebellionRepository;
    private final BaseRepository baseRepository;
    private final PersonMapper personMapper;

    public void testRepository(){
        Optional<PersonEntity> personEntity = personRepository.findById(5L);
        System.out.println("test repo");
    }

    public void insertPerson(PersonRequestDto request) {
        PersonEntity person = personMapper.toEntity(request);

        // Config de Relecionamento
        InventoryEntity inventory = EntityUtils.getInventory(person);
        inventory.setPerson(person);

        LocaleEntity locale = EntityUtils.getLocale(person);
        locale.setPerson(person);

        locale.setBase(this.getBase(request.getBase()));

        personRepository.save(person);
    }

    // todo
    private BaseEntity getBase(String name) {
        return baseRepository.findById(name)
                .get(); // remover
                // Todo criar Exception
                //.orElseThrow( () -> new());
    }

    public void updateLocale(LocaleRequestDto request, Long personId) {
        PersonEntity person = personRepository.findById(personId).get(); //todo -> orElseThrow( EntityNotFound )

        LocaleEntity locale = EntityUtils.getLocale(person);

        locale.setLatitude(request.getLatitude());
        locale.setLongitude(request.getLongitude());
        locale.setBase(this.getBase(request.getBase()));

        personRepository.save(person);
    }

    public void reportPerson(ReportRequestDto request, Long personId) {
        //todo validar se os ids são iguais
        RebellionEntity rebellion = new RebellionEntity();

        RebellionId rebellionId = new RebellionId();
        //todo -> orElseThrow( EntityNotFound )
        rebellionId.setReportByPerson(personRepository.findById(personId).get());

        PersonEntity personReport = personRepository.findById(request.getReportPersonId()).get();
        rebellionId.setPerson(personReport);

        rebellion.setId(rebellionId);
        rebellion.setDescription(request.getDescription());

        rebellionRepository.save(rebellion);

        // todo - Marcação do status de traição no person.
        // todo tranformar em static o número 3
        if (rebellionRepository.countReport(personReport.getId()) > 3 ) {
            personReport.setFaction(FactionEnum.EMPIRE);
            personRepository.save(personReport);
        };


    }



}
