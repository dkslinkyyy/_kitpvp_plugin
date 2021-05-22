package eu.tribusmc.tribuskitpvp.storage;

import eu.tribusmc.tribuskitpvp.db.SQLFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class StorageAbstract <T> {

    protected final List<T> cachedElements;

    public StorageAbstract(SQLFactory sqlFactory) {
        cachedElements = new ArrayList<>();
    }

    public abstract T[] getAll();


    public boolean isCached(T element) {
        return cachedElements.contains(element);
    }

    public void addToCache(T element) {
        cachedElements.add(element);
    }

    public void flush(T element) {
        cachedElements.remove(element);
    }
}
