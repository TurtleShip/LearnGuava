package com.seulgi.collect;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class HashMultimapTest {

    HashMultimap<String, String> hashMultimap;

    @Before
    public void setUp() {
        hashMultimap = HashMultimap.create();
        hashMultimap.put("Foo", "1");
        hashMultimap.put("Foo", "2");
        hashMultimap.put("Foo", "3");
        hashMultimap.put("Foo", "3");
        hashMultimap.put("Foo", "3");
        hashMultimap.put("Foo", "3");
    }

    @Test
    public void testSameKeyValue() {
        Set<String> expected = Sets.newHashSet("1", "2", "3");
        System.out.println("Hey");
        assertThat(hashMultimap.get("Foo"), is(expected));
    }

    @Test
    public void testSize() {
        assertThat(hashMultimap.size(), is(3));
    }
}
