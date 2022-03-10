package br.com.letscode.stwars.service;

import br.com.letscode.stwars.dto.AcceptOfferDto;
import br.com.letscode.stwars.dto.MarketPlaceDto;
import br.com.letscode.stwars.enums.FactionEnum;
import br.com.letscode.stwars.enums.ItemsEnum;
import br.com.letscode.stwars.enums.MessageCodeEnum;
import br.com.letscode.stwars.exceptions.BusinessValidationException;
import br.com.letscode.stwars.mapper.ItemMapper;
import br.com.letscode.stwars.model.*;
import br.com.letscode.stwars.repository.*;
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
    private final AcceptOfferRepository acceptOfferRepository;

    public void insertNewOffer(MarketPlaceDto request) {

        // exception
        Optional<PersonEntity> person = Optional.ofNullable(personRepository.findById(request.getIdPerson()).orElseThrow(
                () -> new BusinessValidationException(ValidationError.builder()
                        .keyMessage(MessageCodeEnum.RESOURCE_NOT_FOUND)
                        .params(List.of(MessageCodeEnum.PERSON_ID_FIELD))
                        .build())));

        if (person.get().getFaction().equals(FactionEnum.EMPIRE)){
            throw new BusinessValidationException(ValidationError.builder()
                                    .keyMessage(MessageCodeEnum.NOT_ALLOWED)
                                    .params(List.of(MessageCodeEnum.FACTION_FIELD))
                                    .build());
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
           throw new BusinessValidationException((ValidationError.builder()
                   .keyMessage(MessageCodeEnum.NOT_ENOUGH_POINTS)
                   .params(List.of(MessageCodeEnum.ITEMS_FIELD))
                   .build()));
        }

        //Conferindo que pontos são iguais
        int pointsReceive = ItemsEnum.getTotalPoints(receive);
        int pointsOffer = ItemsEnum.getTotalPoints(offer);

        if(pointsReceive > pointsOffer){
            throw new BusinessValidationException((ValidationError.builder()
                    .keyMessage(MessageCodeEnum.MORE_THAN_NEEDED)
                    .params(List.of(MessageCodeEnum.ITEMS_FIELD))
                    .build()));
        } else if (pointsReceive < pointsOffer){
            throw new BusinessValidationException((ValidationError.builder()
                    .keyMessage(MessageCodeEnum.LESS_THAN_NEEDED)
                    .params(List.of(MessageCodeEnum.ITEMS_FIELD))
                    .build()));
        }


        MarketPlaceEntity marketPlaceEntity = new MarketPlaceEntity();

        //todo caso não encontre
        BaseEntity base = baseRepository.findById(request.getBase()).get();

        if(base == null){
            throw new BusinessValidationException(ValidationError.builder()
                    .keyMessage(MessageCodeEnum.RESOURCE_NOT_FOUND)
                    .params(List.of(MessageCodeEnum.BASE_FIELD))
                    .build());
        } else marketPlaceEntity.setBase(base);

        marketPlaceEntity.setOfferedBy(person.get());

        itemsRepository.save(receive);
        itemsRepository.save(offer);

        //todo remover os itens do inventario !
        person.get().getInventory().getItems().setAmmunitions(personAmmunitions - offer.getAmmunitions());
        person.get().getInventory().getItems().setWaters(personWaters - offer.getWaters());
        person.get().getInventory().getItems().setFoods(personFoods - offer.getFoods());
        person.get().getInventory().getItems().setWeapons(personWeapons - offer.getWeapons());

        marketPlaceEntity.setReceive(receive);
        marketPlaceEntity.setOffer(offer);

        marketPlaceEntity.setPoints(pointsOffer);

        repository.save(marketPlaceEntity);
    }


    public List<MarketPlaceEntity> getListByMarketPlace() {
        return repository.findAll();
    }


    public void acceptOffer(AcceptOfferDto request, Long acceptPersonId){

        MarketPlaceEntity marketPlace = repository.findById(request.getIdMarketPlace()).get();

        PersonEntity requestPerson = personRepository.findById(request.getAcceptPersonId()).get();

        PersonEntity acceptPerson = personRepository.findById(acceptPersonId).get();

        if (acceptPerson.getFaction().equals(FactionEnum.EMPIRE)){
            throw new BusinessValidationException(ValidationError.builder()
                    .keyMessage(MessageCodeEnum.NOT_ALLOWED)
                    .params(List.of(MessageCodeEnum.FACTION_FIELD))
                    .build());
        }

        // entity.food - request.food (Caso for negativo, apitar erro).
        int personAmmunitions = acceptPerson.getInventory().getItems().getAmmunitions();
        int personWaters = acceptPerson.getInventory().getItems().getWaters();
        int personFoods = acceptPerson.getInventory().getItems().getFoods();
        int personWeapons = acceptPerson.getInventory().getItems().getWeapons();

        if(
                marketPlace.getReceive().getAmmunitions() > personAmmunitions ||
                marketPlace.getReceive().getWaters() > personWaters ||
                marketPlace.getReceive().getFoods() > personFoods ||
                marketPlace.getReceive().getWeapons() > personWeapons
        ){
            throw new BusinessValidationException((ValidationError.builder()
                    .keyMessage(MessageCodeEnum.NOT_ENOUGH_POINTS)
                    .params(List.of(MessageCodeEnum.ITEMS_FIELD))
                    .build()));        }

        //VERIFICANDO SE ESTAO NA MESMA BASE
        if(requestPerson.getLocale().getBase() != acceptPerson.getLocale().getBase()){
            throw new BusinessValidationException((ValidationError.builder()
                    .keyMessage(MessageCodeEnum.NOT_IN_SAME_BASE)
                    .params(List.of(MessageCodeEnum.BASE_FIELD))
                    .build()));
        }


        ItemsEntity receiveItems = marketPlace.getReceive();
        ItemsEntity offerItems = marketPlace.getOffer();


        //UPDATE REQUESTER INVENTORY --> QUEM SOLICITOU A TROCA
        int requestPersonWeapons = requestPerson.getInventory().getItems().getWeapons();
        int requestPersonAmmunitions = requestPerson.getInventory().getItems().getAmmunitions();
        int requestPersonFoods = requestPerson.getInventory().getItems().getFoods();
        int requestPersonWaters = requestPerson.getInventory().getItems().getWaters();

        int newWeapons = requestPersonWeapons + receiveItems.getWeapons();
        requestPerson.getInventory().getItems().setWeapons( newWeapons );

        int newAmmunitions = requestPersonAmmunitions + receiveItems.getAmmunitions();
        requestPerson.getInventory().getItems().setAmmunitions( newAmmunitions );

        int newFoods = requestPersonFoods + receiveItems.getFoods();
        requestPerson.getInventory().getItems().setFoods( newFoods );

        int newWaters = requestPersonWaters + receiveItems.getWaters();
        requestPerson.getInventory().getItems().setWaters( newWaters );


        //UPDATE ACCEPTED PERSON INVENTORY --> QUEM ACEITOU A TRANSAÇÃO
        int acceptPersonWeapons = acceptPerson.getInventory().getItems().getWeapons();
        int acceptPersonAmmunitions = acceptPerson.getInventory().getItems().getAmmunitions();
        int acceptPersonFoods = acceptPerson.getInventory().getItems().getFoods();
        int acceptPersonWaters = acceptPerson.getInventory().getItems().getWaters();


        acceptPerson.getInventory().getItems().setWeapons(acceptPersonWeapons - receiveItems.getWeapons());
        acceptPerson.getInventory().getItems().setAmmunitions(acceptPersonAmmunitions - receiveItems.getAmmunitions());
        acceptPerson.getInventory().getItems().setFoods(acceptPersonFoods - receiveItems.getFoods());
        acceptPerson.getInventory().getItems().setWaters(acceptPersonWaters  - receiveItems.getWaters());


        acceptPerson.getInventory().getItems().setWeapons(acceptPersonWeapons + offerItems.getWeapons());
        acceptPerson.getInventory().getItems().setAmmunitions(acceptPersonAmmunitions + offerItems.getAmmunitions());
        acceptPerson.getInventory().getItems().setFoods(acceptPersonFoods + offerItems.getFoods());
        acceptPerson.getInventory().getItems().setWaters(acceptPersonWaters + offerItems.getWaters());

        repository.delete(marketPlace);
    }
}
