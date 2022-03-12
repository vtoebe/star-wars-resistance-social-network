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
import br.com.letscode.stwars.service.validators.GetPersonServiceValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final PersonRepository personRepository;
    private final RebellionRepository rebellionRepository;
    static final int TRAITOR_VALIDATOR  = 3;
    private final GetPersonServiceValidator getPersonServiceValidator;
    public void reportPerson(ReportRequestDto request, Long personId) {
        //todo validar se os ids são iguais
        RebellionEntity rebellion = new RebellionEntity();

        RebellionId rebellionId = new RebellionId();
        // orElseThrow( EntityNotFound )
       PersonEntity reportedByPerson = personRepository.findById(personId).get();
        getPersonServiceValidator.validate(reportedByPerson);
        rebellionId.setReportByPerson(reportedByPerson);


        PersonEntity personReported = personRepository.findById(request.getReportPersonId()).get();
        getPersonServiceValidator.validateSameId(reportedByPerson, personReported);
        rebellionId.setPerson(personReported);
        rebellion.setId(rebellionId);
        rebellion.setDescription(request.getDescription());

        rebellionRepository.save(rebellion);

        // todo - Marcação do status de traição no person.
        // todo tranformar em static o número 3
        if (rebellionRepository.countReport(personReported.getId()) > TRAITOR_VALIDATOR) {
            personReported.setFaction(FactionEnum.EMPIRE);
            personRepository.save(personReported);
        }


    }
}