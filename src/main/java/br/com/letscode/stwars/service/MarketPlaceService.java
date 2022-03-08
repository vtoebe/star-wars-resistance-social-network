package br.com.letscode.stwars.service;

import br.com.letscode.stwars.dto.MarketPlaceDto;
import br.com.letscode.stwars.enums.FactionEnum;
import br.com.letscode.stwars.enums.ItemsEnum;
import br.com.letscode.stwars.exceptions.BusinessValidationException;
import br.com.letscode.stwars.mapper.ItemMapper;
import br.com.letscode.stwars.model.*;
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

        if (person.get().getFaction().equals(FactionEnum.EMPIRE)){
            throw new BusinessValidationException(" You are not welcome in out MarketPlace (Person is a member from EMPIRE faction)");
        }

        ItemsEntity receive = mapper.toEntity(request.getReceive());
        ItemsEntity offer = mapper.toEntity(request.getOffer());

        // entity.food - request.food (Caso for negativo, apitar erro).
        int personAmmunitions = person.get().getInventory().getItems().getAmmunitions();
        int personWaters = person.get().getInventory().getItems().getWaters();
        int personFoods = person.get().getInventory().getItems().getFoods();
        int personWeapons = person.get().getInventory().getItems().getWeapons();

        if(
                offer.getAmmunitions() > personAmmunitions ||
                offer.getWaters() > personWaters ||
                offer.getFoods() > personFoods ||
                offer.getWeapons() > personWeapons
        ){
           throw new BusinessValidationException("You don't have enough points to do this transaction");
        }

        //Conferindo que pontos são iguais
        int pointsReceive = ItemsEnum.getTotalPoints(receive);
        int pointsOffer = ItemsEnum.getTotalPoints(offer);

        if(pointsReceive > pointsOffer){
            throw new BusinessValidationException("Your total offer has more points than needed to receive these items");
        } else if (pointsReceive < pointsOffer){
            throw new BusinessValidationException("Your total offer has less points than needed to receive these items");
        }


        MarketPlaceEntity marketPlaceEntity = new MarketPlaceEntity();

        //todo caso não encontre
        BaseEntity base = baseRepository.findById(request.getBase()).get();

        if(base != null){
            throw new BusinessValidationException("This base does not exists");
        } else marketPlaceEntity.setBase(base);

        marketPlaceEntity.setOfferedBy(person.get());

        itemsRepository.save(receive);
        itemsRepository.save(offer);

        //todo remover os itens do inventario !
        person.get().getInventory().getItems().setAmmunitions(personAmmunitions - offer.getAmmunitions());
        person.get().getInventory().getItems().setAmmunitions(personWaters - offer.getWaters());
        person.get().getInventory().getItems().setAmmunitions(personFoods - offer.getFoods());
        person.get().getInventory().getItems().setAmmunitions(personWeapons - offer.getWeapons());

        marketPlaceEntity.setReceive(receive);
        marketPlaceEntity.setOffer(offer);

        marketPlaceEntity.setPoints(pointsOffer);

        repository.save(marketPlaceEntity);
    }

    public List<MarketPlaceEntity> getListByMarketPlace() {
        return repository.findAll();
    }
}
