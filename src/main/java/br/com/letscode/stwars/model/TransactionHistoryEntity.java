package br.com.letscode.stwars.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name="hist_transaction")
public class TransactionHistoryEntity {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "requesterPerson")
    private PersonEntity requesterPerson;

    @ManyToOne
    @JoinColumn(name = "receiverPerson")
    private PersonEntity receiverPerson;
    private String transfer;
    @CreationTimestamp
    private LocalDateTime transferAt;
}
