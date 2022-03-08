package br.com.letscode.stwars.repository;

import br.com.letscode.stwars.model.MarketPlaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketPlaceRepository extends JpaRepository<MarketPlaceEntity, Long> {
}
