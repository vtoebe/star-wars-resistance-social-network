package br.com.letscode.stwars.service;

import br.com.letscode.stwars.dto.MarketPlaceDto;
import br.com.letscode.stwars.enums.FactionEnum;
import br.com.letscode.stwars.enums.ItemsEnum;
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

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MarketPlaceService {

    private final ItemMapper mapper;
    private final BaseRepository baseRepository;
    private final MarketPlaceRepository repository;
    private final PersonRepository personRepository;
    private final ItemsRepository itemsRepository;

    public void insertNewOffer(MarketPlaceDto request) {
        // todo - exception
        Optional<PersonEntity> person = personRepository.findById(request.getIdPerson());
        //.orElseThrow()

        //todo -> if (person.get().getFaction().equals(FactionEnum.EMPIRE)) Throw;

        //todo remover os itens do inventario !
        // entity.food - request.food (Caso for negativo, apitar erro).

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

        marketPlaceEntity.setPoints(ItemsEnum.getTotalPoints(offer));

        repository.save(marketPlaceEntity);
    }

    public List<MarketPlaceEntity> getListByMarketPlace() {
        return repository.findAll();
    }
}
