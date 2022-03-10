package br.com.letscode.stwars.service;

import br.com.letscode.stwars.dto.ReportRequestDto;
import br.com.letscode.stwars.enums.FactionEnum;
import br.com.letscode.stwars.model.PersonEntity;
import br.com.letscode.stwars.model.RebellionEntity;
import br.com.letscode.stwars.model.RebellionId;
import br.com.letscode.stwars.repository.PersonRepository;
import br.com.letscode.stwars.repository.RebellionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final PersonRepository personRepository;
    private final RebellionRepository rebellionRepository;

    private final static int TRAITOR_VALIDATOR = 3;

    public void reportPerson(ReportRequestDto request, Long personId) {
        //todo validar se os ids são iguais
        RebellionEntity rebellion = new RebellionEntity();

        RebellionId rebellionId = new RebellionId();
        //todo -> orElseThrow( EntityNotFound )
        rebellionId.setReportByPerson(personRepository.findById(personId).get());

        PersonEntity personReport = personRepository.findById(request.getReportPersonId()).get();
        rebellionId.setPerson(personReport);

        rebellion.setId(rebellionId);
        rebellion.setDescription(request.getDescription());

        rebellionRepository.save(rebellion);

        // todo - Marcação do status de traição no person.
        // todo tranformar em static o número 3
        if (rebellionRepository.countReport(personReport.getId()) >= TRAITOR_VALIDATOR ) {
            personReport.setFaction(FactionEnum.EMPIRE);
            personRepository.save(personReport);
        }


    }
}