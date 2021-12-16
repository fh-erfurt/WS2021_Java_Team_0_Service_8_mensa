package de.fherfurt.mensa.core.persistence;

import de.fherfurt.mensa.core.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public abstract class BaseBusinessEntity {

    @Id
    private int id;
}
