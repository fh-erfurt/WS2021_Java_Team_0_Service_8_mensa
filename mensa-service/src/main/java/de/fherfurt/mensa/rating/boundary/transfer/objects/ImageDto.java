package de.fherfurt.mensa.rating.boundary.transfer.objects;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(setterPrefix = "with")
public class ImageDto {
    private int id;
    private String title;
    private String type;
    private byte[] content;
}
