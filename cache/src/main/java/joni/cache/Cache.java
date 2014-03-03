package joni.cache;

/**
 * Cache
 * 
 * @author Jonatan Ivanov
 */
public interface Cache<T> {

    /**
     * Refresh the cache
     */
    void refresh();

    /**
     * @return cache content
     */
    T getContent();
}