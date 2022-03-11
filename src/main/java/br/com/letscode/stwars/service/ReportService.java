package br.com.letscode.stwars.service;

import br.com.letscode.stwars.dto.ReportRequestDto;
import br.com.letscode.stwars.enums.FactionEnum;
import br.com.letscode.stwars.enums.MessageCodeEnum;
import br.com.letscode.stwars.exceptions.BusinessValidationException;
import br.com.letscode.stwars.model.PersonEntity;
import br.com.letscode.stwars.model.RebellionEntity;
import br.com.letscode.stwars.model.RebellionId;
import br.com.letscode.stwars.model.ValidationError;
import br.com.letscode.stwars.repository.PersonRepository;
import br.com.letscode.stwars.repository.RebellionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final PersonRepository personRepository;
    private final RebellionRepository rebellionRepository;
    static final int TRAITOR_VALIDATOR  = 3;
    public void reportPerson(ReportRequestDto request, Long personId) {
        //todo validar se os ids são iguais
        RebellionEntity rebellion = new RebellionEntity();

        RebellionId rebellionId = new RebellionId();
        // orElseThrow( EntityNotFound )
        rebellionId.setReportByPerson(personRepository.findById(personId).orElseThrow(
                () -> new BusinessValidationException(ValidationError.builder().keyMessage(MessageCodeEnum.RESOURCE_NOT_FOUND).params(List.of(MessageCodeEnum.PERSON_ID_FIELD, personId)).build())
        ));

        PersonEntity personReport = personRepository.findById(request.getReportPersonId()).get();
        rebellionId.setPerson(personReport);
        if(rebellionId.getPerson() == rebellionId.getReportByPerson()) {
            throw new BusinessValidationException(ValidationError.builder().keyMessage(MessageCodeEnum.SAME_ID).params(List.of(MessageCodeEnum.PERSON_ID_FIELD, rebellionId.getPerson(),rebellionId.getReportByPerson())).build());
        }
        rebellion.setId(rebellionId);
        rebellion.setDescription(request.getDescription());

        rebellionRepository.save(rebellion);

        // todo - Marcação do status de traição no person.
        // todo tranformar em static o número 3
        if (rebellionRepository.countReport(personReport.getId()) > TRAITOR_VALIDATOR) {
            personReport.setFaction(FactionEnum.EMPIRE);
            personRepository.save(personReport);
        }


    }
}