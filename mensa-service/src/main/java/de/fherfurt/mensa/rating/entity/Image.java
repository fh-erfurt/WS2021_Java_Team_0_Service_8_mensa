package de.fherfurt.mensa.rating.entity;

import de.fherfurt.mensa.core.persistence.BaseBusinessEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Image extends BaseBusinessEntity {
    private String name;
    private String suffix;

    @Builder(setterPrefix = "with")
    public Image(int id, String name, String suffix) {
        super(id);
        this.name = name;
        this.suffix = suffix;
    }
}
