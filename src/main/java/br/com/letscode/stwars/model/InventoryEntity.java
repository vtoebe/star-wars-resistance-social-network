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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "items")
    private ItemsEntity items;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
