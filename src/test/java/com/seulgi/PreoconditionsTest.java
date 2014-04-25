package com.seulgi;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkElementIndex;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PreoconditionsTest {

  @Test(expected = NullPointerException.class)
  public void checkNotNullThrowsNullPointerException() {
    checkNotNull(null);
  }

  @Test
  public void checkNotNullReturnsValueIfNotNull() {
    String original = "hello";
    assertThat(original, is(checkNotNull(original)));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testCheckElementIndex() {
    List<String> list = Lists.newArrayList("A", "B", "C");
    checkElementIndex(5, list.size(), "Index out of bounds for list");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCheckArgument() {
    int someArg = 100;
    checkArgument(someArg <= 50, "Value can't be more than 50.");
  }
}
