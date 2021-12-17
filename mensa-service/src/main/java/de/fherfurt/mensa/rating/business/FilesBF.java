package de.fherfurt.mensa.rating.business;

import de.fherfurt.mensa.rating.entity.FileRepository;

import java.io.IOException;
import java.util.Optional;

public class FilesBF {

    private final FileRepository fileRepository = new FileRepository();

    public void save(FileRepository.FileTypes type, String fileName, byte[] content) throws IOException {
        fileRepository.save(type, fileName, content);
    }

    public Optional<byte[]> findBy(FileRepository.FileTypes type, String fileName) throws IOException {
        return fileRepository.findBy(type, fileName);
    }

    public void delete(FileRepository.FileTypes type, String fileName) throws IOException {
        fileRepository.delete(type, fileName);
    }
}
