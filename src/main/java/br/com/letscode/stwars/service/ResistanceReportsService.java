package br.com.letscode.stwars.service;

import br.com.letscode.stwars.enums.FactionEnum;
import br.com.letscode.stwars.enums.ItemsEnum;
import br.com.letscode.stwars.model.ItemsEntity;
import br.com.letscode.stwars.model.PersonEntity;
import br.com.letscode.stwars.model.ResistanceReportEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResistanceReportsService {

    private final PersonService personService;

    int traitors = 0;
    int rebels = 0;
    int lostPointsDueTreason;
    ItemsEntity resourcesPerRebel = new ItemsEntity(0, 0, 0, 0);

    ResistanceReportEntity resistanceReportEntity;

    private void countRebelsTraitorsAndResources() {
        List<PersonEntity> rebelList = personService.getListByPerson();

        for (PersonEntity rebel : rebelList){
            if (rebel.getFaction() == FactionEnum.RESISTANCE){
                rebels++;
                countTotalResources(rebel);
            } else {
                traitors++;
                lostPointsDueTreason += ItemsEnum.getTotalPoints(rebel.getInventory().getItems());
            }
        }

        countResourcesPerRebel(rebels);
        resistanceReportEntity = new ResistanceReportEntity(getRebelPercentage(), getTraitorPercentage(), resourcesPerRebel, lostPointsDueTreason);
    }

    public float getTraitorPercentage(){
        return calcPercentage(traitors);
    }
    public float getRebelPercentage(){
        return calcPercentage(rebels);
    }

   private float calcPercentage(int personCount) {
        return (personCount * 100.0F) / (traitors + rebels);
    }

    private void countTotalResources(PersonEntity person) {
        resourcesPerRebel.setWeapons(resourcesPerRebel.getWeapons() + person.getInventory().getItems().getWeapons());
        resourcesPerRebel.setAmmunitions(resourcesPerRebel.getAmmunitions() + person.getInventory().getItems().getAmmunitions());
        resourcesPerRebel.setWaters(resourcesPerRebel.getWaters() + person.getInventory().getItems().getWaters());
        resourcesPerRebel.setFoods(resourcesPerRebel.getFoods() + person.getInventory().getItems().getFoods());
    }

    private void countResourcesPerRebel(int rebelCount){
        resourcesPerRebel.setWeapons(resourcesPerRebel.getWeapons() / rebelCount);
        resourcesPerRebel.setAmmunitions(resourcesPerRebel.getAmmunitions()  / rebelCount);
        resourcesPerRebel.setWaters(resourcesPerRebel.getWaters()  / rebelCount);
        resourcesPerRebel.setFoods(resourcesPerRebel.getFoods()  / rebelCount);
    }

    public ResistanceReportEntity getResistanceReport(){
        countRebelsTraitorsAndResources();
        return resistanceReportEntity;
    }

}
