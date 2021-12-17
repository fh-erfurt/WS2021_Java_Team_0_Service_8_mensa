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
    private String title;
    private String type;

    @Builder(setterPrefix = "with")
    public Image(int id, String title, String type) {
        super(id);
        this.title = title;
        this.type = type;
    }
}
