package br.com.letscode.stwars.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name="inventory")
public class InventoryEntity {

    @Id private Long id;
    @JsonIgnore
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
