package de.fherfurt.mensa.rating.entity;

import de.fherfurt.mensa.core.persistence.errors.MissingContentException;
import de.fherfurt.mensa.core.persistence.errors.MissingValueException;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;

@NoArgsConstructor(staticName = "of")
public class FileRepository {
    public static final String BASE_DIR = System.getProperty("java.io.tmpdir");
    private final String serviceDir = "mensa";

    public void save(final FileTypes type, final String fileName, final byte[] content) throws IOException {
        if (Objects.isNull(type)) {
            throw new MissingValueException("Type of file is missing");
        }

        if (Objects.isNull(content) || content.length == 0) {
            throw new MissingContentException("No content to store");
        }

        Path usedDirectory = Paths.get(BASE_DIR, serviceDir, type.name().toLowerCase());
        if (Files.notExists(usedDirectory)) {
            Files.createDirectories(usedDirectory);
        }
        final Path file = Paths.get(usedDirectory.toString(), fileName);

        if (Files.exists(file)) {
            throw new FileAlreadyExistsException("Could not store file '" + file + "'. Already exists");
        }

        Files.write(file, content);
    }

    public Optional<byte[]> findBy(final FileTypes type, final String fileName) throws IOException {
        Path file = Paths.get(BASE_DIR, serviceDir, type.name().toLowerCase(), fileName);
        if (Files.notExists(file)) {
            return Optional.empty();
        }

        return Optional.of(Files.readAllBytes(file));
    }

    public void delete(final FileTypes type, final String fileName) throws IOException {
        Path file = Paths.get(BASE_DIR, serviceDir, type.name().toLowerCase(), fileName);
        if (Files.notExists(file)) {
            return;
        }

        Files.delete(file);
    }

    public enum FileTypes {
        IMAGE
    }
}