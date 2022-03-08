package br.com.letscode.stwars.model;

import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "hist_localization")
public class LocationHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    @MapsId
    @ManyToOne
    @JoinColumn(name = "personId")
    private PersonEntity person;
    private String latitude;
    private String longitude;
    @OneToOne
    @JoinColumn(name = "base")
    private BaseEntity base;

    @UpdateTimestamp
    private LocalDateTime createdAt;
}
