package br.com.letscode.stwars.service;

import br.com.letscode.stwars.dto.BaseDto;
import br.com.letscode.stwars.mapper.BaseMapper;
import br.com.letscode.stwars.model.BaseEntity;
import br.com.letscode.stwars.repository.BaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BaseService {

    private final BaseRepository baseRepository;
    private final BaseMapper mapper;

    // NÃO ESTA SENDO UTILIZADO -> REMOVER??

    public BaseEntity getBase(String name) {
        Optional<BaseEntity> base = baseRepository.findById(name);
        return base.orElseThrow(() -> new RuntimeException(""));
        //todo - Exception -> ObjectNotFound
    }

    // NÃO ESTA SENDO UTILIZADO -> REMOVER??


    public List<BaseDto> getAllBases() {
        return baseRepository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }
}