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
import br.com.letscode.stwars.service.validators.UpdateLocaleValidator;

import br.com.letscode.stwars.utils.EntityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;
    private final LocaleService localeService;
    private final InsertPersonServiceValidator insertPersonServiceValidator;
    private final UpdateLocaleValidator updateLocaleValidator;
    private final GetPersonServiceValidator getPersonServiceValidator;

    @Transactional
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

    @Transactional
    public void updateLocale(LocaleRequestDto request, Long personId) {
        PersonEntity person = personRepository.findById(personId).get();
        updateLocaleValidator.validate(person);


        localeService.updateLocale(request, person);
        personRepository.save(person);
    }

    public List<PersonEntity> getListByPerson(){
        return personRepository.findAll();
    }

    public PersonEntity getPersonById(Long id) {
        PersonEntity person = personRepository.findById(id).get();
        getPersonServiceValidator.validate(person);
        return person;
    }

    // TODO: check return -> PersonEntity
    //NÃO ESTA SENDO UTILIZADO
    public InventoryEntity getPersonInventory(Long id){
        PersonEntity person = personRepository.findById(id).get();
                getPersonServiceValidator.validate(person);
                return person.getInventory();
    }
  
    public PersonEntity addItemToInventory(PersonEntity person, ItemsEntity items){
        return updateInventory(person, items, 1);
    }

    public PersonEntity removeItemFromInventory(PersonEntity person, ItemsEntity items){
        return updateInventory(person, items, -1);
    }

    public PersonEntity updateInventory(PersonEntity person, ItemsEntity items, int operator){
        int ammo = person.getInventory().getItems().getAmmunitions();
        int weapons = person.getInventory().getItems().getWeapons();
        int waters = person.getInventory().getItems().getWaters();
        int foods = person.getInventory().getItems().getFoods();

        person.getInventory().getItems().setWeapons(weapons + (items.getWeapons() * operator));
        person.getInventory().getItems().setAmmunitions(ammo + (items.getAmmunitions() * operator));
        person.getInventory().getItems().setWaters(waters + (items.getWaters() * operator));
        person.getInventory().getItems().setFoods(foods + (items.getFoods() * operator));

        return person;
    }
}