package br.com.letscode.stwars.service.validators;

import br.com.letscode.stwars.enums.FactionEnum;
import br.com.letscode.stwars.model.PersonEntity;
import br.com.letscode.stwars.model.ValidationError;
import br.com.letscode.stwars.repository.PersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
class GetPersonServiceValidatorTest {

    @InjectMocks
    private GetPersonServiceValidator getPersonServiceValidator;
    @Mock
    private PersonRepository personRepository;

    @Test
    void testValidate() {
        when(personRepository.findById(Mockito.any())).thenReturn(Optional.of(getPersonEntity()));
        List<ValidationError> response =  getPersonServiceValidator.validate(getPersonEntity());
        Assertions.assertNotNull(response);
    }

    @Test
    void testValidateNull() {
        when(personRepository.findById(Mockito.any())).thenReturn(Optional.of(getRebelPersonEntity()));
        List<ValidationError> response = getPersonServiceValidator.validate(getPersonEntity());
        Assertions.assertNotNull(response);
    }

        @Test
    void testValidateSameId() {
        List<ValidationError> response = getPersonServiceValidator.validateSameId(getPersonEntity(), getRebelPersonEntity());
        Assertions.assertNotNull(response);
    }

    PersonEntity getPersonEntity() {
        PersonEntity person = new PersonEntity();
        return person;
    }

    PersonEntity getRebelPersonEntity() {
        PersonEntity person = new PersonEntity();
        person.setFaction(FactionEnum.EMPIRE);
        return person;
    }
}