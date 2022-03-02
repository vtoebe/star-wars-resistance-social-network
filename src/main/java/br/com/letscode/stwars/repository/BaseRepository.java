package br.com.letscode.stwars.repository;

import br.com.letscode.stwars.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseRepository extends JpaRepository<BaseEntity, String> {
}
