package br.com.letscode.stwars.repository;

import br.com.letscode.stwars.model.RebellionEntity;
import br.com.letscode.stwars.model.RebellionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RebellionRepository extends JpaRepository<RebellionEntity, RebellionId> {
    List<RebellionEntity> findByIdPersonId(Long personId);
    List<RebellionEntity> findByIdPersonName(String name);

    @Query("SELECT count(r) FROM RebellionEntity r WHERE r.id.person.id = ?1 ")
    Integer countReport(Long personId);
}
