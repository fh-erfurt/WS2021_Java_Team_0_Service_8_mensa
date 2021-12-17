package de.fherfurt.mensa.rating.entity;

import de.fherfurt.mensa.core.persistence.BaseBusinessEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Image extends BaseBusinessEntity {
    private String title;
    private String type;
}
