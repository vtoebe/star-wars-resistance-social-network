package br.com.letscode.stwars.model;

import br.com.letscode.stwars.enums.FactionEnum;
import br.com.letscode.stwars.enums.GenreEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "person")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PersonEntity implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private String name;
    private LocalDate birth;
    @Enumerated(EnumType.STRING)
    private GenreEnum genre;
    @Enumerated(EnumType.STRING)
    private FactionEnum faction;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @JsonIgnore
    @PrimaryKeyJoinColumn
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "person")
    private InventoryEntity inventory;

    @JsonIgnore
    @PrimaryKeyJoinColumn
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "person")
    private LocaleEntity locale;

}
