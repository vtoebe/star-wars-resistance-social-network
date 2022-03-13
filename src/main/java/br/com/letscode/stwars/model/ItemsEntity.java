package br.com.letscode.stwars.model;

import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "items")
public class ItemsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer weapons;
    private Integer ammunitions;
    private Integer waters;
    private Integer foods;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public ItemsEntity(Integer weapons, Integer ammunitions, Integer waters, Integer foods) {
        this.weapons = weapons;
        this.ammunitions = ammunitions;
        this.waters = waters;
        this.foods = foods;
    }

    public ItemsEntity() {}
}
