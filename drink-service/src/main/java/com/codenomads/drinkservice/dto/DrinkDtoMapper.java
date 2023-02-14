package com.codenomads.drinkservice.dto;

import com.codenomads.drinkservice.model.Drink;
import org.springframework.stereotype.Component;

@Component
public class DrinkDtoMapper {

    public DrinkDto toDto(Drink drink) {
        return DrinkDto.builder()
                .name(drink.getName())
                .minimumAge(drink.getMinimumAge())
                .build();
    }

    public Drink toDrink(DrinkDto drinkDto) {
        return Drink.builder()
                .name(drinkDto.getName())
                .minimumAge(drinkDto.getMinimumAge())
                .build();
    }
}
