package com.seulgi.base;

import com.google.common.base.CharMatcher;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/*
  The CharMatcher class provides functionality for working with characters
  based on the presence or absence of a type of character or a range
  of characters. The methods in the CharMatcher class make formatting
  and working with text very simple.
 */
public class CharMatcherTest {

  @Test
  public void charMatcherCanRemoveNewLine() {
    String originalString = "H\nE\nL\nL\nO\n";
    String expectedString = "HELLO";
    String returnedString = CharMatcher.BREAKING_WHITESPACE.removeFrom(originalString);
    assertThat(returnedString, is(expectedString));
  }

  @Test
  public void charMatcherCanReplaceNewLine() {
    String originalString = "H\nE\nL\nL\nO";
    String expectedString = "H E L L O";
    String returnedString =
        CharMatcher.BREAKING_WHITESPACE.replaceFrom(originalString, ' ');
    assertThat(returnedString, is(expectedString));
  }

  @Test
  public void charMatcherCanCollapseWhiteSpace() {
    String originalString = "H     E  L  L         O";
    String expectedString = "H E L L O";
    String returnedString = CharMatcher.WHITESPACE.collapseFrom(originalString, ' ');
    assertThat(returnedString, is(expectedString));
  }

  @Test
  public void charMatcherCanTrimAndCollapseWhiteSpace() {
    String originalString = "      H     E  L  L         O       ";
    String expectedString = "H E L L O";
    String returnedString = CharMatcher.WHITESPACE.trimAndCollapseFrom(originalString, ' ');
    assertThat(returnedString, is(expectedString));
  }

  @Test
  public void charMatcherCanRetainFrom() {
    String lettersAndNumbers = "hello111World111";
    String expected = "helloWorld";
    String returned = CharMatcher.JAVA_LETTER.retainFrom(lettersAndNumbers);
    assertThat(returned, is(expected));
  }

  @Test
  public void charMatcherCanBeCombined() {
    String jumbledWords = "Hello#$111 I am%%% your ###124512friend";
    String expected = "Hello I am your friend";
    CharMatcher charMatcher = CharMatcher.JAVA_LETTER.or(CharMatcher.WHITESPACE);
    String returned = charMatcher.retainFrom(jumbledWords);
    assertThat(returned, is(expected));
  }

}
