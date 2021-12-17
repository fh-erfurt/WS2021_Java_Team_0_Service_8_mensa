package de.fherfurt.mensa.core;

import de.fherfurt.mensa.core.persistence.errors.MissingPrimaryException;
import de.fherfurt.mensa.core.persistence.errors.ToManyPrimaryKeysException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class DatabaseTest {

    private final Database database = Database.newInstance();

    @AfterEach
    void afterEach() {
        database.clear();
    }

    @Test
    void shouldThrowExceptionForNotExistingPrimaryKey() {
        // GIVEN
        final TestEntityWithoutPK entity = new TestEntityWithoutPK(1);

        // WHEN
        final Throwable result = Assertions.catchThrowable(() -> database.save(entity));

        // THEN
        Assertions.assertThat(result).isInstanceOf(MissingPrimaryException.class);
        Assertions.assertThat(result.getLocalizedMessage())
                .isEqualTo("Could not find a primary key on type 'TestEntityWithoutPK'.");
    }

    @Test
    void shouldThrowExceptionForToManyPrimaryKeys() {
        // GIVEN
        final TestEntityWithToManyPK entity = new TestEntityWithToManyPK(1, 2);

        // WHEN
        final Throwable result = Assertions.catchThrowable(() -> database.save(entity));

        // THEN
        Assertions.assertThat(result).isInstanceOf(ToManyPrimaryKeysException.class);
        Assertions.assertThat(result.getLocalizedMessage())
                .isEqualTo("More then one primary key found on type 'TestEntityWithToManyPK'.");
    }

    @Test
    void shouldStoreNewEntityAndUpdatePK() {
        // GIVEN
        final TestEntity entity = new TestEntity(99);

        // WHEN
        database.save(entity);

        // THEN
        Assertions.assertThat(database.count(TestEntity.class)).isEqualTo(1);
        Assertions.assertThat(entity.id).isEqualTo(1);
    }

    @Test
    void shouldFindEntityForId() {
        // GIVEN
        final TestEntity entity1 = new TestEntity(97);
        database.save(entity1);
        final TestEntity entity2 = new TestEntity(98);
        database.save(entity2);
        final TestEntity entity3 = new TestEntity(99);
        database.save(entity3);

        // WHEN
        Optional<TestEntity> result = database.findBy(TestEntity.class, 2);

        // THEN
        Assertions.assertThat(database.count(TestEntity.class)).isEqualTo(3);
        Assertions.assertThat(result).isPresent().get()
                .hasFieldOrPropertyWithValue("value", 98)
                .hasFieldOrPropertyWithValue("id", 2);
    }

    @Test
    void shouldFindNothingForNotExistingId() {
        // GIVEN
        final TestEntity entity1 = new TestEntity(97);
        database.save(entity1);
        final TestEntity entity2 = new TestEntity(98);
        database.save(entity2);
        final TestEntity entity3 = new TestEntity(99);
        database.save(entity3);

        // WHEN
        Optional<TestEntity> result = database.findBy(TestEntity.class, 4);

        // THEN
        Assertions.assertThat(database.count(TestEntity.class)).isEqualTo(3);
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    void shouldDeleteExistingEntity() {
        // GIVEN
        final TestEntity entity = new TestEntity(99);
        database.save(entity);

        // WHEN
        database.delete(entity);

        // THEN
        Assertions.assertThat(database.count(TestEntity.class)).isEqualTo(0);
    }

    @Test
    void shouldNotFailIfDeleteNotExistingEntity() {
        // GIVEN
        final TestEntity entity = new TestEntity(99);

        // WHEN
        database.delete(entity);

        // THEN
        Assertions.assertThat(database.count(TestEntity.class)).isEqualTo(0);
    }

    @Test
    void shouldInsertNestedEntitiesViaMapper() {
        // GIVEN
        final TestEntityWithNested entity = new TestEntityWithNested(98, new TestEntity(99));
        database.addInsertMapper(TestEntityWithNested.class, (db, object) -> db.save(object.getNested()));

        // WHEN
        database.save(entity);

        // THEN
        Assertions.assertThat(database.count(TestEntity.class)).isOne();
        Assertions.assertThat(database.count(TestEntityWithNested.class)).isOne();

        Assertions.assertThat(entity.id).isOne();
        Assertions.assertThat(entity.getNested().id).isOne();
    }

    @Test
    void shouldDeleteNestedEntitiesViaMapper() {
        // GIVEN
        final TestEntityWithNested entity = new TestEntityWithNested(98, new TestEntity(99));
        database.addInsertMapper(TestEntityWithNested.class, (db, object) -> db.save(object.getNested()));
        database.addDeleteMapper(TestEntityWithNested.class, (db, object) -> db.delete(object.getNested()));

        database.save(entity);
        int testEntityCountBeforeDelete = database.count(TestEntity.class);
        int testEntityWithNestedCountBeforeDelete = database.count(TestEntityWithNested.class);

        // WHEN
        database.delete(entity);

        // THEN
        Assertions.assertThat(database.count(TestEntity.class))
                .isNotEqualTo(testEntityCountBeforeDelete)
                .isZero();
        Assertions.assertThat(database.count(TestEntityWithNested.class))
                .isNotEqualTo(testEntityWithNestedCountBeforeDelete)
                .isZero();
    }

    public static class TestEntity {

        @Id
        private int id;

        private final int value;

        public TestEntity(final int value) {
            this.id = 0;
            this.value = value;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getValue() {
            return value;
        }
    }

    public static class TestEntityWithNested {

        @Id
        private int id;

        private final int value;

        private final TestEntity nested;

        public TestEntityWithNested(final int value, final TestEntity nested) {
            this.id = 0;
            this.value = value;
            this.nested = nested;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getValue() {
            return value;
        }

        public TestEntity getNested() {
            return nested;
        }
    }

    public record TestEntityWithoutPK(int value) {
    }

    public static class TestEntityWithToManyPK {

        @Id
        private int id1;

        @Id
        private int id2;

        public TestEntityWithToManyPK(int id1, int id2) {
            this.id1 = id1;
            this.id2 = id2;
        }

        public void setId1(int id1) {
            this.id1 = id1;
        }

        public void setId2(int id2) {
            this.id2 = id2;
        }
    }
}