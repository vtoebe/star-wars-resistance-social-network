package br.com.letscode.stwars.service;

import br.com.letscode.stwars.dto.AcceptOfferDto;
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

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MarketPlaceService {

    private final ItemMapper mapper;
    private final BaseRepository baseRepository;

    private final PersonRepository personRepository;
    private final ItemsRepository itemsRepository;
    private final br.com.letscode.stwars.validators.OfferValidator offerValidator;
    private final br.com.letscode.stwars.validators.TradeValidator tradeValidator;
    private final TransactionHistoryRepository transactionHistoryRepository;
    private final MarketPlaceRepository marketplaceRepository;
    private final PersonService personService;

    public void insertNewOffer(MarketPlaceDto request) {
        MarketPlaceEntity marketPlaceEntity = new MarketPlaceEntity();
        Optional<PersonEntity> person = personRepository.findById(request.getIdPerson());   // todo - exception -> .orElseThrow()
        offerValidator.factionValidation(person);
        ItemsEntity offer = mapper.toEntity(request.getOffer());
        ItemsEntity receive = mapper.toEntity(request.getReceive());
        offerValidator.itemQuantityValidation(person, offer);

        int pointsOffer = offerValidator.pointsValidation(receive, offer);


        BaseEntity base = baseRepository.findById(request.getBase()).get();
        offerValidator.baseExistsValidation(marketPlaceEntity, base);

        marketPlaceEntity.setOfferedBy(person.get());

        itemsRepository.save(receive);
        itemsRepository.save(offer);

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
        tradeValidator.offerExistsValidation(marketPlaceEntity);

        PersonEntity offerBy = personRepository.getById(marketPlaceEntity.getOfferedBy().getId());
        tradeValidator.offerByExists(offerBy);

        PersonEntity receiver = personService.getPersonById(receiverId.getId());
        tradeValidator.receiverExistsValidation(receiver);

        offerValidator.factionValidation(Optional.of(receiver));
        offerValidator.itemQuantityValidation(Optional.of(receiver), marketPlaceEntity.getReceive());
        tradeValidator.differentRebelsTradingValidation(offerBy, receiver);
        tradeValidator.sameBaseValidation(receiver, offerBy);

        personService.addItemToInventory(receiver, marketPlaceEntity.getOffer());
        personService.addItemToInventory(offerBy, marketPlaceEntity.getReceive());
        personService.removeItemFromInventory(receiver, marketPlaceEntity.getReceive());

        saveTransactionToHistory(marketPlaceEntity, offerId, receiver);

        personRepository.save(receiver);
        personRepository.save(offerBy);
        marketplaceRepository.delete(marketPlaceEntity);
    }

    private void saveTransactionToHistory(MarketPlaceEntity tradeOffer, Long id, PersonEntity receiver){
        TransactionHistoryEntity transactionHistory = new TransactionHistoryEntity();

        transactionHistory.setId(id);
        transactionHistory.setReceiverPerson(receiver);
        transactionHistory.setRequesterPerson(tradeOffer.getOfferedBy());
        transactionHistory.setTransfer(generateTradeLog(tradeOffer, receiver));

        transactionHistoryRepository.save(transactionHistory);
    }

    private String generateTradeLog(MarketPlaceEntity tradeOffer, PersonEntity receiver) {
        return  "Offered by: " + tradeOffer.getOfferedBy().getName() + " / " +
                "Accepted by: " + receiver.getName() + "  | " +
                " Offered: " +
                tradeOffer.getOffer().getWeapons() + " we/" +
                tradeOffer.getOffer().getAmmunitions() + " am/" +
                tradeOffer.getOffer().getWaters() + " wa/" +
                tradeOffer.getOffer().getFoods() + " fo"+ "  | " +
                "Received: " +
                tradeOffer.getReceive().getWeapons() + " we/" +
                tradeOffer.getReceive().getAmmunitions() + " am/" +
                tradeOffer.getReceive().getWaters() + " wa/" +
                tradeOffer.getReceive().getFoods() + " fo" + "  | " +
                "Trade Points: " + tradeOffer.getPoints();
    }

}
