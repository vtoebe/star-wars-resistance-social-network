package br.com.letscode.stwars.model;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "rebellion")
public class RebellionEntity {
    @EmbeddedId private RebellionId id;
    private String description;
    private LocalDateTime registeredAt;
}
