package de.fherfurt.persons.transfer.objects;

/**
 * Represents the data view of persons for the Module Mensa. It is used to allow the access to data that is used for
 * showing comment metadata.
 */
public class MensaPerson {
    private int id;
    private String alias;
    private String imageUrl;

    public MensaPerson(int id, String alias, String imageUrl) {
        this.id = id;
        this.alias = alias;
        this.imageUrl = imageUrl;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public String getAlias() {
        return alias;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
