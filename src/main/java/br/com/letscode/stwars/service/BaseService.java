package br.com.letscode.stwars.service;

import br.com.letscode.stwars.dto.BaseDto;
import br.com.letscode.stwars.exceptions.BusinessValidationException;
import br.com.letscode.stwars.mapper.BaseMapper;
import br.com.letscode.stwars.model.BaseEntity;
import br.com.letscode.stwars.model.ValidationError;
import br.com.letscode.stwars.repository.BaseRepository;
import br.com.letscode.stwars.service.validators.BaseServiceValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BaseService {
    private final BaseRepository baseRepository;
    private final BaseMapper mapper;
    private final BaseServiceValidator baseServiceValidator;

    public BaseEntity getBase(String base) {
        List<ValidationError> validationErrors = baseServiceValidator.validate(base);
        if (!validationErrors.isEmpty()) {
            throw new BusinessValidationException(validationErrors);
        }

        return baseRepository.getById(base);
    }

    public List<BaseDto> getAllBases() {
        return baseRepository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }
}