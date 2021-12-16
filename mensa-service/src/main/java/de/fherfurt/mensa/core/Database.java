package de.fherfurt.mensa.core;

import de.fherfurt.mensa.core.errors.MissingPrimaryException;
import de.fherfurt.mensa.core.errors.PersistenceException;
import de.fherfurt.mensa.core.errors.ToManyPrimaryKeysException;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Database {

    private static Database instance;

    private final Map<Class<?>, CollectionContainer<Object>> cache = new ConcurrentHashMap<>();

    private Database() {
    }

    public static Database newInstance() {
        if (Objects.isNull(instance)) {
            instance = new Database();
        }

        return instance;
    }

    public <T> void save(final T entity) {
        try {
            final CollectionContainer<Object> target = Optional.ofNullable(cache.get(entity.getClass())).orElse(new CollectionContainer<>());

            final Field primaryKey = extractPrimaryKeyField(entity);
            primaryKey.setAccessible(true);
            primaryKey.set(entity, target.count.incrementAndGet());
            target.entries.add(entity);

            cache.put(entity.getClass(), target);
        } catch (IllegalAccessException e) {
            throw new PersistenceException(e);
        }
    }

    public <T> void delete(final T entity) {
        final CollectionContainer<Object> target = Optional.ofNullable(cache.get(entity.getClass())).orElse(new CollectionContainer<>());

        target.entries.remove(entity);

        cache.put(entity.getClass(), target);
    }

    public <T> Optional<T> find(final int id, final Class<T> type) {
        return Optional.ofNullable(cache.get(type))
                .flatMap(target -> target.entries.stream().filter(entity -> Objects.equals(extractPrimaryKeyValue(entity), id))
                        .map(type::cast)
                        .findFirst());
    }

    private static class CollectionContainer<T> {
        final AtomicInteger count = new AtomicInteger(0);
        final List<T> entries = new LinkedList<>();
    }

    private int extractPrimaryKeyValue(final Object entity) {
        try {
            final Field primaryKey = extractPrimaryKeyField(entity);
            primaryKey.setAccessible(true);
            return (int) primaryKey.get(entity);
        } catch (IllegalAccessException e) {
            throw new PersistenceException(e);
        }
    }

    private Field extractPrimaryKeyField(Object entity) {
        final List<Field> primaryKeys = Arrays.stream(entity.getClass().getFields()).filter(field -> field.isAnnotationPresent(Id.class)).collect(Collectors.toList());
        Arrays.stream(entity.getClass().getDeclaredFields()).filter(field -> field.isAnnotationPresent(Id.class)).forEach(primaryKeys::add);

        if (primaryKeys.isEmpty()) {
            throw MissingPrimaryException.of(entity.getClass());
        }

        if (primaryKeys.size() > 1) {
            throw ToManyPrimaryKeysException.of(entity.getClass());
        }

        return primaryKeys.get(0);
    }

    public void clear() {
        cache.values().forEach(entry -> entry.entries.clear());
        cache.clear();
    }

    public <T> int count(Class<T> type) {
        return Optional.ofNullable(cache.get(type)).map(entry -> entry.entries.size())
                .orElseThrow(() -> new PersistenceException("Collection doesn't exist for type '" + type.getSimpleName() + "'."));
    }
}
