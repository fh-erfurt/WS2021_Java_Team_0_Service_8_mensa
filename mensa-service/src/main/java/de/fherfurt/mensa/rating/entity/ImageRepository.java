package de.fherfurt.mensa.rating.entity;

import de.fherfurt.mensa.core.Database;
import de.fherfurt.mensa.core.persistence.Repository;

import java.util.Optional;

public class ImageRepository implements Repository<Image> {

    private final Database database = Database.newInstance();

    @Override
    public void save(Image entity) {
        database.save(entity);
    }

    @Override
    public Optional<Image> findBy(int id) {
        return database.find(id, Image.class);
    }

    @Override
    public void delete(Image entity) {
        database.delete(entity);
    }
}