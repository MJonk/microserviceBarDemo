package com.codenomads.barservice.controller;

import com.codenomads.barservice.dto.DrinkDto;
import com.codenomads.barservice.dto.UserDto;
import com.codenomads.barservice.service.BarService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Email;
import javax.ws.rs.Path;

@RestController
@RequestMapping("/bar")
@RequiredArgsConstructor
public class BarController {

    private final BarService barService;

    @GetMapping("/{email}")
    public boolean userExists(@PathVariable
                              @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
                                      String email) {

        return barService.userExists(email);
    }

    @GetMapping("/drinkAllowed/{email}/{drinkName}")
    public boolean userCanHaveDrink(@PathVariable(required = true) String email,
                                    @PathVariable(required = true) String drinkName) {
        return barService.userCanHaveDrink(email, drinkName);
    }

    @GetMapping("/drinksByEmail/{email}")
    public Page<DrinkDto> getDrinksByEmail(@PathVariable(required = true) String email) {

        return barService.getDrinksByEmail(email);
    }

    @GetMapping("/namesByDrink/{name}")
    public Page<UserDto> getLegalUserNamesByDrink(@PathVariable(required = true) String name) {
        return barService.getLegalUserNamesByDrink(name);
    }

    // Create a GET method to check if a user is allowed to have a certain drink
    // query param user(email) (required), query param drink (name) (required)
    // Option 1: Drink service with a database, name, minimum age
    // Option 2: Include this in BarController
    // Drink Entity with field UUID id, String name (unique), int minimumAge

    // Create a GET method to get a Page of Drinks that a user can consume
    // Create a GET method to get a Page of Users that can drink a certain Drink

    // uriBuilder

    // Commit to github at the end
}
