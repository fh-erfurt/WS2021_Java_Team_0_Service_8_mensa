package de.fherfurt.mensa.core.containers;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED, onConstructor = @__({@Builder(setterPrefix = "with")}))
public class Tuple<V1, V2> {
    private final V1 v1;
    private final V2 v2;
}
