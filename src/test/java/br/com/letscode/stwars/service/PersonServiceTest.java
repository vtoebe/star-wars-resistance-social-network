package br.com.letscode.stwars.service;

import br.com.letscode.stwars.dto.LocaleRequestDto;
import br.com.letscode.stwars.dto.PersonRequestDto;
import br.com.letscode.stwars.mapper.PersonMapper;
import br.com.letscode.stwars.model.*;
import br.com.letscode.stwars.repository.PersonRepository;
import br.com.letscode.stwars.service.validators.GetPersonServiceValidator;
import br.com.letscode.stwars.service.validators.InsertPersonServiceValidator;
import br.com.letscode.stwars.service.validators.UpdateLocaleValidator;
import br.com.letscode.stwars.utils.EntityUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
class PersonServiceTest {

    public static final String PERSON_1 = "Person1";
    public static final long ID = 1L;
    @InjectMocks
    private PersonService personService;

    @Mock
    private PersonRepository personRepository;
    @Mock
    private PersonMapper mapper;
    @Mock
    private LocaleService localeService;
    @Mock
    private InsertPersonServiceValidator insertPersonServiceValidator;
    @Mock
    private UpdateLocaleValidator updateLocaleValidator;
    @Mock
    private GetPersonServiceValidator getPersonServiceValidator;

    @BeforeEach
    void setUp() {
    }

    List<ValidationError> listErrors = new ArrayList<>();

    @Test
    void testInsertPerson() {
        when(insertPersonServiceValidator.validate(any())).thenReturn(listErrors);
        Assertions.assertNotNull(EntityUtils.getInventory(getPersonEntity()));
        when(mapper.toEntity(any())).thenReturn(getPersonEntity());

        doNothing().when(localeService).saveLocale(getPersonRequestDto(), getPersonEntity());
        when(personRepository.save(any())).thenReturn(getPersonEntity());

        personService.insertPerson(getPersonRequestDto());
    }

    @Test
    void testUpdateLocale() {
        when(personRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(new PersonEntity()));

        when(updateLocaleValidator.validate(any())).thenReturn(listErrors);

        doNothing().when(localeService).updateLocale(getLocaleRequestDto(), getPersonEntity());
        when(personRepository.save(any())).thenReturn(getPersonEntity());

        personService.updateLocale(getLocaleRequestDto(), getPersonEntity().getId());
    }

    @Test
    void testGetListByPerson() {
        List<PersonEntity> listPerson = new ArrayList<>();
        listPerson.add(getPersonEntity());
        when(personRepository.findAll()).thenReturn(listPerson);

        List<PersonEntity> response = personService.getListByPerson();
        assertNotNull(response);
        assertEquals(PersonEntity.class, response.get(0).getClass());
        assertEquals(PERSON_1, response.get(0).getName());
    }

    @Test
    void testGetPersonById() {
        when(personRepository.findById(Mockito.anyLong())).thenReturn(getOptionalPersonEntity());
        when(getPersonServiceValidator.validate(any())).thenReturn(listErrors);
        PersonEntity response = personService.getPersonById(ID);
        assertNotNull(response);
        assertEquals(PersonEntity.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(PERSON_1, response.getName());
    }

    @Test //NÃO ESTA SENDO UTILIZADO
    void testGetPersonInventory() {
        when(personRepository.findById(anyLong())).thenReturn(getOptionalPersonEntity());
        when(getPersonServiceValidator.validate(any())).thenReturn(listErrors);

        InventoryEntity response = personService.getPersonInventory(ID);
        assertNotNull(response);
        assertEquals(InventoryEntity.class, response.getClass());
        assertEquals(ID, response.getId());
    }

    @Test
    void testAddItemToInventory() {
        PersonEntity response = personService.addItemToInventory(getPersonEntity(), getItemsEntity());
        assertNotNull(response);
    }

    @Test
    void testRemoveItemToInventory() {
        PersonEntity response = personService.removeItemFromInventory(getPersonEntity(), getItemsEntity());
        assertNotNull(response);
    }

    @Test
    void testUpdateInventory() {
        PersonEntity response = personService.updateInventory(getPersonEntity(), getItemsEntity(), 1);
        assertNotNull(response);
    }

    private PersonEntity getPersonEntity() {
        PersonEntity personEntity = new PersonEntity();

        personEntity.setId(10L);
        personEntity.setName(PERSON_1);

        InventoryEntity inventoryEntity = new InventoryEntity();
        inventoryEntity.setId(1L);
        inventoryEntity.setPerson(personEntity);
        personEntity.setInventory(inventoryEntity);

        LocaleEntity localeEntity = new LocaleEntity();
        localeEntity.setId(1L);
        localeEntity.setPerson(personEntity);
        personEntity.setLocale(localeEntity);

        ItemsEntity itemsEntity = new ItemsEntity();
        itemsEntity.setId(2L);
        itemsEntity.setWaters(1);
        itemsEntity.setAmmunitions(1);
        itemsEntity.setFoods(1);
        itemsEntity.setWeapons(1);
        inventoryEntity.setItems(itemsEntity);

        return personEntity;
    }

    private PersonRequestDto getPersonRequestDto() {
        PersonRequestDto personRequestDto = new PersonRequestDto();
        personRequestDto.setName(PERSON_1);
        return personRequestDto;
    }

    private LocaleRequestDto getLocaleRequestDto() {
        LocaleRequestDto localeRequestDto = new LocaleRequestDto();
        localeRequestDto.setLatitude("123");
        localeRequestDto.setLongitude("456");
        return localeRequestDto;
    }

    private Optional<PersonEntity> getOptionalPersonEntity() {
        Optional<PersonEntity> optionalPersonEntity = Optional.of(new PersonEntity());
        optionalPersonEntity.get().setId(ID);
        optionalPersonEntity.get().setName(PERSON_1);

        optionalPersonEntity.get().setInventory(getInventoryEntity());

        return optionalPersonEntity;
    }

    private InventoryEntity getInventoryEntity() {
        InventoryEntity inventory = new InventoryEntity();
        inventory.setId(ID);
        return inventory;
    }

    private ItemsEntity getItemsEntity() {
        ItemsEntity items = new ItemsEntity();
        items.setId(ID);
        items.setWaters(1);
        items.setWeapons(1);
        items.setFoods(1);
        items.setAmmunitions(1);
        return items;
    }


}