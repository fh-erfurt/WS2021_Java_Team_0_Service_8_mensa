package de.fherfurt.mensa.rating.business;

import de.fherfurt.mensa.core.persistence.Repository;
import de.fherfurt.mensa.rating.entity.Image;
import de.fherfurt.mensa.rating.entity.ImageRepository;

public class FilesBF {

    private final Repository<Image> imageRepository = new ImageRepository();
}
