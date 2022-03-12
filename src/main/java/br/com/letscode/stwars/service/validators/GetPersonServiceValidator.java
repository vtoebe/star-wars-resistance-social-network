package br.com.letscode.stwars.service.validators;
import br.com.letscode.stwars.dto.LocaleRequestDto;
import br.com.letscode.stwars.enums.MessageCodeEnum;
import br.com.letscode.stwars.exceptions.BusinessValidationException;
import br.com.letscode.stwars.model.PersonEntity;
import br.com.letscode.stwars.model.RebellionId;
import br.com.letscode.stwars.model.ValidationError;
import br.com.letscode.stwars.repository.PersonRepository;
import br.com.letscode.stwars.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GetPersonServiceValidator {
    private final PersonRepository personRepository;
    public List<ValidationError> validate(PersonEntity rebel) {
        List<ValidationError> validationErrors = new ArrayList<>();
        if (!personRepository.findById(rebel.getId()).isPresent()) {
            validationErrors.add(
                    ValidationError.builder()
                            .keyMessage(MessageCodeEnum.RESOURCE_NOT_FOUND)
                            .params(List.of(MessageCodeEnum.PERSON_ID_FIELD, rebel.getId()))
                            .build());
        }

        return validationErrors;
    }
    public List<ValidationError> validateSameId(PersonEntity reporterRebel, PersonEntity reportedRebel) {
        List<ValidationError> validationErrors = new ArrayList<>();
        if(reporterRebel == reportedRebel) {
            ValidationError.builder().keyMessage(MessageCodeEnum.SAME_ID).params(List.of(MessageCodeEnum.PERSON_ID_FIELD, reportedRebel,reporterRebel)).build();
        }

        return validationErrors;
    }

}

