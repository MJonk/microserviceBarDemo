package com.codenomads.drinkservice;

import com.codenomads.drinkservice.model.Drink;
import com.codenomads.drinkservice.repository.DrinkRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class DrinkServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DrinkServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(DrinkRepository drinkRepository) {
        return args -> {
            Drink drink1 = Drink.builder()
                    .name("Pina Colada")
                    .minimumAge(18)
                    .build();
            drinkRepository.save(drink1);

            Drink drink2 = Drink.builder()
                    .name("Vodka")
                    .minimumAge(65)
                    .build();
            drinkRepository.save(drink2);

            Drink drink3 = Drink.builder()
                    .name("Fristi")
                    .minimumAge(3)
                    .build();
            drinkRepository.save(drink3);
        };
    }
}
