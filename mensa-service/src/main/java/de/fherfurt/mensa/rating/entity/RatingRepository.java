package de.fherfurt.mensa.rating.entity;

import de.fherfurt.mensa.core.persistence.Database;
import de.fherfurt.mensa.core.persistence.Repository;
import de.fherfurt.mensa.core.persistence.errors.NoResultException;
import de.fherfurt.mensa.core.persistence.errors.ToManyResultsException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RatingRepository implements Repository<Rating> {

    private final Database database = Database.newInstance();

    /**
     * In that stage (Java 1 without persistence) the simulation of cascaded operations is required. For that,
     * the adding of mappers is done in that helper method. After introducing a data base, this method and its
     * containing logic should be removed.
     */
    private void init() {
        database.addInsertMapper(Rating.class, (db, entity) -> entity.getImages().forEach(db::save));
        database.addDeleteMapper(Rating.class, (db, entity) -> entity.getImages().forEach(db::delete));
    }

    public static RatingRepository of() {
        RatingRepository res = new RatingRepository();
        res.init();
        return res;
    }

    @Override
    public void save(final Rating entity) {
        database.save(entity);
    }

    @Override
    public Optional<Rating> findBy(final int id) {
        return database.findBy(Rating.class, id);
    }

    public Image findImageBy(int imageId) {
        List<Image> images = database.findBy(Image.class, image -> Objects.equals(image.getId(), imageId));

        if (images.isEmpty()) {
            throw new NoResultException("Could not found image with id [" + imageId + "]");
        }

        if (images.size() > 1) {
            throw new ToManyResultsException("No unique result found for id [" + imageId + "]");
        }

        return images.get(0);
    }

    @Override
    public void delete(final Rating entity) {
        database.delete(entity);
    }
}
