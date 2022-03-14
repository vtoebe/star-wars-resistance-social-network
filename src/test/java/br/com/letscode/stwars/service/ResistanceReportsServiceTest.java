package br.com.letscode.stwars.service;

import br.com.letscode.stwars.enums.FactionEnum;
import br.com.letscode.stwars.model.PersonEntity;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
class ResistanceReportsServiceTest {

    @InjectMocks
    private ResistanceReportsService resistanceReportsService;
    @Mock
    private PersonService personService;

    @Test
    void when_countRebelsTraitorsAndResources_then_countTotalRebels() {
        when(personService.getListByPerson()).thenReturn(getListPerson());
    }

    private List<PersonEntity> getListPerson() {
        List<PersonEntity> listPerson = new ArrayList<>();
        PersonEntity person = new PersonEntity();
        person.setFaction(FactionEnum.RESISTANCE);
        listPerson.add(person);
        return listPerson;
    }


}