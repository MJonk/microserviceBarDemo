package com.codenomads.drinkservice.controller;

import com.codenomads.drinkservice.dto.DrinkDto;
import com.codenomads.drinkservice.dto.DrinkDtoMapper;
import com.codenomads.drinkservice.model.Drink;
import com.codenomads.drinkservice.services.DrinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("drinks")
@RequiredArgsConstructor
@Validated
public class DrinkController {

    private final DrinkService drinkService;
    private final DrinkDtoMapper drinkDtoMapper;

    @GetMapping
    public Page<DrinkDto> getAllDrinkByPage(@PageableDefault(size = 20, page = 0) Pageable pageable) {
        return drinkService.getAllDrinksByPage(pageable)
                .map(drinkDtoMapper::toDto);
    }

    @GetMapping("/asList")
    public List<DrinkDto> getAllDrinkInList(@PageableDefault(size = 20, page = 0) Pageable pageable) {
        return drinkService.getAllDrinksByPage(pageable)
                .map(drinkDtoMapper::toDto)
                .toList();
    }

    @GetMapping("{name}")
    public DrinkDto getDrinkDtoByName(@PathVariable String name) {
        Drink drink = drinkService.getDrinkByName(name);
        DrinkDto drinkDto = drinkDtoMapper.toDto(drink);
        return drinkDto;
    }


}
