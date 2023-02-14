package com.codenomads.drinkservice.services;

import com.codenomads.drinkservice.model.Drink;
import com.codenomads.drinkservice.repository.DrinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DrinkService {

    private final DrinkRepository drinkRepository;

    public Page<Drink> getAllDrinksByPage(Pageable pageable) {
        System.out.println("Inside drink service");
        return drinkRepository.findAll(pageable);
    }

    @GetMapping
    public Drink getDrinkByName(String name) {
        return drinkRepository.findByName(name)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Drink with name " + name + " could not be found"
                ));
    }
}
