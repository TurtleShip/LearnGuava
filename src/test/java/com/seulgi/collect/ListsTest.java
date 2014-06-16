package com.seulgi.collect;

import com.google.common.collect.Lists;
import com.seulgi.util.Person;
import org.junit.Before;
import com.seulgi.util.Person.Sex;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ListsTest {

    List<Person> people;
    List<Person> youngPeople;
    List<Person> oldPeople;

    @Before
    public void setUp() {
        Person young0 = new Person("A", "A", 10, Sex.MALE);
        Person young1 = new Person("B", "B", 10, Sex.MALE);
        Person old0 = new Person("C", "C", 50, Sex.FEMALE);
        Person old1 = new Person("D", "D", 50, Sex.FEMALE);

        people = Lists.newArrayList(young0, young1, old0, old1);
        youngPeople = Lists.newArrayList(young0, young1);
        oldPeople = Lists.newArrayList(old0, old1);

    }

    @Test
    public void testPartition() {
        List< List<Person> > generations = Lists.partition(people, 2);
        assertThat(generations.get(0), is(youngPeople));
        assertThat(generations.get(1), is(oldPeople));
    }
}
