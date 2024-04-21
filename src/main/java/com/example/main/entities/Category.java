package com.example.main.entities;

import com.example.main.entities.functional_property.ManagementFunctionalProperty;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Category extends ManagementFunctionalProperty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(name = "name", nullable = false)
    private String name;
}
