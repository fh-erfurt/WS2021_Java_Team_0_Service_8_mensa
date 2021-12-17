package de.fherfurt.mensa.rating.business;

import de.fherfurt.mensa.rating.entity.FileRepository;
import de.fherfurt.mensa.rating.entity.Image;
import de.fherfurt.mensa.rating.entity.Rating;
import de.fherfurt.mensa.rating.entity.RatingRepository;

import java.io.IOException;
import java.util.Optional;

public class RatingBF {

    private final RatingRepository ratingRepository = RatingRepository.of();
    private final FilesBF filesBF = new FilesBF();

    public void save(final Rating rating) {
        ratingRepository.save(rating);
    }

    public Optional<Rating> findBy(final int id) {
        return ratingRepository.findBy(id);
    }

    public void saveImage(final Image image, byte[] content) throws IOException {
        filesBF.save(FileRepository.FileTypes.IMAGE, convert(image), content);
    }

    public Optional<byte[]> loadImage(final int imageId) {
        final Image image = ratingRepository.findImageBy(imageId);

        try {
            return filesBF.findBy(FileRepository.FileTypes.IMAGE, convert(image));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    private String convert(final Image image) {
        return image.getTitle() + "." + image.getType();
    }
}
