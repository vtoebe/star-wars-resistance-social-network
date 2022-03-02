package br.com.letscode.stwars.model;

import br.com.letscode.stwars.enums.FactionEnum;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "base")
public class BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private String name;
    @Enumerated(EnumType.STRING)
    private FactionEnum faction;

    public BaseEntity(String name, FactionEnum resistance) {
    }

    public BaseEntity() { }
}
