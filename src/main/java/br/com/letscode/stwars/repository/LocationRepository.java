package br.com.letscode.stwars.repository;

import br.com.letscode.stwars.model.LocationHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<LocationHistoryEntity, Long> {

}
