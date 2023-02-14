package com.codenomads.barservice.service;

import com.codenomads.barservice.dto.DrinkDto;
import com.codenomads.barservice.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BarService {

    private final WebClient.Builder webClientBuilder;

    public boolean userExists(String email) {
        UserDto userDto = getUserDtoFromEmail(email);
        return userDto != null;
    }

    public boolean userCanHaveDrink(String email, String drinkName) {
        UserDto userDto = getUserDtoFromEmail(email);
        DrinkDto drinkDto = getDrinkDtoFromDrinkName(drinkName);

        return userDto.getBirthDate().minusDays(1).isBefore(LocalDate.now().minusYears(drinkDto.getMinimumAge()));
    }

    private DrinkDto getDrinkDtoFromDrinkName(String drinkName) {
        DrinkDto drinkDto = webClientBuilder.build().get()
                .uri("http://drink-service/drinks/" + drinkName)
                .retrieve()
                .onStatus(HttpStatus::isError,
                        clientResponse ->  {
                            throw new ResponseStatusException(clientResponse.statusCode());
                        })
                .bodyToMono(DrinkDto.class)
                .block();

        return drinkDto;
    }

    private UserDto getUserDtoFromEmail(String email) {
        UserDto userDto = webClientBuilder.build().get()
                .uri("http://user-service/users/" + email)
                .retrieve()
                .onStatus(HttpStatus::isError,
                        clientResponse ->  {
                            throw new ResponseStatusException(clientResponse.statusCode());
                        })
                .bodyToMono(UserDto.class)
                .block();

        return userDto;
    }

    public Page<DrinkDto> getDrinksByEmail(String email) {
        UserDto userDto = getUserDtoFromEmail(email);
        int userAge = Period.between(userDto.getBirthDate(), LocalDate.now()).getYears();
        List<DrinkDto> drinkDtoList = webClientBuilder.build().get()
                .uri("http://drink-service/drinks/asList")
                .retrieve()
                .onStatus(HttpStatus::isError,
                        clientResponse ->  {
                            throw new ResponseStatusException(clientResponse.statusCode());
                        })
                .bodyToFlux(DrinkDto.class)
                .collectList()
                .block()
                .stream()
                .filter(drinkDto -> drinkDto.getMinimumAge() < userAge)
                .collect(Collectors.toList());

        return new PageImpl<>(drinkDtoList);
    }

    public Page<UserDto> getLegalUserNamesByDrink(String drinkname) {
        DrinkDto drinkDto = getDrinkDtoFromDrinkName(drinkname);

        List<UserDto> userDtoList = webClientBuilder.build().get()
                .uri("http://user-service/users/asList")
                .retrieve()
                .onStatus(HttpStatus::isError,
                        clientResponse -> {
                            throw new ResponseStatusException(clientResponse.statusCode());
                        })
                .bodyToFlux(UserDto.class)
                .collectList()
                .block()
                .stream()
                .filter(userDto -> {
                    int age = Period.between(userDto.getBirthDate(), LocalDate.now()).getYears();
                    return drinkDto.getMinimumAge() < age;
                }).toList();

        return new PageImpl<>(userDtoList);
    }

}

