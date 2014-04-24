package com.seulgi;

import com.google.common.base.Strings;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

// Strings class provides a few handy utility methods for working with strings.
public class StringsTest {

  @Test
  public void stringsCanPad() {
    String expectedString = "";
    expectedString += "helloxxxxx";
    expectedString += "worldxxxxx";
    expectedString += "IamTenChar";
    expectedString += "Yoxxxxxxxx";

    String myString = "";
    myString += Strings.padEnd("hello", 10, 'x');
    myString += Strings.padEnd("world", 10, 'x');
    myString += Strings.padEnd("IamTenChar", 10, 'x');
    myString += Strings.padEnd("Yo", 10, 'x');

    assertThat(myString, is(expectedString));
  }

  @Test
  public void stringsConvertNullToEmpty() {
    assertThat(Strings.nullToEmpty(null), is(""));
    assertThat(Strings.nullToEmpty(""), is(""));
    assertThat(Strings.nullToEmpty("hello"), is("hello"));
  }

  @Test
  public void stringsConvertEmptyToNull() {
    assertThat(Strings.emptyToNull(null), nullValue());
    assertThat(Strings.emptyToNull(""), nullValue());
    assertThat(Strings.emptyToNull("hello"), is("hello"));
  }

  @Test
  public void stringsCanCheckIsNullOrEmpty() {
    assertThat(Strings.isNullOrEmpty(null), is(true));
    assertThat(Strings.isNullOrEmpty(""), is(true));
    assertThat(Strings.isNullOrEmpty("I am Seulgi"), is(false));
  }


}
