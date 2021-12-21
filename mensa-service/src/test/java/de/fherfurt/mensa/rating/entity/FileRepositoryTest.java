package de.fherfurt.mensa.rating.entity;

import de.fherfurt.mensa.core.errors.ConsumerWithException;
import de.fherfurt.mensa.rating.entity.FileSystemRepository.FileTypes;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Consumer;

import static de.fherfurt.mensa.TestTags.DOMAIN;
import static de.fherfurt.mensa.TestTags.PERSISTENCE;
import static de.fherfurt.mensa.TestTags.UNIT;

@Tags({
        @Tag(UNIT),
        @Tag(DOMAIN),
        @Tag(PERSISTENCE)
})
@ExtendWith(MockitoExtension.class)
class FileRepositoryTest {

    @Captor
    ArgumentCaptor<Path> filePath;

    @Test
    void shouldWriteFileToFSIfNotAlreadyExists() {
        prepareTest(ConsumerWithException.wrap(filesMock -> {
            // GIVEN
            filesMock.when(() -> Files.exists(Mockito.any(Path.class))).thenReturn(false);

            final FileSystemRepository repository = FileSystemRepository.of();

            final FileTypes type = FileTypes.IMAGE;
            final String fileName = "test.jpg";
            final byte[] content = "content".getBytes();

            // WHEN
            repository.save(type, fileName, content);

            // THEN
            filesMock.verify(() -> Files.write(filePath.capture(), Mockito.eq(content)), Mockito.times(1));
            Assertions.assertThat(filePath.getValue()).hasFileName(fileName);
        }));
    }

    @Test
    void shouldThrowExceptionIfFileToSaveAlreadyExists() {
        prepareTest(filesMock -> {
            // GIVEN
            filesMock.when(() -> Files.exists(Mockito.any(Path.class))).thenReturn(true);

            final FileSystemRepository repository = FileSystemRepository.of();

            final FileTypes type = FileTypes.IMAGE;
            final String fileName = "test.jpg";
            final byte[] content = "content".getBytes();

            // WHEN
            final Throwable result = Assertions.catchThrowable(() -> repository.save(type, fileName, content));

            // THEN
            Assertions
                    .assertThat(result)
                    .isInstanceOf(FileAlreadyExistsException.class)
                    .hasMessageContainingAll("Could not store file '", "'. Already exists");
        });
    }

    @Test
    void shouldFindFileIfExists() {
        prepareTest(ConsumerWithException.wrap(filesMock -> {
            // GIVEN
            filesMock.when(() -> Files.exists(Mockito.any(Path.class))).thenReturn(false);

            final FileSystemRepository repository = FileSystemRepository.of();

            final FileTypes type = FileTypes.IMAGE;
            final String fileName = "test.jpg";
            final byte[] expected = "content".getBytes();

            filesMock.when(() -> Files.readAllBytes(Mockito.any(Path.class))).thenReturn(expected);

            // WHEN
            Optional<byte[]> result = repository.findBy(type, fileName);

            // THEN
            filesMock.verify(() -> Files.readAllBytes(filePath.capture()), Mockito.times(1));
            Assertions.assertThat(filePath.getValue()).hasFileName(fileName);
            Assertions.assertThat(result).isPresent().get().isEqualTo(expected);
        }));
    }

    @Test
    void shouldFindNothingIfFileNotExists() {
        prepareTest(ConsumerWithException.wrap(filesMock -> {
            // GIVEN
            filesMock.when(() -> Files.notExists(Mockito.any(Path.class))).thenReturn(true);

            final FileSystemRepository repository = FileSystemRepository.of();

            final FileTypes type = FileTypes.IMAGE;
            final String fileName = "test.jpg";

            // WHEN
            Optional<byte[]> result = repository.findBy(type, fileName);

            // THEN
            filesMock.verify(() -> Files.readAllBytes(Mockito.any(Path.class)), Mockito.never());
            Assertions.assertThat(result).isEmpty();
        }));
    }

    @Test
    void shouldCallDeleteIfFileExists() {
        prepareTest(ConsumerWithException.wrap(filesMock -> {
            // GIVEN
            final FileSystemRepository repository = FileSystemRepository.of();

            final FileTypes type = FileTypes.IMAGE;
            final String fileName = "test.jpg";

            // WHEN
            repository.delete(type, fileName);

            // THEN
            filesMock.verify(() -> Files.delete(filePath.capture()), Mockito.times(1));
            Assertions.assertThat(filePath.getValue()).hasFileName(fileName);
        }));
    }

    @Test
    void shouldNotCallDeleteIfFileNotExists() {
        prepareTest(ConsumerWithException.wrap(filesMock -> {
            // GIVEN
            filesMock.when(() -> Files.notExists(Mockito.any(Path.class))).thenReturn(true);

            final FileSystemRepository repository = FileSystemRepository.of();

            final FileTypes type = FileTypes.IMAGE;
            final String fileName = "test.jpg";

            // WHEN
            repository.delete(type, fileName);

            // THEN
            filesMock.verify(() -> Files.delete(Mockito.any(Path.class)), Mockito.never());

        }));
    }

    private void prepareTest(Consumer<MockedStatic<Files>> testCase) {
        try (MockedStatic<Files> filesMock = Mockito.mockStatic(Files.class)) {
            // GIVEN
            filesMock.when(() -> Files.notExists(Mockito.any(Path.class))).thenReturn(false);
            filesMock.when(() -> Files.exists(Mockito.any(Path.class))).thenReturn(true);

            testCase.accept(filesMock);
        }
    }
}