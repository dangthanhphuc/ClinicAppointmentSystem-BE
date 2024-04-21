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
public class ManagementFunctionalProperty {
    @Column(name = "is_deleted")
    @ColumnDefault("false")
    private boolean isDeleted;

    @Column(name = "created_at")
    @ColumnDefault("CURRENT_TIMESTAMP(6)")
    private LocalDateTime createdAt ;

    @Column(name = "created_by") // User Id
    private Long createdBy;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Column(name = "modified_by") // User Id
    private Long modifiedBy;

    @PrePersist
    protected void onCreateAt() {
        isDeleted = false;
        createdAt = LocalDateTime.now();
        createdBy = null; // Chưa xử lý
        modifiedAt = null; // Chưa xử lý
        modifiedBy = null; // Chưa xử lý

    }

    @PreUpdate
    protected void onUpdate(){
        modifiedAt = LocalDateTime.now();
        modifiedBy = null; // Chưa xử lý
    }
}
