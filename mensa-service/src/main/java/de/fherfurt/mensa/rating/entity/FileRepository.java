package de.fherfurt.mensa.rating.entity;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class FileRepository {
    private final String baseDir = System.getProperty("java.io.tmpdir");
    private final String serviceDir = "mensa";

    public void save(FileTypes type, String fileName, byte[] content) throws IOException {
        Path file = Paths.get(baseDir, serviceDir, type.name().toLowerCase(), fileName);

        if (Files.exists(file)) {
            throw new FileAlreadyExistsException("Could not store file '" + file + "'. Already exists");
        }

        Files.write(file, content);
    }

    public Optional<byte[]> findBy(FileTypes type, String fileName) throws IOException {
        Path file = Paths.get(baseDir, serviceDir, type.name().toLowerCase(), fileName);
        if (Files.notExists(file)) {
            return Optional.empty();
        }

        return Optional.of(Files.readAllBytes(file));
    }

    public void delete(FileTypes type, String fileName) throws IOException {
        Path file = Paths.get(baseDir, serviceDir, type.name().toLowerCase(), fileName);
        if (Files.notExists(file)) {
            return;
        }

        Files.delete(file);
    }

    public enum FileTypes {
        IMAGE, FILE
    }
}