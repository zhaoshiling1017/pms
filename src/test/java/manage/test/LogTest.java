package manage.test;

import com.google.common.base.*;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ComparisonChain;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.*;
import static com.google.common.base.Predicates.*;
import static com.google.common.collect.Iterables.*;
import static com.google.common.collect.Lists.*;
import static com.google.common.collect.Maps.*;
import static com.google.common.collect.Sets.*;


/**
 * Created by lenzhao on 17-1-11.
 */
public class LogTest {
    private static final Logger logger = LoggerFactory.getLogger(LogTest.class);

    @Test
    public void printLog() {
        logger.info("Processing trade with id: {} and symbol : {} ", 1, "c");
    }

    @Test
    public void OptionNull() {
        Optional<Integer> possible = Optional.fromNullable(null);
        //assert  possible.isPresent() == true;
        //assert possible.orNull() == null;
    }

    @Test
    public void dateTest() {
        logger.info("nanotime: {}, currentTime: {}", System.nanoTime(), System.currentTimeMillis());
    }

    @Test
    public void Precondition() {
        int a = 1, b = 2;
        //assert checkNotNull(null) == null;
        int c = checkNotNull(b);
        assert c == 2;
        //checkArgument(a > b, "Expected a > b, but %s < %s ", a, b);
    }

    @Test
    public void objectMethod() {
        assert Objects.equal(null, null) == true;
        //logger.info(Objects.hash("name", "value") + "");
        //logger.info(com.google.common.base.Objects.hashCode("name", "value") + "");
        Person p1 = new Person("zhao", "len", 100010);
        Person p2 = new Person("zhao", "len", 100001);
        logger.info(p1.compareTo(p2) + "");
    }

    public static class Person implements Comparable<Person> {
        private String lastName;
        private String firstName;
        private int zipCode;

        public Person(String lastName, String firstName, int zipCode) {
            this.lastName = lastName;
            this.firstName = firstName;
            this.zipCode = zipCode;
        }

        @Override
        public int compareTo(Person o) {
            return ComparisonChain.start().compare(this.lastName, o.lastName)
                    .compare(this.firstName, o.firstName)
                    .compare(this.zipCode, o.zipCode).result();
        }

    }

    @Test
    public void classTest() {
        logger.info("class name: {}.", LogTest.class.getSimpleName());
    }

    @Test
    public void stringTest() {
        Assert.assertEquals(Strings.commonPrefix("aav", "aaad"), "aa");
    }

    @Test
    public void charTest() {
        Assert.assertTrue(CharMatcher.is('a').matchesNoneOf("a"));
    }

    @Test
    public void cacheTest() {
        CacheLoader<String, String> checkedLoader = new CacheLoader<String, String>() {
            @Override
            public String load(String key) throws Exception {
                return key;//load from disk
            }
        };
        LoadingCache<String, String> cache = CacheBuilder.newBuilder().build(checkedLoader);
        //logger.info(cache.getUnchecked("name"));
        //logger.info(cache.size()+"");
        //logger.info(cache.getUnchecked("name"));
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "lenzhao");
        map.put("age", "29");
        map.put("city", "beijing");
        cache.putAll(map);
        logger.info(cache.size()+"");
        logger.info(cache.getUnchecked("name"));
    }
}
