package br.com.letscode.stwars.model;

import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="inventory")
public class InventoryEntity {
    @Id private Long id;
    @MapsId
    @OneToOne
    @JoinColumn(name = "id")
    private PersonEntity person;
    private Integer weapons;
    private Integer ammunitions;
    private Integer waters;
    private Integer foods;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
