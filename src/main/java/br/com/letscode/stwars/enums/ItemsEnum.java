package br.com.letscode.stwars.enums;

import br.com.letscode.stwars.dto.ItemsDto;
import br.com.letscode.stwars.model.ItemsEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ItemsEnum {

    WATER(2),
    FOOD(1),
    AMMO(3),
    WEAPON(4);

    private Integer points;

    public static Integer getTotalPoints(ItemsEntity itemsEntity) {
        return getTotalPoints(itemsEntity.getWaters(), itemsEntity.getFoods(), itemsEntity.getAmmunitions(), itemsEntity.getWeapons());
    }

    public static Integer getTotalPoints(ItemsDto itemsDto) {
        return getTotalPoints(itemsDto.getWaters(), itemsDto.getFoods(), itemsDto.getAmmunitions(), itemsDto.getWeapons());
    }

    private static Integer getTotalPoints(Integer water, Integer food, Integer ammo, Integer weapon) {
        return (WATER.points * water) + (FOOD.points * food) +
                (AMMO.points * ammo) + (WEAPON.points * weapon);
    }

}
