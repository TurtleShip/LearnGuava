package com.seulgi.collections;

import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SetsTest {

    @Test
    public void testDifference() {
        Set<String> s0 = Sets.newHashSet("1", "2", "3");
        Set<String> s1 = Sets.newHashSet("2", "3", "4");
        Set<String> diff0 = Sets.difference(s0, s1).immutableCopy();
        Set<String> diff1 = Sets.difference(s1, s0).immutableCopy();
        Set<String> expectedDiff0 = Sets.newHashSet("1");
        Set<String> expectedDiff1 = Sets.newHashSet("4");

        // TODO: Find a better way to compare two sets
        assertThat(expectedDiff0.containsAll(diff0) && diff0.containsAll(expectedDiff0), is(true));
        assertThat(expectedDiff1.containsAll(diff1) && diff1.containsAll(expectedDiff1), is(true));
    }
}
