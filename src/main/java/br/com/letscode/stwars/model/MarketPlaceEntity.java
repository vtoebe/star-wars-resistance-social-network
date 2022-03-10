package br.com.letscode.stwars.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "marketplace")
public class MarketPlaceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "offeredBy")
    private PersonEntity offeredBy ;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "offer")
    private ItemsEntity offer;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "receive")
    private ItemsEntity receive;

    @OneToOne
    @JoinColumn(name = "base")
    private BaseEntity base;

    private Integer points;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
