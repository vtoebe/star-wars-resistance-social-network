package br.com.letscode.stwars.service.validators;

import br.com.letscode.stwars.enums.FactionEnum;
import br.com.letscode.stwars.enums.ItemsEnum;
import br.com.letscode.stwars.enums.MessageCodeEnum;
import br.com.letscode.stwars.exceptions.BusinessValidationException;
import br.com.letscode.stwars.model.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class OfferValidator {
    public void factionValidation(Optional<PersonEntity> person) {
        if (person.get().getFaction().equals(FactionEnum.EMPIRE)){
            throw new BusinessValidationException(ValidationError.builder()
                    .keyMessage(MessageCodeEnum.NOT_ALLOWED)
                    .params(List.of(MessageCodeEnum.FACTION_FIELD))
                    .build());
        }
    }

    public int pointsValidation(ItemsEntity receive, ItemsEntity offer) {
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
        return pointsOffer;
    }

    public void baseExistsValidation(MarketPlaceEntity marketPlaceEntity, BaseEntity base) {
        if(base == null){
            throw new BusinessValidationException(ValidationError.builder()
                    .keyMessage(MessageCodeEnum.RESOURCE_NOT_FOUND)
                    .params(List.of(MessageCodeEnum.BASE_FIELD))
                    .build());
        } else marketPlaceEntity.setBase(base);
    }

    public void itemQuantityValidation(Optional<PersonEntity> person, ItemsEntity offer) {
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

        if (    offer.getAmmunitions() == 0 &&
                offer.getWaters() == 0 &&
                offer.getFoods() == 0 &&
                offer.getWeapons() == 0
        ){
            throw new BusinessValidationException((ValidationError.builder()
                    .keyMessage(MessageCodeEnum.EMPTY)
                    .params(List.of(MessageCodeEnum.ITEMS_FIELD))
                    .build()));
        }
    }
}