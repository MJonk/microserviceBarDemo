package com.codenomads.drinkservice.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
@Table(name = "drinks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Drink {

    @Id
    @GeneratedValue
    private UUID id;

    @Size(min = 1, max = 50)
    @Column(unique = true)
    private String name;

    private int minimumAge;
}
