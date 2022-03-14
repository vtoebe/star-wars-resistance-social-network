package br.com.letscode.stwars.validators;

import br.com.letscode.stwars.enums.FactionEnum;
import br.com.letscode.stwars.enums.ItemsEnum;
import br.com.letscode.stwars.exceptions.BusinessValidationException;
import br.com.letscode.stwars.model.BaseEntity;
import br.com.letscode.stwars.model.ItemsEntity;
import br.com.letscode.stwars.model.MarketPlaceEntity;
import br.com.letscode.stwars.model.PersonEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OfferValidator {
    public void factionValidation(Optional<PersonEntity> person) {
        if (person.get().getFaction().equals(FactionEnum.EMPIRE)){
            throw new BusinessValidationException(" You are not welcome in out MarketPlace (Person is a member from EMPIRE faction)");
        }
    }

    public int pointsValidation(ItemsEntity receive, ItemsEntity offer) {
        //Conferindo que pontos são iguais
        int pointsReceive = ItemsEnum.getTotalPoints(receive);
        int pointsOffer = ItemsEnum.getTotalPoints(offer);

        if(pointsReceive > pointsOffer){
            throw new BusinessValidationException("Your total offer has more points than needed to receive these items");
        }
        else if (pointsReceive < pointsOffer){
            throw new BusinessValidationException("Your total offer has less points than needed to receive these items");
        }
        return pointsOffer;
    }

    public void baseExistsValidation(MarketPlaceEntity marketPlaceEntity, BaseEntity base) {
        if(base == null){ //changed: base == null to base.getName() == null
            throw new BusinessValidationException("This base does not exists");
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
            throw new BusinessValidationException("You don't have enough points to do this transaction");
        }

        if (    offer.getAmmunitions() == 0 &&
                offer.getWaters() == 0 &&
                offer.getFoods() == 0 &&
                offer.getWeapons() == 0
        ){
            throw new BusinessValidationException("Your offer is empty!");
        }
    }
}
