package br.com.letscode.stwars.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "accept_offer")
public class AcceptOfferEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "base")
    private String base;

    @OneToOne
    @JoinColumn(name = "market_place")
    private MarketPlaceEntity marketPlace;

}
