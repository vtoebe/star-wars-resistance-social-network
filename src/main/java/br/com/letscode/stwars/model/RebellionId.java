package br.com.letscode.stwars.model;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
@Embeddable
public class RebellionId implements Serializable {
    private static final long serialVersionUID = 1L;
    @ManyToOne
    @JoinColumn(name = "personId")
    private PersonEntity person;

    @ManyToOne
    @JoinColumn(name = "reportByPersonId")
    private PersonEntity reportByPerson;
}
