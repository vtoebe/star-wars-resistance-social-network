package br.com.letscode.stwars.service;

import br.com.letscode.stwars.dto.MarketPlaceDto;
import br.com.letscode.stwars.dto.PersonIdDto;
import br.com.letscode.stwars.exceptions.BusinessValidationException;
import br.com.letscode.stwars.mapper.ItemMapper;
import br.com.letscode.stwars.model.*;
import br.com.letscode.stwars.repository.BaseRepository;
import br.com.letscode.stwars.repository.ItemsRepository;
import br.com.letscode.stwars.repository.MarketPlaceRepository;
import br.com.letscode.stwars.repository.PersonRepository;
import br.com.letscode.stwars.validators.OfferValidator;
import br.com.letscode.stwars.validators.TradeValidators;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MarketPlaceService {

    private final ItemMapper mapper;
    private final BaseRepository baseRepository;
    private final MarketPlaceRepository marketplaceRepository;
    private final PersonRepository personRepository;
    private final ItemsRepository itemsRepository;
    private final PersonService personService;
    private final OfferValidator offerValidator;
    private final TradeValidators tradeValidators;

    public void insertNewOffer(MarketPlaceDto request) {

        // todo - exception
        Optional<PersonEntity> person = personRepository.findById(request.getIdPerson());
        //.orElseThrow()
        offerValidator.factionValidation(person);
        ItemsEntity offer = mapper.toEntity(request.getOffer());
        ItemsEntity receive = mapper.toEntity(request.getReceive());
        offerValidator.itemQuantityValidation(person, offer);
        int pointsOffer = offerValidator.pointsValidation(receive, offer);
        MarketPlaceEntity marketPlaceEntity = new MarketPlaceEntity();

        //todo caso não encontre
        BaseEntity base = baseRepository.findById(request.getBase()).get();
        offerValidator.baseExistsValidation(marketPlaceEntity, base);
        marketPlaceEntity.setOfferedBy(person.get());
        itemsRepository.save(receive);
        itemsRepository.save(offer);
        // removes offered items from rebel's inventory
        personService.removeItemFromInventory(person.get(), offer);
        marketPlaceEntity.setReceive(receive);
        marketPlaceEntity.setOffer(offer);
        marketPlaceEntity.setPoints(pointsOffer);
        marketplaceRepository.save(marketPlaceEntity);
    }
    public List<MarketPlaceEntity> getListByMarketPlace() {
        return marketplaceRepository.findAll();
    }

    public void tradeItems(PersonIdDto receiverId, Long offerId) {
        MarketPlaceEntity marketPlaceEntity = marketplaceRepository.getById(offerId);
        tradeValidators.offerExistsValidation(marketPlaceEntity);

        PersonEntity offerBy = personRepository.getById(marketPlaceEntity.getOfferedBy().getId());
        tradeValidators.offerByExists(offerBy);

        PersonEntity receiver = personRepository.getById(receiverId.getId());
        tradeValidators.receiverExistsValidation(receiver);
        tradeValidators.sameBaseValidation(receiver, offerBy);

        personService.addItemToInventory(receiver, marketPlaceEntity.getOffer());
        personService.addItemToInventory(offerBy, marketPlaceEntity.getReceive());
        personService.removeItemFromInventory(receiver, marketPlaceEntity.getReceive());
        personRepository.save(receiver);
        personRepository.save(offerBy);

        marketplaceRepository.delete(marketPlaceEntity);
    }
}
