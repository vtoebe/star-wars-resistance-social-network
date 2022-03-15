package br.com.letscode.stwars.service.validators;

import br.com.letscode.stwars.dto.PersonRequestDto;
import br.com.letscode.stwars.enums.MessageCodeEnum;
import br.com.letscode.stwars.model.ValidationError;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class InsertPersonServiceValidatorTest {

    @InjectMocks
    private InsertPersonServiceValidator insertPersonServiceValidator;

    @Test
    void testValidate(){
        List<ValidationError> response = insertPersonServiceValidator.validate(getPersonRequest());
        Assertions.assertNotNull(response);
    }

    @Test
    void testValidateNull(){
        List<ValidationError> response = insertPersonServiceValidator.validate(getPersonRequestNull());
        Assertions.assertEquals(MessageCodeEnum.REQUIRED_FIELD, response.get(0).getKeyMessage());
        Assertions.assertNotNull(response);
    }

    PersonRequestDto getPersonRequest() {
        PersonRequestDto person = new PersonRequestDto();
        person.setBirth("Birth");
        person.setGenre("Genre");
        person.setName("Name");
        return person;
    }

    PersonRequestDto getPersonRequestNull() {
        PersonRequestDto person = null;
        return person;
    }



}