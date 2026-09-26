package vn.rikkei.exam.parkingreservation.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(
        name = "resource_inventory",
        uniqueConstraints = @UniqueConstraint(columnNames = {"resource_code", "available_date"})
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResourceInventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "resource_code")
    private ResourceType resourceType;

    private LocalDate availableDate;
    private Integer availableSlots;
}
