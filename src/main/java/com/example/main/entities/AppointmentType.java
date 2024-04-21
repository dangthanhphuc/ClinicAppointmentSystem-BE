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
@Table(name = "appointment_types")
public class AppointmentType extends ManagementFunctionalProperty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appointmentTypeId;

    @Column(name = "type_name", columnDefinition = "nvarchar(255)", nullable = false)
    private String typeName;

    @Column(name = "description", columnDefinition = "nvarchar(255)")
    private String description;
}
