package de.fherfurt.mensa.rating.entity;

import de.fherfurt.mensa.core.Database;
import de.fherfurt.mensa.core.persistence.Repository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RatingRepository implements Repository<Rating> {

    private final Database database = Database.newInstance();

    void init() {
        database.addInsertMapper(Rating.class, (db, entity) -> entity.getImages().forEach(db::save));
        database.addDeleteMapper(Rating.class, (db, entity) -> entity.getImages().forEach(db::delete));
    }

    public static RatingRepository of() {
        RatingRepository res = new RatingRepository();
        res.init();
        return res;
    }

    @Override
    public void save(Rating entity) {
        database.save(entity);
    }

    @Override
    public Optional<Rating> findBy(int id) {
        return database.find(id, Rating.class);
    }

    @Override
    public void delete(Rating entity) {
        database.delete(entity);
    }
}
