package com.seulgi.util;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Book implements Comparable<Book> {

    private static final SecureRandom random = new SecureRandom();
    private String isbn;
    private Person author;
    private String title;

    public static Book getRandomBook() {
        Person randomPerson = new Person(createRandomString(),
            createRandomString(),
           random.nextInt(),
            (random.nextInt() % 2 == 0) ? Person.Sex.MALE : Person.Sex.FEMALE);

        return new Book(createRandomString(), randomPerson, createRandomString());
    }

    public static String createRandomString() {
        return new BigInteger(130, random).toString();
    }

    public Book(String isbn, Person author, String title) {
        this.isbn = isbn;
        this.author = author;
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public Person getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setAuthor(Person author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int compareTo(Book other) {
        return ComparisonChain.start()
            .compare(this.isbn, other.getIsbn())
            .compare(this.author, other.getAuthor())
            .compare(this.title, other.getTitle())
            .result();
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
            .add("ISBN", isbn)
            .add("Author", author)
            .add("Title", title)
            .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;

        Book book = (Book) o;

        if (!author.equals(book.author)) return false;
        if (!isbn.equals(book.isbn)) return false;
        if (!title.equals(book.title)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = isbn.hashCode();
        result = 31 * result + author.hashCode();
        result = 31 * result + title.hashCode();
        return result;
    }
}
