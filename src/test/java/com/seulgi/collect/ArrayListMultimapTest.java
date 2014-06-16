package com.seulgi.collect;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/*
    ArrayListMultimap is a map that uses ArrayList to store the values for the given key.
 */
public class ArrayListMultimapTest {

    @Test
    public void testCreate() {
        ArrayListMultimap<String, String> multimap = ArrayListMultimap.create();
        multimap.put("Foo", "1");
        multimap.put("Foo", "2");
        multimap.put("Foo", "3");
        List<String> fooList = Lists.newArrayList("1", "2", "3");

        assertThat(multimap.get("Foo"), is(fooList));
    }

    @Test
    public void testSameKeyValue() {
        // Note that list doesn't enforce its elements to be unique.

        ArrayListMultimap<String, String> multimap = ArrayListMultimap.create();
        multimap.put("Foo", "1");
        multimap.put("Foo", "2");
        multimap.put("Foo", "3");
        multimap.put("Foo", "3");
        multimap.put("Foo", "3");
        multimap.put("Foo", "3");
        List<String> fooList = Lists.newArrayList("1", "2", "3", "3", "3", "3");

        assertThat(multimap.get("Foo"), is(fooList));
    }

    @Test
    public void testSize() {
        ArrayListMultimap<String, String> multimap = ArrayListMultimap.create();
        multimap.put("Foo", "1");
        multimap.put("Foo", "2");
        multimap.put("Foo", "3");
        multimap.put("Foo", "3");
        multimap.put("Foo", "3");
        multimap.put("Foo", "3");

        assertThat(multimap.size(), is(6));
    }
}
