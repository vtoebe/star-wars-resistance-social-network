package br.com.letscode.stwars.service;

import br.com.letscode.stwars.model.BaseEntity;
import br.com.letscode.stwars.repository.BaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BaseService {
    private final BaseRepository baseRepository;
    // todo
    public BaseEntity getBase(String name) {
        return baseRepository.findById(name)
                .get(); // remover
        // Todo criar Exception
        //.orElseThrow( () -> new());
    }
}