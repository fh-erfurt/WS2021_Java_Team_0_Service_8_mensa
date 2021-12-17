package de.fherfurt.mensa.core.containers;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
public class Tuple3<V1, V2, V3> extends Tuple<V1, V2> {
    private final V3 v3;

    @Builder(setterPrefix = "with")
    public Tuple3(final V1 v1, final V2 v2, final V3 v3) {
        super(v1, v2);
        this.v3 = v3;
    }
}
