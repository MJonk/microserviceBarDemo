package com.codenomads.drinkservice.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DrinkDto {

    @Size(min = 1, max = 50)
    private String name;

    private int minimumAge;
}
