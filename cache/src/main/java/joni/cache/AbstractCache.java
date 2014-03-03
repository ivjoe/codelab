package joni.cache;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * AbstractCache
 * 
 * @author Jonatan Ivanov
 */
public abstract class AbstractCache<T> implements Cache<T> {
    private final CountDownLatch initLatch = new CountDownLatch(1);
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();

    private T content;

    /**
     * @see Cache#refresh()
     */
    @Override
    public void refresh() {
        final T newContent = getNewContent();
        if (newContent != null) {
            try {
                rwLock.writeLock().lock();
                content = newContent;
            }
            finally {
                rwLock.writeLock().unlock();
            }
            initLatch.countDown();
        }
    }

    /**
     * @see Cache#getContent()
     */
    @Override
    public T getContent() {
        T result = null;
        try {
            initLatch.await();
            rwLock.readLock().lock();
            result = content;
        }
        catch (final InterruptedException e) {
            // TODO: log
        }
        finally {
            rwLock.readLock().unlock();
        }

        return result;
    }

    /**
     * Returns the new content of the cache, please return an immutable type e.g.: @see Collections#unmodifiableCollection(java.util.Collection)
     * 
     * @return the new content of the cache
     */
    protected abstract T getNewContent();
}
