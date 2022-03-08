package br.com.letscode.stwars.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="locale")
public class LocaleEntity {
    @Id
    private Long id;
    @MapsId
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "id")
    private PersonEntity person;
    private String latitude;
    private String longitude;
    @OneToOne
    @JoinColumn(name = "base")
    private BaseEntity base;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
