package br.com.letscode.stwars.service.validators;

import br.com.letscode.stwars.enums.MessageCodeEnum;
import br.com.letscode.stwars.model.PersonEntity;
import br.com.letscode.stwars.model.ValidationError;
import br.com.letscode.stwars.repository.PersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class UpdateLocaleValidatorTest {

    @InjectMocks
    private UpdateLocaleValidator updateLocaleValidator;
    @Mock
    private PersonRepository personRepository;

    @Test
    void testValidate() {
        List<ValidationError> response = updateLocaleValidator.validate(getPersonEntity());
        Assertions.assertNotNull(response);
    }

    @Test
    void testValidateNull() {
        List<ValidationError> response = updateLocaleValidator.validate(getPersonEntityNull());
        Assertions.assertEquals(MessageCodeEnum.RESOURCE_NOT_FOUND, response.get(0).getKeyMessage());
        Assertions.assertNotNull(response);
    }

    PersonEntity getPersonEntity() {
        PersonEntity person = new PersonEntity();
        return person;
    }

    PersonEntity getPersonEntityNull() {
        PersonEntity person = null;
        return person;
    }

}