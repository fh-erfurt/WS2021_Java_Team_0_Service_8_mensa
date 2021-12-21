package de.fherfurt.mensa.rating.business;

import de.fherfurt.mensa.core.errors.ConsumerWithException;
import de.fherfurt.mensa.rating.entity.FileRepository.FileTypes;
import de.fherfurt.mensa.rating.entity.Image;
import de.fherfurt.mensa.rating.entity.Rating;
import de.fherfurt.mensa.rating.entity.RatingRepository;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.Optional;

@NoArgsConstructor(staticName = "of")
public class RatingBF {

    private final RatingRepository ratingRepository = RatingRepository.of();
    private final FilesBF filesBF = FilesBF.of();

    public void save(final Rating rating) {
        ratingRepository.save(rating);
    }

    public Optional<Rating> findBy(final int id) {
        return ratingRepository.findBy(id);
    }

    public void saveImage(final Image image, byte[] content) throws IOException {
        final boolean newImage = image.getId() < 1;
        filesBF.save(FileTypes.IMAGE, convert(image), content, newImage);
    }

    public Optional<byte[]> loadImage(final int imageId) {
        final Image image = ratingRepository.findImageBy(imageId);

        try {
            return filesBF.findBy(FileTypes.IMAGE, convert(image));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    public void delete(final int id) {
        final Optional<Rating> toDelete = findBy(id);
        if (toDelete.isEmpty()) {
            return;
        }

        toDelete.get()
                .getImages()
                .forEach(ConsumerWithException.wrap(image -> filesBF.delete(FileTypes.IMAGE, convert(image))));

        ratingRepository.delete(toDelete.get());
    }

    private String convert(final Image image) {
        return image.getName() + "." + image.getSuffix();
    }
}
