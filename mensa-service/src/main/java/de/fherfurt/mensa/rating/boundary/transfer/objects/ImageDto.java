package de.fherfurt.mensa.rating.boundary.transfer.objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
public class ImageDto {
    private int id;
    private String name;
    private String suffix;
    private byte[] content;
}
