package br.com.letscode.stwars.repository;

import br.com.letscode.stwars.model.ItemsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemsRepository extends JpaRepository<ItemsEntity, Long> {
}
