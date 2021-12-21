package de.fherfurt.mensa.rating.business;

import de.fherfurt.mensa.core.errors.ConsumerWithException;
import de.fherfurt.mensa.rating.entity.FileRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.function.Consumer;

import static de.fherfurt.mensa.TestTags.BUSINESS;
import static de.fherfurt.mensa.TestTags.DOMAIN;
import static de.fherfurt.mensa.TestTags.UNIT;
import static de.fherfurt.mensa.rating.entity.FileRepository.FileTypes.IMAGE;

@Tags({
        @Tag(UNIT),
        @Tag(DOMAIN),
        @Tag(BUSINESS)
})
@ExtendWith(MockitoExtension.class)
class FilesBFTest {

    @Mock
    FileRepository repository;

    @Test
    void shouldCallRepositoryForSaveAFile() {
        prepareTest(ConsumerWithException.wrap(repoMock -> {
            // GIVEN
            final FileRepository.FileTypes type = IMAGE;
            final String fileName = "test.jpg";
            final byte[] content = "content".getBytes();
            final boolean newImage = true;

            final FilesBF facade = FilesBF.of();

            // WHEN
            facade.save(type, fileName, content, newImage);

            // THEN
            Mockito.verify(repository, Mockito.times(1)).save(type, fileName, content);
        }));
    }

    @Test
    void shouldCallRepositoryForSaveAFileAndDeleteIfUpdate() {
        prepareTest(ConsumerWithException.wrap(repoMock -> {
            // GIVEN
            final FileRepository.FileTypes type = IMAGE;
            final String fileName = "test.jpg";
            final byte[] content = "content".getBytes();
            final boolean newImage = false;

            final FilesBF facade = FilesBF.of();

            // WHEN
            facade.save(type, fileName, content, newImage);

            // THEN
            Mockito.verify(repository, Mockito.times(1)).delete(type, fileName);
            Mockito.verify(repository, Mockito.times(1)).save(type, fileName, content);
        }));
    }

    @Test
    void shouldCallRepositoryForFindFileByTypeAndNameAndReturnFound() {
        prepareTest(ConsumerWithException.wrap(repoMock -> {
            // GIVEN
            final FileRepository.FileTypes type = IMAGE;
            final String fileName = "test.jpg";
            final byte[] content = "content".getBytes();

            Mockito.when(repository.findBy(type, fileName)).thenReturn(Optional.of(content));

            final FilesBF facade = FilesBF.of();

            // WHEN
            final Optional<byte[]> result = facade.findBy(type, fileName);

            // THEN
            Mockito.verify(repository, Mockito.times(1)).findBy(type, fileName);
            Assertions.assertThat(result).isPresent().get().isEqualTo(content);
        }));
    }

    @Test
    void shouldCallRepositoryForFindFileByTypeAndNameAndReturnEmptyIfNotFound() {
        prepareTest(ConsumerWithException.wrap(repoMock -> {
            // GIVEN
            final FileRepository.FileTypes type = IMAGE;
            final String fileName = "test.jpg";

            Mockito.when(repository.findBy(type, fileName)).thenReturn(Optional.empty());

            final FilesBF facade = FilesBF.of();

            // WHEN
            final Optional<byte[]> result = facade.findBy(type, fileName);

            // THEN
            Mockito.verify(repository, Mockito.times(1)).findBy(type, fileName);
            Assertions.assertThat(result).isEmpty();
        }));
    }

    @Test
    void shouldCallRepositoryForDeleteFileByTypeAndName() {
        prepareTest(ConsumerWithException.wrap(repoMock -> {
            // GIVEN
            final FileRepository.FileTypes type = IMAGE;
            final String fileName = "test.jpg";

            final FilesBF facade = FilesBF.of();

            // WHEN
            facade.delete(type, fileName);

            // THEN
            Mockito.verify(repository, Mockito.times(1)).delete(type, fileName);
        }));
    }

    private void prepareTest(Consumer<MockedStatic<FileRepository>> testCase) {
        try (MockedStatic<FileRepository> filesMock = Mockito.mockStatic(FileRepository.class)) {
            // GIVEN
            filesMock.when(FileRepository::of).thenReturn(repository);

            testCase.accept(filesMock);
        }
    }
}