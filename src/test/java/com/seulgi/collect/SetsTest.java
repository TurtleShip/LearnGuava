package com.seulgi.collect;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SetsTest {

    private Set<String> s0;
    private Set<String> s1;

    @Before
    public void setUp() {
        s0 = Sets.newHashSet("1", "2", "3");
        s1 = Sets.newHashSet("2", "3", "4");
    }

    @Test
    public void testDifference() {
        Set<String> diff0 = Sets.difference(s0, s1).immutableCopy();
        Set<String> diff1 = Sets.difference(s1, s0).immutableCopy();
        Set<String> expectedDiff0 = Sets.newHashSet("1");
        Set<String> expectedDiff1 = Sets.newHashSet("4");

        assertThat(diff0, is(expectedDiff0));
        assertThat(diff1, is(expectedDiff1));
    }

    @Test
    public void testSymmetricDifference() {
        /*
        The Sets.symmetricDifference method returns elements that are contained in one set or the other set,
        but not contained in both. The returned set is an unmodifiable view.
         */
        Set<String> symDiff = Sets.symmetricDifference(s0, s1);
        Set<String> expectedDiff = Sets.newHashSet("1", "4");

        assertThat(symDiff, is(expectedDiff));
    }

    @Test
    public void testIntersection() {
        Set<String> inter = Sets.intersection(s0, s1);
        Set<String> expected = Sets.newHashSet("2", "3");
        assertThat(inter, is(expected));
    }

    @Test
    public void testUnion() {
        Set<String> union = Sets.union(s0, s1);
        Set<String> expected = Sets.newHashSet("1", "2", "3", "4");

        assertThat(union, is(expected));
    }
}
