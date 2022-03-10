package br.com.letscode.stwars.service;

import br.com.letscode.stwars.dto.LocaleRequestDto;
import br.com.letscode.stwars.dto.PersonRequestDto;
import br.com.letscode.stwars.enums.MessageCodeEnum;
import br.com.letscode.stwars.exceptions.BusinessValidationException;
import br.com.letscode.stwars.mapper.PersonMapper;
import br.com.letscode.stwars.model.*;
import br.com.letscode.stwars.repository.PersonRepository;
import br.com.letscode.stwars.service.validators.GetPersonServiceValidator;
import br.com.letscode.stwars.service.validators.InsertPersonServiceValidator;
import br.com.letscode.stwars.utils.EntityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;
    private final LocaleService localeService;
    private final InsertPersonServiceValidator insertPersonServiceValidator;

    public void insertPerson(PersonRequestDto request) {
        List<ValidationError> validationErrors = insertPersonServiceValidator.validate(request);
        if (!validationErrors.isEmpty()) {
            throw new BusinessValidationException(validationErrors);
        }
        PersonEntity person = personMapper.toEntity(request);

        // Config de Relecionamento
        InventoryEntity inventory = EntityUtils.getInventory(person);
        inventory.setPerson(person);

        localeService.saveLocale(request, person);
        personRepository.save(person);
    }

    public void updateLocale(LocaleRequestDto request, Long personId) {
        PersonEntity person = personRepository.findById(personId)
                .orElseThrow(
                () -> new BusinessValidationException(
                ValidationError.builder()
                            .keyMessage(MessageCodeEnum.RESOURCE_NOT_FOUND)
                            .params(List.of(MessageCodeEnum.PERSON_ID_FIELD))
                            .build()));

        localeService.updateLocale(request, person);
        personRepository.save(person);
    }

    public List<PersonEntity> getListByPerson(){
        return personRepository.findAll();
    }

    public PersonEntity getPersonById(Long id) {
        return personRepository.findById(id).orElseThrow(
                () -> new BusinessValidationException(
                        ValidationError.builder()
                                .keyMessage(MessageCodeEnum.RESOURCE_NOT_FOUND)
                                .params(List.of(MessageCodeEnum.PERSON_ID_FIELD))
                                .build()));
    }

    public InventoryEntity getPersonInventory(Long id){
        return personRepository.findById(id).orElseThrow(
                () -> new BusinessValidationException(
                        ValidationError.builder()
                                .keyMessage(MessageCodeEnum.RESOURCE_NOT_FOUND)
                                .params(List.of(MessageCodeEnum.PERSON_ID_FIELD))
                                .build())).getInventory();
    }

}