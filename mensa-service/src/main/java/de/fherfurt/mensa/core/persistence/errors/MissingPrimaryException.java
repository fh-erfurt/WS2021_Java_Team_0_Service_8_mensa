package de.fherfurt.mensa.core.persistence.errors;

import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class MissingPrimaryException extends RuntimeException {
    private final String type;

    private MissingPrimaryException(String message, String type) {
        super(message);
        this.type = type;
    }

    public static MissingPrimaryException of(Class<?> clazz) {
        return of(clazz, null);
    }

    public static MissingPrimaryException of(Class<?> clazz, String message) {
        return new MissingPrimaryException(message, clazz.getSimpleName());
    }

    @Override
    public String getLocalizedMessage() {
        return "Could not find a primary key on type '" + type + "'." + (Objects.isNull(getMessage()) ? "" : " " + getMessage());
    }
}
