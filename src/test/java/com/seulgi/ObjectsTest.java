package com.seulgi;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ObjectsTest {
  private class Person implements Comparable<Person> {
    private String firstName;
    private String lastName;

    public Person() {
      firstName = lastName = null;
    }

    public Person(String firstName, String lastName) {
      this.firstName = firstName;
      this.lastName = lastName;
    }

    @Override
    public String toString() {
      return Objects.toStringHelper(this)
          .add("first name", firstName)
          .add("last name", lastName).toString();
    }

    @Override
    public int compareTo(Person other) {
      return ComparisonChain.start()
          .compare(this.firstName, other.firstName)
          .compare(this.lastName, other.lastName)
          .result();
    }
  }

  class Book implements Comparable<Book> {

    private Person author;
    private String title;
    private String publisher;
    private double price;

    public void setAuthor(Person author) {
      this.author = author;
    }

    public void setTitle(String title) {
      this.title = title;
    }

    public void setPublisher(String publisher) {
      this.publisher = publisher;
    }

    public void setPrice(double price) {
      this.price = price;
    }

    public Person getAuthor() {
      return author;
    }

    public String getTitle() {
      return title;
    }

    public String getPublisher() {
      return publisher;
    }

    public double getPrice() {
      return price;
    }


    // Notice how you can omit null values
    @Override
    public String toString() {
      return Objects.toStringHelper(this)
          .omitNullValues()
          .add("title", title)
          .add("author", author)
          .add("publisher", publisher)
          .add("price", price).toString();
    }

    /*
      Couldn't think of a good way to test this function.
      But please note that Objects class provides a handy
      function to generate a hashcode.
     */
    @Override
    public int hashCode() {
      return Objects.hashCode(title, author, publisher, price);
    }

    /*
      Objects class also provides a cool method to implement
      compareTo function.
      This will come quite handy when we have a data container
      that needs to be compared frequently. For instance,
      an object that contains 3d coordinates, temperature, and
      color.
     */
    @Override
    public int compareTo(Book other) {

      /*
        The ComparisonChain class will stop making comparisons with
        the first non-zero result, the only way a zero will be returned is
        if all comparisons result in a zero.
       */
      return ComparisonChain.start()
          .compare(this.title, other.getTitle())
          .compare(this.author, other.getAuthor())
          .compare(this.publisher, other.getPublisher())
          .compare(this.price, other.getPrice())
          .result();
    }

  }

  @Test
  public void testObjectsToStringHelper() {
    Book book = new Book();
    Person author = new Person("Seulgi", "Kim");
    book.setAuthor(author);
    book.setTitle("Slice and Dice");
    book.setPrice(25.05);

    String stringBeforePublisher
        = "Book{title=Slice and Dice, " +
        "author=Person{first name=Seulgi, last name=Kim}, " +
        "price=25.05}";

    assertThat(stringBeforePublisher, is(book.toString()));

    book.setPublisher("Hello world");
    String stringAfterPublisher
        = "Book" +
        "{title=Slice and Dice, " +
        "author=Person{first name=Seulgi, last name=Kim}, " +
        "publisher=Hello world, price=25.05}";

    assertThat(stringAfterPublisher, is(book.toString()));
  }

  @Test
  public void testFirstNonNull() {
    /*
      We can use firstNonNull as a way to provide default value.
      Optional.or(defaultValue) is more intuitive, but I guess
      we can use firstNonNull when the code base we are working
      with doesn't yet use Optional.
     */

    String defaultValue = "default value";
    String value = Objects.firstNonNull(null, defaultValue);
    assertThat(value, is(defaultValue));
  }

  @Test(expected = NullPointerException.class)
  public void firstNonNullCanThrowException() {
    /*
      Note that if both the first and the second values are null,
      then NullPointerException will be thrown.
     */
    Objects.firstNonNull(null, null);
  }

}
