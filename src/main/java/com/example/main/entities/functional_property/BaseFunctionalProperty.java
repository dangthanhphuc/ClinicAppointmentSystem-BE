package com.example.main.entities.functional_property;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class BaseFunctionalProperty {
    @Column(name = "is_deleted")
    @ColumnDefault("false")
    private boolean isDeleted;

    @Column(name = "deleted_by")
    private Long deletedBy; // Chưa xử lý

    @PrePersist
    protected void onCreateAt() {
        isDeleted = false;
    }

}
