package com.seulgi.collections;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.*;
import com.seulgi.util.Person;
import com.seulgi.util.Person.Sex;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


public class FluentIterableTest {
    private Person james;
    private Person tommy;
    private Person laura;
    private Person karen;
    private Iterable<Person> people;

    @Before
    public void setUp() {
        james = new Person("James", "Douglas", 50, Sex.MALE);
        tommy = new Person("Tommy", "Salesman", 30, Sex.MALE);
        laura = new Person("Laura", "Davies", 34, Sex.FEMALE);
        karen = new Person("Karen", "Zhao", 20, Sex.FEMALE);
        people = Lists.newArrayList(james, tommy, laura, karen);
    }

    @Test
    public void testFilter() throws Exception {

        Iterable<Person> youngPeople =
            FluentIterable.from(people).filter(new Predicate<Person>() {
                @Override
                public boolean apply(Person input) {
                    return input.getAge() <= 30;
                }
            });

        assertThat(Iterables.contains(youngPeople, tommy), is(true));
        assertThat(Iterables.contains(youngPeople, karen), is(true));
    }

    @Test
    public void testTransform() throws Exception {

        List<String> firstNames = FluentIterable
            .from(people)
            .transform(new Function<Person, String>() {
                @Override
                public String apply(Person input) {
                    return input.getFirstName();
                }
            }).toList();

        List<String> expectedOutput = Lists.newArrayList();
        for (Person person : people) {
            expectedOutput.add(person.getFirstName());
        }
        assertThat(firstNames, is(expectedOutput));

        List<String> sortedFirstNames = FluentIterable
            .from(people)
            .transform(new Function<Person, String>() {
                @Override
                public String apply(Person input) {
                    return input.getFirstName();
                }
            }).toSortedList(Ordering.natural());
        Collections.sort(expectedOutput);

        assertThat(sortedFirstNames, is(expectedOutput));

    }


}
