package br.com.letscode.stwars.service;

import br.com.letscode.stwars.dto.MarketPlaceDto;
import br.com.letscode.stwars.mapper.ItemMapper;
import br.com.letscode.stwars.model.ItemsEntity;
import br.com.letscode.stwars.model.MarketPlaceEntity;
import br.com.letscode.stwars.model.PersonEntity;
import br.com.letscode.stwars.repository.BaseRepository;
import br.com.letscode.stwars.repository.ItemsRepository;
import br.com.letscode.stwars.repository.MarketPlaceRepository;
import br.com.letscode.stwars.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MarketPlaceService {

    private ItemMapper mapper;
    private BaseRepository baseRepository;
    private MarketPlaceRepository repository;
    private PersonRepository personRepository;
    private ItemsRepository itemsRepository;

    public void insertNewOffer(MarketPlaceDto request) {
        // todo - exception
        Optional<PersonEntity> person = personRepository.findById(request.getIdPerson());
                //.orElseThrow()

        MarketPlaceEntity marketPlaceEntity = new MarketPlaceEntity();

        //todo caso não encontre
        marketPlaceEntity.setBase(baseRepository.findById(request.getBase()).get());
        marketPlaceEntity.setOfferedBy(person.get());


        ItemsEntity receive = mapper.toEntity(request.getReceive());
        ItemsEntity offer = mapper.toEntity(request.getOffer());

        itemsRepository.save(receive);
        itemsRepository.save(offer);

        marketPlaceEntity.setReceive(receive);
        marketPlaceEntity.setOffer(offer);

        repository.save(marketPlaceEntity);
    }
}
