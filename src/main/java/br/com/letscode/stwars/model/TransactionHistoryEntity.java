package br.com.letscode.stwars.model;

import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="hist_transaction")
public class TransactionHistoryEntity {
    @Id
    private Long id;
    @MapsId
    @ManyToOne
    @JoinColumn(name = "requester_person")
    private PersonEntity requesterPerson;
    @MapsId
    @ManyToOne
    @JoinColumn(name = "receiver_person")
    private PersonEntity receiverPerson;
    private String transfer;
    @UpdateTimestamp
    private LocalDateTime transferedAt;
}
