package db.repositories;

public abstract class EntityRepository <T> {
    private String collectionName;

    public EntityRepository(String collectionName) {
        this.setCollectionName(collectionName);
    }

    public String getCollectionName() {
        return collectionName;
    }

    private void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public boolean save(T t) {
        // return this.csvFileWriter.write(t.toString());
        return false;
    };

    // find All
    public T[] findAll() {
        // abstract
        // reflection
        // this.csvFileReader.readFile(this.collectionName + ".csv") <- MAE IT CONSTANT
        //
        return (T[]) new Object[0];
    }
}
