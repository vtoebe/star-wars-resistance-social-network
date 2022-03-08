package br.com.letscode.stwars.service;

import br.com.letscode.stwars.dto.BaseDto;
import br.com.letscode.stwars.mapper.BaseMapper;
import br.com.letscode.stwars.model.BaseEntity;
import br.com.letscode.stwars.repository.BaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BaseService {

    private final BaseRepository baseRepository;
    private final BaseMapper mapper;

    // todo
    public BaseEntity getBase(String name) {
        return baseRepository.findById(name)
                .get(); // remover
        // Todo criar Exception
        //.orElseThrow( () -> new());
    }

    public List<BaseDto> getAllBases() {
        return baseRepository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }
}