package br.com.letscode.stwars.service;

import br.com.letscode.stwars.dto.LocaleRequestDto;
import br.com.letscode.stwars.dto.PersonRequestDto;
import br.com.letscode.stwars.model.*;
import br.com.letscode.stwars.repository.LocationRepository;
import br.com.letscode.stwars.utils.EntityUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class LocaleServiceTest {

    @InjectMocks
    private LocaleService localeService;

    @Mock
    private LocationRepository locationRepository;
    @Mock
    private BaseService baseService;

    @Test
    void testSaveLocale() {
        Assertions.assertNotNull(EntityUtils.getLocale(getPersonEntity()));
        when(locationRepository.save(any())).thenReturn(getLocaleHistoryEntity());
        localeService.saveLocale( getPersonRequestDto() ,getPersonEntity());
    }


    @Test
    void testUpdateLocale(){
        Assertions.assertNotNull(EntityUtils.getLocale(getPersonEntity()));
        when(locationRepository.save(any())).thenReturn(getLocaleHistoryEntity());
        localeService.updateLocale(getLocaleRequestDto(), getPersonEntity());
    }

    @Test
    void when_saveToLocationHistory_thenReturn_void(){
        when(locationRepository.save(any())).thenReturn(getLocaleHistoryEntity());
        localeService.saveToLocationHistory(getPersonEntity(), getLocaleEntity());
    }


    private PersonRequestDto getPersonRequestDto() {
        PersonRequestDto personRequestDto = new PersonRequestDto();
        personRequestDto.setName("Person1");

        return personRequestDto;
    }


    private PersonEntity getPersonEntity() {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setId(10L);

        InventoryEntity inventoryEntity = new InventoryEntity();
        inventoryEntity.setId(1L);
        inventoryEntity.setPerson(personEntity);
        personEntity.setInventory(inventoryEntity);

        LocaleEntity localeEntity = new LocaleEntity();
        localeEntity.setId(1L);
        localeEntity.setPerson(personEntity);
        personEntity.setLocale(localeEntity);

        return personEntity;
    }

    private LocationHistoryEntity getLocaleHistoryEntity() {
        LocationHistoryEntity locationHistoryEntity = new LocationHistoryEntity();
        locationHistoryEntity.setId(1L);
        return locationHistoryEntity;
    }


    private LocaleEntity getLocaleEntity() {
        LocaleEntity localeEntity = new LocaleEntity();
        localeEntity.setPerson(getPersonEntity());
        localeEntity.setId(1L);
        return localeEntity;
    }

    private LocaleRequestDto getLocaleRequestDto() {
        LocaleRequestDto localeRequestDto = new LocaleRequestDto();
        localeRequestDto.setBase("base");
        localeRequestDto.setLongitude("123");
        localeRequestDto.setLatitude("456");
        return localeRequestDto;
    }
}