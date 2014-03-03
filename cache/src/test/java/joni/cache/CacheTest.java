package joni.cache;

import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * CacheTest: not a real test, just a demo
 * 
 * @author Jonatan Ivanov
 */
public class CacheTest {
    private Cache<Map<String, Integer>> cache;

    @Before
    public void setUp() throws Exception {
        cache = new DummyCache();
    }

    @After
    public void tearDown() throws Exception {
        cache = null;
    }

    @Test
    public void test() {
        cache.refresh();
        Assert.assertNotNull(cache.getContent());
    }
}
