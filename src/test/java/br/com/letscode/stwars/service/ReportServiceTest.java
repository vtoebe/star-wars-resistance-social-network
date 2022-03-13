package br.com.letscode.stwars.service;

import br.com.letscode.stwars.dto.PersonRequestDto;
import br.com.letscode.stwars.dto.ReportRequestDto;
import br.com.letscode.stwars.model.PersonEntity;
import br.com.letscode.stwars.model.RebellionEntity;
import br.com.letscode.stwars.model.RebellionId;
import br.com.letscode.stwars.repository.PersonRepository;
import br.com.letscode.stwars.repository.RebellionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
class ReportServiceTest {

    @InjectMocks
    private ReportService reportService;
    @Mock
    private PersonRepository personRepository;
    @Mock
    private RebellionRepository rebellionRepository;

    @Test
    void testReportPerson() {
        when(personRepository.findById(anyLong())).thenReturn(getOptionalPersonEntity());
        when(rebellionRepository.save(any())).thenReturn(getRebEntity());

        reportService.reportPerson(getReportRequestDto(), 1L);
    }

    private RebellionEntity getRebEntity() {
        RebellionEntity rebellionEntity = new RebellionEntity();

        rebellionEntity.setId(getRebId());
        return rebellionEntity;
    }

    private RebellionId getRebId() {
        RebellionId rebellionId = new RebellionId();

        PersonEntity reportPerson = new PersonEntity();
        rebellionId.setPerson(reportPerson);

        PersonEntity reportedPerson = new PersonEntity();
        rebellionId.setReportByPerson(reportedPerson);

        return rebellionId;
    }

    private Optional<PersonEntity> getOptionalPersonEntity() {
        Optional<PersonEntity> optionalPersonEntity = Optional.of(new PersonEntity());
        optionalPersonEntity.get().setId(1L);
        return optionalPersonEntity;
    }

    private ReportRequestDto getReportRequestDto() {
        ReportRequestDto reportRequestDto = new ReportRequestDto();
        reportRequestDto.setReportPersonId(10L);
        return reportRequestDto;
    }


}