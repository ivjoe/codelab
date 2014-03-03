package joni.cache;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * DummyCache: not a real implementation, just a demo
 * 
 * @author Jonatan Ivanov
 */
public class DummyCache extends AbstractCache<Map<String, Integer>> {

    @Override
    protected Map<String, Integer> getNewContent() {
        final Map<String, Integer> newContent = new HashMap<>();
        newContent.put(UUID.randomUUID().toString(), Integer.valueOf((int) (Math.random() * 100)));

        return Collections.unmodifiableMap(newContent);
    }
}
