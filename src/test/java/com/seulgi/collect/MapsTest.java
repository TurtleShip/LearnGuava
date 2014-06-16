package com.seulgi.collect;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.seulgi.util.Book;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class MapsTest {

    List<Book> books;

    @Before
    public void setUp() {
        books = Lists.newArrayList();
        books.add(Book.getRandomBook());
        books.add(Book.getRandomBook());
        books.add(Book.getRandomBook());
    }

    @Test
    public void testUniqueIndex() {
        /*
            The Maps.uniqueIndex method takes either an iterable or
            iterator of a give type and Function as arguments.
            The elements represented by the iterator/iterable become the values
            for the map, while Function is applied to each element and generates
            the key for that element.
         */
        ImmutableMap<String, Book> bookMap =
            Maps.uniqueIndex(books.iterator(),
                new Function<Book, String>() {
                    @Override
                    public String apply(Book input) {
                        return input.getIsbn();
                    }
                }
            );

        ImmutableMap.Builder<String, Book> builder = ImmutableMap.builder();
        for (Book book : books) {
            builder.put(book.getIsbn(), book);
        }

        assertThat(bookMap, is(builder.build()));
    }

    @Test
    public void testAsMap() {
        /*
            While the Maps.uniqueIndex method uses Function to generate keys from
            the given values, the Maps.asMap method does the inversion operation.
            The Maps.asMap method takes a set of objects to be used as keys,
            and Function is applied to each key object to generate the value for
            entry into a map instance.

            There is another method, Maps.toMap, that takes the same arguments with
            the difference begin ImmutableMap is returned instead of a view of the map.
            The significance of this is that the map returned from the Maps.asMap
            method would reflect any changes made to the original map, and the map
            returned from the Maps.toMap method would remain unchanged from changes
            to the original map.
         */
        HashSet<Book> myBooks = Sets.newHashSet();
        myBooks.add(Book.getRandomBook());
        myBooks.add(Book.getRandomBook());
        myBooks.add(Book.getRandomBook());

        Map<Book, String> myMap = Maps.asMap(myBooks,
            new Function<Book, String>() {

                @Override
                public String apply(Book input) {
                    return input.toString();
                }
            });

        Map<Book, String> immutableMap = Maps.toMap(myBooks,
            new Function<Book, String>() {

                @Override
                public String apply(Book input) {
                    return input.toString();
                }
            });

        assertThat(myMap, is(immutableMap));

        // this change should only affect myMap
        ImmutableMap oldCopy = ImmutableMap.copyOf(myMap);
        myBooks.add(Book.getRandomBook());
        assertThat(oldCopy.equals(ImmutableMap.copyOf(myMap)), is(false));
        assertThat(ImmutableMap.copyOf(immutableMap), is(oldCopy));
    }


}
