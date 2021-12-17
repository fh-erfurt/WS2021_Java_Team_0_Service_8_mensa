package de.fherfurt.mensa.core.persistence;

import de.fherfurt.mensa.core.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseBusinessEntity {

    @Id
    private int id;
}
